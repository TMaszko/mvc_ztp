package com.company.DAO;

import com.company.Connection.ConnectionBase;
import com.company.Model.Faktura;
import com.company.Model.PozycjaFaktury;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FakturaDAOImpl implements FakturaDAO {
    private Connection con;

    public FakturaDAOImpl() throws SQLException, ClassNotFoundException {
        con = ConnectionBase.getConnection();
    }


    private Faktura extractFakturaFromResultSet(ResultSet rs, int fakturaID) throws SQLException {
        LocalDate localDate  = null;
        int id;
        String productName;
        float price;
        List<PozycjaFaktury> pozycjaFakturyList = new ArrayList<>();
        while (rs.next()) {
            if(localDate == null) {
                localDate = rs.getDate("fakturaDate").toLocalDate();
            }
            id = rs.getInt("pozfID");
            productName = rs.getString("productName");
            price = rs.getFloat("price");

            pozycjaFakturyList.add(new PozycjaFaktury(id, fakturaID, productName, price));
        }
        try {
            return new Faktura(fakturaID, localDate, pozycjaFakturyList);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Faktura> getAllFaktura() throws SQLException {
        int fakturaID;
        List<Faktura> fakturaList = new ArrayList<>();
        Faktura faktura = null;
        PreparedStatement preStatement = con.prepareStatement("SELECT * FROM Faktura");
        ResultSet resSet = preStatement.executeQuery();
        while (resSet.next()) {
            fakturaID = resSet.getInt("id");
            PreparedStatement preStatementFaktura = con.prepareStatement("SELECT fak.id as id, pozf.id as pozfID, fakturaID, fakturaDate, productName, price  FROM Faktura as fak JOIN PozycjaFaktury as pozf ON fak.id=pozf.fakturaID WHERE fak.id=?");
            preStatementFaktura.setInt(1, fakturaID);
            ResultSet resSetFaktura = preStatementFaktura.executeQuery();
            faktura = extractFakturaFromResultSet(resSetFaktura, fakturaID);
            if (faktura != null) {
                fakturaList.add(faktura);
            }
        }
        return fakturaList;
    }

    //DOBRE NAZWY KOLUMN????????????
    @Override
    public void addFaktura(int fakturaID, LocalDate fakturaDate, List<PozycjaFaktury> pozFakturyList) throws SQLException {
        PreparedStatement preStatementPozFaktury;
        PreparedStatement preStatementFaktura = con.prepareStatement("INSERT INTO Faktura (id, fakturaDate) VALUES (?,?)");
        preStatementFaktura.setInt(1, fakturaID);
        preStatementFaktura.setDate(2, Date.valueOf(fakturaDate));
        preStatementFaktura.execute();
        for (PozycjaFaktury pozycjaFaktury : pozFakturyList) {
            preStatementPozFaktury = con.prepareStatement("INSERT INTO PozycjaFaktury (id, fakturaID, productName, price) Values (?,?,?,?)");
            preStatementPozFaktury.setInt(1, pozycjaFaktury.getId());
            preStatementPozFaktury.setInt(2, fakturaID);
            preStatementPozFaktury.setString(3, pozycjaFaktury.getProductName());
            preStatementPozFaktury.setFloat(4, pozycjaFaktury.getPrice());
            preStatementPozFaktury.execute();
        }

    }

    @Override
    public void addFaktura(int fakturaID, LocalDate fakturaDate) throws SQLException {
        PreparedStatement preStatementFaktura = con.prepareStatement("INSERT INTO Faktura (id, fakturaDate) VALUES (?,?)");
        preStatementFaktura.setInt(1, fakturaID);
        preStatementFaktura.setDate(2, Date.valueOf(fakturaDate));
        preStatementFaktura.execute();
    }

    @Override
    public void changeDate(int fakturaID, LocalDate newDate) throws SQLException {
        PreparedStatement preStatementFaktura = con.prepareStatement("UPDATE Faktura SET fakturaDate =? WHERE id=?");
        preStatementFaktura.setDate(1, Date.valueOf(newDate));
        preStatementFaktura.setInt(2, fakturaID);
        preStatementFaktura.executeUpdate();
    }

    @Override
    public void removeFaktura(int fakturaID) throws SQLException {
        PreparedStatement preStatementFaktura = con.prepareStatement("DELETE FROM Faktura WHERE id=?");
        preStatementFaktura.setInt(1, fakturaID);
        preStatementFaktura.execute();
        PreparedStatement preStatementPozFaktury = con.prepareStatement("DELETE FROM PozycjaFaktury WHERE fakturaID=?");
        preStatementPozFaktury.setInt(1, fakturaID);
        preStatementPozFaktury.execute();
    }

    @Override
    public void setPozycjaFakturyList(int fakturaID, List<PozycjaFaktury> pozFakturyList) throws SQLException {
        PreparedStatement preStatementPozFaktury = con.prepareStatement("DELETE FROM PozycjaFaktury WHERE fakturaID=?");
        preStatementPozFaktury.setInt(1, fakturaID);
        preStatementPozFaktury.execute();
        for (PozycjaFaktury pozycjaFaktury : pozFakturyList) {
            preStatementPozFaktury = con.prepareStatement("INSERT INTO PozycjaFaktury (id, fakturaID, productName, price) Values (?,?,?,?)");
            preStatementPozFaktury.setInt(1, pozycjaFaktury.getId());
            preStatementPozFaktury.setInt(2, fakturaID);
            preStatementPozFaktury.setString(3, pozycjaFaktury.getProductName());
            preStatementPozFaktury.setFloat(4, pozycjaFaktury.getPrice());
            preStatementPozFaktury.execute();
        }
    }
}
