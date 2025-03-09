package Jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TransactionQuery {
    public static void main(String[] args) {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/info","root","Rohan@12");
            System.out.println("Connection Established Successfully");

            con.setAutoCommit(false);

            try {
                PreparedStatement debit = con.prepareStatement("update bank set amount=amount-? where acc_no=?");
                PreparedStatement credit = con.prepareStatement("update bank set amount=amount+? where acc_no=?");

                debit.setInt(1, 100);
                debit.setInt(2, 1);


                credit.setInt(1, 100);
                credit.setInt(2, 2);

                int debitresult = debit.executeUpdate();
                int creditresult = credit.executeUpdate();

                if (debitresult > 0 && creditresult > 0) {
                    con.commit();
                    System.out.println("Transaction successfully");
                } else {
                    System.out.println("Error in Transaction");
                }
            }
            catch (Exception e){
                con.rollback();
                System.out.println(e.getMessage());
            }




        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
