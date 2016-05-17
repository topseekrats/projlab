package com.topseekrats;

import com.topseekrats.background.*;
import com.topseekrats.foreground.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Stack;

/**
 * A játékost reprezentáló osztály.
 * Minden olyan cselekvés, amit a játékos képes végezni, ebben az osztályban
 * került megvalósításra. Ilyen cselekvés a mozgás, lövés, valamint a pályán
 * elhelyezett előtérobjektumokkal való interakció.
 */
public class Actor implements MazeObject, Serializable {

    private static final long serialVersionUID = 1L;

    private ActorType type;
    private Bullet bullet;
    private Item item;

    /** A játékos által felvett ZPM-ek száma. */
    private int zpmCount;

    /**
     * Játékos konstruktor.
     *
     * @param type játékos típusa
     */
    public Actor(ActorType type) {
        this.type = type;
        if (type == ActorType.COLONEL) bullet = new Bullet(BulletType.YELLOW);
        else bullet = new Bullet(BulletType.RED);
    }

    public ActorType getType() { return type; }
    public int getZpmCount() { return zpmCount; }

    /**
     * A játékos mozgatását megvalósító metódus.
     * A metódus adott irányba elmozdítja a játékost, amennyiben a kívánt
     * irányban olyan mező helyezkedik el, amire rá lehet lépni.
     * Ezen felül
     */
    @Override
    public void move() {
        // Játékos mozgásirányának lekérése.
        MoveDirection moveDirection = Maze.getInstance().moveDirection[type.ordinal()];

        // Aktuális pozíció lekérése és duplikálása.
        int[] oldPos = Maze.getInstance().actorsPosition[type.ordinal()];
        int[] newPos = oldPos.clone();

        // Pozíció módosítása a mozgásiránynak megfelelően.
        switch (moveDirection) {
            case UP:
                if (newPos[0] > 0) newPos[0] -= 1;
                else return;
                break;
            case DOWN:
                if (newPos[0] < Maze.getInstance().playField.length-1) newPos[0] += 1;
                else return;
                break;
            case LEFT:
                if (newPos[1] > 0) newPos[1] -= 1;
                else return;
                break;
            case RIGHT:
                if (newPos[1] < Maze.getInstance().playField[1].length-1) newPos[1] += 1;
                else return;
                break;
        }

        // Ha a játékos olyan mezőre lépne, amire nem lehet rálépni, nem mozog.
        if (!Maze.getInstance().playField[newPos[0]][newPos[1]].getBackground().isPassable()) return;

        // Ha mozoghat, a pozícióját jelző koordináták frissülnek.
        Maze.getInstance().actorsPosition[type.ordinal()] = newPos;

        // Játékos objektum kivétele a régi pozícióból.
        Maze.getInstance().playField[oldPos[0]][oldPos[1]].setActor(getType().ordinal(), null);

        // Ha mérlegről lépett le, a mérleg súlyának csökkentése.
        if (Maze.getInstance().playField[oldPos[0]][oldPos[1]].getBackground() instanceof Switch)
            ((Switch)Maze.getInstance().playField[oldPos[0]][oldPos[1]].getBackground()).decrementWeight();

        // Ha átjárható falra lép, akkor teleportál.
        if (Maze.getInstance().playField[newPos[0]][newPos[1]].getBackground() instanceof Wall) {
            ((Stargate)Maze.getInstance().playField[newPos[0]][newPos[1]].peekForeground()).teleport(this);
            return;
        }

        // Ha nem teleportált, akkor a játékos objektum berakható az új pozícióba.
        Maze.getInstance().playField[newPos[0]][newPos[1]].setActor(getType().ordinal(), this);

        // Ha mérlegre lép, növeljük a mérlegre nehezedő súlyt
        if (Maze.getInstance().playField[newPos[0]][newPos[1]].getBackground() instanceof Switch)
            ((Switch)Maze.getInstance().playField[newPos[0]][newPos[1]].getBackground()).incrementWeight();
        // Ha szakadékba esik a játékos, akkor meghal
        else if (Maze.getInstance().playField[newPos[0]][newPos[1]].getBackground() instanceof Cleft)
            ((Cleft)Maze.getInstance().playField[newPos[0]][newPos[1]].getBackground()).destroy(this);
    }

