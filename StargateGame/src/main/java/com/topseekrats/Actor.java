package com.topseekrats;

import com.topseekrats.background.Background;
import com.topseekrats.background.Door;
import com.topseekrats.background.Wall;
import com.topseekrats.foreground.Bullet;
import com.topseekrats.foreground.BulletType;
import com.topseekrats.foreground.Item;
import com.topseekrats.foreground.ItemType;

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
        //Mezőtől lekérjük az adott actor mozgásirányát
        MoveDirection moveDirection = Maze.getInstance().moveDirection[type.ordinal()];
        Maze actualMaze = Maze.getInstance();

        //Aktort kivesszük az őt tartalmazó wrapperből
        int xCoord = actualMaze.actorsPosition[type.ordinal()][0];
        int yCoord = actualMaze.actorsPosition[type.ordinal()][1];
        actualMaze.playField[xCoord][yCoord].setActor(null);

        //Aktor mozgásiránya szerint módosítjuk a Maze-ben az elhelyezkedésüket
        switch (moveDirection) {
            case DOWN:
                //y koordináta csökken 1-gyel
                actualMaze.actorsPosition[type.ordinal()][1] += 1;
                break;
            case LEFT:
                //x koordináta csökken 1-gyel
                actualMaze.actorsPosition[type.ordinal()][0] += 1;
                break;
            case RIGHT:
                //x koordináta nő 1-gyel
                actualMaze.actorsPosition[type.ordinal()][0] -= 1;
                break;
            case UP:
                //y koordináta nő 1-gyel
                actualMaze.actorsPosition[type.ordinal()][1] -= 1;
                break;
            default: break;
        }

        //Aktort berakjuk a következő mezőbe, ahova lépett
        xCoord = actualMaze.actorsPosition[type.ordinal()][0];
        yCoord = actualMaze.actorsPosition[type.ordinal()][1];
        actualMaze.playField[xCoord][yCoord].setActor(this);
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
            Maze actualMaze = Maze.getInstance();
            //itt kezeljük az eldobást
            int xCoord = actualMaze.actorsPosition[type.ordinal()][0];
            int yCoord = actualMaze.actorsPosition[type.ordinal()][1];

            //Maga elé rakja le a dobozt
            MoveDirection playerDirection = actualMaze.moveDirection[type.ordinal()];
            switch(playerDirection){
                case DOWN:
                    yCoord += 1;
                    break;
                case LEFT:
                    xCoord += 1;
                    break;
                case RIGHT:
                    xCoord -= 1;
                    break;
                case UP:
                    yCoord -= 1;
                    break;
            }
            //Csak akkor dobja el a dobozt, ha az előtte lévő mező átjárható
            //TODO:
            //Így nyitott ajtót is el tud torlaszolni
            //Kérdés az, hogy ha lerak oda egy dobozt, és becsukódik az ajtó, mi történjen,
            //De ezt az ajtónyitásnál lekezeljük
            if(actualMaze.playField[xCoord][yCoord].getBackground().isPassable()) {
                actualMaze.playField[xCoord][yCoord].pushForeground(item);
                item = null;
            }
        }
    }

    //Játékos felszedi az alatta heverő item-et
    //F gombnyomásra meghívódik ez a fv és átadódik az aktuális item
    @Override
    public void pickUp(Item item){

        Maze actualMaze = Maze.getInstance();
        //játékos pozíciója, rá kell lépnie az item helyére
        int xCoord = actualMaze.actorsPosition[type.ordinal()][0];
        int yCoord = actualMaze.actorsPosition[type.ordinal()][1];

        Item poppedItem = item;
        //Ha nullal hívták meg a pickUp függvényt (hibás meghívás, vagy nincs semmi a mezőn)
        if(poppedItem == null){
            poppedItem = (Item)actualMaze.playField[xCoord][yCoord].popForeground();
            //Ha nincs semmi a mezőn, ne csináljunk semmit
            if(poppedItem == null)
                return;
        }
        else {
            poppedItem = item;
        }
        //Ha ZPM, akkor növeljük a zpmCount-ot
        if(poppedItem.getType() == ItemType.ZPM){
            zpmCount++;
        }
        //Ha doboz, akkor felszedjük és elrakjuk az aktorba, ha nincs nála már doboz
        //Ha van az aktornál doboz, akkor visszarakjuk a mezőre
        else{
            //Ha nincs a játékosnál doboz
            if(this.item == null) {
                this.item = poppedItem;
            }
            else {
                //Visszarakjuk a mezőre a nem felvehető itemet
                actualMaze.playField[xCoord][yCoord].pushForeground(poppedItem);
            }
        }
    }

    @Override
    public boolean isForeground() { return false; }
}
