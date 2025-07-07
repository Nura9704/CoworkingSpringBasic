package org.example;

import org.example.config.AppConfig;
import org.example.exceptions.InvalidInputException;
import org.example.exceptions.ReservationException;
import org.example.exceptions.SpaceUnavailableException;
import org.example.manager.CoworkingManager;
import org.example.model.Space;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CoworkingManager manager = context.getBean(CoworkingManager.class);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Coworking Reservation System!");

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Add user");
            System.out.println("2. Add space");
            System.out.println("3. List all spaces");
            System.out.println("4. Make reservation");
            System.out.println("5. View all reservations");
            System.out.println("6. Cancel reservation");
            System.out.println("7. Update space availability");
            System.out.println("8. View user's reservations");
            System.out.println("9. Calculate total cost");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1" -> {
                        System.out.print("Enter user name: ");
                        String name = scanner.nextLine();
                        System.out.print("Is admin (true/false): ");
                        boolean isAdmin = Boolean.parseBoolean(scanner.nextLine());
                        manager.addUser(name, isAdmin);
                        System.out.println("User added successfully.");
                    }
                    case "2" -> {
                        System.out.print("Enter your user name (admin only): ");
                        String adminName = scanner.nextLine();

                        System.out.print("Enter space type: ");
                        String type = scanner.nextLine();

                        BigDecimal price;
                        try {
                            System.out.print("Enter price: ");
                            price = new BigDecimal(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid price format.");
                            break;
                        }

                        try {
                            manager.addSpace(adminName, type, price);
                            System.out.println("Space added successfully.");
                        } catch (InvalidInputException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    case "3" -> {
                        List<Space> spaces = manager.getSpaces();
                        if (spaces.isEmpty()) {
                            System.out.println("No spaces found.");
                        } else {
                            System.out.println("Available spaces:");
                            spaces.forEach(System.out::println);
                        }
                    }
                    case "4" -> {
                        System.out.print("Enter user name: ");
                        String userName = scanner.nextLine();

                        int spaceId;
                        try {
                            System.out.print("Enter space ID: ");
                            spaceId = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid space ID.");
                            break;
                        }

                        System.out.print("Enter reservation date (YYYY-MM-DD): ");
                        String date = scanner.nextLine();

                        System.out.print("Enter reservation time (HH:MM): ");
                        String time = scanner.nextLine();

                        int days;
                        try {
                            System.out.print("Enter number of days: ");
                            days = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid number of days.");
                            break;
                        }

                        manager.makeReservation(userName, spaceId, date, time, days);
                        System.out.println("Reservation made successfully.");
                    }
                    case "5" -> {
                        System.out.println("All reservations:");
                        manager.getAllReservations().forEach(System.out::println);
                    }
                    case "6" -> {
                        int reservationId;
                        try {
                            System.out.print("Enter reservation ID to cancel: ");
                            reservationId = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid reservation ID.");
                            break;
                        }
                        manager.cancelReservation(reservationId);
                        System.out.println("Reservation cancelled successfully.");
                    }
                    case "7" -> {
                        System.out.print("Enter space ID to update: ");
                        int spaceId;
                        try {
                            spaceId = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid space ID.");
                            break;
                        }
                        System.out.print("Set availability (true/false): ");
                        boolean available = Boolean.parseBoolean(scanner.nextLine());

                        manager.updateSpaceAvailability(spaceId, available);
                        System.out.println("Space availability updated.");
                    }
                    case "8" -> {
                        System.out.print("Enter user name to view reservations: ");
                        String userName = scanner.nextLine();
                        List<?> userReservations = manager.getUserReservations(userName);
                        if (userReservations.isEmpty()) {
                            System.out.println("No reservations found for user " + userName);
                        } else {
                            System.out.println("Reservations for " + userName + ":");
                            userReservations.forEach(System.out::println);
                        }
                    }
                    case "9" -> {
                        System.out.print("Enter space ID: ");
                        int spaceId;
                        try {
                            spaceId = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid space ID.");
                            break;
                        }
                        System.out.print("Enter number of days: ");
                        int days;
                        try {
                            days = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid number of days.");
                            break;
                        }
                        BigDecimal totalCost = manager.calculateTotalCost(spaceId, days);
                        System.out.println("Total cost: " + totalCost);
                    }
                    case "0" -> {
                        System.out.println("Goodbye!");
                        context.close();
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }
}
