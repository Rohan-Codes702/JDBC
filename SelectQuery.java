package Jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SelectQuery {
    public static void main(String[] args) {

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded Sucessfully");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/info","root","Rohan@12");

            System.out.println("Connection Created Successfully");
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("Select * from emp");

            while (rs.next()){
                System.out.println("===============================");
                System.out.println("Id: "+rs.getInt("id"));
                System.out.println("Name: "+rs.getString("name"));
                System.out.println("Role: "+rs.getString("role"));
                System.out.println("Salary: "+rs.getInt("salary"));

            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
