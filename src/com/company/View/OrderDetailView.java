package com.company.View;

import com.company.Controller.OrderDetailController;
import com.company.Model.OrderDetail;

import java.sql.SQLException;
import java.util.Scanner;

public class OrderDetailView {

    OrderDetailController controller;
    OrderDetail model;

    public OrderDetailView(OrderDetail orderDetailModel, OrderDetailController controller) {
        this.controller = controller;
        this.model = orderDetailModel;
    }

    public void show() {
        System.out.println(model);
        System.out.println("Co chcesz zrobić z tą pozycją?");
        System.out.println("Wpisz 'change name' jeśli chcesz zmienić datę");
        System.out.println("Wpisz 'change price' jeśli chcesz usunąć to zamówienie");
        System.out.println("Wpisz 'remove' jeśli chcesz dodać pozycję zamówienia");
        askForCommand();
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
}
