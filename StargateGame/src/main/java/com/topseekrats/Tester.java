package com.topseekrats;

import com.topseekrats.background.*;
import com.topseekrats.foreground.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Tester {

    public static void main(String[] args) throws IOException {
        menu();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String temp = br.readLine();
        int testCase = 0;
        if (tryParse(temp)) testCase = Integer.parseInt(temp);
        while (testCase != -1)
        {
            System.out.println("############################### TEST CASE STARTED ##############################");
            runTestCase(testCase);
            System.out.println("################################ TEST CASE ENDED ###############################");
            System.out.println("Press ENTER to continue...");
            br.readLine();
            menu();
            temp = br.readLine();
            if (tryParse(temp)) testCase = Integer.parseInt(temp);
            else testCase = 0;
        }
    }

    //Menu of the application
    private static void menu() {
        StringBuilder sb = new StringBuilder();
        sb.append("################################################################################\n");
        sb.append("############################ TOPSEEKRATS - PROTOTYPE ###########################\n");
        sb.append("################################################################################\n\n");
        sb.append("Test cases:\n");
        sb.append("1: New game.\n");
        sb.append("2: Move, change bullet and shoot to a special wall.\n");
        sb.append("3: Move, change bullet and shoot to a regular wall.\n");
        sb.append("4: Open stargate.\n");
        sb.append("5: Move actor.\n");
        sb.append("6: Actor wanna move into a wall.\n");
        sb.append("7: Actor moves to a switch.\n");
        sb.append("8: Pick up ZPM.\n");
        sb.append("9: Pick up box.\n");
        sb.append("10: Pick up second box.\n");
        sb.append("11: Drop box.\n");
        sb.append("-1: Quit.\n\n");
        sb.append("################################################################################\n\n");
        sb.append("Pick a test case:");
        System.out.println(sb);
    }

    //Helper method for parsing the console input
    private static boolean tryParse(String val) {
        try {
            Integer.parseInt(val);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    //Helper method for running the selected test case
    private static void runTestCase(int testCase) {
        switch (testCase) {
            case 1:
                test1();
                break;
            case 2:
                test2("RIGHT", "COLONEL", "BLUE");
                break;
            case 3:
                test3("RIGHT", "JAFFA", "RED");
                break;
            case 4:
                test4("RIGHT", "COLONEL", "YELLOW");
                break;
            case 5:
                test5("RIGHT", "COLONEL");
                break;
            case 6:
                test6("RIGHT", "COLONEL");
                break;
            case 7:
                test7("RIGHT", "JAFFA");
                break;
            case 8:
                test8("RIGHT", "JAFFA");
                break;
            case 9:
                test9("RIGHT", "COLONEL");
                break;
            case 10:
                test10("RIGHT", "JAFFA");
                break;
            case 11:
                test11("RIGHT", "COLONEL");
                break;
            default:
                System.out.println("Invalid test case.");
                break;
        }
    }

    /**
     * 1. teszteset: játékosok létrehozása a pályán
     */
    private static void test1() {
        try {
            Engine.newGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int[] colonelPos = Maze.getInstance().actorsPosition[0];
        int[] jaffaPos = Maze.getInstance().actorsPosition[1];

        System.out.println("New game started.");
        System.out.println("Actors created at (" + colonelPos[1] + "," + colonelPos[0] + ") and (" + jaffaPos[1] + "," + jaffaPos[0] + ")");
    }

    /**
     * 2. teszteset: Játékos mozog, lő kompatibilis falra, portál létrejön a falon
     * @param strDir mozgás iránya
     * @param strActorType mozgó játékos típusa
     * @param strBulletType kilőtt lövedék típusa
     */
    private static void test2(String strDir, String strActorType, String strBulletType) {
        //Inicializálás
        createTestMaze();
        Actor actor = new Actor(ActorType.valueOf(strActorType));
        Maze.getInstance().moveDirection[actor.getType().ordinal()] = MoveDirection.valueOf(strDir);
        Maze.getInstance().playField[0][0].setActor(actor.getType().ordinal(), actor);
        Maze.getInstance().playField[0][2].setBackground(new Wall(true));
        Maze.getInstance().actorsPosition[actor.getType().ordinal()] = new int[] {0, 0};

        //mozgás, lövedékcsere, lövés
        int[] oldPos = new int[] {0, 0};
        actor.move();
        actor.changeBullet();
        actor.shoot();
        int[] newPos = Maze.getInstance().actorsPosition[actor.getType().ordinal()];
        int[] portalPos = Maze.getInstance().stargateEndPoints[1];

        //log
        System.out.println(strActorType + " moved from (" + oldPos[1] + "," + oldPos[0] +") to (" + newPos[1] + "," + newPos[0] + ")");
        System.out.println("Bullet " + strBulletType + " set for " + strActorType + " successfully");
        System.out.println(strBulletType + " portal created on wall at (" + portalPos[1] + "," + portalPos[0] + ")");
    }

    /**
     * 3. teszteset: Játékos mozog, lő nem kompatibilis falra, portál nem jön létre a falon
     * @param strDir mozgás iránya
     * @param strActorType mozgó játékos típusa
     * @param strBulletType kilőtt lövedék típusa
     */
    private static void test3(String strDir, String strActorType, String strBulletType) {
        //Inicializálás
        createTestMaze();
        Actor actor = new Actor(ActorType.valueOf(strActorType));
        Maze.getInstance().moveDirection[actor.getType().ordinal()] = MoveDirection.valueOf(strDir);
        Maze.getInstance().playField[0][0].setActor(actor.getType().ordinal(), actor);
        Maze.getInstance().playField[0][2].setBackground(new Wall(false));
        Maze.getInstance().actorsPosition[actor.getType().ordinal()] = new int[] {0, 0};

        //mozgás, lövedékcsere, lövés
        int[] oldPos = new int[] {0, 0};
        actor.move();
        actor.changeBullet();
        actor.shoot();
        int[] newPos = Maze.getInstance().actorsPosition[actor.getType().ordinal()];
        int[] portalPos = Maze.getInstance().stargateEndPoints[2];

        //log
        System.out.println(strActorType + " moved from (" + oldPos[1] + "," + oldPos[0] +") to (" + newPos[1] + "," + newPos[0] + ")");
        System.out.println("Bullet " + strBulletType + " set for " + strActorType + " successfully");
        if (portalPos[0] == -1) System.out.println("The wall is not portal-compatible, no portal created.");
    }

    /**
     * 4. teszteset: játékos kilövi a második portált kompatibilis falra, létrejön egy csillagkapu
     * @param strDir mozgás iránya
     * @param strActorType mozgó játékos típusa
     * @param strBulletType kilőtt lövedék típusa
     */
    private static void test4(String strDir, String strActorType, String strBulletType) {
        //Inicializálás
        createTestMaze();
        Actor actor = new Actor(ActorType.valueOf(strActorType));
        Maze.getInstance().moveDirection[actor.getType().ordinal()] = MoveDirection.valueOf(strDir);
        Maze.getInstance().playField[0][0].setActor(actor.getType().ordinal(), actor);
        Maze.getInstance().playField[0][2].setBackground(new Wall(true));
        Maze.getInstance().playField[2][2].setBackground(new Wall(true));
        Maze.getInstance().stargateEndPoints[BulletType.YELLOW.ordinal()] = new int[] {2, 2};
        Maze.getInstance().actorsPosition[actor.getType().ordinal()] = new int[] {0, 0};

        //mozgás, lövedékcsere, lövés
        int[] oldPos = new int[] {0, 0};
        actor.move();
        actor.changeBullet();
        actor.shoot();
        int[] newPos = Maze.getInstance().actorsPosition[actor.getType().ordinal()];
        int[] wallPos = Maze.getInstance().stargateEndPoints[BulletType.BLUE.ordinal()];
        int[] gatePosYellow = Maze.getInstance().stargateEndPoints[BulletType.YELLOW.ordinal()];
        int[] gatePosBlue = Maze.getInstance().stargateEndPoints[BulletType.BLUE.ordinal()];

        //log
        System.out.println(strActorType + " moved from (" + oldPos[1] + "," + oldPos[0] +") to (" + newPos[1] + "," + newPos[0] + ")");
        System.out.println("Bullet " + strBulletType + " set for " + strActorType + " successfully");
        System.out.println(strBulletType + " portal created on wall at (" + wallPos[1] + "," + wallPos[0] + ")");
        System.out.println("Stargate is on the field with portals (" + gatePosYellow[1] + "," + gatePosYellow[0] + "), (" + gatePosBlue[1] + "," + gatePosBlue[0] + ").");
    }

    /**
     * 5. teszteset: játékos mozog átjárható területre
     * @param strDir mozgás iránya
     * @param strType játékos típusa
     */
    private static void test5(String strDir, String strType) {
        //Inicializálás
        createTestMaze();
        Actor actor = new Actor(ActorType.valueOf(strType));
        Maze.getInstance().moveDirection[actor.getType().ordinal()] = MoveDirection.valueOf(strDir);

        Maze.getInstance().playField[1][1].setActor(actor.getType().ordinal(), actor);
        Maze.getInstance().actorsPosition[actor.getType().ordinal()] = new int[] {1, 1};

        //mozgás
        int[] oldPos = new int[] {1, 1};
        actor.move();
        int[] newPos = Maze.getInstance().actorsPosition[actor.getType().ordinal()];

        //log
        System.out.println(strType + " moved from (" + oldPos[1] + "," + oldPos[0] +") to (" + newPos[1] + "," + newPos[0] + ")");
    }

    /**
     * 6. teszteset: mozgás nem átjárható terület felé
     * @param strDir mozgás iránya
     * @param strType játékos típusa
     */
    private static void test6(String strDir, String strType) {
        //Inicializálás
        createTestMaze();
        Actor actor = new Actor(ActorType.valueOf(strType));
        Maze.getInstance().moveDirection[actor.getType().ordinal()] = MoveDirection.valueOf(strDir);
        Maze.getInstance().playField[0][0].setActor(actor.getType().ordinal(), actor);
        Maze.getInstance().playField[0][1].setBackground(new Wall(false));

        //mozgás
        int[] oldPos = new int[] {0, 0};
        actor.move();
        int[] newPos = Maze.getInstance().actorsPosition[actor.getType().ordinal()];

        //log
        System.out.println(strType + " cannot move from (" + oldPos[1] + "," + oldPos[0] + ") to (" + newPos[1] + "," + newPos[0] + "), the field is not passable." );
    }

    /**
     * 7. teszteset: játékos rálép egy mérlegre
     * @param strDir mozgás iránya
     * @param strType játékos típusa
     */
    private static void test7(String strDir, String strType) {
        //Inicializálás
        createTestMaze();
        Actor actor = new Actor(ActorType.valueOf(strType));
        Maze.getInstance().moveDirection[actor.getType().ordinal()] = MoveDirection.valueOf(strDir);
        Maze.getInstance().playField[0][0].setActor(actor.getType().ordinal(), actor);
        Switch s = new Switch(new Door(), 2);
        Maze.getInstance().playField[0][1].setBackground(s);

        //mozgás mérlegre
        int[] oldPos = new int[] {0, 0};
        int oldW = s.getWeight();
        actor.move();
        int[] newPos = Maze.getInstance().actorsPosition[actor.getType().ordinal()];

        //log
        System.out.println(strType + " moved from (" + oldPos[1] + "," + oldPos[0] + ") to (" + newPos[1] + "," + newPos[0] + ").");
        System.out.println("Weight changed from " + oldW + " to " + s.getWeight() + ".");
    }

    /**
     * 8. teszteset: ZPM felvétele
     * @param strDir mozgás iránya
     * @param strType játékos típusa
     */
    private static void test8(String strDir, String strType) {
        //Inicializálás
        createTestMaze();
        Actor actor = new Actor(ActorType.valueOf(strType));
        Maze.getInstance().moveDirection[actor.getType().ordinal()] = MoveDirection.valueOf(strDir);
        Maze.getInstance().playField[0][0].setActor(actor.getType().ordinal(), actor);
        Maze.getInstance().playField[0][1].pushForeground(new Item(ItemType.ZPM));

        //mozgás, felvétel
        int[] oldPos = new int[] {0, 0};
        actor.move();
        int[] newPos = Maze.getInstance().actorsPosition[actor.getType().ordinal()];
        int oldZpm = actor.getZpmCount();
        actor.pickUp();
        int newZpm = actor.getZpmCount();

        //log
        System.out.println(strType + " moved from (" + oldPos[1] + "," + oldPos[0] + ") to (" + newPos[1] + "," + newPos[0] + ").");
        System.out.println("ZPM count of " + strType + " changed from " + oldZpm + " to " + newZpm + ".");
    }

    /**
     * 9. teszteset: doboz felvétele
     * @param strDir mozgás iránya
     * @param strType játékos típusa
     */
    private static void test9(String strDir, String strType) {
        //Inicializálás
        createTestMaze();
        Actor actor = new Actor(ActorType.valueOf(strType));
        Maze.getInstance().moveDirection[actor.getType().ordinal()] = MoveDirection.valueOf(strDir);
        Maze.getInstance().playField[0][0].setActor(actor.getType().ordinal(), actor);
        Maze.getInstance().playField[0][1].pushForeground(new Item(ItemType.BOX));

        //mozgás, felvétel
        int[] oldPos = new int[] {0, 0};
        actor.move();
        int[] newPos = Maze.getInstance().actorsPosition[actor.getType().ordinal()];
        int oldFieldItemsNum = Maze.getInstance().playField[0][1].getForegrounds().size();
        actor.pickUp();
        int newFieldItemsNum = Maze.getInstance().playField[0][1].getForegrounds().size();

        //log
        System.out.println(strType + " moved from (" + oldPos[1] + "," + oldPos[0] + ") to (" + newPos[1] + "," + newPos[0] + ").");
        System.out.println("Count of foreground items on the field decreased from " + oldFieldItemsNum + " to " + newFieldItemsNum + ".");
    }

    /**
     * 10. teszteset: második doboz felvételének tiltása
     * @param strDir mozgás iránya
     * @param strType játékos típusa
     */
    private static void test10(String strDir, String strType) {
        //Inicializálás
        createTestMaze();
        Actor actor = new Actor(ActorType.valueOf(strType));
        Maze.getInstance().moveDirection[actor.getType().ordinal()] = MoveDirection.valueOf(strDir);
        Maze.getInstance().playField[0][0].setActor(actor.getType().ordinal(), actor);
        Maze.getInstance().playField[0][1].pushForeground(new Item(ItemType.BOX));
        Maze.getInstance().playField[0][1].pushForeground(new Item(ItemType.BOX));

        //mozgás, felvétel, 2. felvétel próbája
        int[] oldPos = new int[] {0, 0};
        actor.move();
        int[] newPos = Maze.getInstance().actorsPosition[actor.getType().ordinal()];
        int oldFieldItemNum = Maze.getInstance().playField[0][1].getForegrounds().size();
        actor.pickUp();
        int firstFieldItemNum = Maze.getInstance().playField[0][1].getForegrounds().size();
        actor.pickUp();
        int secondFieldItemNum = Maze.getInstance().playField[0][1].getForegrounds().size();

        //log
        System.out.println(strType + " moved from (" + oldPos[1] + "," + oldPos[0] + ") to (" + newPos[1] + "," + newPos[0] + ").");
        System.out.println("Count of foreground items on the field decreased from " + oldFieldItemNum + " to " + firstFieldItemNum + ", and after that to " + secondFieldItemNum + ".");
        System.out.println(strType + " cannot pick up two boxes.");
    }

    /**
     * 11. teszteset: doboz eldobása
     * @param strDir mozgás iránya
     * @param strType játékos típusa
     */
    private static void test11(String strDir, String strType) {
        //Inicializálás
        createTestMaze();
        Actor actor = new Actor(ActorType.valueOf(strType));
        Maze.getInstance().moveDirection[actor.getType().ordinal()] = MoveDirection.valueOf(strDir);
        Maze.getInstance().playField[0][0].setActor(actor.getType().ordinal(), actor);
        Maze.getInstance().playField[0][0].pushForeground(new Item(ItemType.BOX));
        actor.pickUp();

        //mozgás, eldobás
        int[] oldPos = new int[] {0, 0};
        actor.move();
        int[] newPos = Maze.getInstance().actorsPosition[actor.getType().ordinal()];
        int oldFieldItemNum = Maze.getInstance().playField[0][2].getForegrounds().size();
        actor.dropBox();
        int newFieldItemNum = Maze.getInstance().playField[0][2].getForegrounds().size();

        //log
        System.out.println(strType + " moved from (" + oldPos[1] + "," + oldPos[0] + ") to (" + newPos[1] + "," + newPos[0] + ").");
        System.out.println("Box number at field (1,0) changed from " + oldFieldItemNum + " to " + newFieldItemNum + ".");
    }

    /**
     * Teszt játékmező létrehozása, egyelőre csak padlóval tesztelünk
     */
    private static void createTestMaze() {
        Maze.getInstance().playField = new MazeObjectWrapper[3][3];
        for (int j = 0; j < Maze.getInstance().playField.length; ++j )
            for (int i = 0; i < Maze.getInstance().playField.length; ++i)
                Maze.getInstance().playField[i][j] = new MazeObjectWrapper(new Floor());
    }

}
