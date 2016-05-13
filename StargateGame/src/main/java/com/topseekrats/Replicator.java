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
     * Replikátor létrejöttekor a labirintus objektumban is meg kell adni a
     * megfelelő értéket.
     */
    public Replicator(){ Maze.getInstance().replicatorLives = true; }

    /** Replikátor véletlenszerű mozgásának megvalósítása. */
    @Override
    public void move() {
        // Replikátor pozíciójának lekérdezése.
        int[] oldPos = Maze.getInstance().replicatorPosition;
        int[] newPos = oldPos.clone();

        // Replikátor mozgatása véletlenszerűen.
        switch (r.nextInt(4)) {
            // Felfelé mozog.
            case 0:
                if (newPos[0] > 0) newPos[0] -= 1;
                else return;
                break;
            // Lefelé mozog.
            case 1:
                if (newPos[0] < Maze.getInstance().playField.length-1) newPos[0] += 1;
                else return;
                break;
            // Balra mozog.
            case 2:
                if (newPos[1] > 0) newPos[1] -= 1;
                else return;
                break;
            // Jobbra mozog.
            case 3:
                if (newPos[1] < Maze.getInstance().playField[0].length-1) newPos[1] += 1;
                else return;
                break;
        }

        // Ha nem átjárható mezőre lépne, akkor nem lép.
        if (!Maze.getInstance().playField[newPos[0]][newPos[1]].getBackground().isPassable()) return;

        // Ha léphet, először a replikátor objektumot ki kell venni a régi mezőről.
        Maze.getInstance().playField[oldPos[0]][oldPos[1]].setReplicator(null);

        // Ha szakadékba lép, úgy abból padló lesz, a replikátor pedig elpusztul.
        if (Maze.getInstance().playField[newPos[0]][newPos[1]].getBackground() instanceof Cleft) {
            Maze.getInstance().playField[newPos[0]][newPos[1]].setBackground(new Floor());
            Maze.getInstance().replicatorLives = false;
            return;
        }

        // Objektum és koordináták módosítása az új pozícióra.
        Maze.getInstance().playField[newPos[0]][newPos[1]].setReplicator(this);
        Maze.getInstance().replicatorPosition = newPos;
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
