package com.topseekrats;

import com.topseekrats.foreground.Item;

public interface MazeObject {

    boolean isForeground();
    void dropBox();
    void move();
    void shoot();
    void changeBullet();
    void pickUp(Item item);

}
