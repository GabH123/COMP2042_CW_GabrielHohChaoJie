package BrickDestroy.GameController;


import javafx.scene.input.KeyEvent;

public interface Controllable {

    void updatePosition();
    void detectBallCollision();
    void resetBall();
    void resetLevel();
    void nextLevel();
    void movePlayer(KeyEvent keyEvent);
    void stopPlayer();
    boolean hasLevel();
}
