package com.scc.ui;

import com.scc.dao.UserDao;
import com.scc.models.User;
import java.util.Scanner;

public class Authentication {
    public static void handleAuth(Scanner scanner, UserDao userDao) {
        while (true) {
            System.out.println("\n=== AUTHENTICATION & ACCESS CONTROL ===");
            System.out.println("[1] Register New Account");
            System.out.println("[2] Login");
            System.out.println("[3] Back to Main Menu");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> {
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    System.out.println("Select Role: [1] ADMIN | [2] CITIZEN | [3] AUDITOR");
                    System.out.print("Enter choice: ");
                    int roleChoice;
                    try {
                        roleChoice = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        roleChoice = 2;
                    }

                    String role = switch (roleChoice) {
                        case 1 -> "ADMIN";
                        case 2 -> "CITIZEN";
                        case 3 -> "AUDITOR";
                        default -> "CITIZEN";
                    };

                    userDao.registerUser(username, email, password, role);
                }
                case "2" -> {
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    User user = userDao.authenticate(username, password);
                    if (user == null) {
                        System.out.println("[ERROR] Invalid username or password.");
                    } else if (!"APPROVED".equalsIgnoreCase(user.status())) {
                        System.out.println("[ACCESS DENIED] Account status is [" + user.status() + "]. Awaiting administrator approval.");
                    } else {
                        System.out.println("[SUCCESS] Welcome back, " + user.username() + " (" + user.role() + ")!");
                        routeUser(scanner, userDao, user);
                    }
                }
                case "3" -> {
                    return;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static void routeUser(Scanner scanner, UserDao userDao, User user) {
        switch (user.role().toUpperCase()) {
            case "ADMIN" -> AdminMenu.displayAdminMenu(scanner, userDao);
            case "CITIZEN" -> Menu.displayCitizenMenu(scanner, user);
            case "AUDITOR" -> Menu.displayAuditorMenu(scanner, user);
            default -> System.out.println("Unknown role routing.");
        }
    }
}