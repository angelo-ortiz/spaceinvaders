package fr.unilim.iut.spaceinvaders.metier;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.unilim.iut.spaceinvaders.Direction;
import fr.unilim.iut.spaceinvaders.metier.Dimension;
import fr.unilim.iut.spaceinvaders.metier.Position;
import fr.unilim.iut.spaceinvaders.metier.SpaceInvaders;
import fr.unilim.iut.spaceinvaders.utils.*;

public class SpaceInvadersTest {

    private SpaceInvaders spaceinvaders;

    @Before
    public void initialisation() {
        spaceinvaders = new SpaceInvaders(15, 10);
    }
    
    @After
    public void finalisation() {
        spaceinvaders = null;
    }

    @Test
    public void test_AuDebut_JeuSpaceInvaderEstVide() {
        assertEquals("" + 
                "...............\n" + 
                "...............\n" +
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }

    @Test
    public void test_unNouveauVaisseauEstCorrectementPositionneDansEspaceJeu() {
        spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1,1),new Position(7,9), 1);
        assertEquals("" + 
                "...............\n" + 
                "...............\n" +
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                ".......V.......\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }

    @Test
    public void test_UnNouveauVaisseauPositionneHorsEspaceJeu_DoitLeverUneException() {

        try {
            spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1,1),new Position(15,9), 1);
            fail("Position trop à droite : devrait déclencher une exception HorsEspaceJeuException");
        } catch (final HorsEspaceJeuException e) {
        }


