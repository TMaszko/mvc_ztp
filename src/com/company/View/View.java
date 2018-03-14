package com.company.View;

import com.company.Controller.Controller;
import com.company.Model.Faktura;
import com.company.Model.Model;

import java.sql.SQLException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class View implements Observer{
    private Model model;
    private Controller controller;


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
        List <Faktura> fakturaList = model.getFakturaList();
        for (Faktura faktura : fakturaList) {
            System.out.println(faktura);
        }
    }

    private void askForCommand() {
        try (Scanner scanner = new Scanner(System.in)) {
            String command;
            do {
                System.out.println("Podaj nr faktury lub wpisz 'add' jeśli chcesz dodać nową fakturę");
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
            model.removeFaktura((int) arg);
        }
        show();
    }
}
