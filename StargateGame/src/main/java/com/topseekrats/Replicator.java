package com.topseekrats;

import com.topseekrats.background.Cleft;
import com.topseekrats.background.Floor;
import java.util.Random;

/**
 * A labirintusban egy replikátor véletlenszerűen menetel, de csak olyan
 * helyeken, ahol az ezredes is tud menni.
 * Semmivel, az ezredessel sem konfrontálódik (egyszerre állhat ugyanazon
 * a mezőn más elemekkel).
 * Ha a replikátort golyó éri, akkor elpusztul és eltűnik.
 * Ha a replikátor szakadékba esik, akkor a szakadék normál mezővé alakul át,
 * és a replikátor eltűnik.
 */
public class Replicator implements MazeObject {

    private Random r = new Random();

    /**
     * Replikátor konstruktor.
     *
     * Replikátor létrejöttekor a labirintus objektumban is meg kell adni a megfelelő értéket.
     */
    public Replicator(){ Maze.getInstance().replicatorLives = true; }

    /** Replikátor véletlenszerű mozgásának megvalósítása. */
    @Override
    public void move() {
        //Replikátort kivesszük az őt tartalmazó wrapperből
        int[] pos = Maze.getInstance().replicatorPosition.clone();
        int[] oldPos = pos.clone();

//        Log.log("Replicator POS: " + pos[1] + "," + pos[0]);

        int dir = r.nextInt(4);

        switch (dir) {
            case 0:
                if (pos[0] > 0) pos[0] -= 1; // one row up
                break;
            case 1:
                if (pos[0] < Maze.getInstance().playField.length-1) pos[0] += 1; // one row down
                break;
            case 2:
                if (pos[1] > 0) pos[1] -= 1; // one column left/back
                break;
            case 3:
                if (pos[1] < Maze.getInstance().playField[0].length-1) pos[1] += 1; // one column right/forward
                break;
            // ne rakjunk mar ide default agat amikor soha nem futhat le.
        }

        if (Maze.getInstance().playField[pos[0]][pos[1]].getBackground() != null &&
                !Maze.getInstance().playField[pos[0]][pos[1]].getBackground().isPassable()) return;
        Maze.getInstance().playField[oldPos[0]][oldPos[1]].setReplicator(null);

        // Ha szakadékra lépett.
        /*
        if (Maze.getInstance().playField[pos[1]][pos[0]].getBackground() instanceof Cleft) {
            Maze.getInstance().playField[pos[1]][pos[0]].setBackground(new Floor());
            Maze.getInstance().replicatorLives = false;
            return;
        }
        */
        // maradok az (x = 0.index = sorok) konvenciónál, ha nem oké, egységesen kell javítani
        if (Maze.getInstance().playField[pos[0]][pos[1]].getBackground() instanceof Cleft) {
            Maze.getInstance().playField[pos[0]][pos[1]].setBackground(new Floor());
            Maze.getInstance().replicatorLives = false;
            return;
        }

        // Új mezőre lépés kezelése
        Maze.getInstance().playField[pos[0]][pos[1]].setReplicator(this);
        Maze.getInstance().replicatorPosition = pos.clone(); // ez is lemaradt
    }

    @Override
    public boolean isForeground() { return false; }

    @Override
    public void changeBullet() {}

    @Override
    public void dropBox() {}

    @Override
    public void pickUp() {}

    @Override
    public void shoot() {}
}
