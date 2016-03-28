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
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

public class ConsoleApplication
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
    private static Bullet bullet1 = null;
    private static Bullet bullet2 = null;
    private static Wall wall1 = null;
    private static Wall wall2 = null;
    private static Stargate sg2 = null;

    private static Foreground fg = null;
    private static Background bg = null;

    private static int testNum01() {
        System.out.println("Test #1: Game initialization --- started.");
        //Create engine
        engine = new Engine();
        //Start new game
        engine.newGame();
        //Create an actor
        actor = new Actor();
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
        System.out.println("Test #1: done. Press a key to continue.");

        return 0;
    }

    private static int testNum02() {
        System.out.println("Test #2: The player picks up a box from a switch, door closes --- started.");
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
        System.out.println("Test #2: done. Press a key to continue.");

        return 0;
    }

    private static int testNum03() {
        System.out.println("Test #3: The player drops a box onto a switch, door opens --- started.");
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
        System.out.println("Test #3: done. Press a key to continue.");

        return 0;
    }

    private static int testNum04() {
        System.out.println("Test #4: The player drops a box into a cleft --- started.");
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
        System.out.println("Test #4: done. Press a key to continue.");

        return 0;
    }

    private static int testNum05() {
        System.out.println("Test #5: The player picks up the last ZPM and wins --- started.");
        fg = mow1.getForeground();
        actor.pickUp();
        mow1.setForeground();
        //Disposing zpmItem
        System.out.println("ZPM destructor called");
        zpmItem = null;
        System.out.println("ZPM destructor returned");
        //If Actor.ZPMCount == allZPMs
        System.out.println("if Actor.ZPMCount == allZPMs is true, then:");
        if(true)
            engine.victory();
        System.out.println("Test #5: done. Press a key to continue.");

        return 0;
    }

    private static int testNum06() {
        System.out.println("Test #6: The player steps on a switch and steps off from it --- started.");
        sw = new Switch();
        door = new Door();
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
        System.out.println("Test #6: done. Press a key to continue.");

        return 0;
    }

    private static int testNum07() {
        System.out.println("Test #7: The player steps into a cleft");
        actor.move();
        bg = mow1.getBackground();
        cleft = new Cleft();
        cleft.destroy();
        //Disposing Actor
        System.out.println("Actor destructor called");
        actor = null;
        System.out.println("Actor destructor returned");
        engine.death();
        System.out.println("Test #7: done. Press a key to continue.");

        return 0;
    }

    private static int testNum08() {
        wall1 = new Wall();
        wall2 = new Wall();

        System.out.println("Test #8: .");
        actor.shoot();
        System.out.println("Bullet constructor called");
        bullet1 = new Bullet();
        bullet1.move();
        wall1.changeHasStargete();
        wall1.isPortalCompatible();
        actor.changeBullet();
        actor.shoot();
        System.out.println("Bullet constructor called");
        bullet2 = new Bullet();
        bullet2.move();
        wall2.changeHasStargete();
        wall2.isPortalCompatible();
        System.out.println("Stargate constructor called");
        sg = new Stargate();


        System.out.println("Test #9: done. Press a key to continue.");
        return 0;
    }

    private static int testNum09() {
        System.out.println("Test #9:");
        wall1 = new Wall();
        wall2 = new Wall();

        actor.shoot();
        System.out.println("Bullet constructor called");
        bullet1 = new Bullet();
        bullet1.move();
        wall1.changeHasStargete();
        wall1.isPortalCompatible();
        wall2.changeHasStargete();
        wall2.isPortalCompatible();
        //Disposing Stargate
        System.out.println("Stargate destructor called");
        sg = null;
        System.out.println("Stargate destructor returned");
        System.out.println("Stargate constructor called");
        sg2 = new Stargate();
        System.out.println("Test #9: done. Press a key to continue.");
        return 0;
    }

    private static int testNum10() {
        System.out.println("Test #10: ");
        wallWithPortal = new Wall();
        actor.shoot();
        bullet1 = new Bullet();
        bullet1.move();
        wallWithPortal.changeHasStargete();
        wallWithPortal.isPortalCompatible();
        if(true) {
            //Disposing Stargate
            System.out.println("Stargate destructor called");
            sg = null;
            System.out.println("Stargate destructor returned");
        }
        System.out.println("Test #10: done. Press a key to continue.");
        return 0;
    }

    private static int testNum11() {
        System.out.println("Test #11: The player steps and picks up a box.");
        actor.move();
        fg = mow1.getForeground();
        actor.pickUp();
        mow1.setActor();
        System.out.println("Test #11: done. Press a key to continue.");
        return 0;
    }

    private static int testNum12() {
        System.out.println("Test #12: The player steps picks up the ZPM and wins --- started.");
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
        if(true)
            engine.victory();
        System.out.println("Test #12: done. Press a key to continue.");

        return 0;
    }

    private static int testNum13() {
        System.out.println("Test #13: The player teleports --- started.");
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
        System.out.println("Test #13: done. Press a key to continue.");

        return 0;
    }

    private static int testCase(int caseNumber) {
        testNum01();
        switch (caseNumber) {
            case 1:
                return testNum01();
            case 2:
                return testNum02();
            case 3:
                return testNum03();
            case 4:
                return testNum04();
            case 5:
                return testNum05();
            case 6:
                return testNum06();
            case 7:
                return testNum07();
            case 8:
                return testNum08();
            case 9:
                return testNum09();
            case 10:
                return testNum10();
            case 11:
                return testNum11();
            case 12:
                return testNum12();
            case 13:
                return testNum13();

            default:
                return -1;
        }
    }

    public static void main(String [] args)
    {
        System.out.println("Skeleton tests - TopSeekRats");
        System.out.println("Choose test from 1 to 13");
        // String testCaseString = System.console().readLine();

        int testCaseInt = -2;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            testCaseInt = Integer.parseInt(br.readLine());
        }
        catch (Exception e) {
            System.err.println(e);
        }

        int result = testCase(testCaseInt);
        System.out.println(result);

       // String waitForContinue = System.console().readLine();
    }
}