package service;

import dao.OfficeDAO;
import entity.Atm;
import entity.Office;
import exception.BadRequestException;

import java.util.List;
import java.util.Optional;

public class OfficeService {
    private final OfficeDAO officeDAO = new OfficeDAO();


    public void save(Office office) {
        officeDAO.save(office);
    }

    public List<Office> get() {
        return officeDAO.get();
    }

    public Office get(Long id) {
        return officeDAO.get(id);
    }

    public void replenishAtm(Long idOffice, Long idAtm, Double amount) throws BadRequestException {
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        Office office = this.get(idOffice);

        if(office.getAvailableMoney() < amount) {
            throw new BadRequestException("El monto excede a la cantidad de dinero disponible en la oficina.");
        }

        Optional<Atm> atmOptional = office
                .getAtm()
                .stream()
                .filter(atm -> atm.getId().equals(idAtm))
                .findFirst();

        if(atmOptional.isEmpty()) {
            throw new BadRequestException("El ATM con id: " + idAtm + " no existe.");
        }

        Atm atm = atmOptional.get();
        office.setAvailableMoney(office.getAvailableMoney() - amount);
        atm.setAvailableMoney(atm.getAvailableMoney() + amount);
        this.save(office);
    }
}
