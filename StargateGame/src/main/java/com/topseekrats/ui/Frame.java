package com.topseekrats.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Frame extends JFrame {

    private Panel panel;

    public Frame() {
        setTitle("Frame");

        panel = new Panel();
        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );

        packAndCenter();
        setVisible(true);

        createBufferStrategy(3);
        repaint();
    }

    private void packAndCenter() {
        pack();

        // Center frame on screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();

        int newX = (screenSize.width - frameSize.width) / 2;
        int newY = (screenSize.height - frameSize.height) / 2;

        setLocation(newX, newY);
    }

}
