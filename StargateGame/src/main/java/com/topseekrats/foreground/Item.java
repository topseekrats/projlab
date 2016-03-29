package com.topseekrats.foreground;

public class Item extends Foreground {
    public String getType() {
        System.out.println("Item.getType() called");
        System.out.println("Item.getType() returned");
        return null;
    }

    @Override
    public void setWeight() {
        System.out.println("Item.setWeight() called");
        System.out.println("Item.setWeight() returned");
    }

    @Override
    public int getWeight() {
        System.out.println("Item.getWeight() called");
        System.out.println("Item.getWeight() returned");
        return 0;
    }
}
