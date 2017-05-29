package fr.unilim.iut.spaceinvaders.metier;

import java.util.ArrayList;
import java.util.List;

import fr.unilim.iut.spaceinvaders.Constante;
import fr.unilim.iut.spaceinvaders.Direction;
import fr.unilim.iut.spaceinvaders.moteurjeu.Commande;
import fr.unilim.iut.spaceinvaders.moteurjeu.Jeu;
import fr.unilim.iut.spaceinvaders.utils.DebordementEspaceJeuException;
import fr.unilim.iut.spaceinvaders.utils.HorsEspaceJeuException;
import fr.unilim.iut.spaceinvaders.utils.MissileException;

public class SpaceInvaders implements Jeu {

    private static final int PROPORTION_LONGUEUR_ENVAHISSEUR_SUR_TROU = 3;
    int longueur;
    int hauteur;
    Vaisseau vaisseau;
    List<Missile> missiles;
    List<Envahisseur> envahisseurs;
    int score;

    public SpaceInvaders(int longueur, int hauteur) {
        this.longueur = longueur;
        this.hauteur = hauteur;
        this.missiles = new ArrayList<Missile>();
        this.envahisseurs = new ArrayList<Envahisseur>();
        this.score = 0;
    }
    
    public void initialiserJeu() {
        this.positionnerUnNouveauVaisseau(new Dimension(Constante.VAISSEAU_LONGUEUR, Constante.VAISSEAU_HAUTEUR), 
                new Position(Constante.ESPACEJEU_LONGUEUR/2,550), Constante.VAISSEAU_VITESSE);
        /*this.positionnerUnNouveauEnvahisseur(new Dimension(Constante.ENVAHISSEUR_LONGUEUR, Constante.ENVAHISSEUR_HAUTEUR), 
                new Position(Constante.ESPACEJEU_LONGUEUR/2,30), Constante.ENVAHISSEUR_VITESSE);*/
        this.positionnerUneLigneDeEnvahisseurs(new Dimension(Constante.ENVAHISSEUR_LONGUEUR, Constante.ENVAHISSEUR_HAUTEUR), 
                30, Constante.ENVAHISSEUR_VITESSE,10);
    }
    
    public Vaisseau recupererVaisseau() {
        return this.vaisseau;
    }
    
    public List<Missile> recupererMissiles() {
        return this.missiles;
    }
    
    public List<Envahisseur> recupererEnvahisseurs() {
        return this.envahisseurs;
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
        envahisseurs.add( new Envahisseur(dimension, position, vitesse) );
        
    }
    
    public void positionnerUneLigneDeEnvahisseurs(Dimension dimension, int y, int vitesse, int quantite) {
        if (!estDansEspaceJeu(0, y - dimension.hauteur() + 1))
            throw new DebordementEspaceJeuException("Le personnage déborde de l'espace jeu vers le bas à cause de sa hauteur");
        
        if (quantite <= 0) 
            throw new IllegalArgumentException("La quantité d'envahisseurs dans la ligne doit être positive");
        
        int trou = calculerLespaceEntreDeuxEnvahisseurs(dimension);
        int espaceBordures = calculerLespaceEntreLensembleDenvahisseursEtLesBordures(dimension, quantite, trou);
        if (espaceBordures <= 0)
            throw new DebordementEspaceJeuException("La ligne d'envahisseurs déborderait de l'espace jeu vers les côtés à cause du nombre d'eux");
        
        int x = espaceBordures - dimension.longueur() - trou;
        for (int i = 0; i < quantite; i++) {
            x += dimension.longueur() + trou;
            this.positionnerUnNouveauEnvahisseur(dimension, new Position(x,y), vitesse);
        }
        
    }

    private int calculerLespaceEntreDeuxEnvahisseurs(Dimension dimensionEnvahisseur) {
        return (dimensionEnvahisseur.longueur() + PROPORTION_LONGUEUR_ENVAHISSEUR_SUR_TROU - 1) / PROPORTION_LONGUEUR_ENVAHISSEUR_SUR_TROU;
    }
    
    private int calculerLespaceEntreLensembleDenvahisseursEtLesBordures(Dimension dimensionEnvahisseur, int quantite, int trou) {
        return (this.longueur - dimensionEnvahisseur.longueur() * quantite - 
                (quantite - 1) * trou) / 2;
    }
    
    public Envahisseur envahisseurLePlusAGauche() {
        return this.envahisseurs.get(0);
    }
    
