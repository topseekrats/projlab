package com.topseekrats;

public interface MazeObject {
    boolean isForeground();

    void move();
    void shoot();
    void changeBullet();
    void dropBox();
    void pickUp();
}
