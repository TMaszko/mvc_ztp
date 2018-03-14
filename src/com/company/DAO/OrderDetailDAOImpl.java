package com.company.DAO;

import com.company.Connection.ConnectionBase;
import com.company.Model.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    private Connection con;

    @Override
    public void addOrderDetail(OrderDetail orderDetail) throws SQLException {
        PreparedStatement preStatementOrderDetail = con.prepareStatement("INSERT INTO OrderDetail (id, orderHeaderID, productName, price) Values (?,?,?,?)");
        preStatementOrderDetail.setInt(1, orderDetail.getId());
        preStatementOrderDetail.setInt(2, orderDetail.getOrderHeaderID());
        preStatementOrderDetail.setString(3, orderDetail.getProductName());
        preStatementOrderDetail.setFloat(4, orderDetail.getPrice());
        preStatementOrderDetail.execute();
    }

    public OrderDetailDAOImpl() throws SQLException, ClassNotFoundException {
        setConnection();
    }

    public void setConnection() throws SQLException, ClassNotFoundException {
        con = ConnectionBase.getConnection();
    }


    @Override
    public void removeOrderDetail(int orderHeaderID, int orderDetailD) throws SQLException {
        PreparedStatement preStatementOrderDetail = con.prepareStatement("DELETE FROM OrderDetail WHERE id=? AND orderHeaderID=?");
        preStatementOrderDetail.setInt(1, orderDetailD);
        preStatementOrderDetail.setInt(2, orderHeaderID);
        preStatementOrderDetail.execute();
    }

    @Override
    public void setProductName(int orderHeaderID, int orderDetailID, String newProductName) throws SQLException {
        PreparedStatement preStatementOrderDetail = con.prepareStatement("UPDATE OrderDetail SET productName=? WHERE id=? AND orderHeaderID=?");
        preStatementOrderDetail.setString(1, newProductName);
        preStatementOrderDetail.setInt(2, orderDetailID);
        preStatementOrderDetail.setInt(3, orderHeaderID);
        preStatementOrderDetail.executeUpdate();
    }

    @Override
    public void setPrice(int orderHeaderID, int orderDetailID, float newPrice) throws SQLException {
        PreparedStatement preStatementOrderDetail = con.prepareStatement("UPDATE OrderDetail SET price=? WHERE id=? AND orderHeaderID=?");
        preStatementOrderDetail.setFloat(1, newPrice);
        preStatementOrderDetail.setInt(2, orderDetailID);
        preStatementOrderDetail.setInt(3, orderHeaderID);
        preStatementOrderDetail.executeUpdate();
    }
}
