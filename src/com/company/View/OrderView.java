package com.company.View;

import com.company.Controller.Controller;
import com.company.Controller.OrderHeaderController;
import com.company.Model.Model;
import com.company.Model.OrderDetail;
import com.company.Model.OrderHeader;

import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class OrderView implements Observer {

    OrderHeaderController controller;
    OrderHeader model;

    public OrderView(OrderHeader model, OrderHeaderController controller){
        this.model = model;
        this.controller = controller;
    }

    public void show() {
        showOrder();
        System.out.println("Co chcesz zrobić z tym zamowieniem?");
        System.out.println("Wpisz 'change date' jeśli chcesz zmienić datę");
        System.out.println("Wpisz 'remove' jeśli chcesz usunąć to zamówienie");
        System.out.println("Wpisz 'add' jeśli chcesz dodać pozycję zamówienia");
        System.out.println("Wpisz numer pozycji jeśli chcesz edytować pozycje zamówienia");
        askForCommand();
    }

    private void showOrder() {
        System.out.println("Id zamowienia: " + model.getId());
        if (model.getOrderDate() != null){
            System.out.println("Data zamowienia: " + model.getOrderDate());
        }
        for (OrderDetail orderDetail: model.getOrderDetailList()) {
            System.out.println(orderDetail);
        }
    }

    private void askForCommand() {
        try (Scanner scanner = new Scanner(System.in)) {
            String command;
            do {
                command = scanner.nextLine();
            } while (!controller.execute(command));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Integer){
            try {
                model.removeOrderDetail((int) arg);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        show();
    }

    public void showDetails() {

    }
}
