package BrickDestroy.JavaFX_SceneController;

import BrickDestroy.GameController.GameplayController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

public class DebugDialogController {

    GameplayController gameplayController;

    @FXML
    private Slider setXSpeed;

    @FXML
    private Slider setYSpeed;

    public void initialiseController(GameplayController gameplayController){
        this.gameplayController=gameplayController;
        setXSpeed.setValue(gameplayController.getBall().getSpeedX());
        setYSpeed.setValue(gameplayController.getBall().getSpeedY());
    }

    @FXML
    private void initialize(){

    }
    @FXML
    void resetBallCount(ActionEvent event) {
        gameplayController.resetBallCount();
    }

    @FXML
    void skipLevel(ActionEvent event) {
        gameplayController.setDebugSkip(true);
    }

    @FXML
    void OnDraggedSpeedY(MouseEvent event) {
        gameplayController.setBallYSpeed(setYSpeed.getValue());

    }

    @FXML
    void onDraggedSpeedX(MouseEvent event) {
        gameplayController.setBallXSpeed(setXSpeed.getValue());

    }
}
