package com.topseekrats.foreground;

import com.topseekrats.Actor;
import com.topseekrats.ActorType;
import com.topseekrats.Maze;

/**
 * <p>Csillagkaput reprezentáló osztály.
 * A játékos csillagkapu-végpontok közti áthelyezéséről gondoskodik.
 * A tényleges teleportálást a játékos mozgása metódusa hívja meg.</p>
 * <p>A csillagkapuk páronként értelmezhető objektumok, mert az átjárónak
 * két végpontja van. Ennek megfelelően a csillagkapu-párok szorosan
 * összetartoznak.</p>
 */
public class Stargate extends Foreground {

    /** A csillagkapu párjának koordinátái. */
    private int[] pairCoords;

    /** A csillagkaput kilövő játékos. */
    private ActorType owner;

    /**
     * CSillagkapu konstruktor.
     *
     * @param pairCoords kapupár koordinátái
     */
    public Stargate(int[] pairCoords, ActorType owner) {
        this.pairCoords = pairCoords;
        this.owner = owner;
    }

    public void setPairCoords(int[] pairCoords) { this.pairCoords = pairCoords; }
    public ActorType getOwner() { return owner; }

    /**
     * Játékos egyik végpontból a másikba helyezése.
     *
     * @param actor az áthelyezendő játékos objektum
     */
    public void teleport(Actor actor) {
        int[] pos = Maze.getInstance().actorsPosition[actor.getType().ordinal()];

        // Áthelyezés egyik végpontból a másikba.
        Maze.getInstance().playField[pos[0]][pos[1]].setActor(actor.getType().ordinal(),null);
        Maze.getInstance().playField[pairCoords[0]][pairCoords[1]].setActor(actor.getType().ordinal(),actor);
        Maze.getInstance().actorsPosition[actor.getType().ordinal()] = pairCoords;
    }

}
