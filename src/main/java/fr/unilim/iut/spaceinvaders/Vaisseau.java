package fr.unilim.iut.spaceinvaders;

public class Vaisseau {

    private Dimension dimension;
    private Position origine;
    private int vitesse = 1;

    public Vaisseau(int longueur, int hauteur) {
        this(longueur, hauteur, 0, 0);
    }
    
    public Vaisseau(int longueur, int hauteur, int x, int y) {
        this(new Dimension(longueur, hauteur), new Position(x, y), 1);
     }
    
    public Vaisseau(Dimension dimension, Position positionOrigine, int vitesse) {
        this.dimension = dimension;
        this.origine = positionOrigine;
        this.vitesse = vitesse;
    }

    public boolean occupeLaPosition(int x, int y) {
        return estAbscisseCouverte(x) && estordonneeCouverte(y);
    }
    
    private boolean estordonneeCouverte(int y) {
        return (ordonneeLaPlusBasse()<=y) && (y<=ordonneeLaPlusHaute());
    }
    
    private boolean estAbscisseCouverte(int x) {
        return (abscisseLaPlusAGauche() <= x) && (x <= abscisseLaPlusADroite());
    }
    
    public int abscisseLaPlusAGauche() {
        return this.origine.abscisse();
    }
    
    public int abscisseLaPlusADroite() {
        return this.origine.abscisse() + this.dimension.longueur() - 1;
    }
    
    public int ordonneeLaPlusHaute() {
        return this.origine.ordonnee();
    }

    public int ordonneeLaPlusBasse() {
        return ordonneeLaPlusHaute() - this.dimension.hauteur() + 1;
    }
    
    public void seDeplacerVerLaDroite() {
        this.origine.changerAbscisse(this.origine.abscisse() + this.vitesse);
        }

    public void seDeplacerVerLaGauche() {
        this.origine.changerAbscisse(this.origine.abscisse() - this.vitesse);
    }

    public void positionner(int x, int y) {
        this.origine.changerAbscisse(x);
        this.origine.changerOrdonnee(y);
    }
    
    public int longueur() {
        return this.dimension.longueur();
    }
    
    public int hauteur() {
        return this.dimension.hauteur();
    }
    
}
