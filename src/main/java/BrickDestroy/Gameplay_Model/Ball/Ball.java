package BrickDestroy.Gameplay_Model.Ball;


import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

import java.util.Random;

/**Abstract Ball class provides the abstraction of the ball object in the game.
 * It is represented with a Shape object to be drawn in View.
 */
abstract public class Ball {

    /**The width of the stroke for border
     */
    private static final double BORDER_STROKE_WIDTH=1.0;

    /**Maximum in percentage of the change of path of the ball.
     */
    private static final double MAX_ANGLE_DEVIATION_CHANGE =20;
    /**Speed of the ball.
     */
    private static final double BALL_SPEED = 6;

    /**Shape for the ball object.
     *
     */
    private Shape ballFace;

    /**Center point of the ball.
     *
     */
    private Point2D center;

    /**Up-most point of the ball.
     */
    private Point2D up;
    /**Bottom-most point of the ball.
     */
    private Point2D down;
    /**Left-most point of the ball.
     */
    private Point2D left;
    /**Right-most point of the ball.
     */
    private Point2D right;

    /**Border color of the ball.
     */
    private Color border;
    /**Color of the ball.
     */
    private Color inner;
    /**Horizontal speed of the ball.
     */
    private double speedX;
    /**Vertical speed of the ball.
     */
    private double speedY;

    /**Creates an instance of a Ball of the given location, radius A and B, with the given color for the inside and the border.
     * @param center Decides center of the Ball
     * @param radiusA The radius A for the Ball.
     * @param radiusB   The radius B for the Ball.
     * @param inner The color of the inside of the Ball.
     * @param border The color of the border of the Ball.
     */
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

    /** Makes the Shape object of the ball of the given center, radius A and B.
     * @param center Center of the Ball.
     * @param radiusA Radius A of the ball (Ball may be an Ellipse).
     * @param radiusB Radius B of the Ball
     * @return  Returns the created Shape object of the ball.
     */
    protected abstract Shape makeBall(Point2D center,int radiusA,int radiusB);

    /** Moves and updates the position of the ball based on the speed (X and Y).
     * The center and the corner points of the Ball is updated.
     */
    public void move(){
        center=center.add(speedX,speedY);
        ballFace.setTranslateX(ballFace.getTranslateX()+speedX);
        ballFace.setTranslateY(ballFace.getTranslateY()+speedY);

        setPoints(ballFace.getLayoutBounds().getWidth()/2,ballFace.getLayoutBounds().getHeight()/2);

    }

    /** Sets the angle of the ball randomly from -40 degrees to +40 degrees from the Y-axis.
     */
    public void setBallAngle(){
        Random rnd = new Random();
        double ballAngle = rnd.nextDouble(80)+50;

        adjustSpeedVector(BALL_SPEED,ballAngle);
    }

    public void adjustSpeedVector(double speed, double angle){
        setXSpeed(Math.cos(Math.toRadians(angle))*speed);
        setYSpeed(-1*Math.sin(Math.toRadians(angle))*speed);
    }


    /**Sets the horizontal axis speed of the ball.
     * @param s The new speed for the horizontal axis of the ball
     */
    public void setXSpeed(double s){
        speedX = s;
    }

    /**Sets the vertical axis speed of the ball.
     * @param s The new speed for the vertical axis of the ball
     */
    public void setYSpeed(double s){
        speedY = s;
    }

    /**Reverse the horizontal axis speed of the ball.
     */
    public void reverseX(){
        speedX *= -1;
    }

    /**Reverse the vertical axis speed of the ball.
     */
    public void reverseY(){
        speedY *= -1;
    }


    /**Calculates the deviation of the path of the ball when hitting the player. Then assigns the new speedX and speedY.
     * Instead of a simple reverseY to reflect the collision of the ball and the player, the
     * player can instead use the sides of the Player rectangle to control and deviate the direction of the ball.
     * <p>
     * The closer the impact of the ball is to the edges of the player, the bigger the deviation.
     * <p>
     * The closer the impact is to the center, the less the deviation.
     * <p>
     * The left half of the player sways the path of the ball in a counter-clockwise direction.
     * <p>
     * While the right half of the player sways the path of the ball in a clockwise direction.
     *
     * @param playerX Position of the player on the X-axis.
     * @param playerWidth Width of the player.
     */
    public void calculateDeviation(double playerX, double playerWidth){

        Point2D xAxis = new Point2D(1,0);
        double angleRelativeToXAndSpeedVector = xAxis.angle(getSpeedX(),getSpeedY());
        double actualDeviationPercentage = calculateAngleDeviationPercentage(playerX,playerWidth);

        if (actualDeviationPercentage>0){
            double newAngle = angleRelativeToXAndSpeedVector - angleRelativeToXAndSpeedVector*actualDeviationPercentage/100;

            adjustSpeedVector(BALL_SPEED,newAngle);
        }
        else {

            angleRelativeToXAndSpeedVector = 180 - angleRelativeToXAndSpeedVector;

            double newAngle = angleRelativeToXAndSpeedVector + angleRelativeToXAndSpeedVector*actualDeviationPercentage/100;

            newAngle = 180 - newAngle;

            adjustSpeedVector(BALL_SPEED,newAngle);
        }
    }

