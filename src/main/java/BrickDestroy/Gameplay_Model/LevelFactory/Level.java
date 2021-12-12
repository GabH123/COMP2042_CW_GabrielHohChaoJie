package BrickDestroy.Gameplay_Model.LevelFactory;

import BrickDestroy.Gameplay_Model.Ball.Ball;
import BrickDestroy.Gameplay_Model.Brick.Brick;
import BrickDestroy.Gameplay_Model.Brick.Crack;

import static BrickDestroy.GameController.GameplayController.*;

/**Level object holds the info for all the bricks in the level.
 *
 */
public class Level implements Buildable{

    /**The array of bricks in the level.
     *
     */
    private Brick[] bricks;

    /**Assigns the array of newly made bricks to the level object.
     * @param bricks
     */
    public Level(Brick[] bricks) {
        this.bricks = bricks;
    }

    /**Detects collision between the ball and the bricks.
     * @param ball the current ball object
     * @return the score worth of the brick
     */
    public int ballBrickCollision(Ball ball){
        for(Brick b : getBricks()){

            switch(findImpact(b, ball)) {
                //Vertical Impact
                case UP_IMPACT:

                    ball.reverseY();
                    return b.collidedWithBall(ball.getDown(), Crack.UP);
                case DOWN_IMPACT:

                    ball.reverseY();
                    return b.collidedWithBall(ball.getUp(), Crack.DOWN);

                //Horizontal Impact
                case LEFT_IMPACT:

                    ball.reverseX();
                    return b.collidedWithBall(ball.getRight(), Crack.LEFT);
                case RIGHT_IMPACT:

                    ball.reverseX();
                    return b.collidedWithBall(ball.getLeft(), Crack.RIGHT);
            }
        }
        return 0;
    }

    /**Detects which side of the brick has the ball made contact with.
     * @param brick the brick in question
     * @param ball the current ball
     * @return the constant value to signal which side it is
     */
    private int findImpact(Brick brick, Ball ball){
        if (brick.isBroken())
            return 0;
        int out = 0;
        if (brick.getBrickFace().contains(ball.getRight()))
            out = LEFT_IMPACT;
        else if (brick.getBrickFace().contains(ball.getLeft()))
            out = RIGHT_IMPACT;
        else if (brick.getBrickFace().contains(ball.getUp()))
            out = DOWN_IMPACT;
        else if (brick.getBrickFace().contains(ball.getDown()))
            out = UP_IMPACT;
        return out;
    }

    /**Returns the amount of unbroken brick left in the level
     * @return amount of unbroken brick left in the middle
     */
    public int getTotalBricksLeft(){
        int total=0;
        for(Brick b:getBricks())
            if (!b.isBroken())
                total++;
        return total;
    }

    /**Returns the brick array for the level
     * @return the brick array for the level
     */
    public Brick[] getBricks() {
        return bricks;
    }

}
