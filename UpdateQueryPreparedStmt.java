package Jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UpdateQueryPreparedStmt {
    public static void main(String[] args) {

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/info","root","Rohan@12");
            System.out.println("Connection established sucessfully");

            PreparedStatement preparedStatement=con.prepareStatement("update emp set name='Pratik' where id=3");
            int result=preparedStatement.executeUpdate();

            if (result>0){
                System.out.println("updated ");
            }
            else {
                System.out.println("not updated");
            }

        }
        catch (Exception e){
            System.out.println(e.getMessage());

        }
    }
}
