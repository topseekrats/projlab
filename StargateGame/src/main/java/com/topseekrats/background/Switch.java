package com.topseekrats.background;

public class Switch extends Background {

    private Door door;
    private int weight = 0;
    private int weightConstraint;

    public Switch(Door door, int weightConstraint) {
        this.door = door;
        this.weightConstraint = weightConstraint;
    }

    public int getWeight() { return weight; }

    public void incrementWeight() {
        ++weight;
        if (weight >= weightConstraint && !door.isPassable()) door.changeOpened();
    }

    public void decrementWeight() {
        --weight;
        if (weight < weightConstraint && door.isPassable()) door.changeOpened();
    }

    @Override
    public boolean isPassable() { return true; }

}
