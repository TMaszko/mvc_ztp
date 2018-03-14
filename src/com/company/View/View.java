package com.company.View;

import com.company.Controller.Controller;
import com.company.Model.Model;
import com.company.Model.OrderHeader;

import java.sql.SQLException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class View implements Observer{
    Model model;
    Controller controller;


    public View(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
        model.addObserver(this);
    }

    public void modelChanged(){
        show();
    }

    public void show() {
        show(model);
        askForCommand();
    }

    private void show(Model model) {
        List <OrderHeader> orderHeaderList = model.getOrderList();
        for (OrderHeader orderHeader: orderHeaderList) {
            System.out.println(orderHeader);
        }
    }

    private void askForCommand() {
        try (Scanner scanner = new Scanner(System.in)) {
            String command;
            do {
                System.out.println("Podaj nr zamowienia lub wpisz 'add' jeśli chcesz dodać nowe zamówienie");
                command = scanner.nextLine();
            } while (!controller.execute(command));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {

        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Integer){
            model.removeOrder((int) arg);
        }
        show();
    }
}
