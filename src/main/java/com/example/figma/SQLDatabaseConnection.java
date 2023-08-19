package com.example.figma;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class SQLDatabaseConnection {

    private static SQLDatabaseConnection connectionInstance;
    private static Connection connectiontoDataBase;

    private SQLDatabaseConnection(){

    }

    private static Connection connect() throws IOException {
        File databaseFile = new File("vadDatabase.db");
        if(!databaseFile.exists()){
            databaseFile.createNewFile();
        }
        String url = "jdbc:sqlite:vadDatabase.db";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Connection to the database has been setup");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }


    public static Connection getConnectionToDataBase() throws IOException {
        if(connectiontoDataBase == null){
            connectiontoDataBase = connectionInstance.connect();
            checkTables();
        }

        return connectiontoDataBase;
    }

    public static void checkTables() {
        SQLImplementation.createVideoTable();
        SQLImplementation.createVehicleTable();
    }



}