    /**
     * Játékos által kilőtt lövedéket kezelő metódus.
     * A játékos által kilőtt lövedék abba az irányba indul el, amerre a
     * játékos éppen néz a kilövéskor. Ezt követően a töltény addif halad
     * egyenesen az adott irányba, amíg falat nem ér. Ha erre a falra kilőhető
     * portál, akkor portál, vagy ha már jelen van egy másik megfelelő portál,
     * akkor csillagkapu keletkezik. Ha az adott falra nem lőhető portál, úgy
     * a lövedék megsemmisül.
     */
    @Override
    public void shoot() {
        // A lövedék kezdőhelye ott van, ahol az azt kilővő játékos áll.
        int[] bulletPos = Maze.getInstance().actorsPosition[type.ordinal()].clone();

        // Ezt követően a lövedék addig halad, amíg falat nem ér.
        while (true) {
            // A lövedék abba az irányba indul, amerre a játékos néz.
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
            // Aktuális pozíció háttérobjektumának lekérése.
            Background background = Maze.getInstance().playField[bulletPos[0]][bulletPos[1]].getBackground();

            // Ha az aktuális mező nem átjárható, annak vizsgálata.
            if (!background.isPassable()) {
                // Ha ajtó, akkor a lövedék megsemmisül.
                if (background instanceof Door) return;

                // Egyébként a háttérobjektum biztosan fal.
                Wall wall = (Wall)background;

                // Ha nem speciális fal, akkor a lövedék megsemmisül.
                if (!wall.isPortalCompatible()) return;

                // Egyébként portál vagy csillagkapu megfelelő elhelyezése.
                portalManager(bulletPos);
                return;
            // Ha átjárható fal, vagyis olyan fal, amin éppen csillagkapu van.
            } else if (background instanceof Wall) {
                portalManager(bulletPos);
                return;
            }

            // Ha a lövedék replikátorba csapódik, akkor a replikátor meghal.
            if (background instanceof Floor && Maze.getInstance().playField[bulletPos[0]][bulletPos[1]].getReplicator() != null) {
                killReplicator();
                return;
            }
        }
    }

    /**
     * Lövedék típusának váltása, figyelembe véve, hogy a sárga-kék, illetve
     * zöld-piros párok tartoznak össze.
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
     * Játékosnál levő doboz lerakása. A játékos a dobozt az előtte levő
     * mezőre helyezi. A doboz nem rakható le ajtó vagy fal típusú mezőkre.
     * Szakadék, mérleg, padlóra lehet lerakni.
     */
    @Override
    public void dropBox() {
        // Ha a játékosnál nincs doboz, nem ötrténik semmi.
        if (item == null) return;

        // Játékos pozíciójának lekérése.
        int[] pos = Maze.getInstance().actorsPosition[type.ordinal()].clone();

        // Doboz pozíciójának meghatározása aszerint, hogy a játékos merre néz.
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
        // Aktuális pozíció háttérobjektumának lekérdezése.
        Background background = Maze.getInstance().playField[pos[0]][pos[1]].getBackground();

        // Ha a háttérobjektum nem átjárható vagy ajtó, akkor nem történik semmi.
        if (!background.isPassable() || background instanceof  Door) return;

        // Ha a mező nem szakadék, akkor a doboz rákerül.
        if (!(background instanceof Cleft))
            Maze.getInstance().playField[pos[0]][pos[1]].pushForeground(item);

        // A doboz kikerül a játékos hátizsákjából.
        item = null;

        // Ha mérlegre lett letéve a doboz, úgy a rajta levő súlyt növelni kell.
        if (background instanceof Switch)
            ((Switch) background).incrementWeight();
    }

    /**
     * ZPM vagy doboz felvétele. A játékos arról a mezőről vesz fel egy tárgyat,
     * amin éppen áll. Egy mezőn több tárgy is elhelyezkedhet, ezek közül a
     * legfelső kerül a játékoshoz, amennyiben az lehetséges. ZPM-ből bármennyit
     * fel lehet venni, azonban dobozból kizárólag egy lehet a játékosnál.
     */
    @Override
    public void pickUp(){
        // Játékos pozíciójának lekérdezése.
        int[] pos = Maze.getInstance().actorsPosition[type.ordinal()];

        // Az adott mező előtérobjektumának lekérése.
        Stack<Foreground> foregrounds = Maze.getInstance().playField[pos[0]][pos[1]].getForegrounds();

        // Ha a mező üres volt, nem történik semmi.
        if (foregrounds.empty()) return;
        // Ha dobozt venne fel, de már van nála, nem történik semmi.
        else if (((Item)foregrounds.peek()).getType() == ItemType.BOX && item != null) return;

        // Ha a felvett tárgy doboz.
        if (((Item)foregrounds.peek()).getType() == ItemType.BOX) {
            item = (Item)foregrounds.pop();

            // Mező háttérobjektumának lekérése.
            Background background = Maze.getInstance().playField[pos[0]][pos[1]].getBackground();

            // Ha mérlegről lett felvéve a doboz, súly csökkentése.
            if (background instanceof Switch) ((Switch) background).decrementWeight();
        }
        // Ha a felvett tárgy ZPM.
        else {
            // Játékos ZPM-számának növelése.
            ++zpmCount;
            foregrounds.pop();

            // Összes felvett ZPM mennyiségének növelése.
            ++Maze.getInstance().zpmPickUpCounter;

            // Ha a felvétel után a felvett ZPM-ek száma páros, új ZPM lerakása.
            if (Maze.getInstance().zpmPickUpCounter % 2 == 0) Engine.generateRandomZpm();

            // Pályán levő ZPM-ek számának növelése.
            --Maze.getInstance().zpmOnMap;

            // Ha nem maradt ZPM a pályán, játék vége.
            if (Maze.getInstance().zpmOnMap == 0) Engine.finish();
        }
    }

