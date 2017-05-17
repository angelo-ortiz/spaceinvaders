package fr.unilim.iut.spaceinvaders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import fr.unilim.iut.spaceinvaders.moteurjeu.DessinJeu;

public class DessinSpaceInvaders implements DessinJeu {

    private SpaceInvaders si;
    
    public DessinSpaceInvaders(SpaceInvaders si) {
        this.si = si;
    }
    @Override
    public void dessiner(BufferedImage image) {
        SpaceInvaders spaceInvaders = (SpaceInvaders) si;
        Vaisseau vaisseau = si.getVaisseau();
        
        Graphics2D crayon = (Graphics2D) image.getGraphics();
        crayon.setColor(Color.gray);
        crayon.fillRect(vaisseau.abscisseLaPlusAGauche(), vaisseau.ordonneeLaPlusHaute(), 
                vaisseau.longueur(), vaisseau.hauteur());
        /*switch (s) {
        case "PJ":
            crayon.setColor(Color.blue);
            crayon.fillOval(x * TAILLE_CASE, y * TAILLE_CASE, TAILLE_CASE,
                    TAILLE_CASE);
            break;
        case "MUR":
            crayon.setColor(Color.gray);
            crayon.fillRect(x * TAILLE_CASE, y * TAILLE_CASE, TAILLE_CASE,
                    TAILLE_CASE);
            break;
        default:
            throw new AssertionError("objet inexistant");
        }*/
    }

}
