package Jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class BatchProcessingstmt {
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded successfully");
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }


        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/info", "root", "Rohan@12");
            System.out.println("connection established Successfully");

            connection.setAutoCommit(false);
            try {

                Statement statement= connection.createStatement();
                statement.addBatch("INSERT IGNORE INTO emp VALUES (8,'Rohit','Web engineer',45000)");
                statement.addBatch("INSERT IGNORE INTO emp VALUES (9,'Shayam','Software engineer',49000)");
                statement.addBatch("INSERT IGNORE INTO emp VALUES (10,'Raj','Web engineer',450000)");


                int result[]=statement.executeBatch();
                connection.commit();

                if(result.length>0){
                    System.out.println("Rows Updated");
                }
                else {
                    System.out.println("Rows not updated");
                }


            }
            catch (Exception e){
                connection.rollback();
                System.out.println(e.getMessage());
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
