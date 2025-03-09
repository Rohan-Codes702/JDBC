package Jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import java.sql.Statement;
import java.util.Scanner;

public class HotelReservationSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hotel";
    private static final String username = "root";
    private static final String password = "Rohan@12";

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.out.println("Error loading driver: " + e.getMessage());
        }

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println();
                System.out.println("Hotel Management System");
                System.out.println("1. Reserve a Room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("0. Exit");

                System.out.print("Choose an option: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        reserveRoom(connection, sc);
                        break;
                    case 2:
                        viewReservation(connection);
                        break;
                    case 3:
                        getRoomNumber(connection, sc);
                        break;
                    case 4:
                        updateReservation(connection, sc);
                        break;
                    case 5:
                        deleteReservation(connection, sc);
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid Choice...");
                }
            }
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    private static void reserveRoom(Connection connection, Scanner sc) {
        try {
            System.out.print("Enter Guest Name: ");
            String guestName = sc.nextLine();

            System.out.print("Enter Room Number: ");
            int roomNumber = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Contact Number: ");
            String contactNumber = sc.nextLine();

            String sql = "INSERT INTO reservation (guest_name, room_no, contact_no) VALUES ('" + guestName + "', " + roomNumber + ", '" + contactNumber + "')";

            try (Statement statement = connection.createStatement()) {
                int affectedRows = statement.executeUpdate(sql);
                if (affectedRows > 0) {
                    System.out.println("Reservation Successful");
                } else {
                    System.out.println("Reservation Failed");
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewReservation(Connection connection) {
        String sql = "SELECT reservation_id, guest_name, room_no, contact_no, reservation_date FROM reservation";

        try  {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("\nCurrent Reservations:");
            System.out.printf("%-15s %-15s %-10s %-15s %-20s%n", "Reservation ID", "Guest Name", "Room No", "Contact No", "Reservation Date");
            System.out.println("-------------------------------------------------------------------------------");

            while (resultSet.next()) {
                int reservationId = resultSet.getInt("reservation_id");
                String guestName = resultSet.getString("guest_name");
                int roomNo = resultSet.getInt("room_no");
                String contactNo = resultSet.getString("contact_no");
                String reservationDate = resultSet.getString("reservation_date");

                System.out.printf("%-15d %-15s %-10d %-15s %-20s%n", reservationId, guestName, roomNo, contactNo, reservationDate);
            }

            System.out.println("-------------------------------------------------------------------------------");
                statement.close();
                resultSet.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void getRoomNumber(Connection connection, Scanner sc) {
        try {
            System.out.print("Enter Reservation ID: ");
            int reservationId = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Guest Name: ");
            String guestName = sc.nextLine();

            String sql = "SELECT room_no FROM reservation WHERE reservation_id = " + reservationId + " AND guest_name = '" + guestName + "'";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    int roomNumber = resultSet.getInt("room_no");
                    System.out.println("Room number for Reservation ID " + reservationId + " and Guest " + guestName + " is: " + roomNumber);
                } else {
                    System.out.println("Reservation Not Found");
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateReservation(Connection connection, Scanner sc) {
        try {
            System.out.print("Enter Reservation ID to update: ");
            int reservationId = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter New Guest Name: ");
            String newGuestName = sc.nextLine();

            System.out.print("Enter New Room Number: ");
            int newRoomNumber = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter New Contact Number: ");
            String newContactNumber = sc.nextLine();

            String sql = "UPDATE reservation SET guest_name = '" + newGuestName + "', room_no = " + newRoomNumber + ", contact_no = '" + newContactNumber + "' WHERE reservation_id = " + reservationId;

            try (Statement statement = connection.createStatement()) {
                int affectedRows = statement.executeUpdate(sql);
                if (affectedRows > 0) {
                    System.out.println("Reservation Updated Successfully");
                } else {
                    System.out.println("Update Failed: Reservation Not Found");
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteReservation(Connection connection, Scanner sc) {
        try {
            System.out.print("Enter Reservation ID to delete: ");
            int reservationId = sc.nextInt();
            sc.nextLine();

            String sql = "DELETE FROM reservation WHERE reservation_id = " + reservationId;

            try (Statement statement = connection.createStatement()) {
                int affectedRows = statement.executeUpdate(sql);
                if (affectedRows > 0) {
                    System.out.println("Reservation Deleted Successfully");
                } else {
                    System.out.println("Delete Failed: Reservation Not Found");
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
