package com.topseekrats;

/**
 * A játékteret alkotó labirintus egyes mezőin elhelyezhető objektumok interfésze.
 */
public interface MazeObject {

    void changeBullet();
    void dropBox();
    void move();
    void pickUp();
    void shoot();

    boolean isForeground();

}
