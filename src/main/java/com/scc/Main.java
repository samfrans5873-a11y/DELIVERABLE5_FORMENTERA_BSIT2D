package com.scc;



import com.scc.dao.DatabaseInitializer;

import com.scc.dao.UserDao;

import com.scc.ui.Authentication;

import java.util.Scanner;



public class Main {

    public static void main(String[] args) {

// Initialize database and tables on startup with foreign key constraints enabled

        DatabaseInitializer.initializeDatabase();



        Scanner scanner = new Scanner(System.in);

        UserDao userDao = new UserDao();



        System.out.println("==================================================");

        System.out.println(" OPEN BUDGET & ANTI-CORRUPTION TRACKING SYSTEM ");

        System.out.println("==================================================");



        while (true) {

            System.out.println("\n=== MAIN MENU ===");

            System.out.println("[1] Authentication & User Access");

            System.out.println("[2] Exit System");

            System.out.print("Select an option: ");



            String choice = scanner.nextLine();

            switch (choice) {

                case "1" -> Authentication.handleAuth(scanner, userDao);

                case "2" -> {

                    System.out.println("Exiting system. Goodbye!");

                    scanner.close();

                    System.exit(0);

                }

                default -> System.out.println("Invalid choice! Please select 1 or 2.");

            }

        }

    }

}



