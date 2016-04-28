package com.topseekrats.gui;

import com.topseekrats.*;
import com.topseekrats.background.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Mark-PC10 on 2016. 04. 24..
 */
public class MapPanel extends JPanel {

    public final int rowTitleNum = 20;
    public final int colTitleNum = 20;
    public final int titleWidth = 30;
    public final int titleHeight = titleWidth;
    public final int weight = 2;



    public MapPanel() {
        setPreferredSize(new Dimension(rowTitleNum*titleWidth, colTitleNum*titleHeight));
        // listen to key events
        addKeyListener(KeyMngr.getInstance());

        // Component is focusable so it can receive key events ...
        setFocusable(true);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // drawBackground(g);
        // drawMap(g);
    }

    private void drawBackground(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
    }
/*
    private void drawMap(Graphics g) {
        int[][] map = readMapTest();

        try {
            makeMaze(map);
        } catch (Exception e) {
            e.printStackTrace();
        }


        for (int i=0; i<rowTitleNum; i++) {
            for (int j=0; j<colTitleNum; j++) {
                int value = map[i][j];
                switch (value) {
                    case 0:
                        drawFloor(g, j*titleWidth, i*titleHeight);
                        break;
                    case 1:
                        drawWall(g, j*titleWidth, i*titleHeight);
                        break;
                    case 2:
                        drawCleft(g, j*titleWidth, i*titleHeight);
                        break;
                    case 3:
                        drawDoor(g, j*titleWidth, i*titleHeight);
                        break;
                    case 4:
                        drawSwitch(g, j*titleWidth, i*titleHeight);
                        break;
                    case 5:
                        drawColonel(g, j*titleWidth, i*titleHeight);
                        break;
                    case 6:
                        drawJaffa(g, j*titleWidth, i*titleHeight);
                        break;
                    default:
                        drawFloor(g, j*titleWidth, i*titleHeight);
                        break;
                }
            }
        }
    }
    */

