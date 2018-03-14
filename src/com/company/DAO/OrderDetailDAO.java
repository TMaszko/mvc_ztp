package com.company.DAO;

import com.company.Model.OrderDetail;

import java.sql.SQLException;

public interface OrderDetailDAO {
    void addOrderDetail(OrderDetail orderDetail) throws SQLException;
    void removeOrderDetail(int orderHeaderID, int orderDetailD) throws SQLException;
    void setProductName(int orderHeaderID, int orderDetailID, String newProductName) throws SQLException;
    void setPrice(int orderHeaderID, int orderDetailID, float newPrice) throws SQLException;
}
