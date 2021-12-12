/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package BrickDestroy.Gameplay_Model;

import BrickDestroy.Gameplay_Model.Ball.Ball;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;


public class Player implements Playable {


    /**Defines the color of the border for the player.
     *
     */
    private static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    /**Defines the color for the player.
     *
     */
    private static final Color INNER_COLOR = Color.GREEN;

    private static final double BORDER_WIDTH=1.0F;

    /**Defines the amount of the player to move when updating position
     *
     */
    private static final int DEF_MOVE_AMOUNT = 5;

    /**Shape object that defines the graphical state of the player
     *
     */
    private Rectangle playerFace;
    /**The current position of the player (it uses midpoint as the reference).
     *
     */
    private Point2D playerMidpoint;
    /**The amount to update the player position.
     *
     */
    private int moveAmount;
    /**The left most boundary of the player (relative to midpoint of the player).
     *
     */
    private double min;

    /**The right most boundary of the player (relative to midpoint of the player).
     *
     */
    private double max;


    /**Initialises the player object at the corresponding location and size.
     * @param playerMidpoint the midpoint of the player
     * @param width the width of the player
     * @param height the height of the player
     * @param container the pane where the game is drawn
     */
    public Player(Point2D playerMidpoint, double width, double height, Pane container) {
        this.playerMidpoint = playerMidpoint;
        moveAmount = 0;
        playerFace = makeRectangle(width, height);
        min =  width/2;
        max = container.getPrefWidth() - width/2;

        initialisePlayerFace(playerFace);
    }


    /**Detects collision between player and ball.
     * <p>
     * Since the game only moves the ball after collision is detected, the ball may be stucked inside the player.
     * The method attempts to correct location of the ball to the actual location outside the player if collision is detected.
     * <p>
     * The edges of the player can be used for tactical playing since the edges will deviate the path of the ball by an amount.
     * @param ball ball in question
     * @return whether collision id detected or not
     */
    public boolean ballPlayerCollision(Ball ball){
        if (playerFace.contains(ball.getPosition())) {
            ball.reverseY();
            correctBallLocation(ball);
            ball.calculateDeviation(playerFace.getX(), getPlayerFace().getWidth());

            return true;
        }
        return false;
    }

    /**Corrects the location of the ball to the correct location/
     * @param ball ball in question
     */
    private void correctBallLocation(Ball ball){
        double yDifference = ball.getPosition().getY()-playerFace.getY();
        Point2D correctedPoint2D = new Point2D(ball.getPosition().getX(), playerFace.getY()-yDifference);
        ball.moveTo(correctedPoint2D);
    }

    /**Updates the position of the player based on the amount it shoudl move.
     *
     */
    public void move(){
        double x = playerMidpoint.getX() + moveAmount;
        if(x < min || x > max)
            return;
        playerMidpoint =new Point2D(x, playerMidpoint.getY());
        playerFace.setX(playerMidpoint.getX() - playerFace.getWidth()/2);
        playerFace.setY(playerMidpoint.getY());
    }

    /**Allows the player to move left.
     * It is used to update the location of the player.
     *
     */
    public void moveLeft(){
        moveAmount = -DEF_MOVE_AMOUNT;
    }

    /**Allows the player to move right.
     * It is used to update the location of the player.
     *
     */
    public void moveRight(){
        moveAmount = DEF_MOVE_AMOUNT;
    }
    /**Stops the player from moving.
     * It is used to update the location of the player.
     *
     */
    public void stop(){
        moveAmount = 0;
    }

    /**Gets the Rectangle object of the player
     * @return the Rectangle object of the player
     */
    public Rectangle getPlayerFace(){
        return  playerFace;
    }

    /**Moves the player to the new location (midpoint is used as the reference)
     * @param p the new location of the player
     */
    public void moveTo(Point2D p){
        playerMidpoint = p;
        playerFace.setX(playerMidpoint.getX() - playerFace.getWidth()/2);
        playerFace.setY(playerMidpoint.getY());
    }

    /**Creates the Rectangle object with the corresponding width and height.
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @return the created rectangle object
     */
    private Rectangle makeRectangle(double width,double height){
        Point2D p = playerMidpoint.add(-(width / 2),0);
        return  new Rectangle(p.getX(),p.getY(),width,height);
    }

    /**Initialises the Rectangle object of the player with the corresponding border and color.
     * @param playerFace Rectangle object of the player
     */
    private void initialisePlayerFace(Rectangle playerFace){
        playerFace.setFill(INNER_COLOR);
        playerFace.setStroke(BORDER_COLOR);
        playerFace.setStrokeWidth(BORDER_WIDTH);
    }

}
