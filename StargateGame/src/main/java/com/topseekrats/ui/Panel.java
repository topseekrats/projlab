package com.topseekrats.ui;

import com.topseekrats.Maze;
import com.topseekrats.Replicator;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A játékmezőt kirajzoló panelt tartalmazó panel osztálya.
 * Itt került definiálásra a kirajzolást, valamint a replikátor mozgatását
 * végző Timer.
 */
public class Panel extends JPanel {

    private final MazePanel mazePanel;
    public final static int INTERVAL = 100;


    /**
     * Panel konstruktor.
     */
    public Panel() {
        setLayout(new BorderLayout());

        mazePanel = new MazePanel();
        Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        mazePanel.setBorder(border);
        add(mazePanel, BorderLayout.CENTER);

        // Timer konfigurálása.
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // Játéktér újrarajzolása.
                mazePanel.repaint();

                // Ha a replikátor él, akkor annak mozgatása.
                if (!Maze.getInstance().replicatorLives) return;
                int[] pos = Maze.getInstance().replicatorPosition;
                Maze.getInstance().playField[pos[0]][pos[1]].getReplicator().move();


            }
        });

        // Timer indítása.
        timer.start();
    }

}
