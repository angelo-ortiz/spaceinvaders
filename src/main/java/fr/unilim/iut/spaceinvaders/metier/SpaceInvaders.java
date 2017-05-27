package fr.unilim.iut.spaceinvaders.metier;

import fr.unilim.iut.spaceinvaders.Constante;
import fr.unilim.iut.spaceinvaders.Direction;
import fr.unilim.iut.spaceinvaders.moteurjeu.Commande;
import fr.unilim.iut.spaceinvaders.moteurjeu.Jeu;
import fr.unilim.iut.spaceinvaders.utils.*;

public class SpaceInvaders implements Jeu {

    int longueur;
    int hauteur;
    Vaisseau vaisseau;
    Missile missile;
    Envahisseur envahisseur;

    public SpaceInvaders(int longueur, int hauteur) {
        this.longueur = longueur;
        this.hauteur = hauteur;
    }
    
    public void initialiserJeu() {
        this.positionnerUnNouveauVaisseau(new Dimension(Constante.VAISSEAU_LONGUEUR, Constante.VAISSEAU_HAUTEUR), 
                new Position(Constante.ESPACEJEU_LONGUEUR/2,550), Constante.VAISSEAU_VITESSE);
        this.positionnerUnNouveauEnvahisseur(new Dimension(Constante.ENVAHISSEUR_LONGUEUR, Constante.ENVAHISSEUR_HAUTEUR), 
                new Position(Constante.ESPACEJEU_LONGUEUR/2,30), Constante.ENVAHISSEUR_VITESSE);
    }
    
    public Vaisseau recupererVaisseau() {
        return this.vaisseau;
    }
    
    public Missile recupererMissile() {
        return this.missile;
    }
    
    public Envahisseur recupererEnvahisseur() {
        return this.envahisseur;
    }
    
    private void validerPositionnementPersonnage(Dimension dimension, Position position) {
        int x = position.abscisse();
        int y = position.ordonnee();
        
        if (!estDansEspaceJeu(x, y))
            throw new HorsEspaceJeuException("La position du personnage est en dehors de l'espace jeu");

        int longueurPersonnage = dimension.longueur();
        int hauteurPersonnage = dimension.hauteur();
        
        if (!estDansEspaceJeu(x + longueurPersonnage - 1, y))
            throw new DebordementEspaceJeuException("Le personnage déborde de l'espace jeu vers la droite à cause de sa longueur");
        if (!estDansEspaceJeu(x, y - hauteurPersonnage + 1))
            throw new DebordementEspaceJeuException("Le personnage déborde de l'espace jeu vers le bas à cause de sa hauteur");
    }
    
    
    public void positionnerUnNouveauVaisseau(Dimension dimension, Position position, int vitesse) {
        this.validerPositionnementPersonnage(dimension, position);

        vaisseau = new Vaisseau(dimension, position, vitesse);

    }
    
    public void positionnerUnNouveauEnvahisseur(Dimension dimension, Position position, int vitesse) {
        this.validerPositionnementPersonnage(dimension, position);
        
        envahisseur = new Envahisseur(dimension, position, vitesse);
        
    }

