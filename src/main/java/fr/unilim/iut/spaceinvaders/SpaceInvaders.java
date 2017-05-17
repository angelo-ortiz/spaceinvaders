package fr.unilim.iut.spaceinvaders;

import fr.unilim.iut.spaceinvaders.moteurjeu.Commande;
import fr.unilim.iut.spaceinvaders.moteurjeu.Jeu;
import fr.unilim.iut.spaceinvaders.utils.*;

public class SpaceInvaders implements Jeu {

    int longueur;
    int hauteur;
    Vaisseau vaisseau;

    public SpaceInvaders(int longueur, int hauteur) {
        this.longueur = longueur;
        this.hauteur = hauteur;
    }
    
    public void initialiserJeu() {
        this.positionnerUnNouveauVaisseau(new Dimension(Constante.VAISSEAU_LONGUEUR, Constante.VAISSEAU_HAUTEUR), 
                new Position(Constante.ESPACEJEU_LONGUEUR/2,Constante.ESPACEJEU_HAUTEUR/2));
    }
    
    public Vaisseau getVaisseau() {
        return this.vaisseau;
    }
    
    public void positionnerUnNouveauVaisseau(Dimension dimension, Position position) {
        int x = position.abscisse();
        int y = position.ordonnee();
        
        if (!estDansEspaceJeu(x, y))
            throw new HorsEspaceJeuException("La position du vaisseau est en dehors de l'espace jeu");

        int longueurVaisseau = dimension.longueur();
        int hauteurVaisseau = dimension.hauteur();
        
        if (!estDansEspaceJeu(x + longueurVaisseau - 1, y))
            throw new DebordementEspaceJeuException("Le vaisseau déborde de l'espace jeu vers la droite à cause de sa longueur");
        if (!estDansEspaceJeu(x, y - hauteurVaisseau + 1))
            throw new DebordementEspaceJeuException("Le vaisseau déborde de l'espace jeu vers le bas à cause de sa hauteur");

        vaisseau = new Vaisseau(longueurVaisseau, hauteurVaisseau);
        vaisseau.positionner(x, y);

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
            vaisseau.seDeplacerVerLaDroite();
        }
    }

    public void deplacerVaisseauVersLaGauche() {
        if (vaisseau.abscisseLaPlusAGauche() > 0) {
            vaisseau.seDeplacerVerLaGauche();
        }
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

    private char recupererMarqueDeLaPosition(int x, int y) {
        char marque;
        if (this.aUnVaisseauQuiOccupeLaPosition(x, y))
            marque = Constante.MARQUE_VAISSEAU;
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
    }

    @Override
    public boolean etreFini() {
        return false;
    }

}
