package fr.unilim.iut.spaceinvaders;

public class Vaisseau {

    Dimension dimension;
    Position origine;

    public Vaisseau(int longueur, int hauteur) {
        this(longueur, hauteur, 0, 0);
    }
    
    public Vaisseau(int longueur, int hauteur, int x, int y) {
        this(new Dimension(longueur, hauteur), new Position(x, y));
     }
    
    public Vaisseau(Dimension dimension, Position positionOrigine) {
        this.dimension = dimension;
        this.origine = positionOrigine;
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
    
    private int ordonneeLaPlusHaute() {
        return this.origine.ordonnee();
    }

    private int ordonneeLaPlusBasse() {
        return ordonneeLaPlusHaute() - this.dimension.hauteur() + 1;
    }
    
    public void seDeplacerVerLaDroite() {
        this.origine.changerAbscisse(this.origine.abscisse() + 1);
        }

    public void seDeplacerVerLaGauche() {
        this.origine.changerAbscisse(this.origine.abscisse() - 1);
    }

    public void positionner(int x, int y) {
        this.origine.changerAbscisse(x);
        this.origine.changerOrdonnee(y);
    }
    
}
