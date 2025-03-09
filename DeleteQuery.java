package Jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DeleteQuery {
    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded Sucessfully");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            System.out.println("Connection Established Successfully");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/info","root","Rohan@12");
            Statement stmt=con.createStatement();

            int del=stmt.executeUpdate("delete from emp where id=3");

            if(del>0){
                System.out.println("Row "+del+"Delete Successfully");
            }
            else {
                System.out.println("Row not deleted");
            }

            con.close();
            stmt.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
