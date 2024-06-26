package controller;

import entity.Atm;
import entity.Office;
import exception.BadRequestException;
import lombok.Data;
import model.ATMModel;
import service.OfficeService;
import ui.EmployeeMenu;
import ui.Layout;
import ui.component.ComboBox.Item;
import ui.component.Table.ATMTable;

import javax.swing.*;

import java.awt.event.ItemEvent;
import java.util.List;
import java.util.Optional;

import static java.lang.StringTemplate.STR;
import static util.Constant.DEFAULT_ERROR;

public @Data class EmployeeMenuController {
    EmployeeMenu view = new EmployeeMenu();
    private ATMModel atmModel;
    private OfficeService officeService;
    private ATMTable table;

    public EmployeeMenuController(ATMModel atmModel, OfficeService officeService) {
        this.officeService = officeService;
        this.atmModel = atmModel;
        updateView();
    }

    private void updateView() {
        List<Office> listOffices = officeService.get();

        view.getOfficeSelector().removeAllItems();
        for (Office office: listOffices) {
            view.getOfficeSelector().addItem(new Item(office));
        }

        Item item = (Item) view.getOfficeSelector().getSelectedItem();

        if (item != null) {
            this.table = new ATMTable(view.getAtmTable(), item.getOffice().getAtm(), this);
            updateLabels(item.getOffice());
        }

        view.getOfficeSelector().addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                Item selectedItem = (Item) event.getItem();
                handleOfficeChange(selectedItem.getOffice().getAtm());
                updateLabels(selectedItem.getOffice());
            }
        });
    }

    private void updateLabels(Office office) {
        view.getOfficeIdLabel().setText(STR."Sucursal id: \{office.getId()}");
        view.getAddressLabel().setText(STR."Lugar: \{office.getAddress()}");
        view.getAmountAvailableLabel().setText(STR."Dinero disponible: \{office.getAvailableMoney()}");
    }

    public void handleReplenish(Long id) {
        Item item = (Item) view.getOfficeSelector().getSelectedItem();

        if (item == null) {
            return;
        }

        String input = JOptionPane.showInputDialog(STR."""
        Indique un monto a reponer que no supere la cantidad disponible de dinero en esta oficina.
        Oficina: \{view.getOfficeSelector().getSelectedItem()}
        Dinero disponible: \{item.getOffice().getAvailableMoney()}
        """);

        if(input == null || input.isEmpty()) {
            return;
        }

        try {
            Double amount = Double.parseDouble(input);
            officeService.replenishAtm(item.getOffice().getId(), id, amount);
            updateView();
            //TODO: Back to selected item
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un monto válido", "Error", JOptionPane.WARNING_MESSAGE);
        }
        catch (BadRequestException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error en la solicitud", JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, DEFAULT_ERROR, "Error no identificado", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex.getMessage());
        }
    }

    public void handleMovements(Long id) {
        Item item = (Item) view.getOfficeSelector().getSelectedItem();

        if (item == null) {
            return;
        }

        Optional<Atm> atm = item.getOffice().getAtm().stream().filter(a -> a.getId().equals(id)).findFirst();

        if (atm.isPresent()) {
            atmModel.setAtm(atm.get());
            if(atm.get().getTransaction().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Este cajero no registra ninguna transacción por el momento", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Layout.getInstance().showPanel("ATMTransaction");
        }
    }

    public void handleOfficeChange(List<Atm> atms) {
        this.table.build(atms);
    }

}