    /**Used in method calculateDeviation().
     * Calculates the percentage of how much of the angle does the original path of the ball deviate by (in %).
     * Maximum percentage on the edges of the player, and 0 percentage on the center of the player.
     * <p>
     * If the returning value is negative, the angle used to calculate the change will be the angle between the negative X-axis and the vector speed of the ball.
     * <p>
     * If the returning value is positive, the angle used to calculate the change will be the angle between the positive X-axis and the vector speed of the ball.
     * @param playerX Position of the player on the X-axis.
     * @param playerWidth Width of the player.
     * @return The percentage change of the angle of the ball.
     */
    private double calculateAngleDeviationPercentage(double playerX, double playerWidth){
        double midpointX = playerX + playerWidth/2;
        double distanceBetweenMidAndBallHit = ( getPosition().getX()-midpointX);
        return distanceBetweenMidAndBallHit * MAX_ANGLE_DEVIATION_CHANGE /(playerWidth/2);

    }


    /** Get the color of the border.
     * @return Returns the color of the border.
     */
    public Color getBorderColor(){
        return border;
    }

    /**Get the inside color of the ball.
     * @return Returns the colour of the inside of the Ball.
     */
    public Color getInnerColor(){
        return inner;
    }

    /**Get the center of the ball.
     * @return The center of the ball.
     */
    public Point2D getPosition(){
        return center;
    }

    /**Get the shape of the ball
     * @return The Shape object of the ball.
     */
    public Shape getBallFace(){
        return ballFace;
    }

    /**Moves the ball to a new location.
     * The corner points of the ball is moved too.
     * @param p The new center of the ball.
     */
    public void moveTo(Point2D p){
        center=p;
        ballFace.setTranslateX(0);
        ballFace.setTranslateY(0);

        ballFace.setTranslateX(p.getX()-ballFace.getBoundsInParent().getCenterX());
        ballFace.setTranslateY(p.getY()-ballFace.getBoundsInParent().getCenterY());
        setPoints(ballFace.getLayoutBounds().getWidth()/2,ballFace.getLayoutBounds().getHeight()/2);
    }

    /**Sets up the color and border of the ball.
     * @param ballFace The Shape object of the ball.
     */
    private void initialiseBall(Shape ballFace){
        ballFace.setFill(getInnerColor());
        ballFace.setStroke(getBorderColor());
        ballFace.setStrokeWidth(BORDER_STROKE_WIDTH);
    }

    /**Updates the corner points of the ball to their new location.
     * @param radiusA Radius A of the ball.
     * @param radiusB Radius B of the ball.
     */
    private void setPoints(double radiusA,double radiusB){

        up = center.add(0,-radiusB );
        down = center.add(0,radiusB );

        left = center.add(-radiusA ,0);
        right = center.add(radiusA,0);
    }

    /**Gets the speed of the ball in the horizontal axis.
     * @return The horizontal axis speed of the ball.
     */
    public double getSpeedX(){
        return speedX;
    }

    /**Gets the speed of the ball in the vertical axis.
     * @return The vertical axis speed of the ball.
     */
    public double getSpeedY(){
        return speedY;
    }

    /**Get the top most point of the ball.
     * @return The top most point of the ball.
     */
    public Point2D getUp() {
        return up;
    }

    /**Get the bottom most point of the ball.
     * @return The bottom most point of the ball.
     */
    public Point2D getDown() {
        return down;
    }

    /**Get the left most point of the ball.
     * @return The left most point of the ball.
     */
    public Point2D getLeft() {
        return left;
    }

    /**Get the right most point of the ball.
     * @return The right most point of the ball.
     */
    public Point2D getRight() {
        return right;
    }


}
