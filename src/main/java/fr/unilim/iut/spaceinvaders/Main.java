package fr.unilim.iut.spaceinvaders;

import fr.unilim.iut.spaceinvaders.moteurjeu.MoteurGraphique;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // creation du jeu particulier et de son afficheur
        SpaceInvaders jeu = new SpaceInvaders(Constante.ESPACEJEU_LONGUEUR, Constante.ESPACEJEU_HAUTEUR);
        jeu.initialiserJeu();
        DessinSpaceInvaders afficheur = new DessinSpaceInvaders(jeu);

        // classe qui lance le moteur de jeu generique
        MoteurGraphique moteur = new MoteurGraphique(jeu, afficheur);
        moteur.lancerJeu(Constante.ESPACEJEU_LONGUEUR,Constante.ESPACEJEU_HAUTEUR);
    }

}
