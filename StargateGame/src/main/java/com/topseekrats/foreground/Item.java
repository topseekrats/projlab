package com.topseekrats.foreground;

public class Item extends Foreground {

    private ItemType type;

    public Item(ItemType type) { this.type = type; }

    public ItemType getType() { return type; }

}
