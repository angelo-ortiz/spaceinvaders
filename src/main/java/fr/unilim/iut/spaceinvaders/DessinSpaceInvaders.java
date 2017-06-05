package fr.unilim.iut.spaceinvaders;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import fr.unilim.iut.spaceinvaders.metier.Envahisseur;
import fr.unilim.iut.spaceinvaders.metier.Missile;
import fr.unilim.iut.spaceinvaders.metier.Position;
import fr.unilim.iut.spaceinvaders.metier.SpaceInvaders;
import fr.unilim.iut.spaceinvaders.metier.Vaisseau;
import fr.unilim.iut.spaceinvaders.moteurjeu.DessinJeu;

public class DessinSpaceInvaders implements DessinJeu {

    private static final String NOM_POLICE = "TimesRoman";
    private SpaceInvaders si;
    
    public DessinSpaceInvaders(SpaceInvaders si) {
        this.si = si;
    }
    
    @Override
    public void dessiner(BufferedImage image) {
        SpaceInvaders spaceInvaders = si;
        Vaisseau vaisseau = si.recupererVaisseau();
        List<Missile> missilesVaisseau = si.recupererMissilesVaisseau();
        List<Envahisseur> envahisseurs = si.recupererEnvahisseurs();
        List<Missile> missilesEnvahisseurs = si.recupererMissilesEnvahisseurs();
        boolean jeuFini = si.etreFini();
        
        if (!jeuFini) {
            dessinerVaisseau(image, vaisseau);
            dessinerMissiles(image, missilesVaisseau);
            dessinerEnvahisseurs(image, envahisseurs);
            dessinerMissiles(image, missilesEnvahisseurs);
        } else {
            dessinerMessageFinPartie(image, envahisseurs.isEmpty());
        }
        dessinerScore(image, spaceInvaders.getScore(), jeuFini);
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
    
    private void dessinerMessageFinPartie(BufferedImage image, boolean victoire) {
        Graphics crayon = image.getGraphics();
        Position positionStandard = this.getPositionMessage(crayon, "Game over");
        crayon.setColor(Color.red);
        crayon.drawString("Game over", positionStandard.abscisse(), positionStandard.ordonnee());
        String message = "Vous avez perdu :/";
        if (victoire) {
            message = "Vous avez gagné :D";
            crayon.setColor(Color.green);
        }
        Position positionResultat = this.getPositionMessage(crayon, message);
        crayon.drawString(message, positionResultat.abscisse(), positionResultat.ordonnee() + 150);
    }
    
    private Position getPositionMessage(Graphics crayon, String message) {
        crayon.setFont(new Font(NOM_POLICE, Font.PLAIN, 80));
        int longueurChaine = crayon.getFontMetrics().stringWidth(message);
        return new Position(Constante.ESPACEJEU_LONGUEUR/2 - longueurChaine /2, Constante.ESPACEJEU_HAUTEUR/2);
    }
    
    private void dessinerScore(BufferedImage image, int score, boolean jeuFini) {
        Graphics crayon = image.getGraphics();
        crayon.setColor(Color.black);
        if (jeuFini) {
            crayon.setFont(new Font(NOM_POLICE, Font.PLAIN, 80));
            Position position = this.getPositionMessage(crayon, "Score : 400");
            crayon.drawString("Score : " + score, position.abscisse(), Constante.SCORE_ORDONNEE);
        } else {
            crayon.setFont(new Font(NOM_POLICE, Font.PLAIN, 30));
            crayon.drawString("Score : " + score, Constante.SCORE_ABSCISSE, Constante.SCORE_ORDONNEE);
        }
    }
    
}
