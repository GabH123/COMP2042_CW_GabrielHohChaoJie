package BrickDestroy.Gameplay_Model;

import BrickDestroy.Gameplay_Model.Ball.Ball;
import BrickDestroy.Gameplay_Model.Ball.RubberBall;
import BrickDestroy.Gameplay_Model.Playable.Player;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player player ;
    Ball ballInside;
    Ball ballOutside;
    @BeforeEach
    public void setUp(){
        ballInside = new RubberBall(new Point2D(300,400));
        ballOutside = new RubberBall(new Point2D(300,399));
        Pane pane = new Pane();
        pane.setPrefSize(600,400);
        player = new Player(new Point2D(300,400),100,20,pane);
    }
    @Test
    void ballPlayerCollision() {
        assertTrue(player.ballPlayerCollision(ballInside));
        assertFalse(player.ballPlayerCollision(ballOutside));
    }

    @Test
    void move() {


        for (int i=0;i<1000;i++) {
            player.move();
            assertEquals(player.getPlayerFace().getBoundsInParent().getMinX(), 249.5);
        }
    }

    @Test
    void moveLeft() {
        player.moveLeft();

        for (double i = 244.5;i>=0;i-=5) {
            player.move();
            assertEquals(player.getPlayerFace().getBoundsInParent().getMinX(), i);
        }
        for (int i=0;i<10;i++) {
            player.move();
            assertEquals(player.getPlayerFace().getBoundsInParent().getMinX(), -0.5);
        }
    }

    @Test
    void moveRight() {
        player.moveRight();

        for (double i = 254.5;i<=500;i+=5) {
            player.move();
            assertEquals(player.getPlayerFace().getBoundsInParent().getMinX(), i);
        }
        for (int i=0;i<10;i++) {
            player.move();
            assertEquals(player.getPlayerFace().getBoundsInParent().getMinX(), 499.5);
        }
    }

    @Test
    void stop() {
        player.moveLeft();

        for (int i = 0;i<10;i++) {
            player.move();
        }
        assertEquals(player.getPlayerFace().getBoundsInParent().getMinX(), 199.5 );
        player.stop();
        for (int i = 0;i<10;i++) {
            player.move();
        }
        assertEquals(player.getPlayerFace().getBoundsInParent().getMinX(), 199.5 );

    }

    @Test
    void moveTo() {
        player.moveTo(new Point2D(200,350));
        assertEquals(player.getPlayerFace().getBoundsInParent().getMinX(),149.5);
        assertEquals(player.getPlayerFace().getBoundsInParent().getMinY(),349.5);
    }
}