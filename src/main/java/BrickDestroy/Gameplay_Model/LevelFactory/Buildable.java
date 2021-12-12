package BrickDestroy.Gameplay_Model.LevelFactory;


import BrickDestroy.Gameplay_Model.Ball.Ball;

/**A Wall is made of Bricks, and Levels are build from Bricks. So a Level is Buildable with Bricks (get it?).
 *
 */
public interface Buildable {
    /**Detects collision between the ball and its bricks.
     * @param ball the current ball object
     * @return the score worth of the brick if broken
     */
    int ballBrickCollision(Ball ball);

    /**Returns the amount of unbroken brick left
     * @return amount of unbroken brick
     */
    int getTotalBricksLeft();
}
