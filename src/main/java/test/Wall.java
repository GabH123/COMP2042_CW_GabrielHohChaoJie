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
 * Wall is used to store the current logical state of the game in-game and level.
 * Note: Level making should be in a separate class called Level.
 */
public class Wall {

    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;


    private static final int LEVELS_COUNT = 4;

    private static final int CLAY = 1;
    private static final int STEEL = 2;
    private static final int CEMENT = 3;

    private Random rnd;
    private Rectangle area;

    private Brick[] bricks;
    private Ball ball;
    private Playable player;

    private Brick[][] levels;
    private int level;

    private Point startPoint;
    private int brickCount;
    private int ballCount;
    private boolean ballLost;

    public Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos){

        this.startPoint = new Point(ballPos);

        levels = makeLevels(drawArea,brickCount,lineCount,brickDimensionRatio);
        level = 0;

        ballCount = 3;
        ballLost = false;

        rnd = new Random();

        makeBall(ballPos);
        int speedX,speedY;
        do{
            speedX = rnd.nextInt(5) - 2;
        }while(speedX == 0);
        do{
            speedY = -rnd.nextInt(3);
        }while(speedY == 0);

        getBall().setSpeed(speedX,speedY);

        player = new Player((Point) ballPos.clone(),150,10, drawArea);

        area = drawArea;


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
        int speedX,speedY;
        do{
            speedX = rnd.nextInt(5) - 2;
        }while(speedX == 0);
        do{
            speedY = -rnd.nextInt(3);
        }while(speedY == 0);

        getBall().setSpeed(speedX,speedY);
        ballLost = false;
    }

    public void wallReset(){
        for(Brick b : getBricks())
            b.repair();
        brickCount = getBricks().length;
        ballCount = 3;
    }

    public void nextLevel(){
        bricks = levels[level++];
        this.brickCount = getBricks().length;
    }




    //Makes each of the bricks
    private Brick[] makeSingleTypeLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int type){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */

        //BrickFactory?
        brickCnt = roundBrickCnt(brickCnt,lineCnt);

        int brickOnLine = brickCnt / lineCnt;

        brickCnt += lineCnt / 2;

        Dimension brickSize = setBrickDimension(drawArea,brickOnLine,brickSizeRatio);

        return setBrickLocation(brickCnt,lineCnt,brickOnLine,brickSize,type);

    }

    private Brick[] makeChessboardLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt=roundBrickCnt(brickCnt,lineCnt);

        //First, decide dimensions of the brick
        int brickOnLine = brickCnt / lineCnt;

        int centerLeft = brickOnLine / 2 - 1;
        int centerRight = brickOnLine / 2 + 1;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2; //??

        Brick[] tmp  = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);

        //Second, set the location of each brick
        Point p = new Point();
        int i;

        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;

            int posX = i % brickOnLine;

            double x = posX * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));

            double y = (line) * brickHgt;

            p.setLocation(x,y);

            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
            tmp[i] = b ?  makeBrick(p,brickSize,typeA) : makeBrick(p,brickSize,typeB);
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,typeA);
        }
        return tmp;
    }

    private Brick [] setBrickLocation(int brickCnt, int lineCnt, int brickOnLine,Dimension brickSize, int type){
        Brick [] bricks = new Brick [brickCnt];
        Point p = new Point();

        int brickNo;
        for(brickNo = 0; brickNo < bricks.length; brickNo++){
            int line = brickNo / brickOnLine;
            if(line == lineCnt)
                break;
            double x = (brickNo % brickOnLine) * brickSize.getWidth();
            x =(line % 2 == 0) ? x : (x - (brickSize.getWidth() / 2));
            double y = (line) * brickSize.getHeight();
            p.setLocation(x,y);
            bricks[brickNo] = makeBrick(p,brickSize,type);
            //System.out.println("Brick: "+i+" X: "+x+" Y: "+y);
        }

        for(double y = brickSize.getHeight();brickNo < bricks.length;brickNo++, y += 2*brickSize.getHeight()){
            double x = (brickOnLine * brickSize.getWidth()) - (brickSize.getWidth() / 2);
            p.setLocation(x,y);
            bricks[brickNo] = new ClayBrick(p,brickSize);
        }
        return bricks;
    }

    private Dimension setBrickDimension (Rectangle drawArea, int brickOnLine, double brickSizeRatio){
        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;
        return new Dimension((int) brickLen,(int) brickHgt);
    }

    private int roundBrickCnt (int brickCnt, int lineCnt){
        return brickCnt -= brickCnt % lineCnt;
    }

    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }

    private Brick[][] makeLevels(Rectangle drawArea,int brickCount,int lineCount,double brickDimensionRatio){
        Brick[][] tmp = new Brick[LEVELS_COUNT][];
        tmp[0] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY);
        tmp[1] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,CEMENT);
        tmp[2] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,STEEL);
        tmp[3] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL,CEMENT);
        return tmp;
    }

    private boolean impactWall(){
        for(Brick b : getBricks()){
            switch(findImpact(b, getBall())) {
                //Vertical Impact
                case UP_IMPACT:
                    getBall().reverseY();
                    return b.setImpact(getBall().down, Brick.Crack.UP);
                case DOWN_IMPACT:
                    getBall().reverseY();
                    return b.setImpact(getBall().up,Brick.Crack.DOWN);

                //Horizontal Impact
                case LEFT_IMPACT:
                    getBall().reverseX();
                    return b.setImpact(getBall().right,Brick.Crack.RIGHT);
                case RIGHT_IMPACT:
                    getBall().reverseX();
                    return b.setImpact(getBall().left,Brick.Crack.LEFT);
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

    private Brick makeBrick(Point point, Dimension size, int type){
        //Switch CASE for subclasses not allowed
        Brick out;
        switch(type){
            case CLAY:
                out = new ClayBrick(point,size);
                break;
            case STEEL:
                out = new SteelBrick(point,size);
                break;
            case CEMENT:
                out = new CementBrick(point, size);
                break;
            default:
                throw  new IllegalArgumentException(String.format("Unknown Type:%d\n",type));
        }
        return  out;
    }



    public void drawPlayerShape(Graphics2D g2d){
        getPlayer().playerDrawInfo(g2d);
    }

    public void drawBallShape(Graphics2D g2d){
        getBall().ballDrawInfo(g2d);
    }

    public void drawBrickShape(Graphics2D g2d){

    }

    public boolean hasLevel(){
        return level < levels.length;
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

    public Brick[] getBricks() {
        return bricks;
    }

    public Ball getBall() {
        return ball;
    }

    public Playable getPlayer() {
        return player;
    }


}
