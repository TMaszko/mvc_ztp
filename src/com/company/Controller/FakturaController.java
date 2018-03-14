package com.company.Controller;

import com.company.Model.Faktura;
import com.company.Model.PozycjaFaktury;
import com.company.View.PozFakturyView;
import com.company.View.FakturaView;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.time.LocalDate.now;

public class FakturaController {

    private Faktura model;
    private FakturaView view;

    public FakturaController(Faktura model) {
        this.model = model;
    }


    public boolean execute(String command) throws SQLException, ClassNotFoundException {
        if ("change date".equals(command)) {
            changeFakturaDate();
            return true;
        }
        if ("remove".equals(command)) {
            model.removeFaktura();
            return true;
        }
        if ("add".equals(command)) {
            model.addPozycjaFaktury();
            return true;
        }
        if (isInteger(command) && isPozFakturyNumber(Integer.parseInt(command))) {
            showPozFaktury(Integer.parseInt(command));
            return true;
        }
        return false;
    }

    private void showPozFaktury(int i) {
        PozycjaFaktury pozycjaFakturyModel = model.getPozycjaFakturyList().stream().filter(v -> v.getId() == i).findFirst().orElseThrow(NoSuchElementException::new);

        PozFakturyController controller = new PozFakturyController(pozycjaFakturyModel);
        PozFakturyView pozFakturyView = new PozFakturyView(pozycjaFakturyModel, controller);
        pozycjaFakturyModel.addObserver(view);
        controller.setView(pozFakturyView);

        pozFakturyView.show();
    }

    private boolean isPozFakturyNumber(int i) {
        return model.getPozycjaFakturyList().stream().filter(v -> v.getId() == i).collect(Collectors.toList()).size() == 1;
    }


    public void setView(FakturaView view){
        this.view = view;
    }


    private void changeFakturaDate() throws SQLException {

        Scanner inputReg = new Scanner(System.in);
        System.out.print("Podaj datę faktury (DD/MM/YYYY): ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(inputReg.nextLine(), formatter);
//        System.out.println(date);
        System.out.println("Zmieniono datę faktury");
        model.setFakturaDate(date);
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
