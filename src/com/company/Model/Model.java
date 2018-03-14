package com.company.Model;

import com.company.DAO.FakturaDAO;
import com.company.DAO.FakturaDAOImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

public class Model extends Observable implements Observer  {

    private List<Faktura> fakturaList;
    private FakturaDAO fakturaDAO;

    public Model() throws SQLException, ClassNotFoundException {
        fakturaDAO = new FakturaDAOImpl();
        fakturaList = getFakturyFromDatabase();
    }

    private List<Faktura> getFakturyFromDatabase() throws SQLException {
        return fakturaDAO.getAllFaktura();
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers();
    }

    public void addNewFaktura() throws SQLException, ClassNotFoundException {
        Faktura faktura = new Faktura(getNextId());
        fakturaList.add(faktura);
        fakturaDAO.addFaktura(faktura.getId(), LocalDate.now());
        setChanged();
        notifyObservers();
    }

    private int getNextId() {
        return fakturaList.stream().map(Faktura::getId).mapToInt(v -> v).max().orElse(-1) + 1;
    }

    public void removeFaktura(int index){
        fakturaList = fakturaList.stream().filter(v -> v.getId() != index).collect(Collectors.toList());

    }

    public List<Faktura> getFakturaList() {
        return fakturaList;
    }
}
