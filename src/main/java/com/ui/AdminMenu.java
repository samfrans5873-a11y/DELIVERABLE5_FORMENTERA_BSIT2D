package com.scc.ui;

import com.scc.dao.UserDao;
import com.scc.models.User;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    // Structured table rendering helper using standard string formatters
    public static void renderUserTable(List<User> users) {
        String format = "| %-5s | %-15s | %-12s | %-10s |%n";
        String line = "+-------+----------------+--------------+------------+";
        System.out.println(line);
        System.out.printf(format, "ID", "Username", "Role", "Status");
        System.out.println(line);
        if (users.isEmpty()) {
            System.out.println("|                      No records found                      |");
        } else {
            for (User u : users) {
                System.out.printf(format, u.id(), u.username(), u.role(), u.status());
            }
        }
        System.out.println(line);
    }

    public static void displayAdminMenu(Scanner scanner, UserDao userDao) {
        while (true) {
            System.out.println("\n=== ADMIN MANAGEMENT MENU ===");
            System.out.println("[1] View All System Users");
            System.out.println("[2] View Pending Registrations");
            System.out.println("[3] Approve User Account");
            System.out.println("[4] Return to Main Menu");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> {
                    System.out.println("\n--- ALL SYSTEM USERS ---");
                    List<User> allUsers = userDao.getAllUsers();
                    renderUserTable(allUsers);
                }
                case "2" -> {
                    System.out.println("\n--- PENDING REGISTRATIONS ---");
                    List<User> pendingUsers = userDao.getPendingUsers();
                    renderUserTable(pendingUsers);
                }
                case "3" -> {
                    System.out.println("\n--- APPROVE USER ACCOUNT ---");
                    List<User> pendingUsers = userDao.getPendingUsers();
                    if (pendingUsers.isEmpty()) {
                        System.out.println("No pending accounts awaiting approval.");
                        break;
                    }
                    renderUserTable(pendingUsers);
                    System.out.print("Enter Target User ID to approve (Enter 0 to cancel): ");
                    int targetId;
                    try {
                        targetId = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Action cancelled.");
                        break;
                    }

                    if (targetId == 0) {
                        System.out.println("Approval action cancelled.");
                        break;
                    }

                    boolean exists = pendingUsers.stream().anyMatch(u -> u.id() == targetId);
                    if (exists) {
                        userDao.approveUser(targetId);
                    } else {
                        System.out.println("[ERROR] User ID " + targetId + " does not exist in the pending list.");
                    }
                }
                case "4" -> {
                    return;
                }
                default -> System.out.println("Invalid option! Please try again.");
            }
        }
    }
}