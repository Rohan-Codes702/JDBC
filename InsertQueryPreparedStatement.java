package Jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class InsertQueryPreparedStatement {
    public static void main(String[] args) {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded Successfully");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        try
        {
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/info","root","Rohan@12");
            PreparedStatement pre=connection.prepareStatement("insert into emp values(4,'Shubham','Manager',90000)");
            int result=pre.executeUpdate();

            if (result>0){
                System.out.println("Row Affected "+result);
            }
            else {
                System.out.println("Row Not afffected");
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
