package BrickDestroy.Gameplay_Model;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CementBrickTest {

    CementBrick cementBrick = new CementBrick(new Point2D(0,0), new Dimension2D(20,10));

    @Test
    void setImpact1() {
        assertEquals(cementBrick.setImpact(new Point2D(0,0),Crack.LEFT),0);
        assertEquals(cementBrick.setImpact(new Point2D(0,0),Crack.LEFT),70);
        assertEquals(cementBrick.setImpact(new Point2D(0,0),Crack.LEFT),0);
    }
    @Test
    void setImpact2() {
        assertEquals(cementBrick.setImpact(new Point2D(0,10),Crack.DOWN),0);
        assertEquals(cementBrick.setImpact(new Point2D(0,10),Crack.DOWN),70);
        assertEquals(cementBrick.setImpact(new Point2D(0,10),Crack.DOWN),0);
    }

    @Test
    void setImpact3() {
        assertEquals(cementBrick.setImpact(new Point2D(20,0),Crack.UP),0);
        assertEquals(cementBrick.setImpact(new Point2D(20,0),Crack.UP),70);
        assertEquals(cementBrick.setImpact(new Point2D(20,0),Crack.UP),0);
    }

    @Test
    void setImpact4() {
        assertEquals(cementBrick.setImpact(new Point2D(20,100),Crack.RIGHT),0);
        assertEquals(cementBrick.setImpact(new Point2D(20,100),Crack.RIGHT),70);
        assertEquals(cementBrick.setImpact(new Point2D(20,100),Crack.RIGHT),0);
    }



    @Test
    void impact() {
        cementBrick.impact();
        assertFalse(cementBrick.isBroken());
        cementBrick.impact();
        assertTrue(cementBrick.isBroken());
        cementBrick.impact();
        assertTrue(cementBrick.isBroken());
    }
}