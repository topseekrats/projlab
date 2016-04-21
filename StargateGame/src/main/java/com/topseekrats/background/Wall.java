package com.topseekrats.background;

public class Wall extends Background {

    private boolean hasStargate = false;
    private boolean portalCompatible;

    public Wall(boolean portalCompatible) { this.portalCompatible = portalCompatible; }

    public boolean isPortalCompatible() { return portalCompatible; }

    public void changeHasStargate() { hasStargate = !hasStargate; }

    @Override
    public boolean isPassable() { return hasStargate; }

}
