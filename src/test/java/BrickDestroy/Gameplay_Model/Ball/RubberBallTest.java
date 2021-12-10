package BrickDestroy.Gameplay_Model.Ball;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RubberBallTest {
    Ball rubberBall = new RubberBall(new Point2D(5,5));
    @Test
    void move() {
        for (int i=0;i<10;i++) {
            rubberBall.move();
            assertEquals(rubberBall.getPosition().getX(), 5 + rubberBall.getSpeedX());
            assertEquals(rubberBall.getPosition().getY(), 5 + rubberBall.getSpeedY());
        }
    }

    @Test
    void moveTo() {
        rubberBall.moveTo(new Point2D(10,10));
        assertEquals(rubberBall.getPosition().getX(),10);
        assertEquals(rubberBall.getPosition().getY(),10);

        rubberBall.moveTo(new Point2D(20,30));
        assertEquals(rubberBall.getPosition().getX(),20);
        assertEquals(rubberBall.getPosition().getY(),30);

        rubberBall.moveTo(new Point2D(0,0));
        assertEquals(rubberBall.getPosition().getX(),0);
        assertEquals(rubberBall.getPosition().getY(),0);
        rubberBall.moveTo(new Point2D(1000,1000));
        assertEquals(rubberBall.getPosition().getX(),1000);
        assertEquals(rubberBall.getPosition().getY(),1000);
    }
}