package com.company.Model;

import com.company.DAO.OrderDetailDAO;
import com.company.DAO.OrderDetailDAOImpl;

import java.sql.SQLException;
import java.util.Observable;

public class OrderDetail extends Observable{

    private int id;
    private int orderHeaderID;
    private String productName;
    private float price;

    public OrderDetail(int id, int orderHeaderID, String productName, float price){
        this.id = id;
        this.orderHeaderID = orderHeaderID;
        this.productName = productName;
        this.price = price;
    }

    public OrderDetail(int id, int orderHeaderID) {
        this.id = id;
        this.orderHeaderID = orderHeaderID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderHeaderID() {
        return orderHeaderID;
    }

    public void setOrderHeaderID(int orderHeaderID) {
        this.orderHeaderID = orderHeaderID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) throws SQLException, ClassNotFoundException {
        OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();
        this.productName = productName;
       orderDetailDAO.setProductName(getOrderHeaderID(), getId(), productName);
        setChanged();
        notifyObservers();
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) throws SQLException, ClassNotFoundException {
        OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();
        this.price = price;
        orderDetailDAO.setPrice(orderHeaderID, id, price);
        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        return "Id pozycji zamowienia: " + id + " Nazwa towaru: " + productName + " koszt: " + price;
    }

    public void remove() throws SQLException, ClassNotFoundException {
        OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();
        orderDetailDAO.removeOrderDetail(orderHeaderID, id);
        setChanged();
        notifyObservers(id);
    }
}