        try {
            spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1,1),new Position(-1,9), 1);
            fail("Position trop à gauche : devrait déclencher une exception HorsEspaceJeuException");
        } catch (final HorsEspaceJeuException e) {
        }


        try {
            spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1,1),new Position(14,10), 1);
            fail("Position trop en bas : devrait déclencher une exception HorsEspaceJeuException");
        } catch (final HorsEspaceJeuException e) {
        }


        try {
            spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1,1),new Position(14,-1), 1);
            fail("Position trop à haut : devrait déclencher une exception HorsEspaceJeuException");
        } catch (final HorsEspaceJeuException e) {
        }

    }

    @Test
    public void test_unNouveauVaisseauAvecDimensionEstCorrectementPositionneDansEspaceJeu() {
        spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(7,9), 1);
        assertEquals("" + 
                "...............\n" + 
                "...............\n" +
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                ".......VVV.....\n" + 
                ".......VVV.....\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }

    @Test
    public void test_UnNouveauVaisseauPositionneDansEspaceJeuMaisAvecDimensionTropGrande_DoitLeverUneExceptionDeDebordement() {

        try {
            spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(9,2),new Position(7,9), 1);
            fail("Dépassement du vaisseau à droite en raison de sa longueur trop importante : devrait déclencher une exception DebordementEspaceJeuException");
        } catch (final DebordementEspaceJeuException e) {
        }


        try {
            spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,4),new Position(7,1), 1);
            fail("Dépassement du vaisseau vers le haut en raison de sa hauteur trop importante : devrait déclencher une exception DebordementEspaceJeuException");
        } catch (final DebordementEspaceJeuException e) {
        }

    }

    @Test
    public void test_VaisseauAvance_DeplacerVaisseauVersLaDroite() {

        spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(7,9), 3);
        spaceinvaders.deplacerVaisseauVersLaDroite();
        assertEquals("" + 
                "...............\n" + 
                "...............\n" +
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "..........VVV..\n" + 
                "..........VVV..\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }

    @Test
    public void test_VaisseauImmobile_DeplacerVaisseauVersLaDroite() {

        spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(12,9), 3);
        spaceinvaders.deplacerVaisseauVersLaDroite();
        assertEquals("" + 
                "...............\n" + 
                "...............\n" +
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "............VVV\n" + 
                "............VVV\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }

    @Test
    public void test_VaisseauAvancePartiellement_DeplacerVaisseauVersLaDroite() {

        spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(10,9),3);
        spaceinvaders.deplacerVaisseauVersLaDroite();
        assertEquals("" + 
                "...............\n" + 
                "...............\n" +
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "............VVV\n" + 
                "............VVV\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }

    @Test
    public void test_VaisseauAvance_DeplacerVaisseauVersLaGauche() {

        spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(7,9), 3);
        spaceinvaders.deplacerVaisseauVersLaGauche();

        assertEquals("" + 
                "...............\n" + 
                "...............\n" +
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "....VVV........\n" + 
                "....VVV........\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }

    @Test
    public void test_VaisseauImmobile_DeplacerVaisseauVersLaGauche() {

        spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(0,9), 3);
        spaceinvaders.deplacerVaisseauVersLaGauche();

        assertEquals("" + 
                "...............\n" + 
                "...............\n" +
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "VVV............\n" + 
                "VVV............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }

    @Test
    public void test_VaisseauAvancePartiellement_DeplacerVaisseauVersLaGauche() {

        spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(1,9), 3);
        spaceinvaders.deplacerVaisseauVersLaGauche();

        assertEquals("" + 
                "...............\n" + 
                "...............\n" +
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "VVV............\n" + 
                "VVV............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }

    @Test(expected = MissileException.class)
    public void test_PasAssezDePlacePourTirerUnMissile_UneExceptionEstLevee() throws Exception { 
        spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
        spaceinvaders.tirerUnMissileDepuisLeVaisseau(new Dimension(7,9), 1);
    }

    @Test
    public void test_MissileDisparaitPartiellement_QuandIlCommenceASortirDeEspaceJeuParLeHautEcran() {

        spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
        spaceinvaders.tirerUnMissileDepuisLeVaisseau(new Dimension(3,2), 1);

        for (int i = 1; i <=6 ; i++) {
            spaceinvaders.deplacerMissilesVaisseau(Direction.HAUT_ECRAN);
        }

        spaceinvaders.deplacerMissilesVaisseau(Direction.HAUT_ECRAN);

        assertEquals("" +
                ".......MMM.....\n" + 
                "...............\n" +
                "...............\n" + 
                "...............\n" +
                "...............\n" +
                "...............\n" + 
                "...............\n" +
                "...............\n" + 
                ".....VVVVVVV...\n" + 
                ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }

    @Test
    public void test_unNouveauEnvahisseurAvecDimensionEstCorrectementPositionneDansEspaceJeu() {
        spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(3,2),new Position(7,2), 1);
        assertEquals("" + 
                "...............\n" + 
                ".......EEE.....\n" +
                ".......EEE.....\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }

    @Test
    public void test_EnvahisseurAvance_DeplacerEnvahisseur() {

        spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(3,2),new Position(7,2), 3);
        
        spaceinvaders.deplacerEnvahisseurs();
        
        assertEquals("" + 
                "...............\n" + 
                "..........EEE..\n" +
                "..........EEE..\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }

    @Test
    public void test_EnvahisseurRecule_DeplacerEnvahisseur() {

        spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(3,2),new Position(12,2), 3);
        
        spaceinvaders.deplacerEnvahisseurs();
        
        assertEquals("" + 
                "...............\n" + 
                ".........EEE...\n" +
                ".........EEE...\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }

    @Test
    public void test_EnvahisseurAvancePartiellement_DeplacerEnvahisseur() {

        spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(3,2),new Position(10,2),3);
        
        spaceinvaders.deplacerEnvahisseurs();
        
        assertEquals("" + 
                "...............\n" + 
                "............EEE\n" +
                "............EEE\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }

    @Test
    public void test_MissilesBienTiresDepuisVaisseau_VaisseauLongueurImpaireMissilesLongueurImpaire() {

        spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);

        spaceinvaders.tirerUnMissileDepuisLeVaisseau(new Dimension(3,2), 2);
        Vaisseau vaisseau = spaceinvaders.recupererVaisseau();
        for (int i = 0; i < 2; i++) {
            spaceinvaders.deplacerMissilesVaisseau(Direction.HAUT_ECRAN);
            vaisseau.deplacerHorizontalementVers(Direction.GAUCHE);
        }
        spaceinvaders.tirerUnMissileDepuisLeVaisseau(new Dimension(3,2), 2);

        assertEquals("" + 
                "...............\n" + 
                "...............\n" +
                ".......MMM.....\n" + 
                ".......MMM.....\n" + 
                "...............\n" + 
                "...............\n" + 
                "...MMM.........\n" + 
                "...MMM.........\n" + 
                ".VVVVVVV.......\n" + 
                ".VVVVVVV.......\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }
    
    @Test
    public void test_MissileNonTireDepuisVaisseau_PresenceDeMissilesEnBasEcran() {

        spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);

        spaceinvaders.tirerUnMissileDepuisLeVaisseau(new Dimension(3,2), 2);
        Vaisseau vaisseau = spaceinvaders.recupererVaisseau();
        for (int i = 0; i < 1; i++) {
            spaceinvaders.deplacerMissilesVaisseau(Direction.HAUT_ECRAN);
            vaisseau.deplacerHorizontalementVers(Direction.GAUCHE);
        }
        spaceinvaders.tirerUnMissileDepuisLeVaisseau(new Dimension(3,2), 2);

        assertEquals("" + 
                "...............\n" + 
                "...............\n" +
                "...............\n" + 
                "...............\n" + 
                ".......MMM.....\n" + 
                ".......MMM.....\n" + 
                "...............\n" + 
                "...............\n" + 
                "...VVVVVVV.....\n" + 
                "...VVVVVVV.....\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }

    @Test
    public void test_MissilesAvancentAutomatiquement_ApresTirsDepuisLeVaisseau() {

        spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);

        spaceinvaders.tirerUnMissileDepuisLeVaisseau(new Dimension(3,2), 2);
        Vaisseau vaisseau = spaceinvaders.recupererVaisseau();
        for (int i = 0; i < 2; i++) {
            spaceinvaders.deplacerMissilesVaisseau(Direction.HAUT_ECRAN);
            vaisseau.deplacerHorizontalementVers(Direction.GAUCHE);
        }
        spaceinvaders.tirerUnMissileDepuisLeVaisseau(new Dimension(3,2), 2);
        spaceinvaders.deplacerMissilesVaisseau(Direction.HAUT_ECRAN);

        assertEquals("" + 
                ".......MMM.....\n" + 
                ".......MMM.....\n" +
                "...............\n" + 
                "...............\n" + 
                "...MMM.........\n" + 
                "...MMM.........\n" + 
                "...............\n" + 
                "...............\n" + 
                ".VVVVVVV.......\n" + 
                ".VVVVVVV.......\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }

    @Test
    public void test_LigneEnvahisseursAvecDimensionEstCorrectementPositionneeDansEspaceJeu() {
        spaceinvaders.positionnerUneLigneDeEnvahisseurs(new Dimension(3,2), 2, 1, 2);
        
        assertEquals("" + 
                "...............\n" + 
                "....EEE.EEE....\n" +
                "....EEE.EEE....\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }

    @Test(expected=DebordementEspaceJeuException.class)
    public void test_LigneEnvahisseursPositionneeDansEspaceJeuMaisAvecDimensionTropGrande_DoitLeverUneExceptionDeDebordement() {
        spaceinvaders.positionnerUneLigneDeEnvahisseurs(new Dimension(3,4), 1, 1, 2);

    }

    @Test(expected=IllegalArgumentException.class)
    public void test_LigneEnvahisseursPositionneeDansEspaceJeuMaisAvecNombreEnvahisseursNonPositif_DoitLeverUneExceptionDeDebordement() {
        spaceinvaders.positionnerUneLigneDeEnvahisseurs(new Dimension(3,2), 2, 1, -2);

    }

    @Test(expected=DebordementEspaceJeuException.class)
    public void test_LigneEnvahisseursPositionneeDansEspaceJeuMaisAvecEnvahisseursTresNombreux_DoitLeverUneExceptionDeDebordement() {
        spaceinvaders.positionnerUneLigneDeEnvahisseurs(new Dimension(3,2), 2, 1, 4);

    }
    
    @Test
    public void test_ScoreDe40PointsGraceDesctructionUnEnvahisseur() {
        spaceinvaders.positionnerUneLigneDeEnvahisseurs(new Dimension(3,2), 2, 1, 2);
        spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(7,9), 2);
        spaceinvaders.tirerUnMissileDepuisLeVaisseau(new Dimension(3,2), 2);
        for (int i = 0; i < 2; i++) {
            spaceinvaders.deplacerMissilesVaisseau(Direction.HAUT_ECRAN);
        }
        
        spaceinvaders.detecterCollisionsEnvahisseurs();
        
        assertEquals(40, spaceinvaders.getScore());
    }
    
    @Test
    public void test_MissilesBienTiresDepuisEnvahisseur_EnvahisseurLongueurImpaireMissilesLongueurImpaire() {

        spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(3,2),new Position(7,2), 3);
        spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);
        
        Envahisseur envahisseur = spaceinvaders.envahisseurLePlusAGauche();
        spaceinvaders.tirerUnMissileDepuisUnEnvahisseur(envahisseur, new Dimension(1,1), 2);
        for (int i = 0; i < 2; i++) {
            spaceinvaders.deplacerMissilesEnvahisseurs(Direction.BAS_ECRAN);
            spaceinvaders.deplacerEnvahisseurs();
        }
        spaceinvaders.tirerUnMissileDepuisUnEnvahisseur(envahisseur, new Dimension(1,1), 2);

        assertEquals("" + 
                "...............\n" + 
                "............EEE\n" +
                "............EEE\n" + 
                ".............M.\n" + 
                "...............\n" + 
                "...............\n" + 
                "...............\n" + 
                "........M......\n" + 
                ".....VVVVVVV...\n" + 
                ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }
    
    @Test
    public void test_MissileNonTireDepuisEnvahisseur_PresenceDeMissilesEnHautEcran() {

        spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(3,2),new Position(7,2), 3);
        spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);
        
        Envahisseur envahisseur = spaceinvaders.envahisseurLePlusAGauche();
        spaceinvaders.tirerUnMissileDepuisUnEnvahisseur(envahisseur, new Dimension(1,1), 2);
        for (int i = 0; i < 1; i++) {
            spaceinvaders.deplacerMissilesEnvahisseurs(Direction.BAS_ECRAN);
            spaceinvaders.deplacerEnvahisseurs();
        }
        spaceinvaders.tirerUnMissileDepuisUnEnvahisseur(envahisseur, new Dimension(1,1), 2);

        assertEquals("" + 
                "...............\n" + 
                "..........EEE..\n" +
                "..........EEE..\n" + 
                "...............\n" + 
                "...............\n" + 
                "........M......\n" + 
                "...............\n" + 
                "...............\n" + 
                ".....VVVVVVV...\n" + 
                ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }

    @Test
    public void test_MissilesAvancentAutomatiquement_ApresTirsDepuisLenvahisseur() {

        spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(3,2),new Position(7,2), 3);
        spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(1,9), 2);
        
        Envahisseur envahisseur = spaceinvaders.envahisseurLePlusAGauche();
        spaceinvaders.tirerUnMissileDepuisUnEnvahisseur(envahisseur, new Dimension(1,1), 2);
        for (int i = 0; i < 2; i++) {
            spaceinvaders.deplacerMissilesEnvahisseurs(Direction.BAS_ECRAN);
            spaceinvaders.deplacerEnvahisseurs();
        }
        spaceinvaders.tirerUnMissileDepuisUnEnvahisseur(envahisseur, new Dimension(1,1), 2);
        spaceinvaders.deplacerMissilesEnvahisseurs(Direction.BAS_ECRAN);

        assertEquals("" + 
                "...............\n" + 
                "............EEE\n" +
                "............EEE\n" + 
                "...............\n" + 
                "...............\n" + 
                ".............M.\n" + 
                "...............\n" + 
                "...............\n" + 
                ".VVVVVVV.......\n" + 
                ".VVVVVVVM......\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }
    
    @Test
    public void test_MissileDisparaitPartiellement_QuandIlCommenceASortirDeEspaceJeuParLeBasEcran() {

        spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(3,2),new Position(7,2), 3);
        spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(1,9), 2);
        
        Envahisseur envahisseur = spaceinvaders.envahisseurLePlusAGauche();
        spaceinvaders.tirerUnMissileDepuisUnEnvahisseur(envahisseur, new Dimension(1,2), 2);
        for (int i = 0; i < 2; i++) {
            spaceinvaders.deplacerMissilesEnvahisseurs(Direction.BAS_ECRAN);
            spaceinvaders.deplacerEnvahisseurs();
        }
        spaceinvaders.tirerUnMissileDepuisUnEnvahisseur(envahisseur, new Dimension(1,2), 2);
        spaceinvaders.deplacerMissilesEnvahisseurs(Direction.BAS_ECRAN);

        assertEquals("" + 
                "...............\n" + 
                "............EEE\n" +
                "............EEE\n" + 
                "...............\n" + 
                "...............\n" + 
                ".............M.\n" + 
                ".............M.\n" + 
                "...............\n" + 
                ".VVVVVVV.......\n" + 
                ".VVVVVVVM......\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
        
    }

}