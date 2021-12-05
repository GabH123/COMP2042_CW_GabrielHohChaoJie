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
package BrickDestroy.BrickDestroy_Model_JavaFX;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


public class Player implements Playable {


    public static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    public static final Color INNER_COLOR = Color.GREEN;

    private static final int DEF_MOVE_AMOUNT = 5;

    private Rectangle playerFace;
    private Point2D ballPoint;
    private int moveAmount;
    private int min;
    private int max;


    public Player(Point2D ballPoint, int width, int height, Canvas container) {
        this.ballPoint = ballPoint;
        moveAmount = 0;
        playerFace = makeRectangle(width, height);
        min = (int) container.getWidth() + (width / 2);
        max = min + (int) container.getWidth() - width;

    }



    public boolean detectBallPlayerCollision(Ball ballJavaFX){
        if (playerFace.contains(ballJavaFX.getPosition()) && playerFace.contains(ballJavaFX.getDown())) {
            ballJavaFX.reverseY();
            return true;
        }
        return false;
    }

    public void move(){
        double x = ballPoint.getX() + moveAmount;
        if(x < min || x > max)
            return;
        ballPoint.add(x,ballPoint.getY());
        playerFace.setX(ballPoint.getX() - playerFace.getWidth()/2);
        playerFace.setY(ballPoint.getY());
    }

    public void moveLeft(){
        moveAmount = -DEF_MOVE_AMOUNT;
    }

    public void moveRight(){
        moveAmount = DEF_MOVE_AMOUNT;
    }

    public void stop(){
        moveAmount = 0;
    }

    public Shape getPlayerFace(){
        return  playerFace;
    }

    public void moveTo(Point2D p){
        ballPoint.add(p);
        playerFace.setX(ballPoint.getX() - playerFace.getWidth()/2);
        playerFace.setY(ballPoint.getY());
    }
    private Rectangle makeRectangle(int width,int height){
        Point2D p = new Point2D((ballPoint.getX() - (width / 2)),ballPoint.getY());
        return  new Rectangle(p.getX(),p.getY(),width,height);
    }

    public void playerDrawInfo(GraphicsContext gc){
        gc.setFill(INNER_COLOR);
        gc.fillRect(playerFace.getX(),playerFace.getY(),playerFace.getWidth(),playerFace.getHeight());

        gc.setStroke(BORDER_COLOR);
        gc.setLineWidth(1);
        gc.strokeRect(playerFace.getX(),playerFace.getY(),playerFace.getWidth(),playerFace.getHeight());
    }
}
