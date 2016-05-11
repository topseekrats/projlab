package com.topseekrats.background;

import com.topseekrats.Actor;
import com.topseekrats.Engine;
import com.topseekrats.MazeObject;

/**
 * Szakadékot reprezentáló osztály.
 * Ha egy játékos ilyen mezőre lép, akkor meghal.
 * Ha egy tárgy ilyen mezőbe esik, az megsemmisül.
 */
public class Cleft extends Background {

    /**
     * A konkrét megsemmisítést végző metódus.
     *
     * @param mazeObject a megsemmissíteni kívánt objektum
     */
    public void destroy(MazeObject mazeObject) {
        if (mazeObject instanceof Actor) {
            Actor actor = (Actor)mazeObject;
            Engine.death(actor.getType());
        }
    }

    @Override
    public boolean isPassable() { return true; }

}
