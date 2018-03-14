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
        String sqlCreatePozFakturyTable = "CREATE TABLE PozycjaFaktury (id INT NOT NULL, fakturaID INT NOT NULL, productName VARCHAR2(100), price REAL NOT NULL ,FOREIGN KEY (fakturaID) REFERENCES Faktura(id), PRIMARY KEY(id, fakturaID))";
        String sqlCreateFakturaTable = "CREATE TABLE Faktura (id INT PRIMARY KEY NOT NULL, fakturaDate DATE NOT NULL)";
        Statement createFaktura = con.createStatement();
        createFaktura.execute(sqlCreateFakturaTable);
        Statement createPozFaktury = con.createStatement();
        createPozFaktury.execute(sqlCreatePozFakturyTable);
        con.close();
    }

}
