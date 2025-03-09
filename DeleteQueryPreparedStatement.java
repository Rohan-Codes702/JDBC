package Jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class DeleteQueryPreparedStatement {
    public static void main(String[] args) {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded Sucessfully");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        try
        {
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/info","root","Rohan@12");
            System.out.println("Connection established sucessfully");

            PreparedStatement preparedStatement=con.prepareStatement("delete from emp where id=5");
            int result=preparedStatement.executeUpdate();
            if (result>0){
                System.out.println(result+ " Row Deleted");
            }
            else {
                System.out.println("Rows Not deleted ");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }
}
