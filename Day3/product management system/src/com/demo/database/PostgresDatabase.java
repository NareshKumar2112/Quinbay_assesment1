package com.demo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class PostgresDatabase {

    private Connection connection;
    static final String DB_URL = "jdbc:postgresql://localhost:5432/Assisment1";
    static final String USER = "nareshkumarg";
    static final String PASS = "Naresh2003";

    public PostgresDatabase()
    {
        try {

            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        }
        catch(Exception e)
        {
            System.out.println("error"+e);
        }
        }
    public Connection getConnection()
    {
        return connection;
    }
}
