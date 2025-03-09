package Jdbc.BankingSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {

    private Connection connection;
    private Scanner scanner;

    public AccountManager(Connection connection, Scanner scanner) {
        this.scanner = scanner;
        this.connection = connection;
    }

    public void credit_money(long account_no) throws SQLException {
        scanner.nextLine();
        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Security Pin: ");
        String security_pin = scanner.nextLine();

        try {
            connection.setAutoCommit(false);
            String query = "SELECT * FROM accounts WHERE account_no = ? AND security_pin = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setLong(1, account_no);
                preparedStatement.setString(2, security_pin);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String credit_query = "UPDATE accounts SET balance = balance + ? WHERE account_no = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(credit_query)) {
                            updateStatement.setDouble(1, amount);
                            updateStatement.setLong(2, account_no);
                            if (updateStatement.executeUpdate() > 0) {
                                System.out.println("Rs. " + amount + " credited successfully.");
                                connection.commit();
                            } else {
                                System.out.println("Transaction failed.");
                                connection.rollback();
                            }
                        }
                    } else {
                        System.out.println("Invalid Security Pin.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void debit_money(long account_no) throws SQLException {
        scanner.nextLine();
        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Security Pin: ");
        String security_pin = scanner.nextLine();

        try {
            connection.setAutoCommit(false);
            String query = "SELECT balance FROM accounts WHERE account_no = ? AND security_pin = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setLong(1, account_no);
                preparedStatement.setString(2, security_pin);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        double current_balance = resultSet.getDouble("balance");
                        if (amount <= current_balance) {
                            String debit_query = "UPDATE accounts SET balance = balance - ? WHERE account_no = ?";
                            try (PreparedStatement updateStatement = connection.prepareStatement(debit_query)) {
                                updateStatement.setDouble(1, amount);
                                updateStatement.setLong(2, account_no);
                                if (updateStatement.executeUpdate() > 0) {
                                    System.out.println("Rs. " + amount + " debited successfully.");
                                    connection.commit();
                                } else {
                                    System.out.println("Transaction failed.");
                                    connection.rollback();
                                }
                            }
                        } else {
                            System.out.println("Insufficient Balance!");
                        }
                    } else {
                        System.out.println("Invalid Security Pin.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void transfer_money(long sender_account_no) throws SQLException {
        scanner.nextLine();
        System.out.print("Enter Receiver Account Number: ");
        long receiver_account_no = scanner.nextLong();
        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Security Pin: ");
        String security_pin = scanner.nextLine();

        try {
            connection.setAutoCommit(false);
            String query = "SELECT balance FROM accounts WHERE account_no = ? AND security_pin = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setLong(1, sender_account_no);
                preparedStatement.setString(2, security_pin);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        double current_balance = resultSet.getDouble("balance");
                        if (amount <= current_balance) {
                            String debit_query = "UPDATE accounts SET balance = balance - ? WHERE account_no = ?";
                            String credit_query = "UPDATE accounts SET balance = balance + ? WHERE account_no = ?";
                            try (PreparedStatement debitStatement = connection.prepareStatement(debit_query);
                                 PreparedStatement creditStatement = connection.prepareStatement(credit_query)) {
                                debitStatement.setDouble(1, amount);
                                debitStatement.setLong(2, sender_account_no);
                                creditStatement.setDouble(1, amount);
                                creditStatement.setLong(2, receiver_account_no);

                                if (debitStatement.executeUpdate() > 0 && creditStatement.executeUpdate() > 0) {
                                    System.out.println("Rs. " + amount + " transferred successfully.");
                                    connection.commit();
                                } else {
                                    System.out.println("Transaction failed.");
                                    connection.rollback();
                                }
                            }
                        } else {
                            System.out.println("Insufficient Balance!");
                        }
                    } else {
                        System.out.println("Invalid Security Pin.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void getBalance(long account_no) {
        System.out.print("Enter Security Pin: ");
        String security_pin = scanner.nextLine();

        String query = "SELECT balance FROM accounts WHERE account_no = ? AND security_pin = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, account_no);
            preparedStatement.setString(2, security_pin);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Balance: " + resultSet.getDouble("balance"));
                } else {
                    System.out.println("Invalid Pin!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
