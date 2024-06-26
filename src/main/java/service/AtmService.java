package service;


import dao.AtmDAO;
import entity.Atm;

import java.util.List;

public class AtmService {

    private final AtmDAO atmDAO = new AtmDAO();

    public void save(Atm atm) {
        atmDAO.save(atm);
    }

    public List<Atm> get() {
        return atmDAO.get();
    }

    public Atm get(Long id) {
        return atmDAO.get(id);
    }
}
