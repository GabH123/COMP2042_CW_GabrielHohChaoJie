package BrickDestroy.Gameplay_Model.Brick;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.*;
import javafx.scene.shape.Shape;


/**A Brick represent a brick that can be broken by the ball in the game.
 *
 */
abstract public class Brick {

    /**Defines the default score worth of the brick.
     *
     */
    private final int DEFAULT_BRICK_SCORE_WORTH = 50;


    /**Shape object for the brick.
     *
     */
    private Shape brickFace;

    /**Describes the crack for the brick.
     *
     */
    protected Crack crack;

    /**Color for the border of brick.
     *
     */
    private Color border;
    /**Color of the brick.
     *
     */
    private Color inner;

    /**Current hitpoints left of the brick.
     *
     */
    private int hitPoints;

    /**Flag for whether the brick is broken or not.
     *
     */
    private boolean broken;


    /**Initialises the brick with the intended position, size, color and the amount of hitpoints.
     * @param pos the position of the brick on the pane
     * @param size the size of the brick on the pane
     * @param border the color for the border of the brick
     * @param inner the color for the brick
     * @param hitPoints the hitpoints for the brick
     */
    public Brick( Point2D pos, Dimension2D size, Color border, Color inner, int hitPoints) {
        broken = false;

        brickFace = makeBrickFace(pos, size);
        this.border = border;
        this.inner = inner;
        this.hitPoints = hitPoints;
        crack = new Crack();
    }

    /**Creates the Shape object of the brick with the intended position and size.
     * @param pos position of the brick
     * @param size size of the brick
     * @return
     */
    protected abstract Shape makeBrickFace(Point2D pos, Dimension2D size);

    /**Initialises the brick with the intended border color and color of the brick.
     * @param inner color of the brick
     * @param border color of the border of the brick
     */
    protected void initialiseBrick(Color inner,Color border){
        getBrickFace().setFill(inner);
        getBrickFace().setStroke(border);
        getBrickFace().setStrokeWidth(1);

    }

    /**Invoked when collision between ball and the brick is detected.
     * @param point the point where the ball and brick collided
     * @param dir the part of the brick where it collided (up, down, left or right)
     * @return score worth of the brick (as indicator of a hit)
     */
    public int collidedWithBall(Point2D point, int dir) {
        if (broken)
            return 0;
        impact();
        return DEFAULT_BRICK_SCORE_WORTH;
    }

    /**Gets the crack object for the brick.
     * @return crack object for the brick
     */
    public Crack getCrack() {
        return crack;
    }

    /**Checks whether brick is broken or not.
     * @return brick is broken or not
     */
    public final boolean isBroken() {
        return broken;
    }


    /**Stimulate an impact of ball on the brick.
     * Decreases hit points of the brick by 1.
     * <p>
     * If hitpoints reaches 0, Brick is broken (destroyed) and the visibility of it's Shape and Crack is set invisible.
     */
    protected void impact() {
        hitPoints--;
        broken = (hitPoints <= 0);
        if (broken) {
            getBrickFace().setVisible(false);
            getCrack().getCrackPath().setVisible(false);
        }
    }


    /**Gets the Shape object that represents the brick on the graphics pane.
     * @return the Shape of the object
     */
    public Shape getBrickFace() {
        return brickFace;
    }


}





