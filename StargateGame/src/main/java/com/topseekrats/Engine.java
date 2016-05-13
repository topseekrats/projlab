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

/**
 * A játékkal kapcsolatos általános műveletekért felelős osztály.
 * Ilven műveletek pl. az új játék indítása, játék mentése, játék
 * betöltése stb, melyek a teljes játékra hatással vannak.
 */
public final class Engine {

    /**
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
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

    /**
     *
     * @param filePath betöltendő térkép fájl útvonala
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void load(String filePath) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
        Maze temp = (Maze)in.readObject();
        in.close();
        Maze.getInstance().playField = temp.playField;
        Maze.getInstance().zpmOnMap = temp.zpmOnMap;
    }

    public static void victory(ActorType actorType) {
    }

    public static void death(ActorType actorType) {
        if (actorType == ActorType.COLONEL) {
            int x = Maze.getInstance().actorsPosition[0][0];
            int y = Maze.getInstance().actorsPosition[0][1];
            Maze.getInstance().playField[x][y].setActor(0, null);
        } else if (actorType == ActorType.JAFFA) {
            int x = Maze.getInstance().actorsPosition[1][0];
            int y = Maze.getInstance().actorsPosition[1][1];
            Maze.getInstance().playField[x][y].setActor(1, null);
        }

        Console.log(actorType+" died");
    }

    public static void draw(){
    }

    /**
     * ZPM generálása egy véletlenszerű helye.
     * A metódus figyelembe veszi, hogy ZPM kizárólag egy üres padlóra
     * helyezhető el.
     */
    public static void generateRandomZpm() {
        Random r = new Random();
        int[] pos = new int[2];

        // A pozíció addig változik, amíg nem egy üres padlóra mutat.
        do {
            pos[0] = r.nextInt(20);
            pos[1] = r.nextInt(20);
        } while(!(Maze.getInstance().playField[pos[0]][pos[1]].getBackground() instanceof Floor) &&
                !(Maze.getInstance().playField[pos[0]][pos[1]].getForegrounds().empty()));

        // Pályán levő ZPM-szám növelése.
        ++Maze.getInstance().zpmOnMap;

        // ZPM hozzáadása a megfelelő helyre.
        Maze.getInstance().playField[pos[0]][pos[1]].pushForeground(new Item(ItemType.ZPM));
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
     * Replikátor mozgatása
     */
    public static void replicatorMoving(){
        while(Maze.getInstance().replicatorLives){
            int[] pos = Maze.getInstance().replicatorPosition;
            Maze.getInstance().playField[pos[0]][pos[1]].getReplicator().move();
        }
    }
}
