package com.topseekrats.ui;

import com.topseekrats.Actor;
import com.topseekrats.ActorType;
import com.topseekrats.Engine;
import com.topseekrats.Maze;
import com.topseekrats.MazeObjectWrapper;
import com.topseekrats.MoveDirection;
import com.topseekrats.background.*;
import com.topseekrats.foreground.Item;
import com.topseekrats.foreground.ItemType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class MazePanel extends JPanel implements KeyListener {

    public final int rowTitleNum = 20;
    public final int colTitleNum = 20;
    public final int titleWidth = 30;
    public final int titleHeight = 30;

    private Tiles tiles;

    public MazePanel() {
        try {
            Engine.load("maps/default.sgmap");
            Maze.getInstance().playField[1][1].setActor(ActorType.COLONEL.ordinal(), new Actor(ActorType.COLONEL));
            Maze.getInstance().actorsPosition[ActorType.COLONEL.ordinal()] = new int[] {1, 1};
            Maze.getInstance().playField[18][18].setActor(ActorType.JAFFA.ordinal(), new Actor(ActorType.JAFFA));
            Maze.getInstance().actorsPosition[ActorType.JAFFA.ordinal()] = new int[] {18, 18};
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        tiles = new Tiles();

        setPreferredSize(new Dimension(rowTitleNum * titleWidth, colTitleNum * titleHeight));
        setFocusable(true);
        setFocusCycleRoot(true);

        addKeyListener(this);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // törlés

        // drawBackground(g);
        if (Maze.getInstance() != null && Maze.getInstance().playField.length > 0) {
            drawMap(g);
            repaint();
        }
    }

    private void drawMap(Graphics g) {
        // Teljes játéktér bejárása.
        for (int i = 0; i < Maze.getInstance().playField.length; i++) {
            for (int j = 0; j < Maze.getInstance().playField[0].length; j++) {
                // Aktuális mező lekérdezése.
                MazeObjectWrapper field = Maze.getInstance().playField[i][j];

                // Háttérobjektum rajzolása.
                if (field.getBackground() instanceof Cleft)
                    g.drawImage(tiles.cleft, j * titleWidth, i * titleHeight, null);
                else if (field.getBackground() instanceof Floor)
                    g.drawImage(tiles.floor, j * titleWidth, i * titleHeight, null);
                else if (field.getBackground() instanceof Switch)
                    g.drawImage(tiles.switchButton, j * titleWidth, i * titleHeight, null);
                else if (field.getBackground() instanceof Wall)
                    g.drawImage(tiles.wall, j * titleWidth, i * titleHeight, null);
                else if (field.getBackground().isPassable())
                    g.drawImage(tiles.doorOpened, j * titleWidth, i * titleHeight, null);
                else
                    g.drawImage(tiles.doorClosed, j * titleWidth, i * titleHeight, null);

                // Ha szükséges, előtérobjektum rajzolása.
                if (!field.getForegrounds().empty()) {
                    if (((Item)field.peekForeground()).getType() == ItemType.BOX)
                        g.drawImage(tiles.box, j * titleWidth, i * titleHeight, null);
                    else
                        g.drawImage(tiles.zpm, j * titleWidth, i * titleHeight, null);
                }

                // Ha szükséges, replikátor rajzolása.
                if (field.getReplicator() != null)
                    g.drawImage(tiles.replicator, j * titleWidth, i * titleHeight, null);

                // Ha szükséges, aktor(ok) rajzolása.
                if (field.getActor(ActorType.COLONEL) != null)
                    g.drawImage(tiles.colonel, j * titleWidth, i * titleHeight, null);
                else if (field.getActor(ActorType.JAFFA) != null) {
                    g.drawImage(tiles.jaffa, j * titleWidth, i * titleHeight, null);
                }
            }
        }
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        Maze maze = Maze.getInstance();

        boolean colonelMoves = false;
        boolean jaffaMoves = false;

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            maze.moveDirection[ActorType.COLONEL.ordinal()] = MoveDirection.UP;
            colonelMoves = true;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            maze.moveDirection[ActorType.COLONEL.ordinal()] = MoveDirection.LEFT;
            colonelMoves = true;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            maze.moveDirection[ActorType.COLONEL.ordinal()] = MoveDirection.DOWN;
            colonelMoves = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            maze.moveDirection[ActorType.COLONEL.ordinal()] = MoveDirection.RIGHT;
            colonelMoves = true;
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            maze.moveDirection[ActorType.JAFFA.ordinal()] = MoveDirection.UP;
            jaffaMoves = true;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            maze.moveDirection[ActorType.JAFFA.ordinal()] = MoveDirection.LEFT;
            jaffaMoves = true;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            maze.moveDirection[ActorType.JAFFA.ordinal()] = MoveDirection.DOWN;
            jaffaMoves = true;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            maze.moveDirection[ActorType.JAFFA.ordinal()] = MoveDirection.RIGHT;
            jaffaMoves = true;
        }

        if (colonelMoves) {
            int[] pos = maze.actorsPosition[ActorType.COLONEL.ordinal()];
            maze.playField[pos[0]][pos[1]].getActor(ActorType.COLONEL).move();
        }

        if (jaffaMoves) {
            int[] pos = maze.actorsPosition[ActorType.JAFFA.ordinal()];
            maze.playField[pos[0]][pos[1]].getActor(ActorType.JAFFA).move();
        }
    }

    @Override
    public void keyTyped(final KeyEvent e) {}

    @Override
    public void keyPressed(final KeyEvent e) {}
}
