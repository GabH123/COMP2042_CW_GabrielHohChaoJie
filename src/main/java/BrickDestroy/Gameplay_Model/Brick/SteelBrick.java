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
package BrickDestroy.Gameplay_Model.Brick;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


import java.util.Random;


/**SteelBrick extends Brick and indicates a brick that is made of steel.
 * Even though it only has 1 hit point, it can be only damage based on a 40% probability roll in every hit.
 * <p>
 * This makes it the most durable brick in the game, with the average hit needed to brake the brick being 2.5 hits.
 */
public class SteelBrick extends Brick {
    /**Defines the color for the steel brick.
     *
     */
    private static final Color DEF_INNER = new Color(204.0/256, 204.0/256, 202.0/256,1);
    /**Defines the border of the color for the steel brick.
     *
     */
    private static final Color DEF_BORDER = Color.BLACK;
    /**Defines the max hit points for the steel brick.
     *
     */
    private static final int STEEL_STRENGTH = 1;

    /**Defines the probability of being damaged in every ball hit.
     *
     */
    private static final double STEEL_PROBABILITY = 0.4;
    /**Defines the score worth of the steel brick.
     *
     */
    private static final int STEEL_BRICK_SCORE_WORTH = 90;

    private final Random rnd;

    /**Initialises SteelBrick with the given location and size.
     * @param point upper-left location of the SteelBrick object
     * @param size size of the SteelBrick
     */
    public SteelBrick(Point2D point, Dimension2D size){
        super(point,size,DEF_BORDER,DEF_INNER,STEEL_STRENGTH);
        rnd = new Random();
        initialiseBrick(DEF_INNER,DEF_BORDER);
    }

    /**Creates the Shape object for SteelBrick.
     * @param pos  upper-left position of the brick
     * @param size size of the brick
     * @return Rectangle shape object which represents the actual steel brick
     */
    @Override
    protected Shape makeBrickFace(Point2D pos, Dimension2D size) {
        return new Rectangle(pos.getX(),pos.getY(),size.getWidth(),size.getHeight());
    }


    /**Invoked when collision between ball and the brick is detected.
     * <p>
     * It has 40% to be broken every time it gets hit by the ball.
     * @param point the point where the ball and brick collided
     * @param dir   the part of the brick where it collided (up, down, left or right)
     * @return score worth of the brick (as indicator of a hit)
     */
    public  int collidedWithBall(Point2D point , int dir){
        if(super.isBroken())
            return 0;
        impact();
        return  super.isBroken()?STEEL_BRICK_SCORE_WORTH:0;
    }

    /**Rolls the 40% probability die.
     * If the roll is successful, only then the brick is damaged,and since it has only 1 hit point, it will be brpken.
     */
    protected void impact(){

        if((rnd.nextDouble()) < STEEL_PROBABILITY){

            super.impact();
        }
    }

}
