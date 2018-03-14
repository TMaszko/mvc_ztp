package com.company.Controller;

import com.company.Model.OrderDetail;
import com.company.View.OrderDetailView;

import java.sql.SQLException;
import java.util.Scanner;

public class OrderDetailController {

    OrderDetail model;
    OrderDetailView view;

    public OrderDetailController(OrderDetail orderDetailModel) {
        this.model = orderDetailModel;
    }

    public void setView(OrderDetailView orderView) {
        this.view = orderView;
    }

    public boolean execute(String command) throws SQLException, ClassNotFoundException {
        if ("change name".equals(command)) {
            changeName();
            return true;
        }
        if ("change price".equals(command)) {
            changePrice();
            return true;
        }
        if ("remove".equals(command)) {
            model.remove();
            return true;
        }
        return false;
    }

    private void changePrice() {
        try (Scanner scanner = new Scanner(System.in)) {
            String price;
            System.out.println("Podaj cene: ");
            price = scanner.nextLine();
            model.setPrice(Float.parseFloat(price));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void changeName() {
        try (Scanner scanner = new Scanner(System.in)) {
            String name;
            System.out.println("Podaj nazwe produktu: ");
            name = scanner.nextLine();
            model.setProductName(name);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
