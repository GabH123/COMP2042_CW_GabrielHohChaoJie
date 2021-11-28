package test;

public class Level {

    private Brick[] bricks;

    public Level(Brick[] bricks) {
        this.bricks = bricks;
    }

    public void resetLevel(){
        for(Brick b : getBricks())
            b.repair();
    }

    public Brick[] getBricks() {
        return bricks;
    }
}
