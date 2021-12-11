package BrickDestroy.Gameplay_Model.Ball;


import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

import java.util.Random;

/**
 * Created by filippo on 04/09/16.
 *
 */
abstract public class Ball {

    private static final double BORDER_STROKE_WIDTH=1.0;
    private static final double ANGLE_DEVIATION_CHANGE_FACTOR=20;
    private static final double BALL_SPEED = 6;

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

    public void setBallAngle(){
        Random rnd = new Random();
        double ballAngle = rnd.nextDouble(80)+50;

        setXSpeed(Math.cos(Math.toRadians(ballAngle))*BALL_SPEED);
        setYSpeed(-1*Math.sin(Math.toRadians(ballAngle))*BALL_SPEED);
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

    public void calculateDeviation(double playerX, double playerWidth){
        double midpointX = playerX + playerWidth/2;
        double distanceBetweenMidAndBallHit = ( getPosition().getX()-midpointX);
        double actualDeviationPercentage = distanceBetweenMidAndBallHit * ANGLE_DEVIATION_CHANGE_FACTOR/(playerWidth/2);
        System.out.println(actualDeviationPercentage);
        if (actualDeviationPercentage>0){
            Point2D rootVector = new Point2D(1,0);
            double angleRelativeToX = rootVector.angle(getSpeedX(),getSpeedY());
            System.out.println("Angle before: "+angleRelativeToX);
            double newAngle = angleRelativeToX - angleRelativeToX*actualDeviationPercentage/100;
            System.out.println("Angle after: "+newAngle);
            setXSpeed(Math.cos(Math.toRadians(newAngle))*BALL_SPEED);
            setYSpeed(-Math.sin(Math.toRadians(newAngle))*BALL_SPEED);
        }
        else {
            Point2D rootVector = new Point2D(1,0);
            double angleRelativeToX = rootVector.angle(getSpeedX(),getSpeedY());

            angleRelativeToX = 180 - angleRelativeToX;
            System.out.println("Angle before: "+angleRelativeToX);
            double newAngle = angleRelativeToX + angleRelativeToX*actualDeviationPercentage/100;
            System.out.println("Angle after: "+newAngle);
            newAngle = 180 - newAngle;

            setXSpeed(Math.cos(Math.toRadians(newAngle))*BALL_SPEED);
            setYSpeed(-Math.sin(Math.toRadians(newAngle))*BALL_SPEED);
        }
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

        ballFace.setTranslateX(p.getX()-ballFace.getBoundsInParent().getCenterX());
        ballFace.setTranslateY(p.getY()-ballFace.getBoundsInParent().getCenterY());
        setPoints(ballFace.getLayoutBounds().getWidth()/2,ballFace.getLayoutBounds().getHeight()/2);
    }

    private void initialiseBall(Shape ballFace){
        ballFace.setFill(getInnerColor());
        ballFace.setStroke(getBorderColor());
        ballFace.setStrokeWidth(BORDER_STROKE_WIDTH);
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
