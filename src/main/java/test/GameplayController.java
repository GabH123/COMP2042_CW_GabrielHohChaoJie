package test;

import javax.swing.*;
import java.awt.*;

public class GameplayController {
    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

    private Timer gameTimer;
    private Wall wall;

    public GameplayController() {
        wall = new Wall(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),30,3,6/2,new Point(300,430));

    }
}
