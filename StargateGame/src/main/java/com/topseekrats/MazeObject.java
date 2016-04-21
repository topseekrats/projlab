package com.topseekrats;

import com.topseekrats.foreground.Item;

public interface MazeObject {

    void changeBullet();
    void dropBox();
    void move();
    void pickUp(Item item);
    void shoot();

    boolean isForeground();

}
