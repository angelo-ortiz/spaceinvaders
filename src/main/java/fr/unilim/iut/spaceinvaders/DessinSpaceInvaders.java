package fr.unilim.iut.spaceinvaders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import fr.unilim.iut.spaceinvaders.metier.Missile;
import fr.unilim.iut.spaceinvaders.metier.SpaceInvaders;
import fr.unilim.iut.spaceinvaders.metier.Vaisseau;
import fr.unilim.iut.spaceinvaders.moteurjeu.DessinJeu;

public class DessinSpaceInvaders implements DessinJeu {

    private SpaceInvaders si;
    
    public DessinSpaceInvaders(SpaceInvaders si) {
        this.si = si;
    }
    
    @Override
    public void dessiner(BufferedImage image) {
        SpaceInvaders spaceInvaders = (SpaceInvaders) si;
        Vaisseau vaisseau = si.recupererVaisseau();
        Missile missile = si.recupererMissile();
        
        dessinerVaisseau(image, vaisseau);
        if (missile != null)
            dessinerMissile(image, missile);
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

    private void dessinerVaisseau(BufferedImage image, Vaisseau vaisseau) {
        Graphics2D crayon = (Graphics2D) image.getGraphics();
        crayon.setColor(Color.gray);
        crayon.fillRect(vaisseau.abscisseLaPlusAGauche(), vaisseau.ordonneeLaPlusBasse(), 
                vaisseau.longueur(), vaisseau.hauteur());
    }
    
    private void dessinerMissile(BufferedImage image, Missile missile) {
        Graphics2D crayon = (Graphics2D) image.getGraphics();
        crayon.setColor(Color.blue);
        crayon.fillRect(missile.abscisseLaPlusAGauche(), missile.ordonneeLaPlusBasse(), 
                missile.longueur(), missile.hauteur());
    }

}
