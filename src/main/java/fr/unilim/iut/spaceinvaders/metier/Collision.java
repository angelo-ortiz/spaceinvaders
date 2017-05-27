package fr.unilim.iut.spaceinvaders.metier;

import fr.unilim.iut.spaceinvaders.utils.MissileException;

public class Collision {

    public static boolean detecterCollision(Personnage personnage, Missile missile) {
        if (missile.longueur() > personnage.longueur()) {
            throw new MissileException("La longueur du missile est supérieure à celle du personnage");
        }
        return estLeMissilePartiellementDansLePersonnage(personnage, missile);
    }

    private static boolean estLeMissilePartiellementHorizontalDansLePersonnage(Personnage personnage, Missile missile) {
        return ( personnage.abscisseLaPlusAGauche() <= missile.abscisseLaPlusAGauche() && missile.abscisseLaPlusAGauche() <= personnage.abscisseLaPlusADroite() ) ||
                ( personnage.abscisseLaPlusAGauche() <= missile.abscisseLaPlusADroite() && missile.abscisseLaPlusADroite() <= personnage.abscisseLaPlusADroite() );
    }

    private static boolean estLeMissilePartiellementVerticalDansLePersonnage(Personnage personnage, Missile missile) {
        return personnage.ordonneeLaPlusBasse() <= missile.ordonneeLaPlusBasse() && missile.ordonneeLaPlusBasse() <= personnage.ordonneeLaPlusHaute();
    }
    
    private static boolean estLeMissilePartiellementDansLePersonnage(Personnage personnage, Missile missile) {
        return estLeMissilePartiellementHorizontalDansLePersonnage(personnage, missile) 
                && estLeMissilePartiellementVerticalDansLePersonnage(personnage, missile);
    }
    
}
