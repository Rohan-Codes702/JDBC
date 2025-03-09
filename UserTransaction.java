package Jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class UserTransaction {
    public static void main(String[] args) {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loadede Successfully");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/info","root","Rohan@12");
            System.out.println("Connection established Successfully");

            try {


                PreparedStatement debit = connection.prepareStatement("update bank set amount=amount-? where acc_no=?");
                PreparedStatement credit = connection.prepareStatement("update bank set amount=amount+? where acc_no=?");
                Scanner sc = new Scanner(System.in);

                System.out.println("Enter Amount for debited");
                int d = sc.nextInt();
                System.out.println("Enter Acc_no ");
                int a = sc.nextInt();

                System.out.println("Enter Acc_no ");
                int b = sc.nextInt();

                connection.setAutoCommit(false);

                debit.setInt(1, d);
                debit.setInt(2, a);

                credit.setInt(1, d);
                credit.setInt(2, b);


                int debitresult = debit.executeUpdate();
                int creditresult = credit.executeUpdate();

                if (debitresult > 0 && creditresult > 0) {
                    connection.commit();
                    System.out.println("Transaction successfully");
                } else {
                    System.out.println("Transaction error");
                }
            }
            catch (Exception e){
                connection.rollback();
                System.out.println(e.getMessage());
            }


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
