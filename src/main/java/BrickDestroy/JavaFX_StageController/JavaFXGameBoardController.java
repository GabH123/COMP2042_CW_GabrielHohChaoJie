package BrickDestroy.JavaFX_StageController;

import BrickDestroy.GameController_JavaFX.GameplayController;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;


public class JavaFXGameBoardController {
    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

    private GameplayController gameplayController;

    @FXML
    private Group gameBoardWall;
    @FXML
    private StackPane gameBoard;
    @FXML
    private Pane gameBoardPane;


    @FXML
    private void initialize(){
        System.out.println(" "+gameBoard.getPrefHeight());
        gameplayController = new GameplayController(gameBoard,30,3,6/2,new Point2D(gameBoard.getPrefWidth(),gameBoard.getPrefHeight()));
        gameplayController.getPlayer().print();
        drawGameplay();
    }

    private void drawGameplay(){
        //Rectangle test = new Rectangle(0,100,100,100);
        //gameBoardWall.getChildren().add(test);
        //gameplayController.getPlayer().playerFace.setX(gameplayController.getPlayer().playerFace.getX()+100);
        gameBoardWall.getChildren().add(gameplayController.getPlayer().getPlayerFace());
        gameBoardWall.getChildren().add(gameplayController.getBall().getBallFace());
    }
}
