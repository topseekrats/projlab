package com.topseekrats.gui;

import com.topseekrats.*;
import com.topseekrats.background.*;

import java.io.*;

/**
 * Created by Mark-PC10 on 2016. 04. 26..
 */
public class MazeFileReader {

    public final int weight = 2;
    private String fn = null;
    public int[][] int2D = null;

    public MazeFileReader() {
        this.fn = "mapTest.txt";

    }

    public MazeFileReader(String fn) {
        this.fn = fn;

    }

    public int[][] txtFile2int2D() {
        String userDir = System.getProperty("user.dir");
        // log(userDir);
        File f = new File(userDir + File.separator + fn);

        int[][] mapTest = null; // giveMapTest();

        try {
            Log.log("file2int2D method: readFile "+fn+" starting...");
            mapTest = readFile(f);
            Log.log("file2int2D method: readFile "+fn+" end");
        } catch (Exception e) {
            Log.log("file2int2D method: Exception!!");
            e.printStackTrace();
        }

        return mapTest;

    }

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

    public void makeMaze(int[][] map) {
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

    public int[][] makeMapDummy() {
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

    public int[][] getInt2D() {
        return int2D;
    }

    /**
     * serializált Maze objektumot olvas sgmap típusú fájlból, mejd Maze-be tölti
     */
    public void readSerializedMazeFromFile() {
        MazeObjectWrapper[][] mows = Maze.getInstance().playField;

        // inicializálás Floor objektumokkal
        for (int i = 0; i < 20; i++) {
            MazeObjectWrapper[] playFieldRow = new MazeObjectWrapper[20];
            for (int j = 0; j < playFieldRow.length; j++) {
                playFieldRow[j] = new MazeObjectWrapper(new Floor());
            }
            mows[i] = playFieldRow;
        }

        if (fn != null) {
            Maze maze = Maze.getInstance();
            try {
                String userDir = System.getProperty("user.dir");
                FileInputStream fileIn = new FileInputStream(userDir + File.separator + fn);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                maze = (Maze) in.readObject();
                fileIn.close();
            } catch(IOException i) {
                i.printStackTrace();
            } catch(ClassNotFoundException c) {
                System.out.println("Class not found");
                c.printStackTrace();
            }
        } else {
            Log.log("Fájlnév nincs megadva!");
        }
    }

}
