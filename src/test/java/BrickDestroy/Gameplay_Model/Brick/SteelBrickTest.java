package BrickDestroy.Gameplay_Model.Brick;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SteelBrickTest {
    SteelBrick steelBrick = new SteelBrick(new Point2D(0,0),new Dimension2D(100,20));

    @Test
    void setImpact() {
        int score = 0;
        do{
            assertEquals(score,0);
            score = steelBrick.collidedWithBall(new Point2D(50,1),Crack.UP);
        }while (!steelBrick.isBroken());
        assertEquals(score,90);
        for (int i = 0 ;i<10;i++) {
            score = steelBrick.collidedWithBall(new Point2D(50, 1), Crack.UP);
            assertEquals(score, 0);
        }
    }

}