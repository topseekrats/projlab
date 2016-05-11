package com.topseekrats.foreground;

/**
 * <p>A labirintusban elhelyezett tárgyakat reprezentáló osztály.</p>
 * <p>Lehetséges tárgytípusok:</p>
 * <ul>
 *     <li>{@link ItemType#BOX}</li>
 *     <li>{@link ItemType#ZPM}</li>
 * </ul>
 */
public class Item extends Foreground {

    private ItemType type;

    /**
     * Tárgy konstruktor.
     *
     * @param type tárgy típusa
     */
    public Item(ItemType type) { this.type = type; }

    public ItemType getType() { return type; }

}
