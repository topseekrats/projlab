package com.topseekrats;

import com.topseekrats.background.Cleft;
import com.topseekrats.background.Floor;
import com.topseekrats.foreground.Item;
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

    private Random r;
    private MoveDirection md = MoveDirection.RIGHT;

    public Replicator() {
        r = new Random();
    }

    /**
     * random movement
     */
    @Override
    public void move() {
        Maze m = Maze.getInstance();

        //Replikátort kivesszük az őt tartalmazó wrapperből
        int xCoord = m.replicatorPosition[0];
        int yCoord = m.replicatorPosition[1];
//        m.playField[xCoord][yCoord].setReplicator(null);

        log("rep POS: "+xCoord+","+yCoord);

        int dir = r.nextInt(4);
        log("dir="+dir);

        switch (dir) {
            case 0:
                setMd(MoveDirection.UP);
                if (m.replicatorPosition[0] == 0) {
                    // first row => do nothing
                } else {
                    m.replicatorPosition[0] = m.replicatorPosition[0] - 1; // one row up
                }
                break;
            case 1:
                setMd(MoveDirection.LEFT);
                if (m.replicatorPosition[1] == 0) {
                    // first column => do nothing
                } else {
                    m.replicatorPosition[1] = m.replicatorPosition[1] - 1; // one column left/back
                }
                break;
            case 2:
                setMd(MoveDirection.DOWN);
                if (m.replicatorPosition[0] == m.playField.length-1) {
                    // last row => do nothing
                } else {
                    m.replicatorPosition[0] = m.replicatorPosition[0] + 1; // one row down
                }
                break;
            case 3:
                setMd(MoveDirection.RIGHT);
                if (m.replicatorPosition[1] == m.playField[0].length) {
                    // last column => do nothing
                } else {
                    m.replicatorPosition[1] = m.replicatorPosition[1] + 1; // one column right/forward
                }
                break;
            default:
                setMd(MoveDirection.RIGHT);
                if (m.replicatorPosition[1] == m.playField[0].length-1) {
                    // last column => do nothing
                } else {
                    m.replicatorPosition[1] = m.replicatorPosition[1] + 1; // one column right/forward
                }
                break;
        }

        //Replikátort berakjuk a következő mezőbe, ahova lépett
        xCoord = m.replicatorPosition[0];
        yCoord = m.replicatorPosition[1];
//        m.playField[xCoord][yCoord].setReplicator(this);

        // ha szakadékra lépett
        if (m.playField[xCoord][yCoord].getBackground() instanceof Cleft) {
            m.playField[xCoord][yCoord].setBackground(new Floor());
        }
        log("md="+getMd());
        log("rep POS: "+xCoord+","+yCoord);

    }

    @Override
    public boolean isForeground() { return false; }

    @Override
    public void changeBullet() {}

    @Override
    public void dropBox() {}

    @Override
    public void pickUp(final Item item) {}


    @Override
    public void shoot() {}


    private MoveDirection getMd() {
        return md;
    }

    public void setMd(MoveDirection md) {
        this.md = md;
    }

    private void log(String s) {
        java.lang.System.out.println(s);
    }


}
