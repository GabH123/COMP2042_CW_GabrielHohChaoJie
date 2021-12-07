package BrickDestroy.JavaFX_StageController;

import BrickDestroy.BrickDestroy_Model_JavaFX.Brick;
import BrickDestroy.GameController_JavaFX.GameplayController;
import BrickDestroy.JavaFX_View.BrickDestroyMain;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;


public class JavaFXGameBoardController {
    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

    private GameplayController gameplayController;
    private AnimationTimer timer;
    private boolean isPaused;
    private boolean pauseMenuShown;

    @FXML
    private StackPane gameBoard;

    @FXML
    private Pane gameBoardPane;

    @FXML
    private StackPane pauseMenu;

    @FXML
    private Text pauseMessage;



    @FXML
    private void initialize(){
        gameplayController = new GameplayController(gameBoardPane,30,4,6/2,new Point2D(gameBoardPane.getPrefWidth()/2,gameBoardPane.getPrefHeight()-50));
        isPaused=true;
        pauseMenuShown=false;
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
        for(Brick b : gameplayController.getCurrentLevel().getBricks()) {
            gameBoardPane.getChildren().add(b.getBrickFace());
            gameBoardPane.getChildren().add(b.getCrack().getCrackPath());
        }

    }

    private void initialiseTimer(){
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                gameplayController.updatePosition();
                gameplayController.detectBallCollision();
                if (gameplayController.isBallLost()){
                    lostABall();
                } else if (gameplayController.numberOfRemainingBricks()<=0){
                    completedALevel();
                }
            }
        };
    }

    private void lostABall(){
        gameplayController.resetBall();
        pause();
    }

    private void completedALevel(){
        removeLevelBricksFromGameBoard();
        gameplayController.nextLevel();
        addLevelBricksToGameBoard();
        gameplayController.resetBall();
        pause();
    }

    private void removeLevelBricksFromGameBoard(){
        for (Brick b:gameplayController.getCurrentLevel().getBricks()) {
            gameBoardPane.getChildren().remove(b.getBrickFace());
            gameBoardPane.getChildren().remove(b.getCrack().getCrackPath());
        }
    }
    private void addLevelBricksToGameBoard(){
        for(Brick b : gameplayController.getCurrentLevel().getBricks()) {
            gameBoardPane.getChildren().add(b.getBrickFace());
            gameBoardPane.getChildren().add(b.getCrack().getCrackPath());

        }
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
                    if (!isPaused) {
                        pause();
                    } else if (isPaused) {

                        resume();

                    }
                    break;
            case ESCAPE:
                if (!pauseMenuShown){
                    pause();
                    pauseMenuShown = true;
                    pauseMenu.setVisible(true);
                    pauseMenu.requestFocus();
                } else if (pauseMenuShown){
                    pauseMenu.setVisible(false);
                    pauseMessage.setVisible(true);
                    pauseMenuShown=false;
                }

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

    @FXML
    void continueGame(ActionEvent event) {
        unpausePauseMenu();
    }

    @FXML
    void exitGame(ActionEvent event) throws IOException {
        BrickDestroyMain.setNewScene("BrickDestroy_Menu");
    }

    @FXML
    void restartGame(ActionEvent event) {
        removeLevelBricksFromGameBoard();
        gameplayController.resetGame();
        addLevelBricksToGameBoard();
        pause();
        unpausePauseMenu();
    }

    @FXML
    void unpause(KeyEvent event) {
        if (event.getCode()== KeyCode.ESCAPE){
            unpausePauseMenu();
        }
    }

    void pause(){
        timer.stop();
        isPaused=true;
        pauseMessage.setVisible(true);
    }

    void resume(){
        timer.start();
        isPaused=false;
        pauseMessage.setVisible(false);
    }

    void unpausePauseMenu(){
        pauseMenu.setVisible(false);
        pauseMenuShown=false;
        gameBoardPane.requestFocus();
    }

}
