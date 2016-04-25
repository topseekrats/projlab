package com.topseekrats.foreground;

/**
 * Dobozt és ZPM-et reprezentáló osztály
 * ItemType határozza meg, hogy doboz vagy ZPM
 */
public class Item extends Foreground {

    private ItemType type;

    public Item(ItemType type) { this.type = type; }

    public ItemType getType() { return type; }

}
