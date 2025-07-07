package org.example.manager;

import jakarta.persistence.*;
import org.example.exceptions.InvalidInputException;
import org.example.exceptions.ReservationException;
import org.example.exceptions.SpaceUnavailableException;
import org.example.model.Reservation;
import org.example.model.Space;
import org.example.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class CoworkingManager {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void addUser(String name, boolean isAdmin) throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("User name cannot be empty.");
        }
        if (findUserByName(name).isPresent()) {
            throw new InvalidInputException("User with name '" + name + "' already exists.");
        }
        User newUser = new User(name, isAdmin);
        em.persist(newUser);
    }

    public Optional<User> findUserByName(String name) {
        try {
            User user = em.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional
    public void addSpace(String userName, String type, BigDecimal price) throws InvalidInputException {
        User user = findUserByName(userName)
                .orElseThrow(() -> new InvalidInputException("User not found: " + userName));

        if (!user.isAdmin()) {
            throw new InvalidInputException("Only admins can add spaces.");
        }

        if (type == null || type.trim().isEmpty()) {
            throw new InvalidInputException("Space type cannot be empty.");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidInputException("Price must be positive.");
        }

        Space newSpace = new Space(type, price, true);
        em.persist(newSpace);
    }

    public Optional<Space> findSpaceById(int id) {
        return Optional.ofNullable(em.find(Space.class, id));
    }

    @Transactional
    public void updateSpaceAvailability(int spaceId, boolean available) throws SpaceUnavailableException {
        Space space = findSpaceById(spaceId).orElseThrow(() -> new SpaceUnavailableException("Space not found"));
        space.setAvailable(available);
        em.merge(space);
    }

    @Transactional
    public void makeReservation(String userName, int spaceId, String date, String time, int numberOfDays)
            throws InvalidInputException, SpaceUnavailableException, ReservationException {

        if (date == null || date.trim().isEmpty() || time == null || time.trim().isEmpty() || numberOfDays <= 0) {
            throw new InvalidInputException("Reservation date, time, and number of days cannot be empty/negative.");
        }

        User user = findUserByName(userName)
                .orElseThrow(() -> new InvalidInputException("User not found: " + userName));
        Space space = findSpaceById(spaceId)
                .orElseThrow(() -> new SpaceUnavailableException("Space not found with ID: " + spaceId));

        if (!space.isAvailable()) {
            throw new SpaceUnavailableException("Space with ID " + spaceId + " is currently unavailable.");
        }

        LocalDate reservationDate = LocalDate.parse(date);
        LocalTime reservationTime = LocalTime.parse(time);
        LocalDate endDate = reservationDate.plusDays(numberOfDays - 1);

        List<Reservation> overlappingReservations = em.createQuery(
                        "SELECT r FROM Reservation r WHERE r.space.id = :spaceId AND " +
                                "((r.reservationDate <= :newEndDate AND r.endDate >= :newStartDate))",
                        Reservation.class)
                .setParameter("spaceId", spaceId)
                .setParameter("newStartDate", reservationDate)
                .setParameter("newEndDate", endDate)
                .getResultList();

        if (!overlappingReservations.isEmpty()) {
            throw new ReservationException("Space is already booked for an overlapping period.");
        }

        Reservation newReservation = new Reservation(user, space, reservationDate, reservationTime, numberOfDays);
        em.persist(newReservation);
    }

    @Transactional
    public void cancelReservation(int reservationId) throws ReservationException {
        Reservation reservation = em.find(Reservation.class, reservationId);
        if (reservation == null) {
            throw new ReservationException("Reservation not found.");
        }
        em.remove(reservation);
    }

    public BigDecimal calculateTotalCost(int spaceId, int days) throws InvalidInputException, SpaceUnavailableException {
        if (days <= 0) {
            throw new InvalidInputException("Number of days must be positive.");
        }
        Space space = findSpaceById(spaceId).orElseThrow(() -> new SpaceUnavailableException("Space not found"));
        return space.getPrice().multiply(BigDecimal.valueOf(days));
    }

    public List<Reservation> getAllReservations() {
        return em.createQuery("SELECT r FROM Reservation r", Reservation.class).getResultList();
    }

    public List<Reservation> getUserReservations(String userName) throws InvalidInputException {
        if (userName == null || userName.trim().isEmpty()) {
            throw new InvalidInputException("The user name cannot be empty!");
        }
        User user = findUserByName(userName).orElseThrow(() -> new InvalidInputException("User not found"));
        return em.createQuery("SELECT r FROM Reservation r WHERE r.user = :user", Reservation.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<Space> getSpaces() {
        return em.createQuery("SELECT s FROM Space s", Space.class).getResultList();
    }
}
