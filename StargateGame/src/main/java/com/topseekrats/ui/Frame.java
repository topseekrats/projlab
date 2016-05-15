package com.topseekrats.ui;

import com.topseekrats.Console;
import com.topseekrats.Engine;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Frame extends JFrame {

    private Panel panel;

    public Frame() {
        setTitle("Frame");

        panel = new Panel();
        add(panel);

        createMenuBar();

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

    private void createMenuBar() {

        JMenuBar menubar = new JMenuBar();

        //JMenu menuFile = new JMenu("File");

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setToolTipText("Exit application");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });


        // menu option - load
        final JMenuItem loadItem = new JMenuItem("Load");

        // JFileChooser with filter
        JFileChooser fileChooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("sgmap files (*.sgmap)", "sgmap");
        fileChooser.setFileFilter(filter);

        loadItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int returnVal = fileChooser.showOpenDialog(new JFrame());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        Console.log(file.getAbsolutePath());
                        Console.log(new FileReader(file.getAbsolutePath()).toString());
                        Engine.newGame(file.getAbsolutePath());
                    } catch (IOException ex) {
                        System.out.println("problem accessing file"+file.getAbsolutePath());
                        ex.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("File access cancelled by user.");
                }
            }
        });

        // menu option - save
        final JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Engine.save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // menu option - help
        final JMenuItem helpItem = new JMenuItem("Help");
        helpItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                Help help = new Help();
            }
        });


        menubar.add(loadItem);
        menubar.add(saveItem);
        menubar.add(helpItem);
        menubar.add(exitMenuItem);
        //menubar.add(menuFile);

        setJMenuBar(menubar);
    }

    private void packAndCenter() {
        pack();
        setResizable(false);

        // Center frame on screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();

        int newX = (screenSize.width - frameSize.width) / 2;
        int newY = (screenSize.height - frameSize.height) / 2;

        setLocation(newX, newY);
    }

}
