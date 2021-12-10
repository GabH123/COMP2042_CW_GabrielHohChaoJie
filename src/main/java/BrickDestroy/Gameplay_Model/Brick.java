package BrickDestroy.Gameplay_Model;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.*;
import javafx.scene.shape.Shape;


/**
 * Created by filippo on 04/09/16.
 */
abstract public class Brick {

    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;
    private final int NORMAL_BRICK_SCORE_WORTH = 50;

    private String name;
    private Shape brickFace;

    protected Crack crack;

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
        crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS);
    }

    protected abstract Shape makeBrickFace(Point2D pos, Dimension2D size);

    protected void initialiseBrick(Color inner,Color border){
        getBrickFace().setFill(inner);
        getBrickFace().setStroke(border);
        getBrickFace().setStrokeWidth(1);

    }

    public int setImpact(Point2D point, int dir) {
        if (broken)
            return 0;
        impact();
        return NORMAL_BRICK_SCORE_WORTH;
    }

    public abstract Shape getBrick();

    public Crack getCrack() {
        return crack;
    }

    public final boolean isBroken() {
        return broken;
    }


    public void impact() {
        strength--;
        broken = (strength <= 0);
        if (broken) {
            getBrickFace().setVisible(false);
            getCrack().getCrackPath().setVisible(false);
        }
    }


    public Shape getBrickFace() {
        return brickFace;
    }


}





