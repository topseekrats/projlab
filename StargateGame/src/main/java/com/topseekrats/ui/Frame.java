package com.topseekrats.ui;

import com.topseekrats.Engine;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

/**
 * A játék ablakát megjelenítő osztály.
 */
public class Frame extends JFrame {

    private Panel panel;
    private JMenuBar menuBar;

    /**
     * Frame konstruktor.
     */
    public Frame() {
        setTitle("Frame");

        panel = new Panel();
        this.add(panel);
        createMenuBar();

        this.setResizable(false);
        packAndCenter();
        this.setVisible(true);

        createBufferStrategy(3);
        repaint();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * Menük és menüelemek létrehozása, konfigurációja.
     */
    private void createMenuBar() {
        menuBar = new JMenuBar();

        // Menük.
        JMenu fileMenu = new JMenu("File");
        JMenu gameMenu = new JMenu("Game");

        // Menüelemek.
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem help = new JMenuItem("Help");

        // Új játék indítása.
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Start a new game? Are you sure?", "Confirmation needed", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    try {
                        Engine.newGame();
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });

        // Játék mentése.
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser("save");
                fileChooser.setDialogTitle("Save");
                fileChooser.setSelectedFile(new File("save.sgmap"));
                fileChooser.setFileFilter(new FileNameExtensionFilter("sgmap files (*.sgmap)", "sgmap"));

                int result = fileChooser.showSaveDialog(new JFrame());
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        Engine.save(file.getPath());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });

        // Játék betöltése.
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser("save");
                fileChooser.setDialogTitle("Load");
                fileChooser.setFileFilter(new FileNameExtensionFilter("sgmap files (*.sgmap)", "sgmap"));

                int result = fileChooser.showOpenDialog(new JFrame());
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        Engine.load(file.getPath());
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Segítség megjelenítése.
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                new Help();
            }
        });

        fileMenu.add(save);
        fileMenu.add(load);

        gameMenu.add(newGame);
        gameMenu.add(help);

        menuBar.add(fileMenu);
        menuBar.add(gameMenu);

        setJMenuBar(menuBar);
    }

    /**
     * Az alkalmazás ablakának pozícióját beállító metódus.
     */
    private void packAndCenter() {
        pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();

        int newX = (screenSize.width - frameSize.width) / 2;
        int newY = (screenSize.height - frameSize.height) / 2;

        setLocation(newX, newY);
    }

}
