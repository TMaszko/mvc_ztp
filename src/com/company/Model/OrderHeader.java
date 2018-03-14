package com.company.Model;

import com.company.DAO.OrderDetailDAO;
import com.company.DAO.OrderDetailDAOImpl;
import com.company.DAO.OrderHeaderDAO;
import com.company.DAO.OrderHeaderDAOImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class OrderHeader extends Observable{

    private int id;
    private LocalDate orderDate;
    private List<OrderDetail> orderDetailList;
    private OrderHeaderDAO orderHeaderDAO;

    public OrderHeader(int id, LocalDate orderDate, List<OrderDetail> orderDetailList) throws SQLException, ClassNotFoundException {
        this.id = id;
        this.orderDate = orderDate;
        this.orderDetailList = orderDetailList;
        orderHeaderDAO = new OrderHeaderDAOImpl();
    }

    public OrderHeader(int id, LocalDate orderDate) throws SQLException, ClassNotFoundException {
        this.id = id;
        this.orderDetailList = new ArrayList<>();
        this.orderDate = orderDate;
        orderHeaderDAO = new OrderHeaderDAOImpl();

    }

    public OrderHeader(int id) throws SQLException, ClassNotFoundException {
        this.id = id;
        this.orderDetailList = new ArrayList<>();
        this.orderDate = LocalDate.now();
        orderHeaderDAO = new OrderHeaderDAOImpl();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
        setChanged();
        notifyObservers();
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) throws SQLException {
        this.orderDetailList = orderDetailList;
        orderHeaderDAO.setOrderDetailList(id, orderDetailList);
    }

    public void removeOrder() throws SQLException {
        //Usunac z bazy + wszystkie orderdetails ktore do niego nalezą
        orderHeaderDAO.removeOrder(id);
        setChanged();
        notifyObservers(id);
    }

    @Override
    public String toString() {
        return "ID Zamowienia: " + getId();
    }

    public void addOrderDetail() throws SQLException, ClassNotFoundException {
        OrderDetailDAO orderDetailDao = new OrderDetailDAOImpl();
        OrderDetail orderDetail = new OrderDetail(getNextId(),getId());
        orderDetailList.add(orderDetail);
        orderDetailDao.addOrderDetail(orderDetail);
        setChanged();
        notifyObservers();
    }

    private int getNextId() {
        return orderDetailList.size();
    }

    public void removeOrderDetail(int arg) throws SQLException, ClassNotFoundException {
        OrderDetailDAO orderDetailDao = new OrderDetailDAOImpl();
        OrderDetail orderDetail = orderDetailList.remove(arg);
        orderDetailDao.removeOrderDetail(orderDetail.getOrderHeaderID(), orderDetail.getId());
    }
}
