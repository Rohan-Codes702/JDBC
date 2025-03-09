package Jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class UpdateQuery {
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded Sucessfull");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/info","root","Rohan@12");
            System.out.println("Connection Established Successfully");

            Statement stmt=con.createStatement();
            int up=stmt.executeUpdate("update  emp set name='Nitish',role='mech Enginner' where id =2" );
            if (up>0){
                System.out.println("Row Updated ");
            }
            else {
                System.out.println("Row does not updated");
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
