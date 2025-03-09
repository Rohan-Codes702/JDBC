package Jdbc;

import java.sql.*;

public class SelectQueyPreparedStatement {
    public static void main(String[] args) {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded Successfully");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{

            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/info","root","Rohan@12");
            System.out.println("Connection Established Successfully");
            PreparedStatement pre=con.prepareStatement("Select id,name,role,salary from emp");

            ResultSet result=pre.executeQuery();

            while (result.next()){
                System.out.println("id:"+result.getInt("id"));
                System.out.println("Name:"+result.getString("name"));
                System.out.println("role:"+result.getString("role"));
                System.out.println("id:"+result.getInt("id"));

            }
            con.close();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
