package com.topseekrats.ui;

import com.topseekrats.Maze;
import com.topseekrats.Replicator;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Panel extends JPanel implements MouseMotionListener {

    private final MazePanel mazePanel;
    private final JLabel mouseCoordinatesLabel;
    private final JLabel refreshLabelSetText;
    public final static int INTERVAL = 100;
    public static int timeInt = 0;


    public Panel() {
        setLayout(new BorderLayout());

        mazePanel = new MazePanel();
        Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        mazePanel.setBorder(border);
        add(mazePanel, BorderLayout.CENTER);

        refreshLabelSetText = new JLabel("last refresh: null");
        add(refreshLabelSetText, BorderLayout.SOUTH);

        mouseCoordinatesLabel = new JLabel("x: 0, y: 0");
        add(mouseCoordinatesLabel, BorderLayout.SOUTH);
        addMouseMotionListener(this);

        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //Refresh the panel
                // Replicator mozgatása
                if (Maze.getInstance().replicatorLives) {
                    int xPosRep = Maze.getInstance().replicatorPosition[0];
                    int yPosRep = Maze.getInstance().replicatorPosition[1];
                    Replicator rep = Maze.getInstance().playField[xPosRep][yPosRep].getReplicator();
                    // Console.log("rep pos: "+xPosRep+";"+yPosRep);
                    if (rep != null) {
                        rep.move();
                    }
                }
                mazePanel.repaint();
                timeInt += INTERVAL/100;
                refreshLabelSetText(Integer.toString(timeInt));

            }
        });

        timer.start();

    }

    public void mouseDragged(MouseEvent e) {
        mouseCoordinatesLabel.setText("x: "+e.getPoint().x+", y: "+e.getPoint().y);
    }



    public void mouseMoved(MouseEvent e) {
        mouseCoordinatesLabel.setText("x: "+e.getPoint().x+", y: "+e.getPoint().y);
    }

    public void refreshLabelSetText(String s) {
        mouseCoordinatesLabel.setText("last refresh: "+s);
    }

}
