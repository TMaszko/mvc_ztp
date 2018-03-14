package com.company.View;

import com.company.Controller.PozFakturyController;
import com.company.Model.PozycjaFaktury;

import java.sql.SQLException;
import java.util.Scanner;

public class PozFakturyView {

    private PozFakturyController controller;
    private PozycjaFaktury model;

    public PozFakturyView(PozycjaFaktury pozycjaFakturyModel, PozFakturyController controller) {
        this.controller = controller;
        this.model = pozycjaFakturyModel;
    }

    public void show() {
        System.out.println(model);
        System.out.println("Co chcesz zrobić z tą pozycją faktury?");
        System.out.println("Wpisz 'change name' jeśli chcesz zmienić nazwę produktu w pozycji faktury");
        System.out.println("Wpisz 'change price' jeśli chcesz zmienić koszt produktu w pozycji faktury");
        System.out.println("Wpisz 'remove' jeśli chcesz usunąć tę pozycję faktury");
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
