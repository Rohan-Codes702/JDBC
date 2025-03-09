package Jdbc.HospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Doctor {

    private Connection connection;

    public Doctor(Connection connection) {
        this.connection = connection;
    }

    public void View_Doctor() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM doctor");
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors");


            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specilization = resultSet.getString("specialization");

                System.out.println(id + "    |    "+name +"   |    "+ specilization + "  |  ");


            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean check_Doctor(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM doctor WHERE id=?");
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            return result.next();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}