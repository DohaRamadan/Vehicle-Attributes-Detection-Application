package com.example.figma;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLDatabaseConnection {
    private static SQLDatabaseConnection connectionInstance;
    private static Connection connectionToDataBase;

    private SQLDatabaseConnection(){

    }

    private static Connection connect(){
        String databaseName = "vad";
        String databaseUser = "";
        String databasePassword = "";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try{
//            Class.forName("com.mysql.cj.Driver");
            connectionToDataBase = DriverManager.getConnection(url, "root", "161112");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connectionToDataBase;
    }

    public static Connection getConnectionToDataBase(){
        if(connectionToDataBase == null){
            connectionToDataBase = connectionInstance.connect();
        }
        return connectionToDataBase;
    }
}
