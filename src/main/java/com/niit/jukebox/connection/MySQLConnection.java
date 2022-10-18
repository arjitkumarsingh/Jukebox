package com.niit.jukebox.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConnection {
    public static Connection con;
    public MySQLConnection() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/Jukebox", "root", "5623");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
