package com.topseekrats.ui;

import com.topseekrats.ActorType;
import com.topseekrats.Engine;
import com.topseekrats.Maze;
import com.topseekrats.MazeObjectWrapper;
import com.topseekrats.MoveDirection;
import com.topseekrats.background.*;
import com.topseekrats.foreground.*;

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
            Engine.newGame();
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

        drawMap(g);
        drawEnd(g);
        repaint();
    }

    private void drawMap(Graphics g) {
        // Teljes játéktér bejárása.
        for (int i = 0; i < Maze.getInstance().playField.length; i++) {
            for (int j = 0; j < Maze.getInstance().playField[0].length; j++) {
                // Aktuális mező lekérdezése.
                MazeObjectWrapper field = Maze.getInstance().playField[i][j];

                // Háttérobjektum rajzolása.
                if (field.getBackground() instanceof Cleft) {
                    g.setColor(new Color(50, 40, 25));
                    g.fillRect(j * titleWidth, i * titleHeight, titleWidth, titleHeight);
                }
                else if (field.getBackground() instanceof Floor) {
                    g.setColor(new Color(195, 195, 195));
                    g.fillRect(j * titleWidth, i * titleHeight, titleWidth, titleHeight);
                }
                else if (field.getBackground() instanceof Door) {
                    if (field.getBackground().isPassable())
                        g.drawImage(tiles.doorOpened, j * titleWidth, i * titleHeight, null);
                    else
                        g.drawImage(tiles.doorClosed, j * titleWidth, i * titleHeight, null);
                }
                else if (field.getBackground() instanceof Wall) {
                    if (((Wall)field.getBackground()).isPortalCompatible())
                        g.drawImage(tiles.wallSpecial, j * titleWidth, i * titleHeight, null);
                    else
                        g.drawImage(tiles.wallSimple, j * titleWidth, i * titleHeight, null);
                }
                else
                    g.drawImage(tiles.switchButton, j * titleWidth, i * titleHeight, new Color(195, 195, 195), null);

                // Ha szükséges, előtérobjektum rajzolása.
                if (!field.getForegrounds().empty()) {
                    if (field.peekForeground() instanceof Item) {
                        if (((Item)field.peekForeground()).getType() == ItemType.BOX)
                            g.drawImage(tiles.box, j * titleWidth, i * titleHeight, null);
                        else
                            g.drawImage(tiles.zpm, j * titleWidth, i * titleHeight, null);
                    }
                    else if (field.peekForeground() instanceof Bullet) {
                        if (((Bullet)field.peekForeground()).getType() == BulletType.YELLOW)
                            g.drawImage(tiles.portalYellow, j * titleWidth, i * titleHeight, null);
                        else if (((Bullet)field.peekForeground()).getType() == BulletType.BLUE)
                            g.drawImage(tiles.portalBlue, j * titleWidth, i * titleHeight, null);
                        else if (((Bullet)field.peekForeground()).getType() == BulletType.GREEN)
                            g.drawImage(tiles.portalGreen, j * titleWidth, i * titleHeight, null);
                        else
                            g.drawImage(tiles.portalRed, j * titleWidth, i * titleHeight, null);
                    }
                    else {
                        if (((Stargate)field.peekForeground()).getOwner() == ActorType.COLONEL)
                            g.drawImage(tiles.stargateColonel, j * titleWidth, i * titleHeight, null);
                        else
                            g.drawImage(tiles.stargateJaffa, j * titleWidth, i * titleHeight, null);
                    }
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

    private void drawEnd(Graphics g) {
        if (Engine.END) {
            switch (Engine.END_TYPE) {
                case 0:
                    // Döntetlen.
                    g.drawImage(tiles.draw, 0, 0, null);
                    break;
                case 1:
                    // Colonel nyer.
                    g.drawImage(tiles.win, 0, 0, null);
                    break;
                case 2:
                    // Colonel veszít.
                    g.drawImage(tiles.lose, 0, 0, null);
                    break;
                default:
                    //
                    break;
            }
        }
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        if (Engine.END) return;

        Maze maze = Maze.getInstance();

        // Játékospozíciók lekérdezése.
        int[] colonelPos = maze.actorsPosition[ActorType.COLONEL.ordinal()];
        int[] jaffaPos = maze.actorsPosition[ActorType.JAFFA.ordinal()];

        // Colonel mozgatása.
        boolean colonelMoves = false;
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
        }
        if (colonelMoves) {
            int[] pos = maze.actorsPosition[ActorType.COLONEL.ordinal()];
            maze.playField[pos[0]][pos[1]].getActor(ActorType.COLONEL).move();
        }

        // Colonel lövés kezelése.
        if (e.getKeyCode() == KeyEvent.VK_P)
            maze.playField[colonelPos[0]][colonelPos[1]].getActor(ActorType.COLONEL).shoot();

        // Colonel tölténytípus váltás kezelése.
        if (e.getKeyCode() == KeyEvent.VK_L)
            maze.playField[colonelPos[0]][colonelPos[1]].getActor(ActorType.COLONEL).changeBullet();


        // Colonel tárgyfelvétel kezelése.
        if (e.getKeyCode() == KeyEvent.VK_O)
            maze.playField[colonelPos[0]][colonelPos[1]].getActor(ActorType.COLONEL).pickUp();

        // Colonel doboz lerakás kezelése.
        if (e.getKeyCode() == KeyEvent.VK_K)
            maze.playField[colonelPos[0]][colonelPos[1]].getActor(ActorType.COLONEL).dropBox();

        // Jaffa mozgatása.
        boolean jaffaMoves = false;
        if (e.getKeyCode() == KeyEvent.VK_W) {
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
        if (jaffaMoves)
            maze.playField[jaffaPos[0]][jaffaPos[1]].getActor(ActorType.JAFFA).move();

        // Jaffa lövés kezelése.
        if (e.getKeyCode() == KeyEvent.VK_F)
            maze.playField[jaffaPos[0]][jaffaPos[1]].getActor(ActorType.JAFFA).shoot();

        // Jaffa tölténytípus váltás kezelése.
        if (e.getKeyCode() == KeyEvent.VK_R)
            maze.playField[jaffaPos[0]][jaffaPos[1]].getActor(ActorType.JAFFA).changeBullet();


        // Jaffa tárgyfelvétel kezelése.
        if (e.getKeyCode() == KeyEvent.VK_E)
            maze.playField[jaffaPos[0]][jaffaPos[1]].getActor(ActorType.JAFFA).pickUp();

        // Jaffa doboz lerakás kezelése.
        if (e.getKeyCode() == KeyEvent.VK_Q)
            maze.playField[jaffaPos[0]][jaffaPos[1]].getActor(ActorType.JAFFA).dropBox();
    }

    @Override
    public void keyTyped(final KeyEvent e) {}

    @Override
    public void keyPressed(final KeyEvent e) {}
}
