package com.company.Controller;

import com.company.Model.Model;
import com.company.Model.OrderHeader;
import com.company.View.OrderView;
import com.company.View.View;

import java.sql.SQLException;
import java.time.LocalDate;

import static java.time.LocalDate.now;

public class Controller {

    Model model;
    View view;

    public Controller(Model model){
        this.model = model;
    }

    public boolean execute(String command) throws SQLException, ClassNotFoundException {
        if ("add".equals(command)) {
            model.addNewOrder();
            return true;
        }
        if (isInteger(command) && isOrderNumber(Integer.parseInt(command))) {
            showOrder(Integer.parseInt(command));
            return true;
        }
        return false;
    }

    private void showOrder(int i) {
        OrderHeader orderHeaderModel = model.getOrderList().get(i);

        OrderHeaderController controller = new OrderHeaderController(orderHeaderModel);
        OrderView orderView = new OrderView(orderHeaderModel,controller);
        orderHeaderModel.addObserver(view);
        controller.setView(orderView);

        orderView.show();
    }
    public void setView(View view){
        this.view = view;
    }

    private boolean isOrderNumber(int command) {
        return 0 <= command && command < model.getOrderList().size();
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


