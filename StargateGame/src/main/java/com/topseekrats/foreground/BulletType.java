package com.topseekrats.foreground;

/**
 * <p>A játékosok lövedékeinek típusait tároló felsoroló típus.</p>
 * <p>Játkosokhoz tartozó színek:</p>
 * {@link com.topseekrats.ActorType#COLONEL}:
 * <ul>
 *   <li>{@link BulletType#YELLOW}</li>
 *   <li>{@link BulletType#BLUE}</li>
 * </ul>
 * {@link com.topseekrats.ActorType#JAFFA}:
 * <ul>
 *   <li>{@link BulletType#GREEN}</li>
 *   <li>{@link BulletType#RED}</li>
 * </ul>
 */
public enum BulletType {
    YELLOW,
    BLUE,
    GREEN,
    RED
}
