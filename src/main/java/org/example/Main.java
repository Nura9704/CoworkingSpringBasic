package org.example;

import org.example.config.AppConfig;
import org.example.service.CoworkingService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CoworkingService coworkingService = context.getBean(CoworkingService.class);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Add space");
            System.out.println("2. Show all spaces");
            System.out.println("0. Exit");
            System.out.print("Select an action: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter the type of space: ");
                    String type = scanner.nextLine();
                    System.out.print("Enter the price: ");
                    BigDecimal price = new BigDecimal(scanner.nextLine());
                    coworkingService.addSpace(type, price);
                    break;
                case "2":
                    coworkingService.listSpaces();
                    break;
                case "0":
                    context.close();
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Wrong choice.");
            }
        }
    }
}