    private void drawFloor(Graphics g, int x, int y) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, titleWidth, titleHeight);
    }

    private void drawWall(Graphics g, int x, int y) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, titleWidth, titleHeight);
    }

    private void drawCleft(Graphics g, int x, int y) {
        g.setColor(Color.RED);
        g.fillRect(x, y, titleWidth, titleHeight);
        //log("x:"+x+" y:"+y+"  titleWidth:"+titleWidth+" titleHeight:"+titleHeight);
    }

    private void drawDoor(Graphics g, int x, int y) {
        g.setColor(Color.ORANGE);
        g.fillRect(x, y, titleWidth, titleHeight);
        //log("x:"+x+" y:"+y+"  titleWidth:"+titleWidth+" titleHeight:"+titleHeight);
    }

    private void drawSwitch(Graphics g, int x, int y) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, titleWidth, titleHeight);
        //log("x:"+x+" y:"+y+"  titleWidth:"+titleWidth+" titleHeight:"+titleHeight);
    }

    private void drawColonel(Graphics g, int x, int y) {
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, titleWidth, titleHeight);
        //log("x:"+x+" y:"+y+"  titleWidth:"+titleWidth+" titleHeight:"+titleHeight);
    }

    private void drawJaffa(Graphics g, int x, int y) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, titleWidth, titleHeight);
        //log("x:"+x+" y:"+y+"  titleWidth:"+titleWidth+" titleHeight:"+titleHeight);
    }

    /*
    private int[][] readMapTest() {
        String userDir = System.getProperty("user.dir");
        // log(userDir);
        File f = new File(userDir + File.separator + "mapTest.txt");

        int[][] mapTest = giveMapTest();

        try {
            mapTest = readFile(f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapTest;

    }
    */

    private int[][] giveMapTest() {
        int[][] mapTest = new int[20][20];

        int[] row00 = {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        int[] row01 = {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        int[] row02 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        int[] row03 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        int[] row04 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        int[] row05 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4};
        int[] row06 = {0,0,0,0,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,1};
        int[] row07 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2};
        int[] row08 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3};
        int[] row09 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        int[] row10 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1};
        int[] row11 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1};
        int[] row12 = {0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,5};
        int[] row13 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        int[] row14 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,0};
        int[] row15 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        int[] row16 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        int[] row17 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        int[] row18 = {0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0};
        int[] row19 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

        mapTest[0] = row00;
        mapTest[1] = row01;
        mapTest[2] = row02;
        mapTest[3] = row03;
        mapTest[4] = row04;
        mapTest[5] = row05;
        mapTest[6] = row06;
        mapTest[7] = row07;
        mapTest[8] = row08;
        mapTest[9] = row09;
        mapTest[10] = row10;
        mapTest[11] = row11;
        mapTest[12] = row12;
        mapTest[13] = row13;
        mapTest[14] = row14;
        mapTest[15] = row15;
        mapTest[16] = row16;
        mapTest[17] = row17;
        mapTest[18] = row18;
        mapTest[19] = row19;

        return mapTest;
    }

    private void log(String s) { System.out.println(s); }

    /*
    private static int[][] readFile(File fin) throws Exception {
        FileInputStream fis = new FileInputStream(fin);

        //Construct BufferedReader from InputStreamReader
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line = null;
        int i=0;
        int[][] mapTest = new int[20][20];
        while ((line = br.readLine()) != null && i < 20) {
            // System.out.println(line);
            String[] splittedLine = line.split(";");
            int[] row = new int[20];
            for (int e = 0; e < splittedLine.length; e++) {
                if (e<20) {
                    int v = 0;
                    row[e] = v;
                    try {
                        v = Integer.parseInt(splittedLine[e]);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    row[e] = v;
                }
            }
            mapTest[i] = row;
            i++;
        }

        br.close();

        return mapTest;
    }
    */

    /*
    private void makeMaze(int[][] map) {
        MazeObjectWrapper[][] mows = Maze.getInstance().playField;

        // inicializálás Floor objektumokkal
        for (int i = 0; i < 20; i++) {
            MazeObjectWrapper[] playFieldRow = new MazeObjectWrapper[20];
            for (int j = 0; j < playFieldRow.length; j++) {
                playFieldRow[j] = new MazeObjectWrapper(new Floor());
            }
            mows[i] = playFieldRow;
        }

        Log.log("mow size: "+mows.length);
        Log.log("mow[0] size: "+mows[0].length);
        Background bg = mows[0][0].getBackground();
        Log.log("mow has: "+bg.toString());

        //int[][] mapTest = map;
        // ajtó - kapcsoló párok vannak; eltároljuk az utolsó olvasott ajtót a kapcsoló létrehozásához
        Door lastDoor = null;
        // feltöltés map alapján a megfelelő obejktumokkal
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int v = 0;
                v = map[i][j];
                switch (v) {
                    case 0:
                        //drawFloor
                        mows[i][j].setBackground(new Floor());
                        break;
                    case 1:
                        //drawWall
                        mows[i][j].setBackground(new Wall(false));
                        break;
                    case 2:
                        //drawCleft
                        mows[i][j].setBackground(new Cleft());
                        break;
                    case 3:
                        //drawDoor
                        lastDoor = new Door();
                        mows[i][j].setBackground(lastDoor);
                        break;
                    case 4:
                        //drawSwitch
                        if (lastDoor != null) {
                            mows[i][j].setBackground(new Switch(lastDoor, weight));
                            lastDoor = null;
                        }
                        break;
                    case 5:
                        //drawColonel
                        mows[i][j].setActor(0, new Actor(ActorType.COLONEL));
                        Maze.getInstance().actorsPosition[0][0] = i;
                        Maze.getInstance().actorsPosition[0][1] = j;
                        break;
                    case 6:
                        //drawJaffa
                        mows[i][j].setActor(1, new Actor(ActorType.JAFFA));
                        Maze.getInstance().actorsPosition[1][0] = i;
                        Maze.getInstance().actorsPosition[1][1] = j;
                        break;
                    default:
                        //drawFloor
                        mows[i][j].setBackground(new Floor());
                        break;
                }
            }
        }
        // logMaze();

    }
    */

    /*
    private void logMaze() {
        MazeObjectWrapper[][] mows = Maze.getInstance().playField;
        if (mows.length > 0) {
            for (int i = 0; i < mows.length; i++) {
                for (int j = 0; j < mows[0].length; j++) {
                    log("\t i:"+i+" j:"+j);
                    if (mows[i][j].getBackground() != null) log(mows[i][j].getBackground().toString());
                    if (mows[i][j].getActor(ActorType.COLONEL) != null) log(mows[i][j].getActor(ActorType.COLONEL).toString());
                    if (mows[i][j].getActor(ActorType.JAFFA) != null) log(mows[i][j].getActor(ActorType.JAFFA).toString());
                    if (mows[i][j].getForegrounds() != null) log(mows[i][j].getForegrounds().firstElement().toString());
                    if (mows[i][j].getReplicator() != null) log(mows[i][j].getReplicator().toString());
                }
            }
        }

    }
    */


}