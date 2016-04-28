package com.topseekrats.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Created by Mark-PC10 on 2016. 04. 24..
 */
public class Panel extends JPanel implements MouseMotionListener {

    private final MazePanel mazePanel;
    private final JLabel mouseCoordinatesLabel;


    public Panel() {
        setLayout(new BorderLayout());

        mazePanel = new MazePanel();
        //Border border = BorderFactory.createLineBorder(Color.YELLOW, 5);
        Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        mazePanel.setBorder(border);
        add(mazePanel, BorderLayout.CENTER);


        mouseCoordinatesLabel = new JLabel("x: 0, y: 0");
        add(mouseCoordinatesLabel, BorderLayout.SOUTH);
        addMouseMotionListener(this);

    }



    public void mouseDragged(MouseEvent e) {
        mouseCoordinatesLabel.setText("x: "+e.getPoint().x+", y: "+e.getPoint().y);
    }



    public void mouseMoved(MouseEvent e) {
        mouseCoordinatesLabel.setText("x: "+e.getPoint().x+", y: "+e.getPoint().y);
    }

    private void log(String s) { System.out.println(s); }

}
