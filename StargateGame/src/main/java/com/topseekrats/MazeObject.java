package com.topseekrats;

import com.topseekrats.foreground.Item;

/**
 * Mezőobjektumok közös interfésze
 */
public interface MazeObject {

    void changeBullet();
    void dropBox();
    void move();
    void pickUp();
    void shoot();

    boolean isForeground();

}
