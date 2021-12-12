package BrickDestroy.Gameplay_Model;

import BrickDestroy.Gameplay_Model.Brick.Brick;
import BrickDestroy.Gameplay_Model.Brick.CementBrick;
import BrickDestroy.Gameplay_Model.Brick.ClayBrick;
import BrickDestroy.Gameplay_Model.Brick.SteelBrick;
import BrickDestroy.Gameplay_Model.LevelFactory.Level;
import BrickDestroy.Gameplay_Model.LevelFactory.LevelFactory;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LevelFactoryTest {
    LevelFactory levelFactory = new LevelFactory(new Pane(), 30, 3, 3);

    @Test
    void getThisLevel1() {
        Level level;
        level = levelFactory.getThisLevel(0);
        for (Brick b : level.getBricks())
            assertTrue(b instanceof ClayBrick);

    }

    @Test
    void getThisLevel2() {
        Level level;
        level = levelFactory.getThisLevel(1);
        Brick[] brick = level.getBricks();
        int i;
        for (i = 0; i < 30; i++) {
            int indexX = i % 10;
            int indexY = i / 10;
            if (indexY % 2 == 0) {
                if (indexX % 2 == 0)
                    assertTrue(brick[i] instanceof CementBrick);
                else
                    assertTrue(brick[i] instanceof ClayBrick);
            } else {
                if (indexX > 4 && indexX <= 6)
                    assertTrue(brick[i] instanceof CementBrick);
                else
                    assertTrue(brick[i] instanceof ClayBrick);
            }

        }
        for (; i < brick.length; i++)
            assertTrue(brick[i] instanceof CementBrick);
    }

    @Test
    void getThisLevel3() {
        Level level;
        level = levelFactory.getThisLevel(4);
        Brick[] brick = level.getBricks();
        int i;
        for (i = 0; i < 30; i++) {
            int indexX = i % 10;
            int indexY = i / 10;
            if (indexY % 2 == 0) {
                if (indexX % 2 == 0)
                    assertTrue(brick[i] instanceof SteelBrick);
                else
                    assertTrue(brick[i] instanceof ClayBrick);
            } else {
                if (indexX > 4 && indexX <= 6)
                    assertTrue(brick[i] instanceof SteelBrick);
                else
                    assertTrue(brick[i] instanceof ClayBrick);
            }

        }
        for (; i < brick.length; i++)
            assertTrue(brick[i] instanceof SteelBrick);
    }

    @Test
    void getThisLevel4() {
        Level level;
        level = levelFactory.getThisLevel(8);
        for (Brick b : level.getBricks())
            assertTrue(b instanceof SteelBrick);

    }
}