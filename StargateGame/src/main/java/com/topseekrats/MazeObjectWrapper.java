package com.topseekrats;

import com.topseekrats.background.Background;
import com.topseekrats.foreground.Foreground;

public class MazeObjectWrapper {
    public Background getBackground() {
        System.out.println("MazeObjectWrapper.getBackground() called");

        System.out.println("MazeObjectWrapper.getBackground() returned");
        return null;
    }

    public Foreground getForeground() {
        System.out.println("MazeObjectWrapper.getBackground() called");

        System.out.println("MazeObjectWrapper.getBackground() returned");
        return null;
    }

    public void setForeground() {
        System.out.println("MazeObjectWrapper.setForeground() called");

        System.out.println("MazeObjectWrapper.setForeground() returned");
    }
}
