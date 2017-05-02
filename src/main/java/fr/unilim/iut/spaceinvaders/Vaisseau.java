package fr.unilim.iut.spaceinvaders;

public class Vaisseau {

    int longueur;
    int hauteur;
    int x;
    int y;

    public Vaisseau(int longueur, int hauteur) {
        this(longueur, hauteur, 0, 0);
    }
    
    public Vaisseau(int longueur, int hauteur, int x, int y) {
        this.longueur = longueur;
        this.hauteur = hauteur;
        this.x = x;
        this.y = y;
    }

    public int abscisseLaPlusAGauche() {
        return this.x;
    }
    
    public int abscisseLaPlusADroite() {
        return this.x + this.longueur - 1;
    }
    
    private int ordonneeLaPlusHaute() {
        return this.y;
    }

    private int ordonneeLaPlusBasse() {
        return ordonneeLaPlusHaute()-this.hauteur+1;
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

    public void seDeplacerVerLaDroite() {
        this.x ++;
    }

    public void seDeplacerVerLaGauche() {
        this.x --;
    }

    public void positionner(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
}
