package com.topseekrats.background;

import com.topseekrats.Actor;
import com.topseekrats.ActorType;
import com.topseekrats.Engine;
import com.topseekrats.MazeObject;

/**
 * Szakadék osztály, ha rálép egy ilyen mezőre a játékos, akkor a játékos megsemmisül
 */
public class Cleft extends Background {

    /**
     * Szakadékba zuhanás miatti megsemmisítés
     * @param mazeObject A megsemmissíteni kívánt mazeObject
     */
    public void destroy(MazeObject mazeObject) {
        if (mazeObject instanceof Actor) {
            Actor temp = (Actor)mazeObject;
            //if (temp.getType() == ActorType.COLONEL) Engine.death(ActorType.COLONEL);
            //else Engine.death(ActorType.JAFFA);
            Engine.death(temp.getType());
        }
    }

    @Override
    public boolean isPassable() { return true; }

}
