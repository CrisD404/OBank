package ui.component.ComboBox;

import entity.Atm;
import lombok.Data;

public @Data class AtmItem {
    private Atm atm;

    public AtmItem(Atm atm) {
        this.atm = atm;
    }

    @Override
    public String toString() {
        return STR."Cajero N° \{atm.getId()}";
    }
}
