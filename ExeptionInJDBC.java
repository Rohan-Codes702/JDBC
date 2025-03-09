package Jdbc;

import java.util.Scanner;

public class ExeptionInJDBC {
    public static void main(String[] args)  {
        Scanner sc=new Scanner(System.in);
        int a=sc.nextInt();
        int b=sc.nextInt();

        try
        {
        int division =a/b;
        System.out.println(division);}
        catch (Exception e){
            System.out.println("exception is occur"+e.getMessage());
        }

        try {
            int arr[] = new int[5];
            arr[6] = 8;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
