package com.topseekrats;

import com.topseekrats.foreground.Stargate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Tester
{
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

    private static void menu() {
        StringBuilder sb = new StringBuilder();
        sb.append("################################################################################\n");
        sb.append("############################ TOPSEEKRATS - SKELETON ############################\n");
        sb.append("################################################################################\n\n");
        sb.append("Test cases:\n");
        sb.append("1: Game initialization.\n");
        sb.append("2: Pick up box/ZPM.\n");
        sb.append("3: Drop box.\n");
        sb.append("4: Move.\n");
        sb.append("5: Stargate.\n");
        sb.append("6: Teleport.\n");
        sb.append("7: Change bullet.\n");
        sb.append("-1: Quit.\n\n");
        sb.append("################################################################################\n\n");
        sb.append("Pick a test case:");
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
            default:
                System.out.println("Invalid test case.");
                break;
        }
    }

    private static void test1() {
        new Engine().newGame();
    }

    private static void test2() {
        new Actor().pickUp();
    }

    private static void test3() {
        new Actor().dropBox();
    }

    private static void test4() {
        new Actor().move();
    }

    private static void test5() {
        new Actor().shoot();
    }

    private static void test6() {
        new Stargate().teleport();
    }

    private static void test7() {
        new Actor().changeBullet();
    }
}
