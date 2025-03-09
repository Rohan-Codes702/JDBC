package Jdbc.BankingSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class user {

    private Connection connection;
    private Scanner scanner;

    public user(Connection connection, Scanner scanner) {
        this.scanner = scanner;
        this.connection = connection;
    }

    public void register() {
        System.out.println("Enter Name:");
        scanner.nextLine(); // Consume newline
        String name = scanner.nextLine();

        System.out.println("Enter Email:");
        String email = scanner.next();

        System.out.println("Enter Password:");
        String password = scanner.next();

        if (userExists(email)) {
            System.out.println("User already exists. Please login.");
            return;
        }

        String sql = "INSERT INTO user(name, email, password) VALUES(?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password); // Consider hashing the password

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Registration Successful.");
            } else {
                System.out.println("Registration Failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public boolean userExists(String email) {
        String sql = "SELECT * FROM user WHERE email=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // If a record exists, return true
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    public String login() {
        System.out.println("Enter Email:");
        String email = scanner.next();

        System.out.println("Enter Password:");
        String password = scanner.next();

        String sql = "SELECT * FROM user WHERE email=? AND password=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Login Successful.");
                return email;
            } else {
                System.out.println("Incorrect Email or Password!");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}
