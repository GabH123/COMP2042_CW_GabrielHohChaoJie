package BrickDestroy.GameController;

import java.awt.event.KeyEvent;

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
