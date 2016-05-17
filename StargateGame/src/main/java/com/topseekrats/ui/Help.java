package com.topseekrats.ui;

import java.awt.*;

import javax.swing.*;

public class Help extends JFrame {
    public static JTextArea textArea = null;
    private JPanel panel = null;
    private String hintString = "Colonel:\n" +
            "Arrows - move\n" +
            "K - drop box\n" +
            "O - pick up item\n" +
            "P - shoot\n" +
            "L - switch\n" +
            "\n" +
            "Jaffa:\n" +
            "WASD - move\n" +
            "Q - drop box\n" +
            "E - pick up item\n" +
            "F - shoot\n" +
            "R - switch";

    public Help() {
        setTitle("Help");
        panel = new JPanel(new BorderLayout());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        textArea = new JTextArea();

        textArea.setEditable(false);
        textArea.setText(hintString);
        panel.add(textArea);

        add(panel);
        setLayout(new FlowLayout());
        setResizable(false);
        pack();

        int newX = 50;
        int newY = 100;

        setLocation(newX, newY);
        setVisible(true);

    }
}
