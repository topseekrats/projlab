package com.topseekrats.foreground;

public class Item extends Foreground {
    public String getType() {
        System.out.println("Item.getType() called");

        System.out.println("Item.getType() returned");
        return null;
    }
}
