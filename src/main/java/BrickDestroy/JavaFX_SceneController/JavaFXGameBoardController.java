package BrickDestroy.JavaFX_SceneController;

import BrickDestroy.Gameplay_Model.Brick.Brick;
import BrickDestroy.Gameplay_Model.Manager.HighScoreManager;
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


/**Controls the game board where the game is taking place.
 * Player is allowed to pause, move Player, open debug dialog, open pause menu, restart an exit.
 * Once the player completes a level, a score board with the high scores is listed.
 * Once the player completes the game or loses all the ball, they are transferred to the end game to show their final score and a list of high scores.
 */
public class JavaFXGameBoardController {

    /**Main controller for handling interactions from View to the Model.
     *
     */
    private GameplayController gameplayController;
    /**For constant update of the ball and player and checks for the end conditions.
     *
     */
    private AnimationTimer timer;
    /**Flag for indicating whether the game is paused.
     *
     */
    private boolean isPaused;
    /**Flag for indicating whether the pasue menu is shown or not.
     *
     */
    private boolean pauseMenuShown;

    /**The root Pane
     *
     */
    @FXML
    private StackPane gameBoard;

    /**For handling gameplay
     *
     */
    @FXML
    private Pane gameBoardPane;

    /**For showing the pause menu and the buttons
     *
     */
    @FXML
    private StackPane pauseMenu;

    /**Message displayed during pause
     *
     */
    @FXML
    private Text pauseMessage;

    /**Displays the amount of ball left
     *
     */
    @FXML
    private Text displayBallLeft;

    /**Displays the player's current score
     *
     */
    @FXML
    private Text playerCurrentLevelScore;

    /**For showing the list of high scores after the player completed a game.
     *
     */
    @FXML
    private GridPane scorePane;

    /**Board to display the high score and other texts.
     *
     */
    @FXML
    private VBox highScoreBoard;

    /**Display the score that the player currently has when the high score board is shown.
     *
     */
    @FXML
    private Text displayCurrentScore;


    /**Initialises the controller
     *
     */
    @FXML
    void initialize(){
        gameplayController = new GameplayController(gameBoardPane,36,4,6/2);
        isPaused=true;
        pauseMenuShown=false;
        initialiseTimer();
        drawGameplay();
        initialiseFXML();
        updateNumberOfBallsText(gameplayController.getBallCount());
        updateScoresFromManager(gameplayController.getHighScoreManager());
        updateInLevelScoreText(gameplayController.getCurrentPlayerScore());

    }

    /**Initialises the FXML components and then request focus after it is done.
     *
     */
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

    /**Displays the bricks, player and ball on to the screen.
     *
     */
    private void drawGameplay(){
        gameBoardPane.getChildren().add(gameplayController.getPlayer().getPlayerFace());
        gameBoardPane.getChildren().add(gameplayController.getBall().getBallFace());
        for(Brick b : gameplayController.getCurrentLevel().getBricks()) {
            gameBoardPane.getChildren().add(b.getBrickFace());
            gameBoardPane.getChildren().add(b.getCrack().getCrackPath());
        }

    }

    /**Sets up what needs to be updated and checks the end conditions.
     *
     */
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

    /**Invoked when the player loses a ball.
     * It resets the location of the ball and player to their default location.
     */
    private void lostABall(){
        updateNumberOfBallsText(gameplayController.getBallCount());
        gameplayController.resetBallPlayer();
        pause();
    }
    /**Invoked when the player completes a level.
     * It removes the bricks from the screen and instead add the new bricks of the next level to the screen.
     */
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

    /**Removes the bricks of the current level from the screen.
     *
     */
    private void removeLevelBricksFromGameBoard(){
        for (Brick b:gameplayController.getCurrentLevel().getBricks()) {
            gameBoardPane.getChildren().remove(b.getBrickFace());
            gameBoardPane.getChildren().remove(b.getCrack().getCrackPath());
        }
    }

    /**Adds the bricks of the current level to the screen
     *
     */
    private void addLevelBricksToGameBoard(){
        for(Brick b : gameplayController.getCurrentLevel().getBricks()) {
            gameBoardPane.getChildren().add(b.getBrickFace());
            gameBoardPane.getChildren().add(b.getCrack().getCrackPath());

        }
    }

    /**It retrieves the list of high score and names from the high score manager and displays it onto the score board.
     * @param highScoreManager current high score manager
     */
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

    /**Response to key presses during gameplay.
     * The player can move player, open pause menu, pause and open debug dialog.
     * @param event keyboard press
     */
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

    /** Stops the player from moving once the player stops clicking the keyboard.
     * @param event keyboard key release
     */
    @FXML
    void onKeyReleased(KeyEvent event) {
        gameplayController.stopPlayer();
    }

    /** Continues the game
     * @param event mouse clicked
     */
    @FXML
    void continueGame(ActionEvent event) {
        unpausePauseMenu();
    }

    /**Exits the game to the main menu.
     * @param event mouse clicked
     * @throws IOException
     */
    @FXML
    void exitGame(ActionEvent event) throws IOException {
        BrickDestroyMain.setNewScene("BrickDestroy_Menu");
    }

    /**Restarts the game
     * @param event button clicked
     */
    @FXML
    void restartGame(ActionEvent event) {
        restart();
    }

    /**Unpauses the pause menu
     * @param event keyboard ESCAPE
     */
    @FXML
    void unpause(KeyEvent event) {
        if (event.getCode()== KeyCode.ESCAPE){
            unpausePauseMenu();
        }
    }

    /**Resumes the game
     * @param event keyboard SPACE
     */
    @FXML
    void pressSpaceToContinue(KeyEvent event) {
        if (event.getCode()==KeyCode.SPACE) {
            highScoreBoard.setVisible(false);
            gameBoardPane.setVisible(true);
            gameBoardPane.requestFocus();
        }
    }

    /**Updates the text for displaying the number of balls left.
     * @param numberOfBalls
     */
    private void updateNumberOfBallsText(int numberOfBalls){
        displayBallLeft.setText("Balls left: "+numberOfBalls);
    }


    /**Pauses the game
     *
     */
    private void pause(){
        timer.stop();
        isPaused=true;
        pauseMessage.setVisible(true);
    }

    /**Resumes the game
     *
     */
    private void resume(){
        timer.start();
        isPaused=false;
        pauseMessage.setVisible(false);
    }

    /**Restart and resets the game
     *
     */
    private void restart(){

        removeLevelBricksFromGameBoard();
        gameplayController.resetGame();
        addLevelBricksToGameBoard();
        pause();
        unpausePauseMenu();
        updateNumberOfBallsText(gameplayController.getBallCount());
        updateInLevelScoreText(gameplayController.getCurrentPlayerScore());

    }

    /**Updates the text that displays the score.
     * @param score player's current score
     */
    private void updateInLevelScoreText(int score){
        playerCurrentLevelScore.setText("SCORE: "+score);
    }

    /**Updates the text that display the score after completing a level
     * @param score player's current score
     */
    private void updateScoreBoardText(int score){
        displayCurrentScore.setText("YOUR CURRENT SCORE: "+score);
    }

    /**Invoked when the game is finished. The player is then transferred to the end game scene.
     *
     */
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

    /**Unpauses the pause menu.
     *
     */
    private void unpausePauseMenu(){
        pauseMenu.setVisible(false);
        pauseMenuShown=false;
        gameBoardPane.requestFocus();
    }

}
