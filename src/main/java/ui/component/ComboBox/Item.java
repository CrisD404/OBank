package ui.component.ComboBox;

import entity.Office;
import lombok.Data;

public @Data class Item {
    private Office office;

    public Item(Office office) {
        this.office = office;
    }

    @Override
    public String toString() {
        return office.getName();
    }
}
