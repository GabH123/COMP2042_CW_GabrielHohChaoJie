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
package BrickDestroy.GameController;

import BrickDestroy.Gameplay_Model.*;

import BrickDestroy.Gameplay_Model.Ball.Ball;
import BrickDestroy.Gameplay_Model.Ball.RubberBall;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.util.Random;


/**GameplayController is used to control the interactions between the objects in model.
 *It has the methods to decide how the objects in model behave based on other objects.
 */

public class GameplayController implements Controllable {
    /**The default number of balls during the start of the game.
     */
    private static final int BALL_DEFAULT_AMOUNT=3;

    /**Constant number to signal impact on the upper part of the brick.
     */
    public static final int UP_IMPACT = 100;
    /**Constant number to signal impact on the bottom part of the brick.
     */
    public static final int DOWN_IMPACT = 200;
    /**Constant number to signal impact on the left part of the brick.
     */
    public static final int LEFT_IMPACT = 300;
    /**Constant number to signal impact on the right part of the brick.
     */
    public static final int RIGHT_IMPACT = 400;

    private Random rnd;
    /**Main drawing area for the game.
     */
    private Pane area;

    /**The current level.
     */
    private Level currentLevel;
    /**The main ball inside game.
     */
    private Ball ball;
    /**The object which the player is controlling.
     */
    private Player player;
    /**To manage the highscores in the current game.
     */
    private HighScoreManager highScoreManager;
    /**Contains instructions on how to build a level for th current game.
     */
    private LevelFactory levelMaker;
    /**Tracks current number of the level.
     */
    private int currentLevelNumber;
    /**Tracks current score of the player.
     */
    private int currentPlayerScore;
    /**For debugging. To decide whether to skip or not.
     */
    private boolean debugSkip;
    /**Starting point of the player and ball.
     */
    private Point2D startPoint;
    /**Current amount of balls left before the player loses.
     */
    private int ballCount;
    /**Whether the player has lost the ball or not.
     */
    private boolean ballLost;


    /**Intialises the controller and the objects in the model for the game.
     * @param drawArea The pane that contains the objects for the game.
     * @param brickCount The amount of bricks allowed for every level.
     * @param lineCount The amount of lines which the bricks would fill.
     * @param brickDimensionRatio The ratio between the width and the height of the bricks.
     */
    public GameplayController(Pane drawArea, int brickCount, int lineCount, double brickDimensionRatio){
        Point2D ballPos = new Point2D(drawArea.getPrefWidth()/2,drawArea.getPrefHeight()-50);
        this.startPoint = new Point2D(ballPos.getX(),ballPos.getY());

        highScoreManager = new HighScoreManager("BrickDestroy_HighScore.bin");
        highScoreManager.loadFromFile();
        currentLevelNumber = 0;
        levelMaker=new LevelFactory(drawArea,brickCount,lineCount,brickDimensionRatio);
        currentPlayerScore=0;

        ballCount = 3;
        ballLost = false;

        rnd = new Random(11);

        makeBall(ballPos);

        getBall().setBallAngle();

        player = new Player(ballPos,150,10, drawArea);

        area = drawArea;
        debugSkip=false;
        nextLevel();
    }
    /**Updates position of the ball and player.
     */
    public void updatePosition(){
        getPlayer().move();
        getBall().move();
    }
    /**Detects collision  between the ball and the wall, player, border, rood and death zone.
     */
    public void detectBallCollision(){
        getPlayer().ballPlayerCollision(getBall());
        currentPlayerScore+=getCurrentLevel().ballBrickCollision(getBall());
        ballBorderCollision(getBall());
        ballRoofCollision(getBall());

         if(ballLostCollision(getBall())){
            ballCount--;
            ballLost = true;
        }
    }
    /**Resets the ball and player back to their default location and state.
     */
    public void resetBallPlayer(){
        getPlayer().moveTo(startPoint);
        getBall().moveTo(startPoint);

        getBall().setBallAngle();
        ballLost = false;
    }
    /**Updates and retrieves the next level.
     */
    public void nextLevel(){
        currentLevel = getLevelMaker().getThisLevel(currentLevelNumber++);
    }

