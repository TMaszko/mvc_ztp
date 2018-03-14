package com.company.Model;

import com.company.DAO.PozycjaFakturyDAO;
import com.company.DAO.PozycjaFakturyDAOImpl;

import java.sql.SQLException;
import java.util.Observable;

public class PozycjaFaktury extends Observable{

    private int id;
    private int fakturaID;
    private String productName;
    private float price;

    public PozycjaFaktury(int id, int fakturaID, String productName, float price){
        this.id = id;
        this.fakturaID = fakturaID;
        this.productName = productName;
        this.price = price;
    }

    public PozycjaFaktury(int id, int fakturaID) {
        this.id = id;
        this.fakturaID = fakturaID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFakturaID() {
        return fakturaID;
    }

    public void setFakturaID(int fakturaID) {
        this.fakturaID = fakturaID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) throws SQLException, ClassNotFoundException {
        PozycjaFakturyDAO pozycjaFakturyDAO = new PozycjaFakturyDAOImpl();
        this.productName = productName;
       pozycjaFakturyDAO.setProductName(getFakturaID(), getId(), productName);
        setChanged();
        notifyObservers();
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) throws SQLException, ClassNotFoundException {
        PozycjaFakturyDAO pozycjaFakturyDAO = new PozycjaFakturyDAOImpl();
        this.price = price;
        pozycjaFakturyDAO.setPrice(fakturaID, id, price);
        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        return "Id pozycji faktury: " + id + " Nazwa towaru: " + productName + " koszt: " + price;
    }

    public void remove() throws SQLException, ClassNotFoundException {
        PozycjaFakturyDAO pozycjaFakturyDAO = new PozycjaFakturyDAOImpl();
        pozycjaFakturyDAO.removePozycjaFaktury(fakturaID, id);
        setChanged();
        notifyObservers(id);
    }
}
