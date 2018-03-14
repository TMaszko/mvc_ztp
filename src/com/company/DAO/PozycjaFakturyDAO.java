package com.company.DAO;

import com.company.Model.PozycjaFaktury;

import java.sql.SQLException;

public interface PozycjaFakturyDAO {
    void addPozycjaFaktury(PozycjaFaktury pozycjaFaktury) throws SQLException;
    void removePozycjaFaktury(int fakturaID, int pozFakturyID) throws SQLException;
    void setProductName(int fakturaID, int pozFakturyID, String newProductName) throws SQLException;
    void setPrice(int fakturaID, int pozFakturyID, float newPrice) throws SQLException;
}
