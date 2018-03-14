package com.company;

import com.company.Connection.ConnectionBase;
import com.company.Controller.Controller;
import com.company.Model.Model;
import com.company.View.View;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        try {
//            ConnectionBase.initTables();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        Model model = new Model();
        Controller controller = new Controller(model);

        View view = new View(model, controller);
        model.addObserver(view);
        controller.setView(view);
        view.show();
    }
}
