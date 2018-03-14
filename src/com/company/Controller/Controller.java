package com.company.Controller;

import com.company.Model.Model;
import com.company.Model.Faktura;
import com.company.View.FakturaView;
import com.company.View.View;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static java.time.LocalDate.now;

public class Controller {

    private Model model;
    private View view;

    public Controller(Model model){
        this.model = model;
    }

    public boolean execute(String command) throws SQLException, ClassNotFoundException {
        if ("add".equals(command)) {
            model.addNewFaktura();
            return true;
        }
        if (isInteger(command) && isFakturaNumber(Integer.parseInt(command))) {
            showFaktura(Integer.parseInt(command));
            return true;
        }
        return false;
    }

    private void showFaktura(int i) {
        Faktura fakturaModel = model.getFakturaList().stream().filter(v -> v.getId() == i).findFirst().orElseThrow(NoSuchElementException::new);
        FakturaController controller = new FakturaController(fakturaModel);
        FakturaView fakturaView = new FakturaView(fakturaModel,controller);
        fakturaModel.addObserver(view);
        controller.setView(fakturaView);

        fakturaView.show();
    }
    public void setView(View view){
        this.view = view;
    }

    private boolean isFakturaNumber(int command) {
        return model.getFakturaList().stream().filter(v -> v.getId() == command).collect(Collectors.toList()).size() == 1;
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