    @Override
    public boolean isForeground() { return false; }

    /**
     * A portálok vagy csillagkapuk megalkotásáért, módosításáért és
     * eltávolításáért felelős metódus.
     *
     * @param pos aktuálisan kilőtt portál pozíciója
     */
    private void portalManager(int[] pos) {
        // Csillagkapu-koordináták lekérdezése.
        int[][] stargateEndPoints = Maze.getInstance().stargateEndPoints;



        // Portálok színének meghatározása.
        int color = bullet.getType().ordinal();
        int pairColor = -1;
        int[] otherColors = new int[2];
        switch (bullet.getType()) {
            case YELLOW:
                pairColor = BulletType.BLUE.ordinal();
                otherColors = new int[] {BulletType.GREEN.ordinal(), BulletType.RED.ordinal()};
                break;
            case BLUE:
                pairColor = BulletType.YELLOW.ordinal();
                otherColors = new int[] {BulletType.GREEN.ordinal(), BulletType.RED.ordinal()};
                break;
            case GREEN:
                pairColor = BulletType.RED.ordinal();
                otherColors = new int[] {BulletType.YELLOW.ordinal(), BulletType.BLUE.ordinal()};
                break;
            case RED:
                pairColor = BulletType.GREEN.ordinal();
                otherColors = new int[] {BulletType.YELLOW.ordinal(), BulletType.BLUE.ordinal()};
                break;
        }

        // Ha a másik játékos egyik színű végpontjára lőtte.
        if (Arrays.equals(pos, stargateEndPoints[otherColors[0]])) {
            int[] otherPos = stargateEndPoints[otherColors[0]];

            // Elem kivétele a veremből.
            Foreground foreground = Maze.getInstance().playField[otherPos[0]][otherPos[1]].popForeground();

            // Végpont kivétele a pozíció tároló tömbből.
            stargateEndPoints[otherColors[0]] = new int[] {-1, -1};

            // Ha aktív csillagkapu volt, párját eltüntetni és falakat visszaállítani.
            if (foreground instanceof Stargate) {
                ((Wall)Maze.getInstance().playField[otherPos[0]][otherPos[1]].getBackground()).changeHasStargate();

                int[] anotherPos = stargateEndPoints[otherColors[1]];
                Maze.getInstance().playField[anotherPos[0]][anotherPos[1]].popForeground();
                ((Wall)Maze.getInstance().playField[anotherPos[0]][anotherPos[1]].getBackground()).changeHasStargate();

                stargateEndPoints[otherColors[1]] = new int[] {-1, -1};
            }
        }
        //
        else if (Arrays.equals(pos, stargateEndPoints[otherColors[1]])) {
            int[] otherPos = stargateEndPoints[otherColors[1]];

            // Elem kivétele a veremből.
            Foreground foreground = Maze.getInstance().playField[otherPos[0]][otherPos[1]].popForeground();

            // Végpont kivétele a pozíció tároló tömbből.
            stargateEndPoints[otherColors[1]] = new int[] {-1, -1};

            // Ha aktív csillagkapu volt, párját eltüntetni és falakat visszaállítani.
            if (foreground instanceof Stargate) {
                ((Wall)Maze.getInstance().playField[otherPos[0]][otherPos[1]].getBackground()).changeHasStargate();

                int[] anotherPos = stargateEndPoints[otherColors[0]];
                Maze.getInstance().playField[anotherPos[0]][anotherPos[1]].popForeground();
                ((Wall)Maze.getInstance().playField[anotherPos[0]][anotherPos[1]].getBackground()).changeHasStargate();

                stargateEndPoints[otherColors[0]] = new int[] {-1, -1};
            }
        }

        // Ha egyik fajta portál sincs a pályán.
        if (stargateEndPoints[color][0] == -1 && stargateEndPoints[pairColor][0] == -1) {
            // Portál helyének tárolása és felrakása a falra.
            stargateEndPoints[color] = pos;
            Maze.getInstance().playField[pos[0]][pos[1]].pushForeground(bullet);
        }
        // Ha csak olyan színű portál van a pályán, amilyet éppen lőtt.
        else if (stargateEndPoints[color][0] != -1 && stargateEndPoints[pairColor][0] == -1) {
            // Régi portál eltüntetése.
            int[] oldEndPoint = stargateEndPoints[color];
            Maze.getInstance().playField[oldEndPoint[0]][oldEndPoint[1]].popForeground();

            // Új portál helyének tárolása és felrakása a falra.
            stargateEndPoints[color] = pos;
            Maze.getInstance().playField[pos[0]][pos[1]].pushForeground(bullet);
        }
        // Ha az éppen kilőtt portál pár portálja már jelen van a pályán.
        else if (stargateEndPoints[color][0] == -1 && stargateEndPoints[pairColor][0] != -1) {
            // Éppen kilőtt portál helyének tárolása.
            stargateEndPoints[color] = pos;

            // Ha pontosan a pár portálra lőtte az új portált.
            if (Arrays.equals(pos, stargateEndPoints[pairColor])) {
                // Pár portál eltüntetése.
                stargateEndPoints[pairColor] = new int[] {-1, -1};
                Maze.getInstance().playField[pos[0]][pos[1]].popForeground();

                // Új portál felrakása a falra.
                Maze.getInstance().playField[pos[0]][pos[1]].pushForeground(bullet);
            }
            // Ha a két portál két külön helyen van.
            else {
                // Csillagkapu létrehozása az éppen kilőtt portál helyére.
                Maze.getInstance().playField[pos[0]][pos[1]].pushForeground(new Stargate(stargateEndPoints[pairColor], type));

                // Sima portál eltüntetése a falról.
                int[] pairPos = stargateEndPoints[pairColor];
                Maze.getInstance().playField[pairPos[0]][pairPos[1]].popForeground();

                // Csillagkapu létrehozása a már meglévő portál helyére.
                Maze.getInstance().playField[pairPos[0]][pairPos[1]].pushForeground(new Stargate(stargateEndPoints[color], type));

                // Portálok mezőin levő falak átjárhatóvá tétele.
                ((Wall)Maze.getInstance().playField[pos[0]][pos[1]].getBackground()).changeHasStargate();
                ((Wall)Maze.getInstance().playField[pairPos[0]][pairPos[1]].getBackground()).changeHasStargate();
            }
        }
        // Ha már mindkét típusú portál (vagyis aktív csillagkapu) jelen van a pályán.
        else {
            // Ha az azonos színű portálra lőtt, nem történik semmi.
            if (Arrays.equals(pos, stargateEndPoints[color])) return;

            // Módosítandó csillagkapu eltüntetése.
            int[] oldPos = stargateEndPoints[color];
            Maze.getInstance().playField[oldPos[0]][oldPos[1]].popForeground();
            ((Wall)Maze.getInstance().playField[oldPos[0]][oldPos[1]].getBackground()).changeHasStargate();

            // Éppen kilőtt portál helyének tárolása.
            stargateEndPoints[color] = pos;

            // Ha az ellenkező típusra lőtte.
            if (Arrays.equals(pos, stargateEndPoints[pairColor])) {
                // Másik csillagkapu eltüntetése.
                stargateEndPoints[pairColor] = new int[] {-1, -1};
                Maze.getInstance().playField[pos[0]][pos[1]].popForeground();
                ((Wall)Maze.getInstance().playField[pos[0]][pos[1]].getBackground()).changeHasStargate();

                // Új portál felrakása a falra.
                Maze.getInstance().playField[pos[0]][pos[1]].pushForeground(bullet);
            }
            // Ha egy üres speciális falra lőtte.
            else {
                // Csillagkapu létrehozása az új portál helyére és fal átjáratóvá tétele.
                Maze.getInstance().playField[pos[0]][pos[1]].pushForeground(new Stargate(stargateEndPoints[pairColor], type));
                ((Wall)Maze.getInstance().playField[pos[0]][pos[1]].getBackground()).changeHasStargate();

                // Meglévő Csillagkapu párkoordinátáinak módosítása.
                int[] pairPos = stargateEndPoints[pairColor];
                ((Stargate)Maze.getInstance().playField[pairPos[0]][pairPos[1]].peekForeground()).setPairCoords(pos);
            }
        }
    }

    /** Replikátor golyó által történő megsemmisítése. */
    private void killReplicator() {
        int[] pos = Maze.getInstance().replicatorPosition;
        Maze.getInstance().replicatorLives = false;
        Maze.getInstance().playField[pos[0]][pos[1]].setReplicator(null);
    }

}