    /**Moves the player based on inputs from the keyboard.
     * @param keyEvent
     */
    public void movePlayer(KeyEvent keyEvent){
        switch (keyEvent.getCode()) {
            case A:
                getPlayer().moveLeft();
                break;
            case D:
                getPlayer().moveRight();
                break;
        }
    }
    /**Stops the motion of the player.
     */
    public void stopPlayer(){
        getPlayer().stop();
    }
    /**Reset the state of the game to the initial conditions.
     */
    public void resetGame(){
        resetBallPlayer();
        resetBallCount();
        currentLevelNumber=0;
        nextLevel();
        resetPlayScore();
    }


    /**Creates a new RubberBall object for the controller.
     * @param ballPos center of the ball object
     */
    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }

    /**Detects collision of the roof of the pane and the ball.
     * @param ball the ball object being detected
     * @return collided or not.
     */
    private boolean ballRoofCollision(Ball ball){
        if (getBall().getPosition().getY() <= 0){
            ball.reverseY();
            return true;
        }
        return false;
    }

    /**Detects collision between border of the pane and ball.
     * @param ball the ball object being detected
     * @return collided or not
     */
    private boolean ballBorderCollision(Ball ball){
        if (ball.getLeft().getX() <= 0 ||(ball.getRight().getX() > (area.getLayoutBounds().getWidth()))) {
            ball.reverseX();

            return true;
        }
        return false;
    }

    /**Detects whether the ball is in the death zone or not.
     * @param ball the ball object being detected
     * @return is the ball in the zone or not
     */
    private boolean ballLostCollision(Ball ball){
        return ball.getPosition().getY() > area.getLayoutBounds().getHeight();
    }


    /**Changes the horizontal axis speed of the ball.
     * @param s the new horizontal axis speed of the ball
     */
    public void setBallXSpeed(double s){
        getBall().setXSpeed(s);
    }

    /**Changes the vertical axis speed of the ball.
     * @param s the new vertical axis speed of the ball
     */
    public void setBallYSpeed(double s){
        getBall().setYSpeed(s);
    }

    /** Checks whether there are any remaining levels
     * @return are there any remaining levels or not
     */
    public boolean hasLevel(){
        return currentLevelNumber <= LevelFactory.TOTAL_NUMBER_OF_LEVELS;
    }

    /**Reset the number of balls left to the default amount.
     *
     */
    public void resetBallCount(){
        ballCount = BALL_DEFAULT_AMOUNT;
    }

    /**Reset the score of the player to 0.
     *
     */
    public void resetPlayScore(){
        currentPlayerScore=0;
    }

    /**Get the amount of remaining unbroken bricks.
     * @return amount of unbroken bricks left
     */
    public int numberOfRemainingBricks(){
        return getCurrentLevel().getTotalBricksLeft();
    }

    /**Get the amount of balls left
     * @return amount of balls left
     */
    public int getBallCount(){
        return ballCount;
    }

    /**Checks whether the ball is indeed lost or not.
     * @return is the ball lost
     */
    public boolean isBallLost(){
        return ballLost;
    }

    /**Gets the Level object for the current level.
     * @return the Level object of the current level
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }

    /**Gets the ball object for the game
     * @return the ball object in the game
     */
    public Ball getBall() {
        return ball;
    }

    /**Get the player object for the game
     * @return the player object in the game
     */
    public Player getPlayer() {
        return player;
    }

    /**Gets the current HighScoreManager object with list of current highscores.
     * @return the current HighScoreManager
     */
    public HighScoreManager getHighScoreManager() {
        return highScoreManager;
    }

    /**Gets the current factory for making the levels
     * @return the current LevelFactory
     */
    public LevelFactory getLevelMaker() {
        return levelMaker;
    }

    /**Get the current score of the plaer
     * @return current score of the player
     */
    public int getCurrentPlayerScore() {
        return currentPlayerScore;
    }

    /**Checks the flag from the debug console whether to skip or not
     * @return to skip or not to skip
     */
    public boolean isDebugSkip() {
        return debugSkip;
    }

    /**Sets the flag from the debug console for skipping the current level
     * @param debugSkip skip or not
     */
    public void setDebugSkip(boolean debugSkip) {
        this.debugSkip = debugSkip;
    }
}
