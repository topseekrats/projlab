package com.topseekrats;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Tester {

    public static void main(String[] args) {
        Replicator r = new Replicator();
    }
}
//Class for testing the skeleton
public class Tester
{
    //Entry point of the application
    public static void main(String[] args) throws IOException {
        menu();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for(String temp = br.readLine(); temp!=null; temp=br.readLine()){
            double sum = 0;
            StringTokenizer st = new StringTokenizer(temp);
            String name = st.nextToken();

        }
    }


    // String temp = br.readLine();
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
        sb.append("############################ TOPSEEKRATS - PROTO ############################\n");
        sb.append("################################################################################\n\n");
        sb.append("Test cases:\n");
        sb.append("1: Game initialization.\n");
        sb.append("2: Shot bullet to the special wall.\n");
        sb.append("3: Shot bullet to  the not special wall.\n");
        sb.append("4: Open stargate.\n");
        sb.append("5: Modify stargate.\n");
        sb.append("6: Go across the stargate.\n");
        sb.append("7: Close stargate.\n");
        sb.append("8: Move Actor.\n");
        sb.append("9: The Actor moves to the wall\n");
        sb.append("10: The Actor moves to the swithc.\n");
        sb.append("11: Pick up ZPM.\n");
        sb.append("12: Appear ZPM.\n");
        sb.append("13: Pick up box.\n");
        sb.append("14: Pick up other box.\n");
        sb.append("15: Drop box.\n");
        sb.append("16: Pack boxes on each other.\n");
        sb.append("17: Open door.\n");
        sb.append("18: Go across to the opened door.\n");
        sb.append("19: Go across to the closed door.\n");
        sb.append("20: The Actor dies.\n");
        sb.append("21: Box destroy.\n");
        sb.append("22: The Replicator shot.\n");
        sb.append("23: The Replicator moves.\n");
        sb.append("24: Replicator falls into the crap.\n");
        sb.append("25: Collect all of the ZPM.\n");
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
                test2();
                break;
            case 3:
                test3();
                break;
            case 4:
                test4();
                break;
            case 5:
                test5();
                break;
            case 6:
                test6();
                break;
            case 7:
                test7();
                break;
            case 8:
                test8();
                break;
            case 9:
                test9();
                break;
            case 10:
                test10();
                break;
            case 11:
                test11();
                break;
            case 12:
                test12();
                break;
            case 13:
                test13();
                break;
            case 14:
                test14();
                break;
            case 15:
                test15();
                break;
            case 16:
                test16();
                break;
            case 17:
                test17();
                break;
            case 18:
                test18();
                break;
            case 19:
                test19();
                break;
            case 20:
                test20();
                break;
            case 21:
                test21();
                break;
            case 22:
                test22();
                break;
            case 23:
                test23();
                break;
            case 24:
                test24();
                break;
            case 25:
                test25();
                break;
            default:
                System.out.println("Invalid test case.");
                break;
        }
    }

    //Test #1
    private static void test1() {
        start test.map
        createActors <x1:y1> <x2:y2> <x3:y3>
    }

    //Test #2
    private static void test2() {
        moveActor <actorType> <moveDirection>
                changeBullet <actorType> <bulletType>
                shoot <actorType> <moveDirection>
    }

    //Test #3
    private static void test3() {
        moveActor <moveDirection> <actorType>
                changeBullet <actorType> <bulletType>
                shoot <actorType> <moveDirection>
    }

    //Test #4
    private static void test4() {
        moveActor <moveDirection> <actorType>
                changeBullet <actorType> <bulletType>
                shoot <actorType> <moveDirection>
                getActualState
    }

    //Test #5
    private static void test5() {
        changeBullet <actorType> <bulletType>
                shoot <actorType> <moveDirection>
                getActualState
    }

    //Test #6
    private static void test6() {
        moveActor <moveDirection> <actorType>
                teleport <actorType> <x:y>
    }

    //Test #7
    private static void test7() {
        changeBullet <actorType> <bulletType>
                shoot <actorType> <moveDirection>
                moveActor <moveDirection> <actorType>
                teleport <actorType> <x1:y1>
                getActualState
    }

    //Test #8
    private static void test8() {
        moveActor <moveDirection> <actorType>
    }

    //Test #9
    private static void test9() {
        moveActor <moveDirection> <actorType>
    }

    //Test #10
    private static void test10() {
        moveActor <moveDirection> <actorType>
                changeWeight <x:y> <weight>
    }

    //Test #11
    private static void test11() {
        moveActor <moveDirection> <actorType>
                pickUp <actorType>
    }

    //Test #12
    private static void test12() {
        moveActor <moveDirection> <actorType>
                pickUp <actorType>
    }

    //Test #13
    private static void test13() {
        moveActor <moveDirection> <actorType>
                pickUp <actorType>
                changeWeight <x:y> <weight>
    }

    //Test #14
    private static void test14() {
        moveActor <moveDirection> <actorType>
                pickUp <actorType>
    }

    //Test #15
    private static void test15() {
        moveActor <moveDirection> <actorType>
                dropBox <actorType>
    }

    //Test #16
    private static void test16() {
        new Actor().changeBullet();
    }

    //Test #17
    private static void test17() {
        new Actor().changeBullet();
    }

    //Test #18
    private static void test18() {
        new Actor().changeBullet();
    }

    //Test #19
    private static void test19() {
        new Actor().changeBullet();
    }

    //Test #20
    private static void test20() {
        new Actor().changeBullet();
    }

    //Test #21
    private static void test21() {
        new Actor().changeBullet();
    }

    //Test #22
    private static void test22() {
        new Actor().changeBullet();
    }

    //Test #23
    private static void test23() {
        new Actor().changeBullet();
    }

    //Test #24
    private static void test24() {
        new Actor().changeBullet();
    }

    //Test #25
    private static void test25() {
        new Actor().changeBullet();
    }

}
