package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Window extends JFrame {
    public Window() {
        Layout.getInstance().setMainFrame(this);
        setTitle("OBank");
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        Color defaultColor = new Color(72,61,139);
        Color textColor = new Color(255,255,255);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(defaultColor);

        JButton back = new JButton("<");
        back.setFocusable(false);
        back.setForeground(textColor);
        back.setContentAreaFilled(false);
        back.setBackground(defaultColor);

        JLabel menuLabel = new JLabel("OBank");
        menuLabel.setForeground(textColor);

        JButton close = new JButton("X");
        close.setFocusable(false);
        close.setForeground(textColor);
        close.setContentAreaFilled(false);
        close.setBackground(defaultColor);

        JButton minimize = new JButton("-");
        minimize.setFocusable(false);
        minimize.setForeground(textColor);
        minimize.setContentAreaFilled(false);
        minimize.setBackground(defaultColor);

        close.addActionListener(e -> System.exit(0));
        minimize.addActionListener(e -> setState(Frame.ICONIFIED));
        back.addActionListener(e -> {
            Layout.getInstance().goBack();
            //back.setVisible(!Layout.getInstance().getActivePanel().equals("Welcome"));
            //TODO: enable back button when out of welcome view
        });

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false);
        leftPanel.add(back);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        rightPanel.add(minimize);
        rightPanel.add(close);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);
        centerPanel.add(menuLabel);

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);
        panel.add(centerPanel, BorderLayout.CENTER);

        MouseAdapter mouseAdapter = new MouseAdapter() {
            private Point origin;

            @Override
            public void mousePressed(MouseEvent e) {
                origin = new Point(e.getPoint());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (origin != null) {
                    Point point = getLocation();
                    setLocation(point.x + e.getX() - origin.x, point.y + e.getY() - origin.y);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                origin = null;
            }
        };

        panel.addMouseListener(mouseAdapter);
        panel.addMouseMotionListener(mouseAdapter);

        add(panel, BorderLayout.NORTH);

        setVisible(true);
    }
}
