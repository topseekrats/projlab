package com.topseekrats;

import com.topseekrats.background.*;
import com.topseekrats.foreground.*;

/**
 * Játékos osztálya, a játék fő logikája itt található meg:
 * - mozgás
 * - lövés
 * - objektumokkal interakció
 */
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
    public int getZpmCount() { return zpmCount; }

    /**
     * Játékos mozgása, figyelembe véve az alábbi szabályokat:
     * - mozgásirány szerinti haladás (move-to-direction)
     * - nem átjárható mezőre lépés tiltása (non-passable areas)
     * - játékos nem léphet rá másik játékosra (unit collision)
     * További funkciók:
     * - mérleg interakciók
     * - teleportálás
     */
    @Override
    public void move() {
        // Maze-től lekérjük az adott játékos mozgásirányát.
        MoveDirection moveDirection = Maze.getInstance().moveDirection[type.ordinal()];

        // Játékos aktuális pozícióját kimentjük, hogy ki tudjuk majd később szedni innen
        int[] pos = Maze.getInstance().actorsPosition[type.ordinal()].clone();
        int[] oldPos = pos.clone();

        // Játékos mozgásiránya szerint módosítjuk az új koordinátákat
        switch (moveDirection) {
            case UP:
                if (pos[0] > 0) pos[0] -= 1;
                else return;
                break;
            case DOWN:
                if (pos[0] < Maze.getInstance().playField.length-1) pos[0] += 1;
                else return;
                break;
            case LEFT:
                if (pos[1] > 0) pos[1] -= 1;
                else return;
                break;
            case RIGHT:
                if (pos[1] < Maze.getInstance().playField[1].length-1) pos[1] += 1;
                else return;
                break;
        }

        //Csak akkor rakjuk be, hogyha ráléphet a következő mezőre
        if (!Maze.getInstance().playField[pos[0]][pos[1]].getBackground().isPassable()) return;
        //Csak akkor rakjuk be, hogyha ráléphet a következő mezőre:
        //A mezőre rá lehet lépni
        //A mezőn nincs ott a másik játékos
        if (!Maze.getInstance().playField[pos[0]][pos[1]].getBackground().isPassable()) return;
        if(this.type == ActorType.COLONEL){
            if(Maze.getInstance().playField[pos[0]][pos[1]].getActor(ActorType.JAFFA) != null) return;
        }
        else{
            if(Maze.getInstance().playField[pos[0]][pos[1]].getActor(ActorType.COLONEL) != null) return;
        }


        Maze.getInstance().actorsPosition[type.ordinal()] = pos;

        //Új mezőre lépés kezelése
        Maze.getInstance().playField[oldPos[0]][oldPos[1]].setActor(getType().ordinal(), null);


        //Ha mérlegen állt, csökkentjük a rá nehezedő súlyt
        if (Maze.getInstance().playField[oldPos[0]][oldPos[1]].getBackground() instanceof Switch)
            ((Switch)Maze.getInstance().playField[oldPos[0]][oldPos[1]].getBackground()).decrementWeight();

        //Ha átjárható falra lép, akkor teleportálni kell
        if (Maze.getInstance().playField[pos[0]][pos[1]].getBackground() instanceof Wall) {
            ((Stargate) Maze.getInstance().playField[pos[0]][pos[1]].peekForeground()).teleport(this);
            return;
        }

        Maze.getInstance().playField[pos[0]][pos[1]].setActor(getType().ordinal(), this);

        //Ha mérlegre lép, növeljük a mérlegre nehezedő súlyt
        if (Maze.getInstance().playField[pos[0]][pos[1]].getBackground() instanceof Switch)
            ((Switch)Maze.getInstance().playField[pos[0]][pos[1]].getBackground()).incrementWeight();
        // Ha szakadékba esik a játékos, akkor meghal
        else if (Maze.getInstance().playField[pos[0]][pos[1]].getBackground() instanceof Cleft)
            ((Cleft)Maze.getInstance().playField[pos[0]][pos[1]].getBackground()).destroy(this);
    }

    /**
     * Lövés kezelése
     * - portalManager használata portálnyitáshoz
     */
    @Override
    public void shoot() {
        int[] bulletPos = Maze.getInstance().actorsPosition[type.ordinal()].clone();
        while (true) {
            switch (Maze.getInstance().moveDirection[type.ordinal()]) {
                case UP:
                    if (bulletPos[0] > 0) bulletPos[0] -= 1;
                    else return;
                    break;
                case DOWN:
                    if (bulletPos[0] < Maze.getInstance().playField.length-1) bulletPos[0] += 1;
                    else return;
                    break;
                case LEFT:
                    if (bulletPos[1] > 0) bulletPos[1] -= 1;
                    else return;
                    break;
                case RIGHT:
                    if (bulletPos[1] < Maze.getInstance().playField[1].length-1) bulletPos[1] += 1;
                    else return;
                    break;
            }
            Background background = Maze.getInstance().playField[bulletPos[0]][bulletPos[1]].getBackground();
            //Ha replikátorba csapódik a lövedék
            if(background instanceof Floor && Maze.getInstance().playField[bulletPos[0]][bulletPos[1]].getReplicator() != null){
                Engine.killReplicator(bulletPos);
                return;
            }
            //Ha falba csapódik a lövedék
            if(background instanceof Wall) {
                if (!background.isPassable()) {
                    if (background instanceof Door) return;
                    Wall wall = (Wall) background;
                    if (!wall.isPortalCompatible()) return;
                    portalManager(bulletPos);
                    return;
                }
            }
        }
    }

    /**
     * lövedék cseréje
     */
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

    /**
     * doboz eldobása, maga elé dobja le a dobozt a játékos
     * Ajtóba, falra nem tudja lerakni a dobozt
     * Szakadékba, mérlegre, szabad padlóra le tudja rakni
     */
    @Override
    public void dropBox() {
        if (item == null) return;
        int[] pos = Maze.getInstance().actorsPosition[type.ordinal()];

        //Maga elé rakja le a dobozt
        switch (Maze.getInstance().moveDirection[type.ordinal()]) {
            case UP:
                if (pos[0] > 0) pos[0] -= 1;
                else return;
                break;
            case DOWN:
                if (pos[0] < Maze.getInstance().playField.length-1) pos[0] += 1;
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
            //Ha szakadékba dobjuk a dobozt, akkor nem rakjuk le már sehova
            if(!(background instanceof Cleft)){
                Maze.getInstance().playField[pos[0]][pos[1]].pushForeground(item);
            }
            item = null;
        }
    }

    /**
     * ZPM vagy BOX (doboz) felvétele
     * csak egy doboz lehet a játékosnál
     * pályáról 2. ZPM felvétele esetén új ZPM generálódik, ezt az Engine intézi
     */
    @Override
    public void pickUp(){
        // Játékos pozíciója, rá kell lépnie az item helyére
        int[] pos = Maze.getInstance().actorsPosition[type.ordinal()];
        Item item = (Item)Maze.getInstance().playField[pos[0]][pos[1]].popForeground();

        if (item == null) return;
        if (item.getType() == ItemType.ZPM) {
            ++zpmCount;
            ++Maze.getInstance().zpmPickUpCounter;
            if(Maze.getInstance().zpmPickUpCounter % 2 == 0) Engine.generateRandomZPM();
            --Maze.getInstance().zpmOnMap;
            if(Maze.getInstance().zpmOnMap == 0) Engine.finish();
        }
        else {
            if (this.item == null) this.item = item;
            else Maze.getInstance().playField[pos[0]][pos[1]].pushForeground(item);
        }
    }

    @Override
    public boolean isForeground() { return false; }

    /**
     * Portál manager metódus lövéshez
     * @param bulletPos kilőtt lövedék pozíciója (ahova becsapódott)
     */
    private void portalManager(int[] bulletPos) {
        int[][] stargateEndPoints = Maze.getInstance().stargateEndPoints;

        // Portálok színének meghatározása.
        int bulletId = bullet.getType().ordinal();
        int bulletPairId = -1;
        switch (bullet.getType()) {
            case YELLOW:
                bulletPairId = BulletType.BLUE.ordinal();
                break;
            case BLUE:
                bulletPairId = BulletType.YELLOW.ordinal();
                break;
            case GREEN:
                bulletPairId = BulletType.RED.ordinal();
                break;
            case RED:
                bulletPairId = BulletType.GREEN.ordinal();
                break;
        }

        // Ha egyik fajta portál sincs.
        if (stargateEndPoints[bulletId][0] == -1 && stargateEndPoints[bulletPairId][0] == -1) {
            stargateEndPoints[bulletId] = bulletPos;
            Maze.getInstance().playField[bulletPos[0]][bulletPos[1]].pushForeground(bullet);
        }
        // Ha csak olyan volt, mint amit most lőtt.
        else if (stargateEndPoints[bulletId][0] != -1 && stargateEndPoints[bulletPairId][0] == -1) {
            // Régi portál eltüntetése.
            int[] oldEndPoint = stargateEndPoints[bulletId];
            Maze.getInstance().playField[oldEndPoint[0]][oldEndPoint[1]].popForeground();

            // Új portál tárolása.
            stargateEndPoints[bulletId] = bulletPos;
            Maze.getInstance().playField[bulletPos[0]][bulletPos[1]].pushForeground(bullet);
        }
        // Ha csak az ellenkező típus volt.
        else if (stargateEndPoints[bulletId][0] == -1 && stargateEndPoints[bulletPairId][0] != -1) {
            // Most kilott portal koordinatainak tarolasa.
            stargateEndPoints[bulletId] = bulletPos;

            // Ha az ellenkező típusra lőtte.
            if (bulletPos == stargateEndPoints[bulletPairId]) {
                Maze.getInstance().playField[bulletPos[0]][bulletPos[1]].popForeground();
                Maze.getInstance().playField[bulletPos[0]][bulletPos[1]].pushForeground(bullet);
            }
            // Ha a két portál két külön helyen van.
            else {
                // Csillagkapu létrehozása az egyik portál helyére.
                Stargate stargate = new Stargate(stargateEndPoints[bulletPairId]);
                Maze.getInstance().playField[bulletPos[0]][bulletPos[1]].pushForeground(stargate);

                // Csillagkapu létrehozása a másik portál helyére.
                stargate = new Stargate(stargateEndPoints[bulletId]);
                int[] pairBulletPos = stargateEndPoints[bulletPairId];
                Maze.getInstance().playField[pairBulletPos[0]][pairBulletPos[1]].popForeground();
                Maze.getInstance().playField[pairBulletPos[0]][pairBulletPos[1]].pushForeground(stargate);

                // Falak átjárhatová tétele.
                ((Wall)Maze.getInstance().playField[bulletPos[0]][bulletPos[1]].getBackground()).changeHasStargate();
                ((Wall)Maze.getInstance().playField[pairBulletPos[0]][pairBulletPos[1]].getBackground()).changeHasStargate();
            }
        }
        // Ha már mindkét típusú portál volt.
        else {
            // Csillagkapu levétele a régi falról.
            int[] oldEndPoint = stargateEndPoints[bulletId];
            ((Wall)Maze.getInstance().playField[oldEndPoint[0]][oldEndPoint[1]].getBackground()).changeHasStargate();
            Maze.getInstance().playField[oldEndPoint[0]][oldEndPoint[1]].popForeground();

            // Most kilőtt portál koordinátáinak tárolása.
            stargateEndPoints[bulletId] = bulletPos;

            // Ha az ellenkező típusra lőtte.
            if (bulletPos == stargateEndPoints[bulletPairId]) {
                Maze.getInstance().playField[bulletPos[0]][bulletPos[1]].popForeground();
                Maze.getInstance().playField[bulletPos[0]][bulletPos[1]].pushForeground(bullet);
            }
            // Ha egy üres speciális falra lőtte.
            else {
                // Csillagkapu létrehozása az új portál helyére.
                Stargate stargate = new Stargate(stargateEndPoints[bulletPairId]);
                Maze.getInstance().playField[bulletPos[0]][bulletPos[1]].popForeground();
                Maze.getInstance().playField[bulletPos[0]][bulletPos[1]].pushForeground(stargate);

                // Meglévő Csillagkapu párkoordinátáinak módosítása.
                int[] pairBulletPos = stargateEndPoints[bulletPairId];
                ((Stargate)Maze.getInstance().playField[pairBulletPos[0]][pairBulletPos[1]].peekForeground()).setPairCoords(bulletPos);

                // Új fal átjárhatóvá tétele.
                ((Wall)Maze.getInstance().playField[pairBulletPos[0]][pairBulletPos[1]].getBackground()).changeHasStargate();
            }
        }
        Maze.getInstance().stargateEndPoints = stargateEndPoints;
    }
}
