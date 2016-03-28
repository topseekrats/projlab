package com.topseekrats;

public class Engine {
    public void newGame() {
        System.out.println("Engine.newGame() called");
        System.out.println("Creating a 2x2 Maze.");
        System.out.println("Maze constructor called");
        new Maze();
        System.out.println("Maze constructor returned");
        for (int i = 0; i < 4; ++i) {
            new MazeObjectWrapper().setForeground();
        }
        System.out.println("Engine.newGame() returned");
    }

    public void save() {
    }

    public void load() {
    }

    public void victory() {
        System.out.println("Engine.victory() called");
        System.out.println("Engine.victory() returned");
    }

    public void death() {
        System.out.println("Engine.death() called");
        System.out.println("Engine.death() returned");

    }
}
