package BrickDestroy.Gameplay_Model.Brick;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


/**CementBrick extends Brick and indicates a brick that is made of cement.
 * It is durable and has 2 hit points before being broken.
 * <p>
 * A crack will form if it is ot in full health.
 */
public class CementBrick extends Brick {


    /**Defines the color for the cement brick.
     *
     */
    private static final Color DEF_INNER = new Color(148.0/256, 148.0/256, 148.0/256,1);
    /**Defines the border of the color for the cement brick.
     *
     */
    private static final Color DEF_BORDER = new Color(218.0/256, 200.0/256, 176.0/256,1);
    /**Defines the max hit points for the cement brick.
     *
     */
    private static final int CEMENT_STRENGTH = 2;
    /**Defines the score worth of the cement brick.
     *
     */
    private final int CEMENT_BRICK_SCORE_WORTH=70;


    /**Initialises CementBrick with the given location and size.
     * @param point upper-left location of the CementBrick object
     * @param size size of the CementBrick
     */
    public CementBrick(Point2D point, Dimension2D size){
        super(point,size,DEF_BORDER,DEF_INNER,CEMENT_STRENGTH);
        initialiseBrick(DEF_INNER,DEF_BORDER);
    }


    /**Creates the Shape object for CementBrick.
     * @param pos  upper-left position of the brick
     * @param size size of the brick
     * @return Rectangle shape object which represents the actual cement brick
     */
    @Override
    protected Shape makeBrickFace(Point2D pos, Dimension2D size) {
        return new Rectangle(pos.getX(),pos.getY(),size.getWidth(),size.getHeight());
    }

    /**Invoked when collision between ball and the brick is detected.
     * <p>
     * The Crack that belongs to this CementBrick will be formed and will be visible.
     * @param point the point where the ball and brick collided
     * @param dir   the part of the brick where it collided (up, down, left or right)
     * @return score worth of the brick (as indicator of a hit)
     */
    @Override
    public int collidedWithBall(Point2D point, int dir) {
        if(super.isBroken())
            return 0;
        super.impact();
        if(!super.isBroken()){
            crack.makeCrack(point,dir,(Rectangle) getBrickFace());
            return 0;
        }
        return CEMENT_BRICK_SCORE_WORTH;
    }




}
