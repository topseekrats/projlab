package com.topseekrats;

import java.io.Serializable;

/**
 * Játékmező objektum, szingleton
 */
public class Maze implements Serializable {

    private static final long serialVersionUID = 1L;

    public int zpmOnMap = 15;
    public int zpmPickUpCounter = 0;
    public int[] replicatorPosition = new int[2];
    public int[][] actorsPosition = new int[2][2];
    public int[][] stargateEndPoints = new int[][] {{-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}};
    public boolean replicatorLives = false;
    public MazeObjectWrapper[][] playField = new MazeObjectWrapper[20][20];
    public MoveDirection[] moveDirection = new MoveDirection[2];

    private static Maze instance = new Maze();

    private Maze() {}

    public static Maze getInstance() { return instance; }

}
