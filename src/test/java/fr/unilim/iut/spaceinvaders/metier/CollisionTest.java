package fr.unilim.iut.spaceinvaders.metier;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CollisionTest {
    
    @Test
    public void test_PersonnageCollisionneAvecMissile_DetecterCollision() {

       Personnage personnage = new Personnage(new Dimension(7,2),new Position(5,2),3);
       Missile missile = new Missile(new Dimension(3,2),new Position(10,3),3);
       
       boolean ilYaCollision = Collision.detecterCollision(personnage, missile);
       
       assertEquals(ilYaCollision, true);
       
    }
    
    @Test
    public void test_PersonnageLoinDuMissile_DetecterCollision() {

       Personnage personnage = new Personnage(new Dimension(7,2),new Position(5,2),3);
       Missile missile = new Missile(new Dimension(3,2),new Position(10,9),3);
       
       boolean ilYaCollision = Collision.detecterCollision(personnage, missile);
       
       assertEquals(ilYaCollision, false);
       
    }
    
    @Test
    public void test_PersonnageACoteDuMissileCollisionPosterieureNonTraitee_DetecterCollision() {

       Personnage personnage = new Personnage(new Dimension(7,2),new Position(5,2),3);
       Missile missile = new Missile(new Dimension(3,2),new Position(10,4),3);
       
       boolean ilYaCollision = Collision.detecterCollision(personnage, missile);
       
       assertEquals(ilYaCollision, false);
       
    }

}
