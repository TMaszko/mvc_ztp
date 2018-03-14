package com.company.View;

import com.company.Controller.FakturaController;
import com.company.Model.Faktura;
import com.company.Model.PozycjaFaktury;

import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class FakturaView implements Observer {

    private FakturaController controller;
    private Faktura model;

    public FakturaView(Faktura model, FakturaController controller){
        this.model = model;
        this.controller = controller;
    }

    public void show() {
        showFaktura();
        System.out.println("Co chcesz zrobić z tą fakturą?");
        System.out.println("Wpisz 'change date' jeśli chcesz zmienić datę");
        System.out.println("Wpisz 'remove' jeśli chcesz usunąć tę fakturę");
        System.out.println("Wpisz 'add' jeśli chcesz dodać pozycję do faktury");
        System.out.println("Wpisz numer pozycji jeśli chcesz edytować pozycje faktury");
        askForCommand();
    }

    private void showFaktura() {
        System.out.println("Id faktury: " + model.getId());
        if (model.getFakturaDate() != null){
            System.out.println("Data faktury: " + model.getFakturaDate());
        }
        for (PozycjaFaktury pozycjaFaktury : model.getPozycjaFakturyList()) {
            System.out.println(pozycjaFaktury);
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
                model.removePozFaktury((int) arg);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        show();
    }

}
