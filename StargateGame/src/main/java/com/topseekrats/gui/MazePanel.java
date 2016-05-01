package com.topseekrats.gui;

import com.topseekrats.ActorType;
import com.topseekrats.Maze;
import com.topseekrats.MazeObjectWrapper;
import com.topseekrats.background.*;
import com.topseekrats.foreground.Bullet;
import com.topseekrats.foreground.Item;
import com.topseekrats.foreground.ItemType;
import com.topseekrats.foreground.Stargate;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.zip.ZipEntry;

/**
 * Created by Mark-PC10 on 2016. 04. 24..
 */
public class MazePanel extends JPanel {

    public final int rowTitleNum = 20;
    public final int colTitleNum = 20;
    public final int titleWidth = 30;
    public final int titleHeight = titleWidth;
    public final int weight = 2;
    private int i = 0;

    private Tile cleftTile = null;
    private Tile doorOpenTile = null;
    private Tile doorClosedTile = null;
    private Tile floorTile = null;
    private Tile switchTile = null;
    private Tile wallTile = null;
    private Tile boxTile = null;
    private Tile zpmTile = null;
    private Tile stargate1Tile = null;
    private Tile stargate2Tile = null;
    private Tile colonelTile = null;
    private Tile jaffaTile = null;
    private Tile replicatorTile = null;


    public MazePanel() {

        // megadott fájlból olvasás int[][] -be, majd int[][] átalakítása playField-dé és eltárolás Maze -be

        /*
        MazeFileReader mfr = new MazeFileReader("StargateGame"+File.separator+"maps"+File.separator+"default.sgmap");
        mfr.readSerializedMazeFromFile();
        */
        // VAGY
        MazeFileReader mfr = new MazeFileReader("StargateGame"+File.separator+"maps"+File.separator+"mapTest.txt");
        mfr.int2D = mfr.txtFile2int2D();
        if (mfr.getInt2D() != null) {
            mfr.makeMaze(mfr.getInt2D());
        } else {
            // ha int[][] null, akkor dummy adatokból playField generálás és eltárolás Maze-be
            mfr.makeMaze(mfr.makeMapDummy());
        }

        initTiles();

        setPreferredSize(new Dimension(rowTitleNum*titleWidth, colTitleNum*titleHeight));
        // listen to key events
        addKeyListener(KeyMngr.getInstance());

        // Component is focusable so it can receive key events ...
        setFocusable(true);
        setFocusCycleRoot(true);
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
        MazeObjectWrapper[][] playFLoc = Maze.getInstance().playField;

        for (int i=0; i<playFLoc.length; i++) {         // playFLoc.length == rowTitleNum
            for (int j=0; j<playFLoc[0].length; j++) {  // playFLoc[0].length == colTitleNum
                MazeObjectWrapper value = playFLoc[i][j];

                // backgroud rajzolása
                if (value.getBackground() != null) {    // ha van háttér elem

                    // ENUM-mal
                    /*
                    int type = value.getBackground().getType().ordinal();
                    //ordinal: Cleft, Door, Floor, Switch, Wall
                    switch (type) {
                        case 0:
                            drawCleft(g, j * titleWidth, i * titleHeight);
                            break;
                        case 1:
                            drawDoor(g, j * titleWidth, i * titleHeight);
                            break;
                        case 2:
                            drawFloor(g, j * titleWidth, i * titleHeight);
                            break;
                        case 3:
                            drawSwitch(g, j * titleWidth, i * titleHeight);
                            break;
                        case 4:
                            drawWall(g, j * titleWidth, i * titleHeight);
                            break;
                        default:
                            drawFloor(g, j * titleWidth, i * titleHeight);
                    }
                    */
                    // instanceof-fal
                    if (value.getBackground() instanceof Cleft) {
                        drawCleft(g, j * titleWidth, i * titleHeight);
                    } else if (value.getBackground() instanceof Door) {
                        drawDoor(g, j * titleWidth, i * titleHeight);
                    } else if (value.getBackground() instanceof Floor) {
                        drawFloor(g, j * titleWidth, i * titleHeight);
                    } else if (value.getBackground() instanceof Switch) {
                        drawSwitch(g, j * titleWidth, i * titleHeight);
                    } else if (value.getBackground() instanceof Wall) {
                        drawWall(g, j * titleWidth, i * titleHeight);
                    }
                    else {
                        drawFloor(g, j * titleWidth, i * titleHeight);
                    }

                } else {
                    // draw Floor
                    drawFloor(g, j * titleWidth, i * titleHeight);
                }

                // foreground rajzolása

                if (!value.getForegrounds().empty()) {    // ha van előtérben elem
                    // ENUM-mal
                    /*
                   int type = value.getForegrounds().firstElement().getFgType().ordinal();
                    //ordinal: Bullet, Item, Stargate
                    switch (type) {
                        case 0:
                            // drawBullet()
                            break;
                        case 1:
                            // drawItem()
                            break;
                        case 2:
                            // drawStargate()
                            break;
                        default:
                            // do nothing
                            break;
                    }
                    */
                    // instanceof-fal
                    if (value.getForegrounds().firstElement() instanceof Bullet) {
                        // drawBullet()
                    } else if (value.getForegrounds().firstElement() instanceof Item) {
                        // drawItem()
                        if (((Item) value.getForegrounds().firstElement()).getType() == ItemType.BOX) {
                            drawBox(g, j * titleWidth, i * titleHeight);
                        } else if (((Item) value.getForegrounds().firstElement()).getType() == ItemType.ZPM) {
                            drawZpm(g, j * titleWidth, i * titleHeight);
                        }
                    } else if (value.getForegrounds().firstElement() instanceof Stargate) {
                        // drawStargate()
                    }
                    else {
                        // draw nothing
                    }
                } else {
                    // draw nothing
                }

                // Replicator
                // külön if-ben kezelve a replicator, mert lehet egyszerre egy helyen az Actor-okkal
                if (value.getReplicator() != null) {
                    drawReplicator(g, j * titleWidth, i * titleHeight);
                } else {
                    // draw nothing
                }

                // Actor-ok
                if (value.getActor(ActorType.COLONEL) != null) {
                    drawColonel(g, j * titleWidth, i * titleHeight);
                } else if (value.getActor(ActorType.JAFFA) != null) {
                    drawJaffa(g, j * titleWidth, i * titleHeight);
                } else {
                    // draw nothing
                }
            }
        }
    }

