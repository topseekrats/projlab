package com.topseekrats.ui;

import com.topseekrats.Maze;
import com.topseekrats.Replicator;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Panel extends JPanel {

    private final MazePanel mazePanel;
    public final static int INTERVAL = 100;


    public Panel() {
        setLayout(new BorderLayout());

        mazePanel = new MazePanel();
        Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        mazePanel.setBorder(border);
        add(mazePanel, BorderLayout.CENTER);

        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (Maze.getInstance().replicatorLives) {
                    int xPosRep = Maze.getInstance().replicatorPosition[0];
                    int yPosRep = Maze.getInstance().replicatorPosition[1];
                    Replicator rep = Maze.getInstance().playField[xPosRep][yPosRep].getReplicator();
                    if (rep != null) {
                        rep.move();
                    }
                }
                mazePanel.repaint();
            }
        });

        timer.start();
    }

}
