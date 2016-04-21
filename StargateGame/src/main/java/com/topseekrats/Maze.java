package com.topseekrats;

public final class Maze {

    public static int[][] actorsPosition = new int[2][];
    public static int actualZpmCount;
    public static MazeObjectWrapper[][] playField = new MazeObjectWrapper[20][20];
    public static int[][] stargateEndPoints = new int[2][];
    public static MoveDirection[] moveDirection = new MoveDirection[2];

    private Maze() {}
}
