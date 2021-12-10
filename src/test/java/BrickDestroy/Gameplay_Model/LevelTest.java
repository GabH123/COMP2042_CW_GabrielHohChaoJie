package BrickDestroy.Gameplay_Model;

import BrickDestroy.Gameplay_Model.Ball.Ball;
import BrickDestroy.Gameplay_Model.Ball.RubberBall;
import BrickDestroy.Gameplay_Model.Brick.Brick;
import BrickDestroy.Gameplay_Model.Brick.CementBrick;
import BrickDestroy.Gameplay_Model.Brick.ClayBrick;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LevelTest {
    Brick[] bricks={new ClayBrick(new Point2D(0,0),new Dimension2D(100,20))};
    Level level = new Level(bricks);

    @Test
    void ballBrickCollision1() {
        Ball ball = new RubberBall(new Point2D(50,23));
        assertEquals(level.getTotalBricksLeft(),1);
        assertEquals(level.ballBrickCollision(ball),50);
        assertEquals(level.getTotalBricksLeft(),0);
        assertEquals(level.ballBrickCollision(ball),0);
        assertEquals(level.getTotalBricksLeft(),0);
    }

    @Test
    void ballBrickCollision2() {
        Ball ball = new RubberBall(new Point2D(50,26));
        assertEquals(level.getTotalBricksLeft(),1);
        assertEquals(level.ballBrickCollision(ball),0);
        assertEquals(level.ballBrickCollision(ball),0);
        assertEquals(level.getTotalBricksLeft(),1);
    }

    @Test
    void getTotalBricksLeft() {
        assertEquals(level.getTotalBricksLeft(),1);
        Ball ball = new RubberBall(new Point2D(50,24));
        assertEquals(level.ballBrickCollision(ball),50);
        assertEquals(level.getTotalBricksLeft(),0);
    }
}