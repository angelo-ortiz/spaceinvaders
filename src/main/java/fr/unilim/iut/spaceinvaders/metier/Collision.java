package fr.unilim.iut.spaceinvaders.metier;

import fr.unilim.iut.spaceinvaders.utils.MissileException;

public class Collision {

    public static boolean detecterCollisionAttaque(Personnage personnage, Missile missileAttaque) {
        if (missileAttaque.longueur() > personnage.longueur()) {
            throw new MissileException("La longueur du missile est supérieure à celle du personnage");
        }
        return estLeMissilePartiellementDansLeSprite(personnage, missileAttaque);
    }

    private static boolean estLeMissilePartiellementHorizontalDansLeSprite(Sprite sprite, Missile missile) {
        return ( sprite.abscisseLaPlusAGauche() <= missile.abscisseLaPlusAGauche() && missile.abscisseLaPlusAGauche() <= sprite.abscisseLaPlusADroite() ) ||
                ( sprite.abscisseLaPlusAGauche() <= missile.abscisseLaPlusADroite() && missile.abscisseLaPlusADroite() <= sprite.abscisseLaPlusADroite() );
    }

    private static boolean estLeMissilePartiellementVerticalDansLeSprite(Sprite sprite, Missile missile) {
        return ( sprite.ordonneeLaPlusBasse() <= missile.ordonneeLaPlusBasse() && missile.ordonneeLaPlusBasse() <= sprite.ordonneeLaPlusHaute() ) ||
                ( sprite.ordonneeLaPlusBasse() <= missile.ordonneeLaPlusHaute() && missile.ordonneeLaPlusHaute() <= sprite.ordonneeLaPlusHaute() );
    }
    
    private static boolean estLeMissilePartiellementDansLeSprite(Sprite sprite, Missile missile) {
        return estLeMissilePartiellementHorizontalDansLeSprite(sprite, missile) 
                && estLeMissilePartiellementVerticalDansLeSprite(sprite, missile);
    }
    
    public static boolean detecterCollisionDefense(Missile missileDefense, Missile missileAttaque) {
        if (estLeMissilePartiellementHorizontalDansLeSprite(missileDefense, missileAttaque)) {
            if (estLeMissilePartiellementVerticalDansLeSprite(missileDefense, missileAttaque))
                return true;
            else if ( distanceEntreMissiles(missileDefense, missileAttaque) <= espaceCritique(missileAttaque) ) {
                return true;
            }
        }
        return false;
    }

    private static int espaceCritique(Missile missileAttaque) {
        return 2 * (missileAttaque.getVitesse() - missileAttaque.hauteur());
    }

    private static int distanceEntreMissiles(Missile missileDefense, Missile missileAttaque) {
        return missileDefense.ordonneeLaPlusBasse() - missileAttaque.ordonneeLaPlusHaute();
    }
    
}
