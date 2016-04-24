package com.topseekrats;

import com.topseekrats.background.Background;
import com.topseekrats.background.Door;
import com.topseekrats.background.Wall;
import com.topseekrats.foreground.Bullet;
import com.topseekrats.foreground.BulletType;
import com.topseekrats.foreground.Item;

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
        //Colonel az első játékos, Jaffa a második
        int actorID;
        if(this.type == ActorType.COLONEL)
            actorID = 0;
        else
            actorID = 1;

        //Mezőtől lekérjük az adott actor mozgásirányát
        MoveDirection moveDirection = Maze.getInstance().moveDirection[actorID];
        Maze actualMaze = Maze.getInstance();

        //Aktor mozgásiránya szerint módosítjuk a Maze-ben az elhelyezkedésüket
        switch (moveDirection) {
            case DOWN:
                //y koordináta csökken 1-gyel
                actualMaze.actorsPosition[actorID][1] = actualMaze.actorsPosition[actorID][1]-1;
                break;
            case LEFT:
                //x koordináta csökken 1-gyel
                actualMaze.actorsPosition[actorID][0] = actualMaze.actorsPosition[actorID][0]-1;
                break;
            case RIGHT:
                //x koordináta nő 1-gyel
                actualMaze.actorsPosition[actorID][0] = actualMaze.actorsPosition[actorID][0]+1;
                break;
            case UP:
                //y koordináta nő 1-gyel
                actualMaze.actorsPosition[actorID][1] = actualMaze.actorsPosition[actorID][1]+1;
                break;
            default: break;
        }
    }

    @Override
    public void shoot() {
//        MazeObjectWrapper[][] playField = Maze.getInstance().playField;
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
                if(!wall.isPortalCompatible()) return;
                switch (bullet.getType()) {
                    case YELLOW:
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
        switch(bullet.getType()){
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
        if(item != null)
        {
            //doboz kezeli önmagát?
            //item.dropBox();
            item = null;
        }
    }

    @Override
    public void pickUp(Item item) {
        this.item = item;
    }

    @Override
    public boolean isForeground() { return false; }
}
