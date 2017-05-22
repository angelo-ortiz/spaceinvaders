package fr.unilim.iut.spaceinvaders.metier;

import fr.unilim.iut.spaceinvaders.Direction;
import fr.unilim.iut.spaceinvaders.utils.MissileException;

public abstract class Personnage extends Sprite {

    public Personnage(Dimension dimension, Position origine, int vitesse) {
        super(dimension, origine, vitesse);
    }

    public Missile tirerUnMissile(Dimension dimensionMissile, int vitesseMissile, Direction direction) {
        if (dimensionMissile.longueur() > this.dimension.longueur()) {
            throw new MissileException("La longueur du missile est supérieure à celle du personnage");
        }
        Position positionOrigineMissile = calculerLaPositionDeTirDuMissile(dimensionMissile, direction);
        return new Missile(dimensionMissile, positionOrigineMissile, vitesseMissile);
    }

    private Position calculerLaPositionDeTirDuMissile(Dimension dimensionMissile, Direction direction) {
        int abscisseMilieuPersonnage = this.abscisseLaPlusAGauche() + (this.longueur() / 2);
        int abscisseOriginePersonnage = abscisseMilieuPersonnage - (dimensionMissile.longueur() / 2);
    
        int ordonneeeOrigineMissile = this.ordonneeLaPlusBasse() - 1;
        if (Direction.BAS_ECRAN.equals(direction))
            ordonneeeOrigineMissile= this.ordonneeLaPlusHaute() + 1;
        /*else
            ordonneeeOrigineMissile= this.ordonneeLaPlusHaute() + 1;
            ordonneeeOrigineMissile= this.ordonneeLaPlusBasse() - 1;*/
        return new Position(abscisseOriginePersonnage, ordonneeeOrigineMissile);
    }

}