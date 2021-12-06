package BrickDestroy.JavaFX_StageController;

import BrickDestroy.BrickDestroy_Model_JavaFX.Brick;
import BrickDestroy.GameController_JavaFX.GameplayController;
import BrickDestroy.JavaFX_View.BrickDestroyMain;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;


public class JavaFXGameBoardController {
    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

    private GameplayController gameplayController;
    private AnimationTimer timer;
    private boolean isPaused;

    @FXML
    private StackPane gameBoard;
    @FXML
    private Pane gameBoardPane;


    @FXML
    private void initialize(){
        gameplayController = new GameplayController(gameBoardPane,30,4,6/2,new Point2D(gameBoardPane.getPrefWidth()/2,gameBoardPane.getPrefHeight()-50));
        isPaused=true;
        initialiseTimer();
        drawGameplay();
        initialiseFXML();
    }
    private void initialiseFXML(){
        gameBoardPane.requestFocus();
        gameBoard.requestFocus();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameBoardPane.requestFocus();
            }
        });
    }

    private void drawGameplay(){
        gameBoardPane.getChildren().add(gameplayController.getPlayer().getPlayerFace());
        gameBoardPane.getChildren().add(gameplayController.getBall().getBallFace());
        for(Brick b : gameplayController.getCurrentLevel().getBricks())
            gameBoardPane.getChildren().add(b.getBrickFace());
    }

    private void initialiseTimer(){
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                gameplayController.updatePosition();
                gameplayController.detectBallCollision();
                if (gameplayController.isBallLost()){
                    lostABall();
                } else if (gameplayController.getCurrentLevel().getNumberOfBricksLeft()<=0){
                    completedALevel();
                }
            }
        };
    }

    private void lostABall(){
        gameplayController.resetBall();
        isPaused=true;
        timer.stop();
    }

    private void completedALevel(){
        removeLevelBricksFromGameBoard();
        gameplayController.nextLevel();
        addLevelBricksToGameBoard();
        gameplayController.resetBall();
        isPaused=true;
        timer.stop();
    }

    private void removeLevelBricksFromGameBoard(){
        for (Brick b:gameplayController.getCurrentLevel().getBricks())
            gameBoardPane.getChildren().remove(b.getBrickFace());
    }
    private void addLevelBricksToGameBoard(){
        for(Brick b : gameplayController.getCurrentLevel().getBricks())
            gameBoardPane.getChildren().add(b.getBrickFace());
    }
    @FXML
    void onKeyPressed(KeyEvent event) {
        //System.out.println("TEST "+event.getCode());
        switch (event.getCode()){
            case A:
            case D:
                gameplayController.movePlayer(event);
                break;
            case SPACE:
                 System.out.println("SPACE PRESSED ");
                    if (!isPaused) {
                        System.out.println("Stop ");
                        timer.stop();
                        isPaused = true;
                    } else if (isPaused) {
                        System.out.println("Start ");

                        timer.start();
                        isPaused = false;
                    }
                    break;

            default:
                gameplayController.stopPlayer();
        }
    }

    @FXML
    void onKeyTyped(KeyEvent event) {
    }

    @FXML
    void onKeyReleased(KeyEvent event) {
        gameplayController.stopPlayer();
    }
}
