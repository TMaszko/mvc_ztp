package com.company.Connection;

import java.sql.*;

public class ConnectionBase {
    private static final String URL = "jdbc:sqlite:store.db";
    private static final String USER = "root";
    private static final String PASS = "root";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            return con;
    }


    public static void initTables() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection(URL, USER, PASS);
        String sqlCreateOrderDetail = "CREATE TABLE OrderDetail (id INT NOT NULL, orderHeaderID INT NOT NULL, productName VARCHAR2(100), price REAL NOT NULL ,FOREIGN KEY (orderHeaderID) REFERENCES OrderHeader(id), PRIMARY KEY(id, orderHeaderID))";
        String sqlCreateOrderHeader = "CREATE TABLE OrderHeader (id INT PRIMARY KEY NOT NULL, orderHeaderDate DATE NOT NULL)";
        Statement createOrderHeader = con.createStatement();
        createOrderHeader.execute(sqlCreateOrderHeader);
        Statement createOrderDetail = con.createStatement();
        createOrderDetail.execute(sqlCreateOrderDetail);
        con.close();
    }

}
