package Jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class BatchProcessing {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/info", "root", "Rohan@12");
            System.out.println("Connection Established ");
            connection.setAutoCommit(false);

            try {
                PreparedStatement preparedStatement = connection.prepareStatement("insert into emp values(?,?,?,?)");

                preparedStatement.setInt(1, 4);
                preparedStatement.setString(2, "Ram");
                preparedStatement.setString(3, "Deveops Enginner");
                preparedStatement.setInt(4, 39990);
                preparedStatement.addBatch();

                preparedStatement.setInt(1, 5);
                preparedStatement.setString(2, "Pratik");
                preparedStatement.setString(3, "Software Enginner");
                preparedStatement.setInt(4, 56990);
                preparedStatement.addBatch();

                preparedStatement.setInt(1, 6);
                preparedStatement.setString(2, "vivek");
                preparedStatement.setString(3, "Data Enginner");
                preparedStatement.setInt(4, 45090);
                preparedStatement.addBatch();

                int[] result = preparedStatement.executeBatch();
                connection.commit();

                if (result.length > 0) {
                    System.out.println("Rows Updated Successfully");
                } else {
                    System.out.println("Rows Not updated");
                }


            } catch (Exception e) {
                connection.rollback();
                System.out.println(e.getMessage());
            }
        }
        catch (Exception e){

            System.out.println(e.getMessage());
        }
    }
}
