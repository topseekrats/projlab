package com.topseekrats.foreground;

import com.topseekrats.Actor;
import com.topseekrats.Maze;

/**
 * Csillagkapu osztály, ez végzi a játékos teleportálását
 * A teleportálást a játékos mozgása hívja meg
 */
public class Stargate extends Foreground {

    private int[] pairCoords;

    public Stargate(int[] pairCoords) { this.pairCoords = pairCoords; }

    public void setPairCoords(int[] pairCoords) { this.pairCoords = pairCoords; }

    /**
     * Játékos teleportálása
     * @param actor A teleportálandó játékos
     */
    public void teleport(Actor actor) {
        int[] pos = Maze.getInstance().actorsPosition[actor.getType().ordinal()];

        //elteleportáljuk egyik helyről a másikra
        Maze.getInstance().playField[pos[0]][pos[1]].setActor(actor.getType().ordinal(),null);
        Maze.getInstance().playField[pairCoords[0]][pairCoords[1]].setActor(actor.getType().ordinal(),actor);
        Maze.getInstance().actorsPosition[actor.getType().ordinal()] = pairCoords;
    }

}
