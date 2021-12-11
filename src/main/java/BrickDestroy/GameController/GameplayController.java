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


/**
 * GameplayController is used to store the current logical state of the game in-game.
 *
 */

public class GameplayController implements Controllable {

    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;

    private Random rnd;
    private Pane area;

    private Level currentLevel;
    private Ball ball;
    private Player player;
    private HighScoreManager highScoreManager;

    private LevelFactory levelMaker;

    private int currentLevelNumber;
    private int currentPlayerScore;

    private boolean debugSkip;

    private Point2D startPoint;
    private int ballCount;
    private boolean ballLost;


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

    public void updatePosition(){
        getPlayer().move();
        getBall().move();
    }

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

    public void resetBall(){
        getPlayer().moveTo(startPoint);
        getBall().moveTo(startPoint);

        getBall().setBallAngle();
        ballLost = false;
    }

    public void nextLevel(){
        currentLevel = getLevelMaker().getThisLevel(currentLevelNumber++);
    }

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

    public void stopPlayer(){
        getPlayer().stop();
    }

    public boolean hasLevel(){
        return currentLevelNumber <= LevelFactory.TOTAL_NUMBER_OF_LEVELS;
    }

    public void resetGame(){
        resetBall();
        resetBallCount();
        currentLevelNumber=0;
        nextLevel();
        resetPlayScore();
    }


    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }

    private boolean ballRoofCollision(Ball ball){
        if (getBall().getPosition().getY() <= 0){
            ball.reverseY();
            return true;
        }
        return false;
    }

    private boolean ballBorderCollision(Ball ball){
        if (ball.getLeft().getX() <= 0 ||(ball.getRight().getX() > (area.getLayoutBounds().getWidth()))) {
            ball.reverseX();

            return true;
        }
        return false;
    }

    private boolean ballLostCollision(Ball ball){
        return ball.getPosition().getY() > area.getLayoutBounds().getHeight();
    }



    public void setBallXSpeed(double s){
        getBall().setXSpeed(s);
    }

    public void setBallYSpeed(double s){
        getBall().setYSpeed(s);
    }



    public void resetBallCount(){
        ballCount = 3;
    }

    public void resetPlayScore(){
        currentPlayerScore=0;
    }

    public int numberOfRemainingBricks(){
        return getCurrentLevel().getTotalBricksLeft();
    }

    public int getBallCount(){
        return ballCount;
    }

    public boolean isBallLost(){
        return ballLost;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public Ball getBall() {
        return ball;
    }

    public Player getPlayer() {
        return player;
    }

    public HighScoreManager getHighScoreManager() {
        return highScoreManager;
    }

    public LevelFactory getLevelMaker() {
        return levelMaker;
    }

    public Random getRnd() {
        return rnd;
    }

    public int getCurrentPlayerScore() {
        return currentPlayerScore;
    }

    public boolean isDebugSkip() {
        return debugSkip;
    }

    public void setDebugSkip(boolean debugSkip) {
        this.debugSkip = debugSkip;
    }
}
