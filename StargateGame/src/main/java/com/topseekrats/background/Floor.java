package com.topseekrats.background;

/**
 * Padló osztály, erre kerülhetnek rá a ZPM-ek, dobozok, játékosok ezeken haladnak végig.
 */
public class Floor extends Background {

    public Floor() {}

    @Override
    public boolean isPassable() { return true; }

}
