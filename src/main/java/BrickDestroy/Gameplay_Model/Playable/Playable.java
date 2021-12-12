package BrickDestroy.Gameplay_Model.Playable;

import BrickDestroy.Gameplay_Model.Ball.Ball;
import javafx.geometry.Point2D;


/**Interface Playable defines what is interactable with the player and it's interaction with other objects.
 *
 */
public interface Playable {

    /**Detects collision between the playable object and the ball.
     * @param b ball in question
     * @return whether a collision is detected or not
     */
    boolean ballPlayerCollision(Ball b);

    /**Updates the position of the playable object.
     *
     */
    void move();

    /**Allows the playable object to move left.
     *
     */
    void moveLeft();
    /**Allows the playable object to move right.
     *
     */
    void moveRight();
    /**Stops the playable object to from moving.
     *
     */
    void stop();
    /**Moves the playable object to the new location
     *
     */
    void moveTo(Point2D p);
}
