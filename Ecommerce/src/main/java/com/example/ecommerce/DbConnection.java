package com.example.ecommerce;
import java.sql.*;

public class DbConnection {
    // creation of the  url and accessing the database from mysql software
    private final String dbUrl = "jdbc:mysql://localhost:3306/ecommerce";
    private final String userName = "root";
    private final String password = "1234";

    private Statement  getStatement(){
       try {
               Connection connection = DriverManager.getConnection(dbUrl , userName ,password);
               return  connection.createStatement();
       }catch (Exception e){
           e.printStackTrace();
       }
       return null;
    }
    // resultset is table kind of data which we created in mysql
    public ResultSet getQueryTable(String query) {
        try {
           Statement statement = getStatement();
           return statement.executeQuery(query);

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public int updateDatabase (String query){
        try {
            Statement statement = getStatement();
            return statement.executeUpdate(query);

        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        DbConnection conn = new DbConnection();
        ResultSet rs  = conn.getQueryTable("select * from customer");
        if (rs != null){
            System.out.println("Connection successful");
        }
        else {
            System.out.println("Connection Failed");
        }
    }

}
