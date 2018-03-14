package com.company.DAO;

import com.company.Model.Faktura;
import com.company.Model.PozycjaFaktury;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface FakturaDAO {
    List<Faktura> getAllFaktura() throws SQLException;
    void addFaktura(int fakturaID, LocalDate fakturaDate, List<PozycjaFaktury> pozFakturyList) throws SQLException;
    void addFaktura(int fakturaID, LocalDate fakturaDate) throws SQLException;
    void changeDate(int fakturaID, LocalDate newDate) throws SQLException;
    void removeFaktura(int fakturaID) throws SQLException;
    void setPozycjaFakturyList(int fakturaID, List<PozycjaFaktury> pozFakturyList) throws SQLException;
}
