package fr.unilim.iut.spaceinvaders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import fr.unilim.iut.spaceinvaders.metier.Envahisseur;
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
        List<Missile> missiles = si.recupererMissiles();
        List<Envahisseur> envahisseurs = si.recupererEnvahisseurs();
        
        dessinerVaisseau(image, vaisseau);
        if (missiles != null)
            dessinerMissiles(image, missiles);
        if (envahisseurs != null)
            dessinerEnvahisseurs(image, envahisseurs);
    }

    private void dessinerVaisseau(BufferedImage image, Vaisseau vaisseau) {
        Graphics2D crayon = (Graphics2D) image.getGraphics();
        crayon.setColor(Color.gray);
        crayon.fillRect(vaisseau.abscisseLaPlusAGauche(), vaisseau.ordonneeLaPlusBasse(), 
                vaisseau.longueur(), vaisseau.hauteur());
    }
    
    private void dessinerMissiles(BufferedImage image, List<Missile> missiles) {
        Graphics2D crayon = (Graphics2D) image.getGraphics();
        crayon.setColor(Color.blue);
        for (Missile missile : missiles) {
            crayon.fillRect(missile.abscisseLaPlusAGauche(), missile.ordonneeLaPlusBasse(), missile.longueur(),
                    missile.hauteur());
        }
    }

    private void dessinerEnvahisseurs(BufferedImage image, List<Envahisseur> envahisseurs) {
        Graphics2D crayon = (Graphics2D) image.getGraphics();
        crayon.setColor(Color.black);
        for (Envahisseur envahisseur : envahisseurs) {
            crayon.fillRect(envahisseur.abscisseLaPlusAGauche(), envahisseur.ordonneeLaPlusBasse(),
                    envahisseur.longueur(), envahisseur.hauteur());
        }
    }
    
}
