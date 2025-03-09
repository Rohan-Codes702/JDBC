package Jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class InsertQuery {
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded Sucessfully");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/info","root","Rohan@12");
            System.out.println("Connection created Sucessfully");

            Statement stmt=con.createStatement();
            int res=stmt.executeUpdate("insert into emp(id,name,role,salary)values(4,'Nitish','Mech Engineer',50000)");

            if(res>0){
                System.out.println(res+"Row affected");
            }
            else {
                System.out.println("row does not affected");
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
