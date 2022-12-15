package com.example.bilabonnement.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseManager {

    private static String hostname;
    private static String username;
    private static String password;
    private static Connection conn;

    private DatabaseManager(){}

    public static Connection getConn(){
        if(conn != null){
            System.out.println("Connection established!");
            return conn;
        }

        //Læser informationerne fra "run", "edit configurations" så det ikke er synligt i koden
        hostname = System.getenv("db.url");
        username = System.getenv("db.username");
        password = System.getenv("db.password");
        try {
            conn = DriverManager.getConnection(hostname, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
