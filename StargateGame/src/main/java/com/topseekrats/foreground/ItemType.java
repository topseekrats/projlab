package com.topseekrats.foreground;

/**
 * <p>A labirintusban elhelyezett tárgyakat meghatározó felsoroló típus.</p>
 * <p>Lehetséges tárgytípusok:</p>
 * <ul>
 *     <li>{@link ItemType#BOX} - felvehető doboz, aminek súlya van</li>
 *     <li>{@link ItemType#ZPM} - felvehető küldetéstárgy</li>
 * </ul>
 */
public enum ItemType {
    ZPM,
    BOX
}
