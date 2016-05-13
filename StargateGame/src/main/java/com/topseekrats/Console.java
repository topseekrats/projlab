package com.topseekrats;

import com.topseekrats.ui.Frame;


/**
 * A játék indításáért, valamint a konzolra történő log-olásért felelős
 * osztály.
 */
public class Console {

    public static void main(String[] args) {
        Frame jf = new Frame();
    }

    public static void log(String s) { System.out.println(s); }

}
