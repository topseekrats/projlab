package com.topseekrats;

public interface MazeObject {
    boolean isForeground();
    void setWeight();
    int getWeight();
    void move();
    void shoot();
    void changeBullet();
    void dropBox();
    void pickUp();
    void dispose();

}
