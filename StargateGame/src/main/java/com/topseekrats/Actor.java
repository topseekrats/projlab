package com.topseekrats;

import com.topseekrats.background.*;
import com.topseekrats.foreground.*;

public class Actor implements MazeObject {

    private ActorType type;
    private Bullet bullet;
    private Item item;
    private int zpmCount;

    public Actor(ActorType type) {
        this.type = type;
        if (type == ActorType.COLONEL) bullet = new Bullet(BulletType.YELLOW);
        else bullet = new Bullet(BulletType.RED);
    }

    public ActorType getType() { return type; }
    public int getZpmCount() { return  zpmCount; }

    @Override
    public void move() {
        // Maze-től lekérjük az adott játékos mozgásirányát.
        MoveDirection moveDirection = Maze.getInstance().moveDirection[type.ordinal()];

        // Játékos aktuális pozícióját kimentjük, hogy ki tudjuk majd később szedni innen
        int[] pos = Maze.getInstance().actorsPosition[type.ordinal()];
        int[] oldPos = pos;

        // Játékos mozgásiránya szerint módosítjuk az új koordinátákat
        switch (moveDirection) {
            case UP:
                if (pos[0] > 0) pos[1] -= 1;
                else return;
                break;
            case DOWN:
                if (pos[0] < Maze.getInstance().playField.length-1) pos[1] += 1;
                else return;
                break;
            case LEFT:
                if (pos[1] > 0) pos[1] -= 1;
                else return;
                break;
            case RIGHT:
                if (pos[1] < Maze.getInstance().playField[0].length-1) pos[1] += 1;
                else return;
                break;
        }
        // A következő mező koordinátája, ahova lépne a játékos
        MazeObjectWrapper wrapper = Maze.getInstance().playField[pos[0]][pos[1]];

        //Csak akkor rakjuk be, hogyha ráléphet a következő mezőre
        if (!wrapper.getBackground().isPassable()) return;

        Maze.getInstance().actorsPosition[type.ordinal()] = pos;

        //Új mezőre lépés kezelése
        Maze.getInstance().playField[pos[0]][pos[1]].setActor(getType().ordinal(), this);
        Maze.getInstance().playField[oldPos[0]][oldPos[1]].setActor(getType().ordinal(), null);

        //Ha mérlegen állt, csökkentjük a rá nehezedő súlyt
        if (Maze.getInstance().playField[oldPos[0]][oldPos[1]].getBackground() instanceof Switch)
            ((Switch)Maze.getInstance().playField[oldPos[0]][oldPos[1]].getBackground()).decrementWeight();

        //Ha mérlegre lép, növeljük a mérlegre nehezedő súlyt
        if (wrapper.getBackground() instanceof Switch) ((Switch)wrapper.getBackground()).incrementWeight();
        // Ha szakadékba esik a játékos, akkor meghal
        else if (wrapper.getBackground() instanceof Cleft) ((Cleft)wrapper.getBackground()).destroy(this);
        //Ha átjárható falra lép, akkor teleportálni kell
        else if (wrapper.getBackground() instanceof Wall) {
            ((Stargate) wrapper.getForegrounds().peek()).teleport(this);
        }
    }

