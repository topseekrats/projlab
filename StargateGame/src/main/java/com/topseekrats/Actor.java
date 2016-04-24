package com.topseekrats;

import com.topseekrats.background.Background;
import com.topseekrats.background.Cleft;
import com.topseekrats.background.Door;
import com.topseekrats.background.Switch;
import com.topseekrats.background.Wall;
import com.topseekrats.foreground.Bullet;
import com.topseekrats.foreground.BulletType;
import com.topseekrats.foreground.Item;
import com.topseekrats.foreground.ItemType;
import com.topseekrats.foreground.Stargate;

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
                pos[1] -= 1;
                break;
            case DOWN:
                pos[1] += 1;
                break;
            case LEFT:
                pos[0] -= 1;
                break;
            case RIGHT:
                pos[0] += 1;
                break;
        }
        // A következő mező koordinátája, ahova lépne a játékos
        MazeObjectWrapper wrapper = Maze.getInstance().playField[pos[0]][pos[1]];

        //Csak akkor rakjuk be, hogyha ráléphet a következő mezőre
        if (!wrapper.getBackground().isPassable()) return;

        Maze.getInstance().actorsPosition[type.ordinal()] = pos;
        Maze.getInstance().playField[oldPos[0]][oldPos[1]].setActor(null);

        //Új mezőre lépés kezelése
        Maze.getInstance().playField[pos[0]][pos[1]].setActor(this);

        //Ha mérlegen állt, csökkentjük a rá nehezedő súlyt
        if (Maze.getInstance().playField[oldPos[0]][oldPos[1]].getBackground() instanceof Switch)
            ((Switch)Maze.getInstance().playField[oldPos[0]][oldPos[1]].getBackground()).decrementWeight();

        //Ha mérlegre lép, növeljük a mérlegre nehezedő súlyt
        if (wrapper.getBackground() instanceof Switch) ((Switch)wrapper.getBackground()).incrementWeight();
        // Ha szakadékba esik a játékos, akkor meghal
        else if (wrapper.getBackground() instanceof Cleft) ((Cleft)wrapper.getBackground()).destroy(this);
        //Ha átjárható falra lép, akkor teleportálni kell
        else if (wrapper.getBackground() instanceof Wall) {
            //TODO: teleportálni a játékost
        }
    }

    @Override
    public void shoot() {
        int[][] stargateEndPoints = Maze.getInstance().stargateEndPoints;
        int[] bulletPosition = Maze.getInstance().actorsPosition[type.ordinal()];
        while (true) {
            switch (Maze.getInstance().moveDirection[type.ordinal()]) {
                case UP:
                    bulletPosition[1] -= 1;
                    break;
                case DOWN:
                    bulletPosition[1] += 1;
                    break;
                case LEFT:
                    bulletPosition[0] -= 1;
                    break;
                case RIGHT:
                    bulletPosition[0] += 1;
                    break;
            }
            Background background = Maze.getInstance().playField[bulletPosition[0]][bulletPosition[1]].getBackground();
            if (background.isPassable() == false) {
                if (background instanceof Door) return;
                Wall wall = (Wall)background;
                if (!wall.isPortalCompatible()) return;
                switch (bullet.getType()) {
                    case YELLOW:
                        if (stargateEndPoints[BulletType.YELLOW.ordinal()] == null && stargateEndPoints[BulletType.BLUE.ordinal()] == null) {
                            stargateEndPoints[BulletType.YELLOW.ordinal()] = bulletPosition;
                            Maze.getInstance().playField[bulletPosition[0]][bulletPosition[1]].pushForeground(bullet);
                        }
                        else if (stargateEndPoints[BulletType.YELLOW.ordinal()] != null && stargateEndPoints[BulletType.BLUE.ordinal()] == null) {
                            int[] oldEndPoint = stargateEndPoints[BulletType.YELLOW.ordinal()];
                            Maze.getInstance().playField[oldEndPoint[0]][oldEndPoint[1]].popForeground();
                            stargateEndPoints[BulletType.YELLOW.ordinal()] = bulletPosition;
                        }
                        else if (stargateEndPoints[BulletType.YELLOW.ordinal()] == null && stargateEndPoints[BulletType.BLUE.ordinal()] != null) {
                            stargateEndPoints[BulletType.YELLOW.ordinal()] = bulletPosition;
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
                Maze.getInstance().stargateEndPoints[bullet.getType().ordinal()] = bulletPosition;
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
                pos[1] -= 1;
                break;
            case DOWN:
                pos[1] += 1;
                break;
            case LEFT:
                pos[0] -= 1;
                break;
            case RIGHT:
                pos[0] += 1;
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
        else if ( item.getType() == ItemType.ZPM) ++zpmCount;
        else {
            if (this.item == null) this.item = item;
            else Maze.getInstance().playField[pos[0]][pos[1]].pushForeground(item);
        }
    }

    @Override
    public boolean isForeground() { return false; }
}
