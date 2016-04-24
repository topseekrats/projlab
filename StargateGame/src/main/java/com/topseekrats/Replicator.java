package com.topseekrats;

import com.topseekrats.background.Cleft;
import com.topseekrats.background.Floor;
import java.util.Random;

/**
 * <p>
 *     A labirintusban egy replikátor véletlenszerűen menetel,
 *     de csak olyan helyeken, ahol az ezredes is tud menni.
 *     Semmivel, az ezredessel sem konfrontálódik (egyszerre állhat ugyanazon a mezőn más elemekkel).
 *     Ha a replikátort golyó éri, akkor elpusztul és eltűnik.
 *     Ha a replikátor szakadékba esik, akkor a szakadék normál mezővé alakul át, és a replikátor eltűnik.
 * </p>
 */
public class Replicator implements MazeObject {

    private MoveDirection md = MoveDirection.RIGHT;
    private Random r = new Random();

    /**
     * random movement
     */
    @Override
    public void move() {
        //Replikátort kivesszük az őt tartalmazó wrapperből
        int[] pos = Maze.getInstance().replicatorPosition;
        int[] oldPos = pos;

//        log("Replicator POS: " + pos[1] + "," + pos[0]);

        int dir = r.nextInt(4);
//        log("dir=" + dir);

        switch (dir) {
            case 0:
                md = MoveDirection.UP;
                if (pos[1] > 0) pos[0] -= 1; // one row up
                break;
            case 1:
                md = MoveDirection.DOWN;
                if (pos[1] < Maze.getInstance().playField.length-1) pos[0] += 1; // one row down
                break;
            case 2:
                md = MoveDirection.LEFT;
                if (pos[1] > 0) pos[1] -= 1; // one column left/back
                break;
            case 3:
                md = MoveDirection.RIGHT;
                if (pos[1] < Maze.getInstance().playField[0].length-1) pos[1] += 1; // one column right/forward
                break;
            default:
                md = MoveDirection.RIGHT;
                if (pos[1] < Maze.getInstance().playField[0].length-1) pos[1] += 1; // one column right/forward
                break;
        }

        if (!Maze.getInstance().playField[pos[0]][pos[1]].getBackground().isPassable()) return;
        Maze.getInstance().playField[oldPos[0]][oldPos[1]].setReplicator(null);

        // Ha szakadékra lépett.
        if (Maze.getInstance().playField[pos[1]][pos[0]].getBackground() instanceof Cleft) {
            Maze.getInstance().playField[pos[1]][pos[0]].setBackground(new Floor());
            return;
        }

        //Új mezőre lépés kezelése
        Maze.getInstance().playField[pos[0]][pos[1]].setReplicator(this);

//        log("md=" + md);
//        log("rep POS: " + pos[1] + "," + pos[0]);
    }

//    private void log(String s) { System.out.println(s); }

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
