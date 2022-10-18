package com.niit.jukebox;

import com.niit.jukebox.connection.MySQLConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Table {
    protected ResultSet getTable(String sql) {
        ResultSet rs = null;
        try {
            PreparedStatement st = MySQLConnection.con.prepareStatement(sql);
//            Statement st = MySQLConnection.con.createStatement();
            rs = st.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
