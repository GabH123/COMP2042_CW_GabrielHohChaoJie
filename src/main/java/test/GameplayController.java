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
package test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;


/**
 * Wall is used to store the current logical state of the game in-game.
 *
 */
public class GameplayController {

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

    private Point startPoint;
    private int brickCount;
    private int ballCount;
    private boolean ballLost;

    public GameplayController(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos){

        this.startPoint = new Point(ballPos);


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


    public void move(){
        getPlayer().move();
        getBall().move();
    }

    public void findImpacts(){
        if(getPlayer().impact(getBall())){
            getBall().reverseY();
        }
        else if(impactWall()){
            /*for efficiency reverse is done into method impactWall
            * because for every brick program checks for horizontal and vertical impacts
            */
            brickCount--;
        }
        else if(impactBorder()) {
            getBall().reverseX();
        }
        else if(getBall().getPosition().getY() < area.getY()){
            getBall().reverseY();
        }
        else if(getBall().getPosition().getY() > area.getY() + area.getHeight()){
            ballCount--;
            ballLost = true;
        }
    }

    public void ballReset(){
        getPlayer().moveTo(startPoint);
        getBall().moveTo(startPoint);

        getBall().setSpeed(randomiseSpeedX(),randomiseSpeedY());
        ballLost = false;
    }

    public void wallReset(){
        getCurrentLevel().resetBricks();
        brickCount = getLevelMaker().getBrickCount();
        ballCount = 3;
    }

    public void nextLevel(){
        currentLevel = getLevelMaker().getThisLevel(currentLevelNumber++);
        this.brickCount = getLevelMaker().getBrickCount();
    }



    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }


    private boolean impactWall(){
        for(Brick b : getCurrentLevel().getBricks()){
            switch(findImpact(b, getBall())) {
                //Vertical Impact
                case UP_IMPACT:
                    getBall().reverseY();
                    return b.setImpact(getBall().down, Crack.UP);
                case DOWN_IMPACT:
                    getBall().reverseY();
                    return b.setImpact(getBall().up,Crack.DOWN);

                //Horizontal Impact
                case LEFT_IMPACT:
                    getBall().reverseX();
                    return b.setImpact(getBall().right,Crack.RIGHT);
                case RIGHT_IMPACT:
                    getBall().reverseX();
                    return b.setImpact(getBall().left,Crack.LEFT);
            }
        }
        return false;
    }

    private int findImpact(Brick brick, Ball ball){
        if (brick.isBroken())
            return 0;
        int out = 0;
        if (brick.brickFace.contains(ball.right))
            out = LEFT_IMPACT;
        else if (brick.brickFace.contains(ball.left))
            out = RIGHT_IMPACT;
        else if (brick.brickFace.contains(ball.up))
            out = DOWN_IMPACT;
        else if (brick.brickFace.contains(ball.down))
            out = UP_IMPACT;
        return out;
    }

    private boolean impactBorder(){
        Point2D p = getBall().getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
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

    public boolean hasLevel(){
        return currentLevelNumber < LevelFactory.TOTAL_NUMBER_OF_LEVELS;
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
        return brickCount == 0;
    }

    public int getBrickCount(){
        return brickCount;
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

    public Playable getPlayer() {
        return player;
    }

    public LevelFactory getLevelMaker() {
        return levelMaker;
    }


    public Random getRnd() {
        return rnd;
    }
}
