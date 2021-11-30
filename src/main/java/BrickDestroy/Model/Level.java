package BrickDestroy.Model;

public class Level {

    private Brick[] bricks;

    public Level(Brick[] bricks) {
        this.bricks = bricks;
    }

    public void resetBricks(){
        for(Brick b : getBricks())
            b.repair();
    }

    public Brick[] getBricks() {
        return bricks;
    }

    public int getNumberOfBricks(){
        return getBricks().length;
    }
}
