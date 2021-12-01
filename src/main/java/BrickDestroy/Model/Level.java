package BrickDestroy.Model;

import static BrickDestroy.Controller.GameplayController.*;

public class Level {

    private Brick[] bricks;

    public Level(Brick[] bricks) {
        this.bricks = bricks;
    }

    public void resetBricks(){
        for(Brick b : getBricks())
            b.repair();
    }

    public boolean detectBallBrickCollision(Ball ball){
        for(Brick b : getBricks()){
            switch(findImpact(b, ball)) {
                //Vertical Impact
                case UP_IMPACT:
                    ball.reverseY();
                    return b.setImpact(ball.getDown(), Crack.UP);
                case DOWN_IMPACT:
                    ball.reverseY();
                    return b.setImpact(ball.getUp(),Crack.DOWN);

                //Horizontal Impact
                case LEFT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.getRight(),Crack.RIGHT);
                case RIGHT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.getLeft(),Crack.LEFT);
            }
        }
        return false;
    }

    private int findImpact(Brick brick, Ball ball){
        if (brick.isBroken())
            return 0;
        int out = 0;
        if (brick.getBrickFace().contains(ball.getRight()))
            out = LEFT_IMPACT;
        else if (brick.getBrick().contains(ball.getLeft()))
            out = RIGHT_IMPACT;
        else if (brick.getBrick().contains(ball.getUp()))
            out = DOWN_IMPACT;
        else if (brick.getBrickFace().contains(ball.getDown()))
            out = UP_IMPACT;
        return out;
    }


    public Brick[] getBricks() {
        return bricks;
    }

    public int getNumberOfBricks(){
        return getBricks().length;
    }
}
