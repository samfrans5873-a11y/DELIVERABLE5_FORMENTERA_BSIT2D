package com.scc.ui;

import com.scc.models.User;
import java.util.Scanner;

public class Menu {

    // Citizen Dashboard Menu
    public static void displayCitizenMenu(Scanner scanner, User currentUser) {
        while (true) {
            System.out.println("\n=== CITIZEN DASHBOARD ===");
            System.out.println("Logged in as: " + currentUser.username() + " (CITIZEN)");
            System.out.println("[1] View Public Budget Allocations");
            System.out.println("[2] Flag a Budget Discrepancy");
            System.out.println("[3] Logout / Return to Main");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> System.out.println("\n[INFO] Viewing budget allocations feature is ready for integration.");
                case "2" -> System.out.println("\n[INFO] Flagging discrepancy feature is ready for integration.");
                case "3" -> {
                    System.out.println("Logging out from Citizen session.");
                    return;
                }
                default -> System.out.println("Invalid option! Please try again.");
            }
        }
    }

    // Auditor Dashboard Menu
    public static void displayAuditorMenu(Scanner scanner, User currentUser) {
        while (true) {
            System.out.println("\n=== AUDITOR DASHBOARD ===");
            System.out.println("Logged in as: " + currentUser.username() + " (AUDITOR)");
            System.out.println("[1] View Audit Logs & Reports");
            System.out.println("[2] Review Flagged Discrepancies");
            System.out.println("[3] Logout / Return to Main");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> System.out.println("\n[INFO] Audit logs and reporting feature is ready for integration.");
                case "2" -> System.out.println("\n[INFO] Reviewing discrepancy reports feature is ready for integration.");
                case "3" -> {
                    System.out.println("Logging out from Auditor session.");
                    return;
                }
                default -> System.out.println("Invalid option! Please try again.");
            }
        }
    }
}