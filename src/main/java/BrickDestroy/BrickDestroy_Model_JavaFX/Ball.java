package BrickDestroy.BrickDestroy_Model_JavaFX;


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

    private int speedX;
    private int speedY;

    public Ball(Point2D center, int radiusA, int radiusB, Color inner, Color border){
        this.center = center;

        up = new Point2D(0,0);
        down = new Point2D(0,0);
        left = new Point2D(0,0);
        right = new Point2D(0,0);

        getUp().add(center.getX(),center.getY()-(radiusB / 2));
        getDown().add(center.getX(),center.getY()+(radiusB / 2));

        getLeft().add(center.getX()-(radiusA /2),center.getY());
        getRight().add(center.getX()+(radiusA /2),center.getY());


        ballFace = makeBall(center,radiusA,radiusB);
        this.border = border;
        this.inner  = inner;
        speedX = 0;
        speedY = 0;
    }

    protected abstract Shape makeBall(Point2D center,int radiusA,int radiusB);

    public void move(){
        //Shape tmp = (Shape) ballFace;
        center.add((center.getX() + speedX),(center.getY() + speedY));
        ballFace.setLayoutX(speedX);
        ballFace.setLayoutY(speedY);
        //double w = tmp.getWidth();
        //double h = tmp.getHeight();


        //tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        setPoints(ballFace.getLayoutBounds().getWidth(),ballFace.getLayoutBounds().getHeight());
    }

    public void setSpeed(int x,int y){
        speedX = x;
        speedY = y;
    }

    public void setXSpeed(int s){
        speedX = s;
    }

    public void setYSpeed(int s){
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
        center.add(p);
        ballFace.setLayoutX(p.getX());
        ballFace.setLayoutY(p.getY());
        setPoints(ballFace.getLayoutBounds().getWidth(),ballFace.getLayoutBounds().getHeight());
    }

    private void setPoints(double width,double height){

        getUp().add(center.getX(),center.getY()-(height / 2));
        getDown().add(center.getX(),center.getY()+(height / 2));

        getLeft().add(center.getX()-(width / 2),center.getY());
        getRight().add(center.getX()+(width / 2),center.getY());
    }

    public int getSpeedX(){
        return speedX;
    }

    public int getSpeedY(){
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