    private void initTiles() {
        cleftTile = new Tile("szakadek02_3030.jpg");
        doorOpenTile = new Tile("door_open_3030.jpg");
        doorClosedTile = new Tile("door_closed_3030.jpg");
        floorTile = new Tile("floor_3030.jpg");
        switchTile = new Tile("switch_01_3030.jpg");
        wallTile = new Tile("wall_3030.jpg");
        boxTile = new Tile("cube_3030.gif");
        zpmTile = new Tile("zpm_3030.png");
        stargate1Tile = new Tile("stargate_01_3030.jpg");
        stargate2Tile = new Tile("stargate_02_3030.jpg");
        colonelTile = new Tile("colonel_3030.jpg");
        jaffaTile = new Tile("jaffa_3030.jpg");
        replicatorTile = new Tile("replicator_3030.jpg");
    }

    /**
     * BLACK
     * @param g
     * @param x
     * @param y
     */
    private void drawFloor(Graphics g, int x, int y) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, titleWidth, titleHeight);
        g.drawImage(floorTile.getImage(), x, y, null);
    }

    /**
     * BLUE
     * @param g
     * @param x
     * @param y
     */
    private void drawWall(Graphics g, int x, int y) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, titleWidth, titleHeight);
        g.drawImage(wallTile.getImage(), x, y, null);
    }

    /**
     * RED
     * @param g
     * @param x
     * @param y
     */
    private void drawCleft(Graphics g, int x, int y) {
        g.setColor(Color.RED);
        g.fillRect(x, y, titleWidth, titleHeight);
        //log("x:"+x+" y:"+y+"  titleWidth:"+titleWidth+" titleHeight:"+titleHeight);
        g.drawImage(cleftTile.getImage(), x, y, null);
    }

    /**
     * ORANGE
     * @param g
     * @param x
     * @param y
     */
    private void drawDoor(Graphics g, int x, int y) {
        g.setColor(Color.ORANGE);
        g.fillRect(x, y, titleWidth, titleHeight);
        //log("x:"+x+" y:"+y+"  titleWidth:"+titleWidth+" titleHeight:"+titleHeight);
        g.drawImage(doorClosedTile.getImage(), x, y, null);
    }

    /**
     * GREEN
     * @param g
     * @param x
     * @param y
     */
    private void drawSwitch(Graphics g, int x, int y) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, titleWidth, titleHeight);
        //log("x:"+x+" y:"+y+"  titleWidth:"+titleWidth+" titleHeight:"+titleHeight);
        g.drawImage(switchTile.getImage(), x, y, null);
    }

    /**
     * MAGENTA
     * @param g
     * @param x
     * @param y
     */
    private void drawColonel(Graphics g, int x, int y) {
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, titleWidth, titleHeight);
        //log("x:"+x+" y:"+y+"  titleWidth:"+titleWidth+" titleHeight:"+titleHeight);
        g.drawImage(colonelTile.getImage(), x, y, null);
    }

    /**
     * YELLOW
     * @param g
     * @param x
     * @param y
     */
    private void drawJaffa(Graphics g, int x, int y) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, titleWidth, titleHeight);
        //log("x:"+x+" y:"+y+"  titleWidth:"+titleWidth+" titleHeight:"+titleHeight);
        g.drawImage(jaffaTile.getImage(), x, y, null);
    }

    /**
     * WHITE
     * @param g
     * @param x
     * @param y
     */
    private void drawReplicator(Graphics g, int x, int y) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, titleWidth, titleHeight);
        //log("x:"+x+" y:"+y+"  titleWidth:"+titleWidth+" titleHeight:"+titleHeight);
        g.drawImage(replicatorTile.getImage(), x, y, null);
    }

    /**
     * ZPM
     * @param g
     * @param x
     * @param y
     */
    private void drawZpm(Graphics g, int x, int y) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, titleWidth, titleHeight);
        //log("x:"+x+" y:"+y+"  titleWidth:"+titleWidth+" titleHeight:"+titleHeight);
        g.drawImage(zpmTile.getImage(), x, y, null);
    }

    /**
     * Box
     * @param g
     * @param x
     * @param y
     */
    private void drawBox(Graphics g, int x, int y) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, titleWidth, titleHeight);
        //log("x:"+x+" y:"+y+"  titleWidth:"+titleWidth+" titleHeight:"+titleHeight);
        g.drawImage(boxTile.getImage(), x, y, null);
    }

}