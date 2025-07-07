package org.example.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "space_id", nullable = false)
    private Space space;

    @Column(name = "reservation_date", nullable = false)
    private LocalDate reservationDate;

    @Column(name = "reservation_time", nullable = false)
    private LocalTime reservationTime;

    @Column(name = "number_of_days", nullable = false)
    private int numberOfDays;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    public Reservation() {}

    public Reservation(User user, Space space, LocalDate reservationDate, LocalTime reservationTime, int numberOfDays) {
        this.user = user;
        this.space = space;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.numberOfDays = numberOfDays;
        this.endDate = reservationDate.plusDays(numberOfDays - 1);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public LocalTime getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(LocalTime reservationTime) {
        this.reservationTime = reservationTime;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", user=" + user +
                ", space=" + space +
                ", reservationDate=" + reservationDate +
                ", reservationTime=" + reservationTime +
                ", numberOfDays=" + numberOfDays +
                ", endDate=" + endDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id && numberOfDays == that.numberOfDays && Objects.equals(user, that.user) && Objects.equals(space, that.space) && Objects.equals(reservationDate, that.reservationDate) && Objects.equals(reservationTime, that.reservationTime) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, space, reservationDate, reservationTime, numberOfDays, endDate);
    }
}
