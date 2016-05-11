package com.topseekrats.background;

/**
 * Ajtó objektumot reprezentáló osztály.
 */
public class Door extends Background {

    /** Az ajtó állapotát jelző változó. */
    private boolean opened = false;

    /** Ajtó állapotát változtató metódus. */
    public void changeOpened() { opened = !opened; }

    /**
     * Átjárhatóságot szabályzó metódus.
     * Az átjárhatóság ajtó esetén az opened változó értékétől függ.
     */
    @Override
    public boolean isPassable() { return opened; }

}
