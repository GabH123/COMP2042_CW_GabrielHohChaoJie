package BrickDestroy.Gameplay_Model;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public interface Playable {

    boolean ballPlayerCollision(Ball b);
    void move();
    void moveLeft();
    void moveRight();
    void stop();
    void moveTo(Point2D p);
    void playerDrawInfo(GraphicsContext gc);
}
