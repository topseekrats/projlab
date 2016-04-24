package com.topseekrats.background;

import com.topseekrats.Actor;
import com.topseekrats.ActorType;
import com.topseekrats.Engine;
import com.topseekrats.MazeObject;

public class Cleft extends Background {

    public void destroy(MazeObject mazeObject) {
        if (mazeObject instanceof Actor) {
            Actor temp = (Actor)mazeObject;
            if (temp.getType() == ActorType.COLONEL) Engine.death(ActorType.COLONEL);
            else Engine.death(ActorType.JAFFA);
        }
    }

    @Override
    public boolean isPassable() { return true; }

}
