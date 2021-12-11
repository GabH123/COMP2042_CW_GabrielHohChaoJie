package BrickDestroy.GameController;


import javafx.scene.input.KeyEvent;

/**Defines the interface of a controller for the interactions between the objects in the model.
 */
public interface Controllable {

    /**Updates position of the ball and player.
     */
    void updatePosition();
    /**Detects collision  between the ball and the wall, player, border, rood and death zone.
     */
    void detectBallCollision();

    /**Resets the ball and player back to their default location and state.
     */
    void resetBallPlayer();

    /**Updates and retrieves the next level.
     */
    void nextLevel();

    /**Moves the player based on inputs from the keyboard.
     * @param keyEvent
     */
    void movePlayer(KeyEvent keyEvent);

    /**Stops the motion of the player.
     */
    void stopPlayer();

    /**Reset the state of the game to the initial conditions.
     */
    void resetGame();
}
