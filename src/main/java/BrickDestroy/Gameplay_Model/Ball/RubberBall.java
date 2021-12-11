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
package BrickDestroy.Gameplay_Model.Ball;

import javafx.geometry.Point2D;
import javafx.scene.paint.*;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;


/**RubberBall realises Ball as if it was made with rubber.
 */
public class RubberBall extends Ball {


    /**Defines the radius of the ball.
     */
    private static final int DEF_RADIUS = 5;
    /**Defines the color of the ball.
     */
    static final Color DEF_INNER_COLOR = new Color(1.0, 220.0/256, 89.0/256,1);
    /**Defines the color of the border of the ball.
     */
    private static final Color DEF_BORDER_COLOR = DEF_INNER_COLOR.darker().darker();


    /**Instantiates the ball with the given center.
     * @param center Center of the ball.
     */
    public RubberBall(Point2D center){
        super(center,DEF_RADIUS,DEF_RADIUS,DEF_INNER_COLOR,DEF_BORDER_COLOR);
    }


    /**Creates an Ellipse as the shape of the ball.
     * @param center  Center of the Ball.
     * @param radiusA Radius A of the ball
     * @param radiusB Radius B of the Ball
     * @return
     */
    @Override
    protected Shape makeBall(Point2D center, int radiusA, int radiusB) {

        double x = center.getX() - (radiusA / 2);
        double y = center.getY() - (radiusB / 2);

        return new Ellipse(x,y,radiusA,radiusB);
    }
}
