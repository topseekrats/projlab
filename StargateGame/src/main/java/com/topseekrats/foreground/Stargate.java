package com.topseekrats.foreground;

import com.topseekrats.Actor;
import com.topseekrats.ActorType;
import com.topseekrats.Maze;

public class Stargate extends Foreground {

    private int[] pairCoors;

    public Stargate(int[] pairCoors) { this.pairCoors = pairCoors; }

    public void teleport(Actor actor) {
        int[] pos = Maze.getInstance().actorsPosition[actor.getType().ordinal()];

        //elteleportáljuk egyik helyről a másikra
        Maze.getInstance().playField[pos[0]][pos[1]].setActor(actor.getType().ordinal(),null);
        Maze.getInstance().playField[pairCoors[0]][pairCoors[1]].setActor(actor.getType().ordinal(),actor);
        Maze.getInstance().actorsPosition[actor.getType().ordinal()] = pairCoors;
    }

}
