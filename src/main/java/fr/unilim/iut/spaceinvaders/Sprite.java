package fr.unilim.iut.spaceinvaders;

public abstract class Sprite {

    protected Dimension dimension;
    protected Position origine;
    protected int vitesse = 1;

    public Sprite(Dimension dimension, Position origine, int vitesse) {
        super();
        this.dimension = dimension;
        this.origine = origine;
        this.vitesse = vitesse;
    }

    public Sprite() {
        super();
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