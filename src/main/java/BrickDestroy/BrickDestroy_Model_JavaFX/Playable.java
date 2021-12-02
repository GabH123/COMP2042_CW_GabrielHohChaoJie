package BrickDestroy.BrickDestroy_Model_JavaFX;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public interface Playable {

    boolean detectBallPlayerCollision(Ball b);
    void move();
    void moveLeft();
    void moveRight();
    void stop();
    void moveTo(Point2D p);
    void playerDrawInfo(GraphicsContext gc);
}
