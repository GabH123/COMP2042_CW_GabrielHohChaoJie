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
import javafx.scene.shape.Shape;


public class Player implements Playable {


    public static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    public static final Color INNER_COLOR = Color.GREEN;

    private static final int DEF_MOVE_AMOUNT = 5;

    private Rectangle playerFace;
    private Point2D ballPoint;
    private int moveAmount;
    private double min;
    private double max;


    public Player(Point2D ballPoint, double width, double height, Pane container) {

        this.ballPoint = ballPoint;
        moveAmount = 0;
        playerFace = makeRectangle(width, height);
        min =  width/2;
        max = container.getPrefWidth() - width/2;

        initialisePlayerFace(playerFace);
    }



    public boolean ballPlayerCollision(Ball ball){
        if (playerFace.contains(ball.getPosition())) {
            ball.reverseY();
            return true;
        }
        return false;
    }

    public void move(){
        double x = ballPoint.getX() + moveAmount;
        if(x < min || x > max)
            return;
        ballPoint=new Point2D(x,ballPoint.getY());
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

    public Rectangle getPlayerFace(){
        return  playerFace;
    }

    public void moveTo(Point2D p){
        ballPoint = p;
        playerFace.setX(ballPoint.getX() - playerFace.getWidth()/2);
        playerFace.setY(ballPoint.getY());
    }

    private Rectangle makeRectangle(double width,double height){
        Point2D p = ballPoint.add(-(width / 2),0);
        return  new Rectangle(p.getX(),p.getY(),width,height);
    }

    private void initialisePlayerFace(Rectangle playerFace){
        playerFace.setFill(INNER_COLOR);
        playerFace.setStroke(BORDER_COLOR);
        playerFace.setStrokeWidth(1.0f);
    }

}
