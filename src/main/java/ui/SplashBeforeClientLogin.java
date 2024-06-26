package ui;

import lombok.Data;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public @Data class SplashBeforeClientLogin {
    private JPanel panel;
    private BufferedImage backgroundImage;
    private float alpha = 1f;

    private void createUIComponents() {
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/assets/insert-card-bg.png"));
        } catch (IOException e) {
            System.out.println("Error loading background image");
            Layout.getInstance().showPanel("ClientLogin");
        }

        this.panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                    g2d.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
                    g2d.dispose();
                }
            }
        };

        panel.setVisible(true);

        this.panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Timer timer = new Timer(10, null);
                timer.addActionListener(evt -> {
                    if (alpha > 0) {
                        alpha = Math.round((alpha - 0.01f) * 100.0f) / 100.0f;
                        if (alpha < 0.01f) {
                            alpha = 0f;
                        }
                        panel.repaint();
                    } else {
                        timer.stop();
                        backgroundImage = null;
                        Layout.getInstance().showPanel("ClientLogin");
                    }
                });
                timer.start();
            }
        });
    }
}
