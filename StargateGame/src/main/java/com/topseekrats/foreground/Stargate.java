package com.topseekrats.foreground;

public class Stargate extends Foreground {

    public Stargate() {
        System.out.println("Stargate.newStargate() called");

        System.out.println("Stargate.newStargate() returned");
    }

    public void teleport() {
        System.out.println("Stargate.teleport() called");

        System.out.println("Stargate.teleport() returned");
    }

    @Override
    public void dispose() {
        System.out.println("Stargate.dispose() called");

        System.out.println("Stargate.dispose() returned");
    }
}
