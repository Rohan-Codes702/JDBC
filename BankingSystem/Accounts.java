package Jdbc.BankingSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Accounts {

    private Connection connection;
    private Scanner scanner;

    public Accounts(Connection connection, Scanner scanner) {
        this.scanner = scanner;
        this.connection = connection;
    }

    public long open_Account(String email) {
        if (account_exist(email)) {
            System.out.println("Account already exists for this email.");
            return -1;
        }

        String open_Account_query = "INSERT INTO accounts(account_no, name, email, balance, security_pin) VALUES (?, ?, ?, ?, ?)";
        scanner.nextLine();
        System.out.println("Enter Name:");
        String name = scanner.nextLine();

        System.out.println("Enter Initial Amount:");
        double balance = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter Security Pin:");
        String security_pin = scanner.nextLine();

        try {
            long account_number = generateAccountNumber();
            try (PreparedStatement preparedStatement = connection.prepareStatement(open_Account_query)) {
                preparedStatement.setLong(1, account_number);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, email);
                preparedStatement.setDouble(4, balance);
                preparedStatement.setString(5, security_pin);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Account created successfully. Your account number is: " + account_number);
                    return account_number;
                } else {
                    System.out.println("Account creation failed.");
                    return -1;
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return -1;
    }

    public long getAccount_number(String email) {
        String query = "SELECT account_no FROM accounts WHERE email=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("account_no"); // Corrected column name
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        throw new RuntimeException("Account number does not exist.");
    }

    public long generateAccountNumber() {
        String query = "SELECT account_no FROM accounts ORDER BY account_no DESC LIMIT 1";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                long last_account_no = resultSet.getLong("account_no");
                return last_account_no + 1;
            } else {
                return 10000100;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return 10000100;
    }

    public boolean account_exist(String email) {
        String query = "SELECT account_no FROM accounts WHERE email=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }
}
