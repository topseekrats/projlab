package com.topseekrats;

import com.topseekrats.gui.Frame;

import java.util.Arrays;


/**
 * A játék indításáért, valamint a konzolra történő log-olásért felelős
 * osztály.
 */
public class Console {

    public static void main(String[] args) {
//        Frame jf = new Frame();
        int[] a = new int[] {1,1};
        int[] b = new int[] {1,1};
        boolean e = Arrays.equals(a, b);

        int i = 51;
    }

    public static void log(String s) { System.out.println(s); }

}