    public Envahisseur envahisseurLePlusADroite() {
        return this.envahisseurs.get(this.envahisseurs.size() - 1);
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
    
    public void deplacerEnvahisseurs() {
        if (Direction.DROITE.equals(envahisseurLePlusADroite().getSensDeplacement()) && envahisseurLePlusADroite().abscisseLaPlusADroite() >= longueur - 1)
            changerSensDeplacement(Direction.GAUCHE);
        if (Direction.GAUCHE.equals(envahisseurLePlusAGauche().getSensDeplacement()) && envahisseurLePlusAGauche().abscisseLaPlusAGauche() <= 0)
            changerSensDeplacement(Direction.DROITE);
        for (Envahisseur envahisseur : this.envahisseurs) {
            envahisseur.deplacerHorizontalementVers(envahisseur.getSensDeplacement());
        }
    }

    private void changerSensDeplacement(Direction direction) {
        for (Envahisseur envahisseur : this.envahisseurs) {
            envahisseur.setSensDeplacement(direction);
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
    
    private boolean aDesMissiles() {
       return !this.missiles.isEmpty();
    }
    
    private boolean aUnMissileQuiOccupeLaPosition(int x, int y) {
        
        if ( this.aDesMissiles() ) {
            for (Missile missile : this.missiles) {
                if (missile.occupeLaPosition(x, y))
                    return true;
            }
        }
        return false;
    }
    
    private boolean aDesEnvahisseurs() {
        return !this.envahisseurs.isEmpty();
    }
    
    private boolean aUnEnvahissseurQuiOccupeLaPosition(int x, int y) {
        if ( this.aDesEnvahisseurs() ) {
            for (Envahisseur envahisseur : this.envahisseurs) {
                if (envahisseur.occupeLaPosition(x, y))
                    return true;
            }
        }
        return false;
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
        detecterCollisions();
        
        if (commandeUser.gauche) {
            this.deplacerVaisseauVersLaGauche();
        }
        
        if (commandeUser.droite) {
            this.deplacerVaisseauVersLaDroite();
        }
        
        if (commandeUser.tir) {
            this.tirerUnMissile(new Dimension(Constante.MISSILE_LONGUEUR, Constante.MISSILE_HAUTEUR), Constante.MISSILE_VITESSE);
        }
        
        if (!this.envahisseurs.isEmpty())
            this.deplacerEnvahisseurs();
        
        this.deplacerMissiles(Direction.HAUT_ECRAN);
        
        
    }

    @Override
    public boolean etreFini() {
        return !this.aDesEnvahisseurs();
    }

    public void detecterCollisions() {
        boolean continuation;
        do {
            continuation = false;
            for (Envahisseur envahisseur : this.envahisseurs) {
                for (Missile missile : this.missiles) {
                    if (Collision.detecterCollision(envahisseur, missile)) {
                        this.envahisseurs.remove(envahisseur);
                        this.missiles.remove(missile);
                        actualiserScore();
                        continuation = true;
                        break;
                    }
                }
                if (continuation) 
                    break;
            }
        } while (continuation);
    }

    private void actualiserScore() {
        this.score += Constante.SCORE_ENVAHISSEUR_CLASSIQUE;
    }

    private boolean ilYaDesMissilesEnBasDecran() {
        for (Missile missile : this.missiles) {
            if (missile.ordonneeLaPlusHaute() >= this.hauteur / 2) {
                return true;
            }
        }
        return false;
    }
    
    public void tirerUnMissile(Dimension dimensionMissile, int vitesseMissile) {
        if ( (vaisseau.hauteur() + dimensionMissile.hauteur()) > this.hauteur ) {
            throw new MissileException("Pas assez de hauteur libre entre le vaisseau et le haut de l'espace jeu pour tirer le missile");
        }
        if (!ilYaDesMissilesEnBasDecran())
            this.missiles.add( this.vaisseau.tirerUnMissile(dimensionMissile, vitesseMissile, Direction.HAUT) );
    }

    public void deplacerMissiles(Direction direction) {
        for (Missile missile : this.missiles) {
            missile.deplacerVerticalementVers(direction);
        }
        supprrimerMissilesHorsEspaceDeJeu();
    }

    private void supprrimerMissilesHorsEspaceDeJeu() {
        boolean continuation;
        do {
            continuation = false;
            for (Missile missile : this.missiles) {
                if (missile.ordonneeLaPlusHaute() < 0) {
                    this.missiles.remove(missile);
                    continuation = true;
                    break;
                }
            }
        } while (continuation);
    }

    public int getScore() {
        return this.score;
    }

}
