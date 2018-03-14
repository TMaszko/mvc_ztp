package com.company.DAO;

import com.company.Connection.ConnectionBase;
import com.company.Model.PozycjaFaktury;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PozycjaFakturyDAOImpl implements PozycjaFakturyDAO {
    private Connection con;

    @Override
    public void addPozycjaFaktury(PozycjaFaktury pozycjaFaktury) throws SQLException {
        PreparedStatement preStatementPozFaktury = con.prepareStatement("INSERT INTO PozycjaFaktury (id, fakturaID, productName, price) Values (?,?,?,?)");
        preStatementPozFaktury.setInt(1, pozycjaFaktury.getId());
        preStatementPozFaktury.setInt(2, pozycjaFaktury.getFakturaID());
        preStatementPozFaktury.setString(3, pozycjaFaktury.getProductName());
        preStatementPozFaktury.setFloat(4, pozycjaFaktury.getPrice());
        preStatementPozFaktury.execute();
    }

    public PozycjaFakturyDAOImpl() throws SQLException, ClassNotFoundException {
        setConnection();
    }

    private void setConnection() throws SQLException, ClassNotFoundException {
        con = ConnectionBase.getConnection();
    }


    @Override
    public void removePozycjaFaktury(int fakturaID, int pozFakturylD) throws SQLException {
        PreparedStatement preStatementPozFaktury = con.prepareStatement("DELETE FROM PozycjaFaktury WHERE id=? AND fakturaID=?");
        preStatementPozFaktury.setInt(1, pozFakturylD);
        preStatementPozFaktury.setInt(2, fakturaID);
        preStatementPozFaktury.execute();
    }

    @Override
    public void setProductName(int fakturaID, int pozFakturyID, String newProductName) throws SQLException {
        PreparedStatement preStatementPozFaktury = con.prepareStatement("UPDATE PozycjaFaktury SET productName=? WHERE id=? AND fakturaID=?");
        preStatementPozFaktury.setString(1, newProductName);
        preStatementPozFaktury.setInt(2, pozFakturyID);
        preStatementPozFaktury.setInt(3, fakturaID);
        preStatementPozFaktury.executeUpdate();
    }

    @Override
    public void setPrice(int fakturaID, int pozFakturyID, float newPrice) throws SQLException {
        PreparedStatement preStatementPozFaktury = con.prepareStatement("UPDATE PozycjaFaktury SET price=? WHERE id=? AND fakturaID=?");
        preStatementPozFaktury.setFloat(1, newPrice);
        preStatementPozFaktury.setInt(2, pozFakturyID);
        preStatementPozFaktury.setInt(3, fakturaID);
        preStatementPozFaktury.executeUpdate();
    }
}
