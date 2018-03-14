package com.company.DAO;

import com.company.Connection.ConnectionBase;
import com.company.Model.OrderDetail;
import com.company.Model.OrderHeader;

import java.sql.*;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderHeaderDAOImpl implements OrderHeaderDAO {
    private Connection con;

    public OrderHeaderDAOImpl() throws SQLException, ClassNotFoundException {
        con = ConnectionBase.getConnection();
    }


    private OrderHeader extractOrderHeaderFromResultSet(ResultSet rs, int orderHeaderID) throws SQLException {
        LocalDate localDate  = null;
        int id;
        String productName;
        float price;
        List<OrderDetail> orderDetailList = new ArrayList<>();
        while (rs.next()) {
            if(localDate == null) {
                localDate = rs.getDate("orderHeaderDate").toLocalDate();
            }
            id = rs.getInt("ordID");
            productName = rs.getString("productName");
            price = rs.getFloat("price");

            orderDetailList.add(new OrderDetail(id, orderHeaderID, productName, price));
        }
        try {
            return new OrderHeader(orderHeaderID, localDate, orderDetailList);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<OrderDetail> extractOrderDetailFromResultSet(ResultSet rs, int orderHeaderID) throws SQLException {
        int id;
        String productName;
        float price;
        List<OrderDetail> orderDetailList = new ArrayList<>();
        while (rs.next()) {
            id = rs.getInt("ord.id");
            productName = rs.getString("productName");
            price = rs.getFloat("price");

            orderDetailList.add(new OrderDetail(id, orderHeaderID, productName, price));
        }
        return orderDetailList;
    }

    @Override
    public OrderHeader getOrderHeader(int orderHeaderID) throws SQLException {
        OrderHeader orderHeader = null;
//        PreparedStatement preStatementOrderHeader = con.prepareStatement("SELECT * FROM OrderHeader WHERE id=? oh JOIN OrderDetail od ON oh.id = od.orderHeaderID ");
//        preStatementOrderHeader.setInt(1, orderHeaderID);
//        ResultSet resSetOrderHeader = preStatementOrderHeader.executeQuery();
//        while (resSetOrderHeader.next()) {
//            orderHeader = extractOrderHeaderFromResultSet(resSetOrderHeader, orderHeaderID);
//        }
        return orderHeader;
    }

    @Override
    public List<OrderHeader> getAllOrderHeaders() throws SQLException {
        int orderHeaderID = 0;
        List<OrderHeader> orderHeaderList = new ArrayList<>();
        OrderHeader orderHeader = null;
        PreparedStatement preStatement = con.prepareStatement("SELECT * FROM OrderHeader");
        ResultSet resSet = preStatement.executeQuery();
        while (resSet.next()) {
            orderHeaderID = resSet.getInt("id");
            PreparedStatement preStatementOrderHeader = con.prepareStatement("SELECT orh.id as id, ord.id as ordID, orderHeaderID, orderHeaderDate, productName, price  FROM OrderHeader as orh JOIN OrderDetail as ord ON orh.id=ord.orderHeaderID WHERE orh.id=?");
            preStatementOrderHeader.setInt(1, orderHeaderID);
            ResultSet resSetOrderHeader = preStatementOrderHeader.executeQuery();
            orderHeader = extractOrderHeaderFromResultSet(resSetOrderHeader, orderHeaderID);
            if (orderHeader != null) {
                orderHeaderList.add(orderHeader);
            }
        }
        return orderHeaderList;
    }

    //DOBRE NAZWY KOLUMN????????????
    @Override
    public void addOrderHeader(int orderHeaderID, LocalDate orderHeaderDate, List<OrderDetail> orderDetailsList) throws SQLException {
        PreparedStatement preStatementOrderDetail;
        PreparedStatement preStatementOrderHeader = con.prepareStatement("INSERT INTO OrderHeader (id, orderHeaderDate) VALUES (?,?)");
        preStatementOrderHeader.setInt(1, orderHeaderID);
        preStatementOrderHeader.setDate(2, Date.valueOf(orderHeaderDate));
        preStatementOrderHeader.execute();
        for (OrderDetail orderDetail : orderDetailsList) {
            preStatementOrderDetail = con.prepareStatement("INSERT INTO OrderDetail (id, orderHeaderID, productName, price) Values (?,?,?,?)");
            preStatementOrderDetail.setInt(1, orderDetail.getId());
            preStatementOrderDetail.setInt(2, orderHeaderID);
            preStatementOrderDetail.setString(3, orderDetail.getProductName());
            preStatementOrderDetail.setFloat(4, orderDetail.getPrice());
            preStatementOrderDetail.execute();
        }

    }

    @Override
    public void addOrderHeader(int orderHeaderID, LocalDate orderHeaderDate) throws SQLException {
        PreparedStatement preStatementOrderHeader = con.prepareStatement("INSERT INTO OrderHeader (id, orderHeaderDate) VALUES (?,?)");
        preStatementOrderHeader.setInt(1, orderHeaderID);
        preStatementOrderHeader.setDate(2, Date.valueOf(orderHeaderDate));
        preStatementOrderHeader.execute();
    }

    @Override
    public void changeDate(int orderHeaderID, LocalDate newDate) throws SQLException {
        PreparedStatement preStatementOrderHeader = con.prepareStatement("UPDATE OrderHeader SET orderHeaderDate = ? WHERE id=?");
        preStatementOrderHeader.setDate(1, Date.valueOf(newDate));
        preStatementOrderHeader.setInt(2, orderHeaderID);
        preStatementOrderHeader.executeUpdate();
    }

    @Override
    public void removeOrder(int orderHeaderID) throws SQLException {
        PreparedStatement preStatementOrderHeader = con.prepareStatement("DELETE FROM OrderHeader WHERE id=?");
        preStatementOrderHeader.setInt(1, orderHeaderID);
        preStatementOrderHeader.execute();
        PreparedStatement preStatementOrderDetail = con.prepareStatement("DELETE FROM OrderDetail WHERE orderHeaderID=?");
        preStatementOrderDetail.setInt(1, orderHeaderID);
        preStatementOrderDetail.execute();
    }

    @Override
    public void setOrderDetailList(int orderHeaderID, List<OrderDetail> orderDetailsList) throws SQLException {
        PreparedStatement preStatementOrderDetail = con.prepareStatement("DELETE FROM OrderDetail WHERE orderHeaderID=?");
        preStatementOrderDetail.setInt(1, orderHeaderID);
        preStatementOrderDetail.execute();
        for (OrderDetail orderDetail : orderDetailsList) {
            preStatementOrderDetail = con.prepareStatement("INSERT INTO OrderDetail (id, orderHeaderID, productName, price) Values (?,?,?,?)");
            preStatementOrderDetail.setInt(1, orderDetail.getId());
            preStatementOrderDetail.setInt(2, orderHeaderID);
            preStatementOrderDetail.setString(3, orderDetail.getProductName());
            preStatementOrderDetail.setFloat(4, orderDetail.getPrice());
            preStatementOrderDetail.execute();
        }
    }
}
