package Jdbc.HospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Patient {

    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void add_Patient() {
        System.out.println("Enter Name of Patient:");
        scanner.nextLine(); // Consume newline
        String name = scanner.nextLine();
        System.out.println("Enter Age of Patient:");
        int age = scanner.nextInt();
        System.out.println("Enter Gender of Patient:");
        String gender = scanner.next();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO patient (name, age, gender) VALUES (?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Patient Added Successfully");
            } else {
                System.out.println("Patient not Added");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void View_Patient() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM patient");
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.println(id + "  |  "+name +" |  "+ age +"  |  "+gender +"  |  ");

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean check_patient(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM patient WHERE id=?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}