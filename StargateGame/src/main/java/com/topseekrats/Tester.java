package com.topseekrats;

import com.topseekrats.background.Background;
import com.topseekrats.background.Cleft;
import com.topseekrats.background.Door;
import com.topseekrats.background.Switch;
import com.topseekrats.background.Wall;
import com.topseekrats.foreground.Bullet;
import com.topseekrats.foreground.Foreground;
import com.topseekrats.foreground.Item;
import com.topseekrats.foreground.Stargate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

import static com.sun.corba.se.spi.activation.IIOP_CLEAR_TEXT.value;

public class Tester
{
    private static Engine engine = null;
    private static Maze dummyMaze = null;
    private static Actor actor = null;
    private static Item boxItem = null;
    private static Item zpmItem = null;
    private static List<MazeObjectWrapper> dummyMazeObjects = null;
    private static MazeObjectWrapper mow1 = null;
    private static MazeObjectWrapper mow2 = null;
    private static Switch sw = null;
    private static Door door = null;
    private static Cleft cleft = null;
    private static Stargate sg = null;
    private static Wall wallWithPortal = null;

    private static Foreground fg = null;
    private static Background bg = null;

    public static void main(String[] args) throws IOException {
        menu();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String temp = br.readLine();
        int testCase = 0;
        if (tryParse(temp)) testCase = Integer.parseInt(temp);
        while (testCase != -1) {
            System.out.println("############################### TEST CASE STARTED ##############################");
            runTest(testCase);
            System.out.println("################################ TEST CASE ENDED ###############################");
            System.out.println("Press ENTER to continue...");
            System.in.read();
            menu();
            temp = br.readLine();
            if (tryParse(temp)) testCase = Integer.parseInt(temp);
            else testCase = 0;
        }
    }

    private static void menu() {
        StringBuffer sb = new StringBuffer();
        sb.append("################################################################################\n");
        sb.append("############################ TOPSEEKRATS - SKELETON ############################\n");
        sb.append("################################################################################\n\n");
        sb.append("Test cases:\n\n");
        sb.append("#1: Game initialization\n");
        sb.append("#2: The player picks up a box from a switch, door closes\n");
        sb.append("#3: The player drops a box onto a switch, door opens\n");
        sb.append("#4: The player drops a box into a cleft\n");
        sb.append("#5: The player picks up the last ZPM and wins\n");
        sb.append("#6: The player steps on a switch and steps off from it\n");
        sb.append("#7: The player steps on a cleft and dies. Game over!\n");
        sb.append("#8: The player opens a stargate\n");
        sb.append("#9: The player modify one of the stargate's endpoints\n");
        sb.append("#10: The player tries to place the stargate's both ends on the same wall causing stargate destroyed\n");
        sb.append("#11: The player steps next to a box and picks it up\n");
        sb.append("#12: The player steps next to a ZPM and picks it up\n");
        sb.append("#13: The player teleports\n\n");
        sb.append("Pick test case:");
        System.out.println(sb);
    }

