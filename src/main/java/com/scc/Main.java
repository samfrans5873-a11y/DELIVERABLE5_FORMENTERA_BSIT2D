package com.scc;

import com.scc.dao.DatabaseInitializer;
import com.scc.dao.UserDao;
import com.scc.ui.Authentication;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1. Initialize DB Tables
        DatabaseInitializer.initializeDatabase();

        // 2. Shared instances
        Scanner scanner = new Scanner(System.in);
        UserDao userDao = new UserDao();

        // 3. Delegate execution to Authentication
        Authentication.handleAuth(scanner, userDao);
    }
}