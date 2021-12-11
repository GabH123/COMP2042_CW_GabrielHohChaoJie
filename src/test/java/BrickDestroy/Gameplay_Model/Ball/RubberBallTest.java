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
        rubberBall.moveTo(new Point2D(10, 10));
        assertEquals(rubberBall.getBallFace().getBoundsInParent().getCenterX(), 10);
        assertEquals(rubberBall.getBallFace().getBoundsInParent().getCenterY(), 10);

        rubberBall.moveTo(new Point2D(20, 30));
        assertEquals(rubberBall.getBallFace().getBoundsInParent().getCenterX(), 20);
        assertEquals(rubberBall.getBallFace().getBoundsInParent().getCenterY(), 30);

        rubberBall.moveTo(new Point2D(0, 0));
        assertEquals(rubberBall.getBallFace().getBoundsInParent().getCenterX(), 0);
        assertEquals(rubberBall.getBallFace().getBoundsInParent().getCenterY(), 0);
        rubberBall.moveTo(new Point2D(1000, 1000));
        assertEquals(rubberBall.getBallFace().getBoundsInParent().getCenterX(), 1000);
        assertEquals(rubberBall.getBallFace().getBoundsInParent().getCenterY(), 1000);
    }
}