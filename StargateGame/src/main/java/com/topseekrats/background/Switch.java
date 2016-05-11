package com.topseekrats.background;

/**
 * Mérleget reprezentáló osztály.
 * A mérlegek vezérlik az ajtók nyitott/zárt állapotát.
 * Ha valamilyen egyéb objektum kerül a mérlegre, akkor a mérlegre helyezett súly nő.
 * Minden egyes mérleg rendelkezik egy súlykorláttal, amit ha az aktuális súly elér,
 * úgy a mérleghez tartozó ajtó kinyílik.
 * Ha a mérlegen levő súly a súlykorlát alá csökken, akkor az ajtó bezáródik.
 */
public class Switch extends Background {

    /** Az adott mérleghez tartozó ajtó. */
    private Door door;

    /** A mérleg aktuális súlya. */
    private int weight = 0;

    /** A mérleg súlykorlátja. */
    private int weightConstraint;

    /**
     * Mérleg konstruktor.
     *
     * @param door             mérleghez tartozó ajtó
     * @param weightConstraint mérleghez tartozó súlykorlát
     */
    public Switch(Door door, int weightConstraint) {
        this.door = door;
        this.weightConstraint = weightConstraint;
    }

    public int getWeight() { return weight; }

    /** Mérlegen levő súly növelése és ajtó nyitása, ha kell. */
    public void incrementWeight() {
        ++weight;
        if (weight >= weightConstraint && !door.isPassable()) door.changeOpened();
    }

    /** Mérlegen levő súly csökkentése és ajtó zárása, ha kell. */
    public void decrementWeight() {
        --weight;
        if (weight < weightConstraint && door.isPassable()) door.changeOpened();
    }

    @Override
    public boolean isPassable() { return true; }

}
