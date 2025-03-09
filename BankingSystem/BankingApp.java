package Jdbc.BankingSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class BankingApp {
    public static void main(String[] args) {
        Connection connection = null;
        Scanner scanner = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");


            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking_system", "root", "Rohan@12");
            user user = new user(connection, scanner);
            Accounts accounts = new Accounts(connection, scanner);
            AccountManager accountManager = new AccountManager(connection, scanner);

            String email;
            long account_no;

            while (true) {
                System.out.println(" *** WELCOME TO BANKING SYSTEM *** ");
                System.out.println();
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.println("Enter your choice");
                int choice1 = scanner.nextInt();

                switch (choice1) {
                    case 1:
                        user.register();
                        break;

                    case 2:
                        email = user.login();

                        if (email != null) {
                            System.out.println();
                            System.out.println("User Logged IN");

                            if (!accounts.account_exist(email)) {
                                System.out.println();
                                System.out.println("1. Open a new Bank Account");
                                System.out.println("2. Exit");

                                if (scanner.nextInt() == 1) {
                                    account_no = accounts.open_Account(email);
                                    System.out.println("Account Created Successfully");
                                    System.out.println("Your Account Number is: " + account_no);
                                } else {
                                    break;
                                }
                            }

                            account_no = accounts.getAccount_number(email);
                            int choice2 = 0;

                            while (choice2 != 5) {
                                System.out.println();
                                System.out.println("1. Debit Money");
                                System.out.println("2. Credit Money");
                                System.out.println("3. Transfer Money");
                                System.out.println("4. Check Balance");
                                System.out.println("5. Log Out");
                                System.out.println("Enter your Choice:");
                                choice2 = scanner.nextInt();

                                switch (choice2) {
                                    case 1:
                                        accountManager.debit_money(account_no);
                                        break;

                                    case 2:
                                        accountManager.credit_money(account_no);
                                        break;

                                    case 3:
                                        accountManager.transfer_money(account_no);
                                        break;

                                    case 4:
                                        accountManager.getBalance(account_no);
                                        break;

                                    case 5:
                                        break;

                                    default:
                                        System.out.println("Invalid Choice");
                                        break;
                                }
                            }

                        } else {
                            System.out.println("Incorrect Email or Password!");
                        }
                        break;

                    case 3:
                        System.out.println("THANK YOU FOR USING BANKING SYSTEM!!!");
                        System.out.println("Exiting System!");
                        return;

                    default:
                        System.out.println("Enter Valid Choice");
                        break;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (scanner != null) {
                    scanner.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }
}
