package BrickDestroy.Gameplay_Model.Brick;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;



/**ClayBrick extends Brick and is made of clay.
 * It is the weakest brick of all and has only 1 hit points before being broken.
 */
public class ClayBrick extends Brick {
    /**Defines the color for the clay brick.
     */
    private static final Color DEF_INNER = new Color(179.0/256, 35.0/256, 35.0/256,1).darker();
    /**Defines the border of the color for the clay brick.
     */
    private static final Color DEF_BORDER = Color.GRAY;
    /**Defines the max hit points for the clay brick.
     */
    private static final int CLAY_STRENGTH = 1;

    /**Initialises ClayBrick with the given location and size.
     * @param point upper-left location of the clay brick
     * @param size size of the clay brick
     */
    public ClayBrick(Point2D point, Dimension2D size){
        super(point,size,DEF_BORDER,DEF_INNER,CLAY_STRENGTH);
        initialiseBrick(DEF_INNER,DEF_BORDER);
    }
    /**Creates the Shape object for ClayBrick.
     * @param pos  upper-left position of the brick
     * @param size size of the brick
     * @return Rectangle shape object which represents the actual clay brick
     */
    @Override
    protected Shape makeBrickFace(Point2D pos, Dimension2D size) {
        return new Rectangle(pos.getX(),pos.getY(),size.getWidth(),size.getHeight());
    }

}