    private static boolean tryParse(String val) {
        try {
            Integer.parseInt(val);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private static void runTest(int testCase) {
        switch (testCase) {
            case 1:
                testNum01();
                break;
            case 2:
                testNum02();
                break;
            case 3:
                testNum03();
                break;
            case 4:
                testNum04();
                break;
            case 5:
                testNum05();
                break;
            case 6:
                testNum06();
                break;
            case 7:
                testNum07();
                break;
            case 8:
                testNum08();
                break;
            case 9:
                testNum09();
                break;
            case 10:
                testNum10();
                break;
            case 11:
                testNum11();
                break;
            case 12:
                testNum12();
                break;
            case 13:
                testNum13();
                break;
            default:
                System.out.println("Invalid test case.");
                break;
        }
    }

    private static void testNum01() {
        System.out.println("Test #1: Game initialization.");
        //Create engine
        engine = new Engine();
        //Start new game
        engine.newGame();
        //Create maze for game
        System.out.println("Maze constructor called");
        dummyMaze = new Maze();
        System.out.println("Maze constructor returned");
        //Create 2x2 maze with 4 mazeobjects
        System.out.println("Creating a 2x2 maze with 4 mazeobjects.");
        dummyMazeObjects = new ArrayList<MazeObjectWrapper>();
        for(int i = 0; i < 4; i++){
            MazeObjectWrapper tempDummyMazeObject = new MazeObjectWrapper();
            tempDummyMazeObject.setForeground();
            dummyMazeObjects.add(tempDummyMazeObject);
        }
        //Two dummy maze fields
        mow1 = dummyMazeObjects.get(0);
        mow2 = dummyMazeObjects.get(1);


        //init for other tests START
        actor = new Actor();
        cleft = new Cleft();
        sw = new Switch();
        door = new Door();

        //init for other tests END
    }

    private static void testNum02() {
        System.out.println("Test #2: The player picks up a box from a switch, door closes.");
        //Create an actor
        actor = new Actor();
        //This will return the box
        Foreground fg = mow1.getForeground();
        actor.pickUp();
        mow1.setForeground();
        //This will return the switch
        Background bg = mow1.getBackground();
        //For now, this will be our dummy Switch
        sw = new Switch();
        sw.changeDoorState();
        //For now, this will be the door which belongs to the dummy Switch
        door = new Door();
        door.changeOpened();
        // System.out.println("Test #2: done. Press a key to continue.");
    }

    private static void testNum03() {
        System.out.println("Test #3: The player drops a box onto a switch, door opens.");
        actor.dropBox();
        mow1.setForeground();
        //This will return the switch
        bg = mow1.getBackground();
        //For now, this will be our dummy Switch
        sw = new Switch();
        sw.changeDoorState();
        //For now, this will be the door which is belonging to the dummy Switch
        door = new Door();
        door.changeOpened();
        // System.out.println("Test #3: done. Press a key to continue.");
    }

    private static void testNum04() {
        System.out.println("Test #4: The player drops a box into a cleft.");
        actor.dropBox();
        //This will return the cleft
        bg = mow1.getBackground();
        //For now, this will be our dummy Cleft
        cleft = new Cleft();
        cleft.destroy();
        //Disposing boxItem
        System.out.println("Box destructor called");
        boxItem = null;
        System.out.println("Box destructor returned");
        // System.out.println("Test #4: done. Press a key to continue.");
    }

    private static void testNum05() {
        System.out.println("Test #5: The player picks up the last ZPM and wins.");
        fg = mow1.getForeground();
        actor.pickUp();
        mow1.setForeground();
        //Disposing zpmItem
        System.out.println("ZPM destructor called");
        zpmItem = null;
        System.out.println("ZPM destructor returned");
        //If Actor.ZPMCount == allZPMs
        System.out.println("if Actor.ZPMCount == allZPMs is true, then:");
        if(true) engine.victory();
    }

    private static void testNum06() {
        System.out.println("Test #6: The player steps on a switch and steps off from it.");
        //Step on
        bg = mow1.getBackground();
        actor.move();
        mow1.setActor();
        sw.changeDoorState();
        door.changeOpened();
        //Step off
        bg = mow2.getBackground();
        actor.move();
        mow2.setActor();
        mow1.setActor();
        sw.changeDoorState();
        door.changeOpened();
    }

    private static void testNum07() {
        System.out.println("Test #7: The player steps on a cleft and dies. Game over!");
        actor.move();
        bg = mow1.getBackground();
        cleft.destroy(); // destroy actor
        engine.death();
    }

    private static void testNum08() {
        System.out.println("Test #8: The player opens a stargate.\n");

        // init START
        Wall wy = new Wall();
        Wall wb = new Wall();
        // init END

        actor.shoot();

        Bullet by = new Bullet();
        by.move();
        wy.changeHasStargate();
        wy.isPortalCompatible();

        actor.changeBullet();
        actor.shoot();

        Bullet bb = new Bullet();
        bb.move();
        wb.changeHasStargate();
        wb.isPortalCompatible();

        Stargate s = new Stargate();
    }

    private static void testNum09() {
        System.out.println("Test #9: The player modify one of the stargate's endpoints.");

        // init START
        Stargate so = new Stargate();
        // init END

        actor.shoot();
        Bullet by = new Bullet();
        by.move();
        Wall wyo = new Wall();
        wyo.changeHasStargate();    // disable passable = isPassable will return false
        wyo.isPortalCompatible();

        Wall wyn = new Wall();
        wyn.changeHasStargate();
        wyn.isPortalCompatible();

        so.dispose();

        Stargate sn = new Stargate();
    }

    private static void testNum10() {
        System.out.println("Test #10: The player tries to place the stargate's both ends on the same wall causing stargate destroy.");

        // init START
        Stargate s = new Stargate();
        Wall w = new Wall();
        // init END

        actor.shoot();
        Bullet b = new Bullet();
        b.move();

        w.changeHasStargate();
        w.isPortalCompatible(); // this will return true

        boolean hasStargate = true;

        if(hasStargate) s.dispose();
    }

    private static void testNum11() {
        System.out.println("Test #11: The player steps next to a box and picks it up.");
        actor.move();
        fg = mow1.getForeground();
        // fg is box
        actor.pickUp();
    }

    private static void testNum12() {
        System.out.println("Test #12: The player steps next to a ZPM and picks it up.");

        actor.move();
        fg = mow1.getForeground();
        actor.pickUp();
        mow1.setForeground();
        //Disposing zpmItem
        System.out.println("ZPM destructor called");
        zpmItem = null;
        System.out.println("ZPM destructor returned");
        //If Actor.ZPMCount == allZPMs
        System.out.println("if Actor.ZPMCount == allZPMs is true, then:");
        if(true) engine.victory();
    }

    private static void testNum13() {
        System.out.println("Test #13: The player teleports.");
        //Step on
        actor.move();
        bg = mow1.getBackground();
        //This will returns a wall with a portal
        wallWithPortal = new Wall();
        wallWithPortal.isPortalCompatible();
        //Here the stargate has been created before
        sg = new Stargate();
        sg.teleport();
        //Teleports the player from a maze field to another
        mow1.setActor();
        mow2.setActor();
    }
}