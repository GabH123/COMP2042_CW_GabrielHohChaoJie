package BrickDestroy.BrickDestroy_Model_JavaFX;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


/**
 * Created by filippo on 04/09/16.
 */
abstract public class Brick {

    public static final int MIN_CRACK = 1;
    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;

    private String name;
    private Rectangle brickFace;

    private Color border;
    private Color inner;

    private int fullStrength;
    private int strength;

    private boolean broken;


    public Brick(String name, Point2D pos, Dimension2D size, Color border, Color inner, int strength) {
        broken = false;
        this.name = name;
        brickFace = makeBrickFace(pos, size);
        this.border = border;
        this.inner = inner;
        this.fullStrength = this.strength = strength;

    }

    protected abstract Rectangle makeBrickFace(Point2D pos, Dimension2D size);

    public boolean setImpact(Point2D point, int dir) {
        if (broken)
            return false;
        impact();
        return broken;
    }

    public abstract Shape getBrick();


    public Color getBorderColor() {
        return border;
    }

    public Color getInnerColor() {
        return inner;
    }

    public final boolean isBroken() {
        return broken;
    }

    public void repair() {
        broken = false;
        strength = fullStrength;
    }

    public void impact() {
        strength--;
        broken = (strength == 0);
    }

    public void brickDrawInfo(GraphicsContext g2d){
        g2d.setFill(getInnerColor());
        g2d.fillRect(brickFace.getX(),brickFace.getY(),brickFace.getWidth(),brickFace.getHeight());

        g2d.setStroke(getBorderColor());
        g2d.strokeRect(brickFace.getX(),brickFace.getY(),brickFace.getWidth(),brickFace.getHeight());

    }

    public Shape getBrickFace() {
        return brickFace;
    }
}