    @Override
    public void shoot() {
        int[][] stargateEndPoints = Maze.getInstance().stargateEndPoints;
        int[] bulletPos = Maze.getInstance().actorsPosition[type.ordinal()];
        while (true) {
            switch (Maze.getInstance().moveDirection[type.ordinal()]) {
                case UP:
                    if (bulletPos[0] > 0) bulletPos[1] -= 1;
                    else return;
                    break;
                case DOWN:
                    if (bulletPos[0] < Maze.getInstance().playField.length-1) bulletPos[1] += 1;
                    else return;
                    break;
                case LEFT:
                    if (bulletPos[1] > 0) bulletPos[1] -= 1;
                    else return;
                    break;
                case RIGHT:
                    if (bulletPos[1] < Maze.getInstance().playField[0].length-1) bulletPos[1] += 1;
                    else return;
                    break;
            }
            Background background = Maze.getInstance().playField[bulletPos[0]][bulletPos[1]].getBackground();
            if (background.isPassable() == false) {
                if (background instanceof Door) return;
                Wall wall = (Wall)background;
                if (!wall.isPortalCompatible()) return;
                switch (bullet.getType()) {
                    case YELLOW:
                        if (stargateEndPoints[BulletType.YELLOW.ordinal()] == null && stargateEndPoints[BulletType.BLUE.ordinal()] == null) {
                            stargateEndPoints[BulletType.YELLOW.ordinal()] = bulletPos;
                            Maze.getInstance().playField[bulletPos[0]][bulletPos[1]].pushForeground(bullet);
                        }
                        else if (stargateEndPoints[BulletType.YELLOW.ordinal()] != null && stargateEndPoints[BulletType.BLUE.ordinal()] == null) {
                            int[] oldEndPoint = stargateEndPoints[BulletType.YELLOW.ordinal()];
                            Maze.getInstance().playField[oldEndPoint[0]][oldEndPoint[1]].popForeground();
                            stargateEndPoints[BulletType.YELLOW.ordinal()] = bulletPos;
                        }
                        else if (stargateEndPoints[BulletType.YELLOW.ordinal()] == null && stargateEndPoints[BulletType.BLUE.ordinal()] != null) {
                            stargateEndPoints[BulletType.YELLOW.ordinal()] = bulletPos;
                            Stargate stargate = new Stargate(stargateEndPoints[BulletType.YELLOW.ordinal()]);
                        }
                        else {

                        }
                        break;
                    case BLUE:
                        break;
                    case GREEN:
                        break;
                    case RED:
                        break;
                }
                Maze.getInstance().stargateEndPoints[bullet.getType().ordinal()] = bulletPos;
            }

        }
    }

    @Override
    public void changeBullet() {
        switch (bullet.getType()) {
            case BLUE:
                bullet = new Bullet(BulletType.YELLOW);
                break;
            case GREEN:
                bullet = new Bullet(BulletType.RED);
                break;
            case RED:
                bullet = new Bullet(BulletType.GREEN);
                break;
            case YELLOW:
                bullet = new Bullet(BulletType.BLUE);
                break;
        }
    }

    @Override
    public void dropBox() {
        if (item == null) return;
        int[] pos = Maze.getInstance().actorsPosition[type.ordinal()];

        //Maga elé rakja le a dobozt
        switch (Maze.getInstance().moveDirection[type.ordinal()]) {
            case UP:
                if (pos[0] > 0) pos[1] -= 1;
                else return;
                break;
            case DOWN:
                if (pos[0] < Maze.getInstance().playField.length-1) pos[1] += 1;
                else return;
                break;
            case LEFT:
                if (pos[1] > 0) pos[1] -= 1;
                else return;
                break;
            case RIGHT:
                if (pos[1] < Maze.getInstance().playField[0].length-1) pos[1] += 1;
                else return;
                break;
        }

        //Csak akkor dobja el a dobozt, ha az előtte lévő mező átjárható
        //És ha az nem egy ajtó
        Background background = Maze.getInstance().playField[pos[0]][pos[1]].getBackground();
        if (background.isPassable() && !(background instanceof Door)) {
            Maze.getInstance().playField[pos[0]][pos[1]].pushForeground(item);
            item = null;
        }
    }

    @Override
    public void pickUp(){
        // Játékos pozíciója, rá kell lépnie az item helyére
        int[] pos = Maze.getInstance().actorsPosition[type.ordinal()];
        Item item = (Item)Maze.getInstance().playField[pos[0]][pos[1]].popForeground();

        if (item == null) return;
        else if (item.getType() == ItemType.ZPM) {
            ++zpmCount;
            ++Maze.getInstance().zpmCounter;
            Maze.getInstance().actualZpmCount -= 1;
        }
        else {
            if (this.item == null) this.item = item;
            else Maze.getInstance().playField[pos[0]][pos[1]].pushForeground(item);
        }
    }

    @Override
    public boolean isForeground() { return false; }
}
