package Jdbc.HospitalManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class HospitalManagement {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.out.println("Error loading driver: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "Rohan@12");
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection);

            while (true) {
                System.out.println("\nHOSPITAL MANAGEMENT SYSTEM\n");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.print("\nEnter Your Choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        patient.add_Patient();
                        break;
                    case 2:
                        patient.View_Patient();
                        break;
                    case 3:
                        doctor.View_Doctor();
                        break;
                    case 4:
                        book_Appointment(patient, doctor, connection, scanner);
                        break;
                    case 5:
                        System.out.println("Exiting... Thank you!");
                        return;
                    default:
                        System.out.println("Invalid choice! Please enter a valid option.");
                }
            }

        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public static void book_Appointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
        System.out.print("Enter Patient ID: ");
        int patientId = scanner.nextInt();
        System.out.print("Enter Doctor ID: ");
        int doctorId = scanner.nextInt();
        System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
        String appointmentDate = scanner.next();

        if (patient.check_patient(patientId) && doctor.check_Doctor(doctorId)) {
            if (checkDoctorAvailability(doctorId, appointmentDate, connection)) {
                try {
                    String query = "INSERT INTO appointment (patient_id, doctor_id, appointment_date) VALUES (?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3, appointmentDate);

                    int result = preparedStatement.executeUpdate();

                    if (result > 0) {
                        System.out.println("Appointment Booked Successfully.");
                    } else {
                        System.out.println("Appointment Booking Failed.");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                System.out.println("Doctor is not available on this date.");
            }
        } else {
            System.out.println("Either the Doctor or the Patient does not exist.");
        }
    }

    public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection) {
        String query = "SELECT COUNT(*) FROM appointment WHERE doctor_id = ? AND appointment_date = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, appointmentDate);
            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) == 0;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }
}
