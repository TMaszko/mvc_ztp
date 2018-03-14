package com.company.Model;

import com.company.DAO.PozycjaFakturyDAO;
import com.company.DAO.PozycjaFakturyDAOImpl;
import com.company.DAO.FakturaDAO;
import com.company.DAO.FakturaDAOImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Faktura extends Observable{

    private int id;
    private LocalDate fakturaDate;
    private List<PozycjaFaktury> pozycjaFakturyList;
    private FakturaDAO fakturaDAO;

    public Faktura(int id, LocalDate fakturaDate, List<PozycjaFaktury> pozycjaFakturyList) throws SQLException, ClassNotFoundException {
        this.id = id;
        this.fakturaDate = fakturaDate;
        this.pozycjaFakturyList = pozycjaFakturyList;
        fakturaDAO = new FakturaDAOImpl();
    }

    public Faktura(int id, LocalDate fakturaDate) throws SQLException, ClassNotFoundException {
        this.id = id;
        this.pozycjaFakturyList = new ArrayList<>();
        this.fakturaDate = fakturaDate;
        fakturaDAO = new FakturaDAOImpl();

    }

    public Faktura(int id) throws SQLException, ClassNotFoundException {
        this.id = id;
        this.pozycjaFakturyList = new ArrayList<>();
        this.fakturaDate = LocalDate.now();
        fakturaDAO = new FakturaDAOImpl();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFakturaDate() {
        return fakturaDate;
    }

    public void setFakturaDate(LocalDate fakturaDate) throws SQLException {
        this.fakturaDate = fakturaDate;
        fakturaDAO.changeDate(id, fakturaDate);
        setChanged();
        notifyObservers();
    }

    public List<PozycjaFaktury> getPozycjaFakturyList() {
        return pozycjaFakturyList;
    }

    public void setPozycjaFakturyList(List<PozycjaFaktury> pozycjaFakturyList) throws SQLException {
        this.pozycjaFakturyList = pozycjaFakturyList;
        fakturaDAO.setPozycjaFakturyList(id, pozycjaFakturyList);
    }

    public void removeFaktura() throws SQLException {
        fakturaDAO.removeFaktura(id);
        setChanged();
        notifyObservers(id);
    }

    @Override
    public String toString() {
        return "ID Faktury: " + getId();
    }

    public void addPozycjaFaktury() throws SQLException, ClassNotFoundException {
        PozycjaFakturyDAO pozycjaFakturyDao = new PozycjaFakturyDAOImpl();
        PozycjaFaktury pozycjaFaktury = new PozycjaFaktury(getNextId(),getId());
        pozycjaFakturyList.add(pozycjaFaktury);
        pozycjaFakturyDao.addPozycjaFaktury(pozycjaFaktury);
        setChanged();
        notifyObservers();
    }

    private int getNextId() {
        return pozycjaFakturyList.stream().map(PozycjaFaktury::getId).mapToInt(v -> v).max().orElse(-1) + 1;
    }

    public void removePozFaktury(int arg) throws SQLException, ClassNotFoundException {
        PozycjaFakturyDAO pozycjaFakturyDao = new PozycjaFakturyDAOImpl();
        PozycjaFaktury pozycjaFaktury = pozycjaFakturyList.stream().filter(v -> v.getId() == arg).collect(Collectors.toList()).get(0);
        pozycjaFakturyList = pozycjaFakturyList.stream().filter(v -> v.getId() != arg).collect(Collectors.toList());
        pozycjaFakturyDao.removePozycjaFaktury(pozycjaFaktury.getFakturaID(), pozycjaFaktury.getId());
    }
}
