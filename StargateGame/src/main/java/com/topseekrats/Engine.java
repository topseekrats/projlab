package com.topseekrats;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class Engine {

    public static void newGame() {
    }

    public static void save() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("maps/default.sgmap"));
        out.writeObject(Maze.getInstance());
        out.close();
    }

    public static Maze load() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("maps/default.sgmap"));
        Maze temp = (Maze)in.readObject();
        in.close();
        return temp;
    }

    public static void victory(ActorType actorType) {
    }

    public static void death(ActorType actorType) {
    }

}
