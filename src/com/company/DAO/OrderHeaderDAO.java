package com.company.DAO;

import com.company.Model.OrderDetail;
import com.company.Model.OrderHeader;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface OrderHeaderDAO {
    OrderHeader getOrderHeader(int orderHeaderID) throws SQLException;
    List<OrderHeader> getAllOrderHeaders() throws SQLException;
    void addOrderHeader(int orderHeaderID, LocalDate orderHeaderDate, List<OrderDetail> orderDetailsList) throws SQLException;
    void addOrderHeader(int orderHeaderID, LocalDate orderHeaderDate) throws SQLException;
    void changeDate(int orderHeaderID, LocalDate newDate) throws SQLException;
    void removeOrder(int orderHeaderID) throws SQLException;
    void setOrderDetailList(int orderHeaderID, List<OrderDetail> orderDetailList) throws SQLException;
}
