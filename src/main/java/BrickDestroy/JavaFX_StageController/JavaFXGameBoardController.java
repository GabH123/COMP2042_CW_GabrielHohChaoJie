package BrickDestroy.JavaFX_StageController;

import BrickDestroy.GameController.GameplayController;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;

import java.awt.*;

public class JavaFXGameBoardController {
    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

    private GameplayController gameplayController;


    @FXML
    private Canvas gameBoardWall;

    @FXML
    private void initialize(){
        gameplayController = new GameplayController(new Rectangle(0,0,(int)gameBoardWall.getWidth(),(int)gameBoardWall.getHeight()),30,3,6/2,new Point(300,430));
    }

    private void drawGameplay(){

    }
}
