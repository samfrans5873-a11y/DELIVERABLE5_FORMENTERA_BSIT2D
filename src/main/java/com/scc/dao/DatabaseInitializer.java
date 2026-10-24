package com.scc.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Enable SQLite Foreign Key constraints
            stmt.execute("PRAGMA foreign_keys = ON;");

            String usersTable = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL,
                    email TEXT UNIQUE NOT NULL,
                    password_hash TEXT NOT NULL,
                    role TEXT NOT NULL,
                    status TEXT DEFAULT 'PENDING',
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                );
            """;

            String budgetsTable = """
                CREATE TABLE IF NOT EXISTS budgets (
                    budget_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    agency_name TEXT NOT NULL,
                    sector TEXT NOT NULL,
                    fiscal_year INTEGER NOT NULL,
                    allocated_amount REAL NOT NULL,
                    remaining_amount REAL NOT NULL,
                    description TEXT,
                    created_by_user_id INTEGER,
                    FOREIGN KEY (created_by_user_id) REFERENCES users(id)
                );
            """;

            String disbursementsTable = """
                CREATE TABLE IF NOT EXISTS disbursements (
                    disbursement_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    budget_id INTEGER,
                    amount_released REAL NOT NULL,
                    purpose TEXT NOT NULL,
                    recipient_contractor TEXT NOT NULL,
                    disbursement_date TEXT NOT NULL,
                    status TEXT DEFAULT 'RELEASED',
                    FOREIGN KEY (budget_id) REFERENCES budgets(budget_id)
                );
            """;

            String discrepancyReportsTable = """
                CREATE TABLE IF NOT EXISTS discrepancy_reports (
                    report_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    budget_id INTEGER,
                    user_id INTEGER,
                    issue_description TEXT NOT NULL,
                    date_flagged TEXT NOT NULL,
                    status TEXT DEFAULT 'PENDING',
                    FOREIGN KEY (budget_id) REFERENCES budgets(budget_id),
                    FOREIGN KEY (user_id) REFERENCES users(id)
                );
            """;

            String activityLogsTable = """
                CREATE TABLE IF NOT EXISTS activity_logs (
                    log_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER,
                    action TEXT NOT NULL,
                    table_affected TEXT NOT NULL,
                    record_id INTEGER,
                    description TEXT,
                    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (user_id) REFERENCES users(id)
                );
            """;

            stmt.execute(usersTable);
            stmt.execute(budgetsTable);
            stmt.execute(disbursementsTable);
            stmt.execute(discrepancyReportsTable);
            stmt.execute(activityLogsTable);

        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        }
    }
}