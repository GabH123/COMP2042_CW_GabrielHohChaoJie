package BrickDestroy.JavaFX_SceneController;

import BrickDestroy.Gameplay_Model.Brick.Brick;
import BrickDestroy.Gameplay_Model.HighScoreManager;
import BrickDestroy.GameController.GameplayController;
import BrickDestroy.JavaFX_View.BrickDestroyMain;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    private Text displayBallLeft;

    @FXML
    private Text playerCurrentLevelScore;

    @FXML
    private GridPane scorePane;

    @FXML
    private VBox highScoreBoard;

    @FXML
    private Text displayCurrentScore;



    @FXML
    void initialize(){
        gameplayController = new GameplayController(gameBoardPane,30,3,6/2);
        isPaused=true;
        pauseMenuShown=false;
        initialiseTimer();
        drawGameplay();
        initialiseFXML();
        updateNumberOfBallsText(gameplayController.getBallCount());
        updateScoresFromManager(gameplayController.getHighScoreManager());
        updateInLevelScoreText(gameplayController.getCurrentPlayerScore());


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
                updateNumberOfBallsText(gameplayController.getBallCount());
                updateInLevelScoreText(gameplayController.getCurrentPlayerScore());
                if (gameplayController.isBallLost()){
                    if(gameplayController.getBallCount()==0){
                        gameFinished();
                    }
                    lostABall();
                } else if (gameplayController.numberOfRemainingBricks()<=0|| gameplayController.isDebugSkip()){
                    completedALevel();
                    if (!gameplayController.hasLevel()){
                        gameFinished();
                    }
                }
            }
        };
    }

    private void lostABall(){
        updateNumberOfBallsText(gameplayController.getBallCount());
        gameplayController.resetBallPlayer();
        pause();
    }

    private void completedALevel(){
        removeLevelBricksFromGameBoard();
        gameplayController.nextLevel();
        addLevelBricksToGameBoard();
        gameplayController.resetBallPlayer();
        pause();
        gameBoardPane.setVisible(false);
        highScoreBoard.setVisible(true);
        highScoreBoard.requestFocus();
        updateScoreBoardText(gameplayController.getCurrentPlayerScore());
        gameplayController.setDebugSkip(false);
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

    private void updateScoresFromManager(HighScoreManager highScoreManager) {
        Text rowName;
        Text rowScore;

        int i;
        for (i=0; (i< scorePane.getRowCount()-1)&&(i<highScoreManager.getHighScores().size()); i++) {
            rowName=(Text) scorePane.getChildren().get((scorePane.getRowCount()-1)+i);
            rowName.setText(highScoreManager.getHighScores().get(i).getName());

            rowScore=(Text) scorePane.getChildren().get(2*(scorePane.getRowCount()-1)+i);
            rowScore.setText(Integer.toString(highScoreManager.getHighScores().get(i).getScore()));
        }
    }

    @FXML
    void onKeyPressed(KeyEvent event) {
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
                    resume();
                    pauseMenu.setVisible(false);
                    pauseMessage.setVisible(true);
                    pauseMenuShown=false;
                }
                break;
            case F1:
                if (/*event.isAltDown()&&event.isShiftDown()*/true) {
                    pause();
                    FXMLLoader loadDebugDialog = new FXMLLoader(BrickDestroyMain.class.getClassLoader().getResource("BrickDestroy_DebugConsole.fxml"));

                    Dialog debugDialog = new Dialog();
                    ButtonType type = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
                    try {
                        debugDialog.setDialogPane(loadDebugDialog.load());
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    DebugDialogController debugDialogController = loadDebugDialog.getController();
                    debugDialogController.initialiseController(gameplayController);
                    debugDialog.getDialogPane().getButtonTypes().add(type);
                    debugDialog.showAndWait();
                }
                break;
            default:
                gameplayController.stopPlayer();
        }
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
        restart();
    }

    @FXML
    void unpause(KeyEvent event) {
        if (event.getCode()== KeyCode.ESCAPE){
            unpausePauseMenu();
        }
    }

    @FXML
    void pressSpaceToContinue(KeyEvent event) {
        if (event.getCode()==KeyCode.SPACE) {
            highScoreBoard.setVisible(false);
            gameBoardPane.setVisible(true);
            gameBoardPane.requestFocus();
        }
    }

    private void updateNumberOfBallsText(int numberOfBalls){
        displayBallLeft.setText("Balls left: "+numberOfBalls);
    }

    private void pause(){
        timer.stop();
        isPaused=true;
        pauseMessage.setVisible(true);
    }

    private void resume(){
        timer.start();
        isPaused=false;
        pauseMessage.setVisible(false);
    }

    private void restart(){

        removeLevelBricksFromGameBoard();
        gameplayController.resetGame();
        addLevelBricksToGameBoard();
        pause();
        unpausePauseMenu();
        updateNumberOfBallsText(gameplayController.getBallCount());
        updateInLevelScoreText(gameplayController.getCurrentPlayerScore());

    }

    private void updateInLevelScoreText(int score){
        playerCurrentLevelScore.setText("SCORE: "+score);
    }

    private void updateScoreBoardText(int score){
        displayCurrentScore.setText("YOUR CURRENT SCORE: "+score);
    }

    private void gameFinished(){
        FXMLLoader loadEndGameScoreBoard = new FXMLLoader(BrickDestroyMain.class.getClassLoader().getResource("BrickDestroy_GameEnd.fxml"));
        try {
            BrickDestroyMain.setSceneRoot(loadEndGameScoreBoard);
        }catch (IOException e){
            e.printStackTrace();
        }
        EndGameController endGameController = loadEndGameScoreBoard.getController();
        endGameController.initialiseController(gameplayController.getHighScoreManager(), gameplayController.getCurrentPlayerScore());
        endGameController.setupScorePane();

    }

    private void unpausePauseMenu(){
        pauseMenu.setVisible(false);
        pauseMenuShown=false;
        gameBoardPane.requestFocus();
    }

}
