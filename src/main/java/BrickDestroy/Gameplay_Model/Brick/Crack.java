package BrickDestroy.Gameplay_Model.Brick;

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


    protected void makeCrack(Point2D point, int direction, Rectangle brickFace) {
        Rectangle bounds = brickFace;

        Point2D impact = new Point2D( point.getX(),  point.getY());
        Point2D start = new Point2D(0,0);
        Point2D end = new Point2D(0,0);


        switch (direction) {
            case LEFT:
                start= new Point2D(bounds.getX() + bounds.getWidth(), bounds.getY());
                end = new Point2D(bounds.getX() + bounds.getWidth(), bounds.getY() + bounds.getHeight());
                Point2D tmp = makeRandomPoint(start, end, VERTICAL);
                makeCrack(impact, tmp);
                break;
            case RIGHT:
                start = new Point2D(bounds.getX(),bounds.getY());
                end = new Point2D(bounds.getX(), bounds.getY() + bounds.getHeight());
                tmp = makeRandomPoint(start, end, VERTICAL);
                makeCrack(impact, tmp);
                break;
            case UP:
                start = new Point2D(bounds.getX(), bounds.getY() + bounds.getHeight());
                end = new Point2D(bounds.getX() + bounds.getWidth(), bounds.getY() + bounds.getHeight());
                tmp = makeRandomPoint(start, end, HORIZONTAL);
                makeCrack(impact, tmp);
                break;
            case DOWN:
                start = new Point2D(bounds.getX(),bounds.getY());
                end = new Point2D(bounds.getX() + bounds.getWidth(), bounds.getY());
                tmp = makeRandomPoint(start, end, HORIZONTAL);
                makeCrack(impact, tmp);

                break;

        }
    }

    private void makeCrack(Point2D start, Point2D end) {

        Path path = crack;


        path.getElements().add(new MoveTo(start.getX(), start.getY()));

        double w = (end.getX() - start.getX()) / (double) steps;
        double h = (end.getY() - start.getY()) / (double) steps;

        int bound = crackDepth;
        int jump = bound * 5;

        double x, y;
        for (int i = 1; i < steps; i++) {

            x = (i * w) + start.getX();
            y = (i * h) + start.getY() + randomInBounds(bound);

            if (inMiddle(i, CRACK_SECTIONS, steps))
                y += jumps(jump, JUMP_PROBABILITY);

            y=checkIfOutOfBrickBound(y,start,end);
            path.getElements().add(new LineTo(x, y));

        }
        path.getElements().add(new LineTo(end.getX(), end.getY()));
    }

    private double checkIfOutOfBrickBound(double y,Point2D start, Point2D end){
        if ( ( start.getY()<=y && y<=end.getY() ) || ( start.getY()>=y && y>=end.getY() ) )
            return y;
        else{
            double yStartDifference = start.getY() - y;
            double yEndDifference = end.getY() - y;
            if (Math.abs(yStartDifference)<Math.abs(yEndDifference))
                return start.getY();
            else return end.getY();
        }

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
        double pos;

        switch (direction) {
            case HORIZONTAL:
                pos = rnd.nextInt((int) (to.getX() - from.getX())) +  from.getX();
                out = new Point2D(pos, to.getY());
                break;
            case VERTICAL:
                pos = rnd.nextInt((int) (to.getY() - from.getY())) + (int)from.getY();
                out = new Point2D(to.getX(), pos);
                break;
        }
        return out;
    }

    public Path getCrackPath() {
        return crack;
    }
}
