package com.topseekrats.background;

/**
 * Mérleg, ez vezérli az ajtó nyitását.
 * Amikor egy doboz vagy egy játékos rákerül a mérlegre, meghívódik az incrementWeight,
 * ha teljesül a feltétel, kinyílik az ajtó.
 * Ellenkező esetben csukódik az ajtó.
 */
public class Switch extends Background {

    private Door door;
    private int weight = 0;
    private int weightConstraint;

    public Switch(Door door, int weightConstraint) {
        this.door = door;
        this.weightConstraint = weightConstraint;
    }

    public int getWeight() { return weight; }

    /**
     * Súly növelése és ajtó nyitása, ha kell
     */
    public void incrementWeight() {
        ++weight;
        if (weight >= weightConstraint && !door.isPassable()) door.changeOpened();
    }

    /**
     * Súly csökkentése és ajtó zárása, ha kell
     */
    public void decrementWeight() {
        --weight;
        if (weight < weightConstraint && door.isPassable()) door.changeOpened();
    }

    @Override
    public boolean isPassable() { return true; }

}
