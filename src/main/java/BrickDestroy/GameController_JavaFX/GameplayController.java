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
package BrickDestroy.GameController_JavaFX;

import BrickDestroy.BrickDestroy_Model_JavaFX.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
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
    private Rectangle area;

    private Level currentLevel;
    private Ball ball;
    private Player player;

    private LevelFactory levelMaker;

    private int currentLevelNumber;
    private boolean pauseMenuShown;

    private Point startPoint;
    private int ballCount;
    private boolean ballLost;


    public GameplayController(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos){

        this.startPoint = new Point(ballPos);
        pauseMenuShown = false;

        currentLevelNumber = 0;
        levelMaker=new LevelFactory(drawArea,brickCount,lineCount,brickDimensionRatio);

        ballCount = 3;
        ballLost = false;

        rnd = new Random();

        makeBall(ballPos);


        getBall().setSpeed(randomiseSpeedX(),randomiseSpeedY());

        player = new Player((Point) ballPos.clone(),150,10, drawArea);

        area = drawArea;

        nextLevel();
    }


    public void updatePosition(){
        getPlayer().move();
        getBall().move();
    }

    public void detectBallCollision(){
        if(getPlayer().detectBallPlayerCollision(getBall())){
        }
        else if(getCurrentLevel().detectBallBrickCollision(getBall())){
        }
        else if(detectBallBorderCollision(getBall())) {
        }
        else if(detectBallRoofCollision(getBall())){
        }
        else if(detectBallLostCollision(getBall())){
            ballCount--;
            ballLost = true;
        }
    }

    public void resetBall(){
        getPlayer().moveTo(startPoint);
        getBall().moveTo(startPoint);

        getBall().setSpeed(randomiseSpeedX(),randomiseSpeedY());
        ballLost = false;
    }

    public void resetLevel(){
        getCurrentLevel().resetBricks();
        ballCount = 3;
    }

    public void nextLevel(){
        currentLevel = getLevelMaker().getThisLevel(currentLevelNumber++);
    }

    public void movePlayer(KeyEvent keyEvent){
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_A:
                getPlayer().moveLeft();
                break;
            case KeyEvent.VK_D:
                getPlayer().moveRight();
                break;
        }
    }

    public void stopPlayer(){
        getPlayer().stop();
    }

    public boolean hasLevel(){
        return currentLevelNumber < LevelFactory.TOTAL_NUMBER_OF_LEVELS;
    }


    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }

    private boolean detectBallRoofCollision(Ball ball){
        if (getBall().getPosition().getY() < area.getY()){
            ball.reverseY();
            return true;
        }
        return false;
    }

    private boolean detectBallBorderCollision(Ball ball){
        Point2D p = ball.getPosition();
        if ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth()))) {
            ball.reverseX();
            return true;
        }
        return false;
    }

    private boolean detectBallLostCollision(Ball ball){
        return getBall().getPosition().getY() > area.getY() + area.getHeight();
    }

    private int randomiseSpeedX(){
        int speedX;
        do{
            speedX = getRnd().nextInt(5) - 2;
        }while(speedX == 0);
        return speedX;
    }

    private int randomiseSpeedY(){
        int speedY;
        do{
            speedY = -getRnd().nextInt(3);
        }while(speedY == 0);
        return speedY;
    }



    public void drawPlayerShape(Graphics2D g2d){
        getPlayer().playerDrawInfo(g2d);
    }

    public void drawBallShape(Graphics2D g2d){
        getBall().ballDrawInfo(g2d);
    }


    public void setBallXSpeed(int s){
        getBall().setXSpeed(s);
    }

    public void setBallYSpeed(int s){
        getBall().setYSpeed(s);
    }



    public void resetBallCount(){
        ballCount = 3;
    }

    public boolean ballEnd(){
        return ballCount == 0;
    }

    public boolean isDone(){
        return getCurrentLevel().getNumberOfBricksLeft() == 0;
    }

    public int numberOfRemainingBricks(){
        return getCurrentLevel().getNumberOfBricksLeft();
    }

    public int getBallCount(){
        return ballCount;
    }

    public boolean isBallLost(){
        return ballLost;
    }

    public boolean isPauseMenuShown() {
        return pauseMenuShown;
    }

    public void changeShowPauseMenu() {
        this.pauseMenuShown = !this.pauseMenuShown;
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

    public LevelFactory getLevelMaker() {
        return levelMaker;
    }

    public Random getRnd() {
        return rnd;
    }
}
