package BrickDestroy.BrickDestroy_Model_JavaFX;

import static BrickDestroy.GameController_JavaFX.GameplayController.*;

public class Level {

    private Brick[] bricks;

    public Level(Brick[] bricks) {
        this.bricks = bricks;
    }

    public void resetBricks(){
        for(Brick b : getBricks())
            b.repair();
    }

    public void updateBrickBrokenStatus(){
        for (Brick b:getBricks())
            if (b.isBroken())
                b.getBrickFace().setDisable(true);
    }

    public int detectBallBrickCollision(Ball ballJavaFX){
        for(Brick b : getBricks()){
            switch(findImpact(b, ballJavaFX)) {
                //Vertical Impact
                case UP_IMPACT:
                    ballJavaFX.reverseY();
                    return b.setImpact(ballJavaFX.getDown(), Crack.UP);
                case DOWN_IMPACT:
                    ballJavaFX.reverseY();
                    return b.setImpact(ballJavaFX.getUp(), Crack.DOWN);

                //Horizontal Impact
                case LEFT_IMPACT:
                    ballJavaFX.reverseX();
                    return b.setImpact(ballJavaFX.getRight(), Crack.RIGHT);
                case RIGHT_IMPACT:
                    ballJavaFX.reverseX();
                    return b.setImpact(ballJavaFX.getLeft(), Crack.LEFT);
            }
        }
        return 0;
    }

    private int findImpact(Brick brickJavaFX, Ball ballJavaFX){
        if (brickJavaFX.isBroken())
            return 0;
        int out = 0;
        if (brickJavaFX.getBrickFace().contains(ballJavaFX.getRight()))
            out = LEFT_IMPACT;
        else if (brickJavaFX.getBrick().contains(ballJavaFX.getLeft()))
            out = RIGHT_IMPACT;
        else if (brickJavaFX.getBrick().contains(ballJavaFX.getUp()))
            out = DOWN_IMPACT;
        else if (brickJavaFX.getBrickFace().contains(ballJavaFX.getDown()))
            out = UP_IMPACT;
        return out;
    }


    public Brick[] getBricks() {
        return bricks;
    }

    public int getTotalNumberOfBricks(){
        return getBricks().length;
    }

    public int getNumberOfBricksLeft(){
        int total=0;
        for(Brick b:getBricks())
            if (!b.isBroken())
                total++;
        return total;
    }
}
