package com.topseekrats.foreground;

import com.topseekrats.MazeObjectWrapper;

public class Stargate extends Foreground {
    public void dispose() {
        System.out.println("Stargate.dispose() called");
        System.out.println("Stargate.dispose() returned");
    }

    public void teleport() {
        System.out.println("Stargate.teleport() called");
        new MazeObjectWrapper().getActor();
        new MazeObjectWrapper().setActor();
        System.out.println("Stargate.teleport() returned");
    }
}
