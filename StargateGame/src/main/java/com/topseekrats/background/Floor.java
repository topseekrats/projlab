package com.topseekrats.background;

/**
 * Padlót reprezentáló osztály.
 * Az ilyen típusú mezők minden esetben átjárhatók.
 */
public class Floor extends Background {

    @Override
    public boolean isPassable() { return true; }

}
