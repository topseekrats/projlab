package com.topseekrats.ui;

import java.awt.*;

import javax.swing.*;

/**
 * A játék irányítását tartalmazó panelt megvalósító osztály.
 */
public class Help extends JFrame {

    public static final String HINT = "<html>Colonel:<br>"
            + "Arrows - Move<br>"
            + "K - Drop box<br>"
            + "O - Pick up item<br>"
            + "P - Shoot<br>"
            +"L - Change Bullet<br><br>"
            + "Jaffa:<br>"
            + "WASD - Move<br>"
            + "Q - Drop box<br>"
            + "E - Pick up item<br>"
            + "F - Shoot<br>"
            + "R - Change bullet</html>";

    private JPanel panel;
    private JLabel label;

    /**
     * Help konstruktor.
     */
    public Help() {
        setTitle("Help");
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        label = new JLabel(HINT);
        label.setFont(new Font("Verdana", 1, 20));

        panel = new JPanel();
        panel.add(label);

        add(panel, new GridBagConstraints());

        pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();

        setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        setSize(300, 450);

        setVisible(true);
    }

}
