package BrickDestroy.Gameplay_Model;


/**A Wall is made of Bricks, and Levels are build from Bricks. So a Level is Buildable with Bricks (get it?).
 *
 */
public interface Buildable {
    int ballBrickCollision(Ball ball);
    int getTotalBricksLeft();
}
