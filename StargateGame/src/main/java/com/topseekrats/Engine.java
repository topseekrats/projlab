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

    public static boolean END = false;
    public static int END_TYPE = 0;

    private Engine() {}

    /**
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void newGame() throws IOException, ClassNotFoundException {
        END = false;
        load("maps/default.sgmap");
        Maze.getInstance().zpmOnMap = 10;
        Maze.getInstance().stargateEndPoints = new int[][] {{-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}};
        Maze.getInstance().playField[1][1].setActor(ActorType.COLONEL.ordinal(), new Actor(ActorType.COLONEL));
        Maze.getInstance().actorsPosition[ActorType.COLONEL.ordinal()] = new int[] {1, 1};
        Maze.getInstance().moveDirection[ActorType.COLONEL.ordinal()] = MoveDirection.DOWN;
        Maze.getInstance().playField[18][18].setActor(ActorType.JAFFA.ordinal(), new Actor(ActorType.JAFFA));
        Maze.getInstance().actorsPosition[ActorType.JAFFA.ordinal()] = new int[] {18, 18};
        Maze.getInstance().moveDirection[ActorType.JAFFA.ordinal()] = MoveDirection.LEFT;
        Maze.getInstance().playField[4][7].setReplicator(new Replicator());
        Maze.getInstance().replicatorPosition = new int[] {4, 7};
        Maze.getInstance().replicatorLives = true;
    }

    public static void save(String filePath) throws IOException {
        String extension = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
        if (!extension.equals("sgmap"))
            filePath += ".sgmap";

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath));
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
        END = false;
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
        Maze temp = (Maze)in.readObject();
        in.close();

        Maze.getInstance().playField = temp.playField;
        Maze.getInstance().zpmOnMap = temp.zpmOnMap;

        if (filePath.contains("default.sgmap")) return;

        Maze.getInstance().actorsPosition = temp.actorsPosition;
        Maze.getInstance().replicatorPosition = temp.replicatorPosition;
        Maze.getInstance().replicatorLives = temp.replicatorLives;
        Maze.getInstance().zpmPickUpCounter = temp.zpmPickUpCounter;
        Maze.getInstance().stargateEndPoints = temp.stargateEndPoints;
        Maze.getInstance().moveDirection = temp.moveDirection;
    }

    public static void victory(ActorType actorType) {
        END = true;
        if (actorType == ActorType.COLONEL) END_TYPE = 1;
        else END_TYPE = 2;
    }

    public static void death(ActorType actorType) {
        END = true;
        int[] pos = Maze.getInstance().actorsPosition[actorType.ordinal()];
        Maze.getInstance().playField[pos[0]][pos[1]].setActor(actorType.ordinal(), null);
        if (actorType == ActorType.COLONEL) END_TYPE = 2;
        else END_TYPE = 1;
    }

    public static void draw(){
        END = true;
        END_TYPE = 0;
    }

    /**
     * ZPM generálása egy véletlenszerű helye.
     * A metódus figyelembe veszi, hogy ZPM kizárólag egy üres padlóra
     * helyezhető el.
     */
    public static void generateRandomZpm() {
        Random r = new Random();
        int[] pos;

        // A pozíció addig változik, amíg nem egy üres padlóra mutat.
        do {
            pos = new int[] {r.nextInt(20),r.nextInt(20)};
        } while(!(Maze.getInstance().playField[pos[0]][pos[1]].getBackground() instanceof Floor) ||
                !(Maze.getInstance().playField[pos[0]][pos[1]].getForegrounds().empty()));

        // Pályán levő ZPM-szám növelése.
        ++Maze.getInstance().zpmOnMap;

        // ZPM hozzáadása a megfelelő helyre.
        Maze.getInstance().playField[pos[0]][pos[1]].pushForeground(new Item(ItemType.ZPM));
    }

    /**
     * Eldönti, hogyan ért véget a játék.
     */
    public static void finish() {
        int[] colPos = Maze.getInstance().actorsPosition[ActorType.COLONEL.ordinal()];
        int[] jaffaPos = Maze.getInstance().actorsPosition[ActorType.JAFFA.ordinal()];
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
