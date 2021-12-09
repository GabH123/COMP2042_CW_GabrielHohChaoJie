package BrickDestroy.Gameplay_Model;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;



public class CementBrick extends Brick {


    private static final String NAME = "Cement Brick";
    private static final Color DEF_INNER = new Color(148.0/256, 148.0/256, 148.0/256,1);
    private static final Color DEF_BORDER = new Color(218.0/256, 200.0/256, 176.0/256,1);
    private static final int CEMENT_STRENGTH = 2;
    private final int CEMENT_BRICK_SCORE_WORTH=70;

    private Shape brickFace;


    public CementBrick(Point2D point, Dimension2D size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CEMENT_STRENGTH);
        brickFace = super.getBrickFace();
        initialiseBrick(DEF_INNER,DEF_BORDER);
    }



    @Override
    protected Shape makeBrickFace(Point2D pos, Dimension2D size) {
        return new Rectangle(pos.getX(),pos.getY(),size.getWidth(),size.getHeight());
    }

    @Override
    public int setImpact(Point2D point, int dir) {
        if(super.isBroken())
            return 0;
        super.impact();
        if(!super.isBroken()){
            crack.makeCrack(point,dir,(Rectangle) getBrick());
            return 0;
        }
        return CEMENT_BRICK_SCORE_WORTH;
    }


    @Override
    public Shape getBrick() {
        return brickFace;
    }


    public void repair(){
        super.repair();
        crack.reset();
        brickFace = super.getBrickFace();
    }
}
