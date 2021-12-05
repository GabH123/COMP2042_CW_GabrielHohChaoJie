package BrickDestroy.BrickDestroy_Model_JavaFX;

import javafx.geometry.Point2D;
import javafx.scene.shape.*;

import java.util.Random;

public class Crack {

    private static final int CRACK_SECTIONS = 3;
    private static final double JUMP_PROBABILITY = 0.7;

    public static final int LEFT = 10;
    public static final int RIGHT = 20;
    public static final int UP = 30;
    public static final int DOWN = 40;
    public static final int VERTICAL = 100;
    public static final int HORIZONTAL = 200;

    private static Random rnd;

    private Path crack;

    private int crackDepth;
    private int steps;


    public Crack(int crackDepth, int steps) {
        rnd=new Random();
        crack = new Path();
        this.crackDepth = crackDepth;
        this.steps = steps;

    }


    public Path draw() {
        return crack;
    }

    public void reset() {
        crack = new Path();
    }

    protected void makeCrack(Point2D point, int direction, Rectangle brickFace) {
        Rectangle bounds = brickFace;

        Point2D impact = new Point2D( point.getX(),  point.getY());
        Point2D start = new Point2D(0,0);
        Point2D end = new Point2D(0,0);


        switch (direction) {
            case LEFT:
                start.add(bounds.getX() + bounds.getWidth(), bounds.getY());
                end.add(bounds.getX() + bounds.getWidth(), bounds.getY() + bounds.getHeight());
                Point2D tmp = makeRandomPoint(start, end, VERTICAL);
                makeCrack(impact, tmp);

                break;
            case RIGHT:
                start.add(new Point2D(bounds.getX(),bounds.getY()));
                end.add(bounds.getX(), bounds.getY() + bounds.getHeight());
                tmp = makeRandomPoint(start, end, VERTICAL);
                makeCrack(impact, tmp);

                break;
            case UP:
                start.add(bounds.getX(), bounds.getY() + bounds.getHeight());
                end.add(bounds.getX() + bounds.getWidth(), bounds.getY() + bounds.getHeight());
                tmp = makeRandomPoint(start, end, HORIZONTAL);
                makeCrack(impact, tmp);
                break;
            case DOWN:
                start.add(new Point2D(bounds.getX(),bounds.getY()));
                end.add(bounds.getX() + bounds.getWidth(), bounds.getY());
                tmp = makeRandomPoint(start, end, HORIZONTAL);
                makeCrack(impact, tmp);

                break;

        }
    }

    protected void makeCrack(Point2D start, Point2D end) {

        Path path = crack;


        path.getElements().add(new MoveTo(start.getX(), start.getY()));

        double w = (end.getX() - start.getY()) / (double) steps;
        double h = (end.getX() - start.getY()) / (double) steps;

        int bound = crackDepth;
        int jump = bound * 5;

        double x, y;

        for (int i = 1; i < steps; i++) {

            x = (i * w) + start.getX();
            y = (i * h) + start.getY() + randomInBounds(bound);

            if (inMiddle(i, CRACK_SECTIONS, steps))
                y += jumps(jump, JUMP_PROBABILITY);

            path.getElements().add(new LineTo(x, y));

        }

        path.getElements().add(new LineTo(end.getX(), end.getY()));
    }

    private int randomInBounds(int bound) {
        int n = (bound * 2) + 1;
        return rnd.nextInt(n) - bound;
    }

    private boolean inMiddle(int i, int steps, int divisions) {
        int low = (steps / divisions);
        int up = low * (divisions - 1);

        return (i > low) && (i < up);
    }

    private int jumps(int bound, double probability) {

        if (rnd.nextDouble() > probability)
            return randomInBounds(bound);
        return 0;

    }

    private Point2D makeRandomPoint(Point2D from, Point2D to, int direction) {

        Point2D out = new Point2D(0,0);
        int pos;

        switch (direction) {
            case HORIZONTAL:
                pos = rnd.nextInt((int) (to.getX() - from.getX())) + (int) from.getX();
                out.add(pos, to.getY());
                break;
            case VERTICAL:
                pos = rnd.nextInt((int) (to.getY() - from.getY())) + (int)from.getY();
                out.add(to.getX(), pos);
                break;
        }
        return out;
    }

}