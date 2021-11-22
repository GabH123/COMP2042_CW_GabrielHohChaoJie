package test;

import java.awt.*;

public interface Playable {

    boolean impact(Ball b);
    void move();
    void moveLeft();
    void moveRight();
    void stop();
    void moveTo(Point p);
    void playerDrawInfo(Graphics2D g2d);
}
