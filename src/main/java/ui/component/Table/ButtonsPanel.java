package ui.component.Table;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class ButtonsPanel extends JPanel {
    public final List<JButton> buttons = new ArrayList<>();
    public ButtonsPanel() {
        super(new FlowLayout(FlowLayout.LEFT));
        setOpaque(true);
        for (ActionsE a : ActionsE.values()) {
            JButton b = new JButton(a.toString());
            b.setFocusable(false);
            b.setRolloverEnabled(false);
            add(b);
            buttons.add(b);
        }
    }

    protected void updateButtons(Object value) {
        if (value instanceof EnumSet) {
            EnumSet ea = (EnumSet) value;
            removeAll();
            if (ea.contains(ActionsE.REPONER)) {
                add(buttons.get(0));
            }
            if (ea.contains(ActionsE.MOVIMIENTOS)) {
                add(buttons.get(1));
            }
        }
    }
}
