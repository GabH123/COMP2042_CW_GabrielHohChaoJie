package BrickDestroy.Gameplay_Model.Ball;


import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

/**
 * Created by filippo on 04/09/16.
 *
 */
abstract public class Ball {

    private Shape ballFace;

    private Point2D center;

    private Point2D up;
    private Point2D down;
    private Point2D left;
    private Point2D right;

    private Color border;
    private Color inner;

    private double speedX;
    private double speedY;

    public Ball(Point2D center, int radiusA, int radiusB, Color inner, Color border){
        this.center = center;

        setPoints(radiusA,radiusB);
        ballFace = makeBall(center,radiusA,radiusB);
        this.border = border;
        this.inner  = inner;
        speedX = 0;
        speedY = 0;

        initialiseBall(ballFace);
    }

    protected abstract Shape makeBall(Point2D center,int radiusA,int radiusB);

    public void move(){
        center=center.add(speedX,speedY);
        ballFace.setTranslateX(ballFace.getTranslateX()+speedX);
        ballFace.setTranslateY(ballFace.getTranslateY()+speedY);

        setPoints(ballFace.getLayoutBounds().getWidth()/2,ballFace.getLayoutBounds().getHeight()/2);

    }

    public void setSpeed(int x,int y){
        speedX = x;
        speedY = y;
    }

    public void setXSpeed(double s){
        speedX = s;
    }

    public void setYSpeed(double s){
        speedY = s;
    }

    public void reverseX(){
        speedX *= -1;
    }

    public void reverseY(){
        speedY *= -1;
    }

    public Color getBorderColor(){
        return border;
    }

    public Color getInnerColor(){
        return inner;
    }

    public Point2D getPosition(){
        return center;
    }

    public Shape getBallFace(){
        return ballFace;
    }

    public void moveTo(Point2D p){
        center=p;
        ballFace.setTranslateX(0);
        ballFace.setTranslateY(0);

        setPoints(ballFace.getLayoutBounds().getWidth()/2,ballFace.getLayoutBounds().getHeight()/2);
        //System.out.println("Resetting: "+p);
    }

    private void initialiseBall(Shape ballFace){
        ballFace.setFill(inner);
        ballFace.setStroke(border);
        ballFace.setStrokeWidth(1.0);
    }

    private void setPoints(double width,double height){

        up = center.add(0,-height );
        down = center.add(0,height );

        left = center.add(-width ,0);
        right = center.add(width,0);
    }

    public double getSpeedX(){
        return speedX;
    }

    public double getSpeedY(){
        return speedY;
    }

    public Point2D getUp() {
        return up;
    }

    public Point2D getDown() {
        return down;
    }

    public Point2D getLeft() {
        return left;
    }

    public Point2D getRight() {
        return right;
    }


}
