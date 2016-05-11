package com.topseekrats.background;

/**
 * Fal osztálya, nem átjárható, speciális falra kilőhető portál.
 * Tárolja, hogy van-e rajta portál, vagy sem.
 */
public class Wall extends Background {

    /** Csillagkapu jelenlétét jelző változó. */
    private boolean hasStargate = false;

    /** A fal csillagkapu-kompatibilitását jelző változó. */
    private boolean portalCompatible;

    /**
     * Fal konstruktor.
     *
     * @param portalCompatible csillagkapu kompatibilitás
     */
    public Wall(boolean portalCompatible) { this.portalCompatible = portalCompatible; }

    /** Csillagkapu kompatibilitás lekérdezése. */
    public boolean isPortalCompatible() { return portalCompatible; }

    /** Csillagkapu jelenlétének lekérdezése. */
    public void changeHasStargate() { hasStargate = !hasStargate; }

    /**
     * Dal átjárhatóságának lekérdezése.
     * Ha van csillagkapu a falon, akkor az átjárható, egyéb esetben nem.
     *
     * @return átjárhatóságot jelző logikai érték
     */
    @Override
    public boolean isPassable() { return hasStargate; }

}
