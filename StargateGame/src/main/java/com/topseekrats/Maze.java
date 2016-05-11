package com.topseekrats;

import java.io.Serializable;

/**
 * A játékteret alkotó labirintust reprezentáló osztály.
 * Az osztály Singleton, vagyis kizárólag egyetlen példánya létezhet.
 */
public class Maze implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Az aktuálisan pályán levő ZPM-ek száma. */
    public int zpmOnMap = 15;

    /** ZPM felvételek számláló változó. */
    public int zpmPickUpCounter = 0;

    /** Replikátor pozíciója. */
    public int[] replicatorPosition = new int[2];

    /** Játékosok pozíciói. */
    public int[][] actorsPosition = new int[2][2];

    /** Csillagkapuk pozíciói. */
    public int[][] stargateEndPoints = new int[][] {{-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}};

    /** Replikátor jelenlétét mutató változó. */
    public boolean replicatorLives = false;

    /** A tényleges játékteret reprezentáló tömb. */
    public MazeObjectWrapper[][] playField = new MazeObjectWrapper[20][20];

    /** Játékosok aktuális mozgásiránya. */
    public MoveDirection[] moveDirection = new MoveDirection[2];

    private static Maze instance = new Maze();

    private Maze() {}

    public static Maze getInstance() { return instance; }

}
