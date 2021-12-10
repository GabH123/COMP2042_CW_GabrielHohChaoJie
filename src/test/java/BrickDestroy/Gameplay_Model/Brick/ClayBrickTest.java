package BrickDestroy.Gameplay_Model.Brick;

import BrickDestroy.Gameplay_Model.Brick.ClayBrick;
import BrickDestroy.Gameplay_Model.Brick.Crack;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClayBrickTest {
    ClayBrick clayBrick = new ClayBrick(new Point2D(0,0),new Dimension2D(20,10));
    @Test
    void setImpact1() {
        assertEquals(clayBrick.setImpact(new Point2D(0,0), Crack.LEFT),50);
        assertEquals(clayBrick.setImpact(new Point2D(0,0),Crack.LEFT),0);
    }
    @Test
    void setImpact2() {
        assertEquals(clayBrick.setImpact(new Point2D(20,0),Crack.DOWN),50);
        assertEquals(clayBrick.setImpact(new Point2D(20,0),Crack.DOWN),0);
    }
    @Test
    void setImpact3() {
        assertEquals(clayBrick.setImpact(new Point2D(0,10),Crack.UP),50);
        assertEquals(clayBrick.setImpact(new Point2D(0,10),Crack.UP),0);
    }
    @Test
    void setImpact4() {
        assertEquals(clayBrick.setImpact(new Point2D(20,10),Crack.RIGHT),50);
        assertEquals(clayBrick.setImpact(new Point2D(20,10),Crack.RIGHT),0);
    }

    @Test
    void impact() {
        clayBrick.impact();
        assertTrue(clayBrick.isBroken());
    }
}