    public String recupererEspaceJeuDansChaineASCII() {
        StringBuilder espaceDeJeu = new StringBuilder();
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < longueur; x++) {
                espaceDeJeu.append(recupererMarqueDeLaPosition(x, y));
            }
            espaceDeJeu.append(Constante.MARQUE_FIN_LIGNE);
        }
        return espaceDeJeu.toString();
    }

    public void deplacerVaisseauVersLaDroite() {
        if (vaisseau.abscisseLaPlusADroite() < longueur - 1) {
            vaisseau.deplacerHorizontalementVers(Direction.DROITE);
            if (!estDansEspaceJeu(vaisseau.abscisseLaPlusADroite(), vaisseau.ordonneeLaPlusHaute())) {
                vaisseau.positionner(longueur - vaisseau.longueur(), vaisseau.ordonneeLaPlusHaute());
            }
        }
    }

    public void deplacerVaisseauVersLaGauche() {
        if (0 < vaisseau.abscisseLaPlusAGauche()) {
            vaisseau.deplacerHorizontalementVers(Direction.GAUCHE);
            if (!estDansEspaceJeu(vaisseau.abscisseLaPlusAGauche(), vaisseau.ordonneeLaPlusHaute())) {
                vaisseau.positionner(0, vaisseau.ordonneeLaPlusHaute());
            }
        }
    }
    
    public void deplacerEnvahisseur() {
        if (Direction.DROITE.equals(envahisseur.getSensDeplacement()) && envahisseur.abscisseLaPlusADroite() >= longueur - 1)
            envahisseur.setSensDeplacement(Direction.GAUCHE);
        if (Direction.GAUCHE.equals(envahisseur.getSensDeplacement()) && envahisseur.abscisseLaPlusAGauche() <= 0)
            envahisseur.setSensDeplacement(Direction.DROITE);
        envahisseur.deplacerHorizontalementVers(envahisseur.getSensDeplacement());
    }

    private boolean estDansEspaceJeu(int x, int y) {
        return ((x >= 0) && (x < longueur)) && ((y >= 0) && (y < hauteur));
    }

    private boolean aUnVaisseau() {
        return vaisseau != null;
    }

    private boolean aUnVaisseauQuiOccupeLaPosition(int x, int y) {
        return this.aUnVaisseau() && vaisseau.occupeLaPosition(x, y);
    }
    
    private boolean aUnMissile() {
       return missile != null;
    }
    
    private boolean aUnMissileQuiOccupeLaPosition(int x, int y) {
        return this.aUnMissile() && missile.occupeLaPosition(x, y);
    }
    
    private boolean aUnEnvahisseur() {
        return envahisseur != null;
    }
    
    private boolean aUnEnvahissseurQuiOccupeLaPosition(int x, int y) {
        return this.aUnEnvahisseur() && envahisseur.occupeLaPosition(x, y);
    }

    private char recupererMarqueDeLaPosition(int x, int y) {
        char marque;
        if (this.aUnVaisseauQuiOccupeLaPosition(x, y))
            marque = Constante.MARQUE_VAISSEAU;
        else if (this.aUnEnvahissseurQuiOccupeLaPosition(x, y))
            marque = Constante.MARQUE_ENVAHISSEUR;
        else if (this.aUnMissileQuiOccupeLaPosition(x, y))
            marque = Constante.MARQUE_MISSILE;
        else
            marque = Constante.MARQUE_VIDE;
        return marque;
    }

    @Override
    public void evoluer(Commande commandeUser) {
        if (commandeUser.gauche) {
            this.deplacerVaisseauVersLaGauche();
        }
        
        if (commandeUser.droite) {
            this.deplacerVaisseauVersLaDroite();
        }
        
        if (commandeUser.tir && !this.aUnMissile()) {
            this.tirerUnMissile(new Dimension(Constante.MISSILE_LONGUEUR, Constante.MISSILE_HAUTEUR), Constante.MISSILE_VITESSE);
        }
        
        if (this.aUnMissile())
            this.deplacerMissile(Direction.HAUT_ECRAN);
        
        this.deplacerEnvahisseur();
    }

    @Override
    public boolean etreFini() {
        if (this.aUnMissile() && this.aUnEnvahisseur() && Collision.detecterCollision(envahisseur, missile))
            return true;
        return false;
    }

    public void tirerUnMissile(Dimension dimensionMissile, int vitesseMissile) {
        if ( (vaisseau.hauteur() + dimensionMissile.hauteur()) > this.hauteur ) {
            throw new MissileException("Pas assez de hauteur libre entre le vaisseau et le haut de l'espace jeu pour tirer le missile");
        }
        this.missile = this.vaisseau.tirerUnMissile(dimensionMissile, vitesseMissile, Direction.HAUT);
    }

    public void deplacerMissile(Direction direction) {
        this.missile.deplacerVerticalementVers(direction);
        if (this.missile.ordonneeLaPlusHaute() < 0)
            this.missile = null;
    }

}
