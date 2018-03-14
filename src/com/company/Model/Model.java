package com.company.Model;

import com.company.DAO.OrderHeaderDAO;
import com.company.DAO.OrderHeaderDAOImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Model extends Observable implements Observer  {

    private List<OrderHeader> orderHeaderList;
    private OrderHeaderDAO orderHeaderDAO;

    public Model() throws SQLException, ClassNotFoundException {
        orderHeaderDAO = new OrderHeaderDAOImpl();
        orderHeaderList = getOrderHeadersFromDatabase();
    }

    private List<OrderHeader> getOrderHeadersFromDatabase() throws SQLException {
        return orderHeaderDAO.getAllOrderHeaders();
    }

    public void changeOrderDate(int index, LocalDate date) throws SQLException {
        OrderHeader orderHeader = orderHeaderList.get(index);
        orderHeader.setOrderDate(date);
        orderHeaderDAO.changeDate(orderHeader.getId(), date);
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers();
    }

    public void addNewOrder() throws SQLException, ClassNotFoundException {
        OrderHeader orderHeader = new OrderHeader(getNextId());
        orderHeaderList.add(orderHeader);
        orderHeaderDAO.addOrderHeader(orderHeader.getId(), LocalDate.now());
        setChanged();
        notifyObservers();
    }

    private int getNextId() {
        return orderHeaderList.size();
    }

    public void removeOrder(int index){
        orderHeaderList.remove(index);

    }

    public List<OrderHeader> getOrderList() {
//      orderHeaderList = getOrderHeadersFromDatabase();   -> za kazdym razem pobieramy od nowa z bazy
        return orderHeaderList;
    }
}
