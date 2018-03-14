package com.company.Controller;

import com.company.Model.Model;
import com.company.Model.OrderDetail;
import com.company.Model.OrderHeader;
import com.company.View.OrderDetailView;
import com.company.View.OrderView;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static java.time.LocalDate.now;

public class OrderHeaderController {

    OrderHeader model;
    OrderView view;

    public OrderHeaderController(OrderHeader model) {
        this.model = model;
    }


    public boolean execute(String command) throws SQLException, ClassNotFoundException {
        if ("change date".equals(command)) {
            changeOrderDate();
            return true;
        }
        if ("remove".equals(command)) {
            model.removeOrder();
            return true;
        }
        if ("add".equals(command)) {
            model.addOrderDetail();
            return true;
        }
        if (isInteger(command) && isOrderDetailsNumber(Integer.parseInt(command))) {
            showOrderDetails(Integer.parseInt(command));
            return true;
        }
        return false;
    }

    private void showOrderDetails(int i) {
        OrderDetail orderDetailModel = model.getOrderDetailList().get(i);

        OrderDetailController controller = new OrderDetailController(orderDetailModel);
        OrderDetailView orderView = new OrderDetailView(orderDetailModel,controller);
        orderDetailModel.addObserver(view);
        controller.setView(orderView);

        orderView.show();
    }

    private boolean isOrderDetailsNumber(int i) {
        return 0 <= i  && i < model.getOrderDetailList().size();
    }


    private int getNextId() {
        return model.getOrderDetailList().size();
    }

    public void setView(OrderView view){
        this.view = view;
    }


    private void changeOrderDate() throws SQLException {

        Scanner inputReg = new Scanner(System.in);
        System.out.print("Enter Date of order (DD/MM/YYYY): ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(inputReg.nextLine(), formatter);
//        System.out.println(date);
        System.out.println("Zmieniono date");
        model.setOrderDate(date);
        inputReg.close();
    }

    public static boolean isInteger(String s) {
        return isInteger(s,10);
    }

    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }
}
