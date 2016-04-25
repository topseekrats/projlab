package com.topseekrats;

import com.topseekrats.background.Floor;
import com.topseekrats.foreground.Item;
import com.topseekrats.foreground.ItemType;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

public final class Engine {

    public static void newGame() throws IOException, ClassNotFoundException {
        load("maps/default.sgmap");
        Maze.getInstance().playField[2][2].setActor(0, new Actor(ActorType.COLONEL));
        Maze.getInstance().playField[19][19].setActor(1, new Actor(ActorType.JAFFA));
        Maze.getInstance().actorsPosition[0] = new int[] {2,2};
        Maze.getInstance().actorsPosition[1] = new int[] {19,19};
    }

    public static void save() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("maps/save1.sgmap"));
        out.writeObject(Maze.getInstance());
        out.close();
    }

    public static void load() throws IOException, ClassNotFoundException {

    }

    public static void load(String filename) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
        Maze temp = (Maze)in.readObject();
        in.close();
        Maze.getInstance().playField = temp.playField;
        Maze.getInstance().zpmOnMap = temp.zpmOnMap;
    }

    public static void victory(ActorType actorType) {
    }

    public static void death(ActorType actorType) {
    }

    public static void draw(){
    }

    /**
     * random ZPM generálása
     */
    public static void generateRandomZPM() {
        Random r = new Random();
        int[] newZPMCoord = new int[2];
        newZPMCoord[0] = r.nextInt(20);
        newZPMCoord[1] = r.nextInt(20);
        //Amíg nem találtunk olyan padlót, ami szabad, generáljuk a koordinátákat
        while(!(Maze.getInstance().playField[newZPMCoord[0]][newZPMCoord[1]].getBackground() instanceof Floor) &&
                !(Maze.getInstance().playField[newZPMCoord[0]][newZPMCoord[1]].getForegrounds().empty())){
            newZPMCoord[0] = r.nextInt(20);
            newZPMCoord[1] = r.nextInt(20);
        }
        Maze.getInstance().zpmOnMap += 1;
        Maze.getInstance().playField[newZPMCoord[0]][newZPMCoord[1]].pushForeground(new Item(ItemType.ZPM));
    }

    /**
     * Eldönti, hogyan ért véget a játék
     */
    public static void finish() {
        int[] colPos = Maze.getInstance().actorsPosition[0];
        int[] jaffaPos = Maze.getInstance().actorsPosition[1];
        Actor colonel = Maze.getInstance().playField[colPos[0]][colPos[1]].getActor(ActorType.COLONEL);
        Actor jaffa = Maze.getInstance().playField[jaffaPos[0]][jaffaPos[1]].getActor(ActorType.JAFFA);

        if(colonel.getZpmCount() > jaffa.getZpmCount())
            Engine.victory(ActorType.COLONEL);
        else if(colonel.getZpmCount() < jaffa.getZpmCount())
            Engine.victory(ActorType.JAFFA);
        else
            Engine.draw();
    }

    /**
     * Replikátor kivégzése golyó által
     * @param replicatorPos replikátor jelenlegi pozíciója
     */
    public static void killReplicator(int[] replicatorPos) {
        Maze.getInstance().replicatorLives = false;
        Maze.getInstance().playField[replicatorPos[0]][replicatorPos[1]].setReplicator(null);
    }

    /**
     * Replikátor mozgatása
     */
    public static void replicatorMoving(){
        while(Maze.getInstance().replicatorLives){
            int[] pos = Maze.getInstance().replicatorPosition;
            Maze.getInstance().playField[pos[0]][pos[1]].getReplicator().move();
        }
    }
}
