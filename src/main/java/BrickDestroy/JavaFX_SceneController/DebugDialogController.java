package BrickDestroy.JavaFX_SceneController;

import BrickDestroy.GameController.GameplayController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

/**Controller for controlling debug dialog.
 * The debug panel can be used to skip, reset ball amount, adjust horizontal and vertical speed of the ball.
 */
public class DebugDialogController {

    /**Main controller for controlling the interaction between the objects in the model.
     *
     */
    GameplayController gameplayController;

    /**Slider for setting the horizontal speed of the ball.
     *
     */
    @FXML
    private Slider setXSpeed;

    /**Slider for setting the vertical speed of the ball.
     *
     */
    @FXML
    private Slider setYSpeed;

    /**Intialised the controller with the corresponding GameplayController
     * @param gameplayController main gameplay controller
     */
    public void initialiseController(GameplayController gameplayController){
        this.gameplayController=gameplayController;
        setXSpeed.setValue(gameplayController.getBall().getSpeedX());
        setYSpeed.setValue(gameplayController.getBall().getSpeedY());
    }

    /**Reset the count of the ball to the default amount.
     * @param event mouse click
     */
    @FXML
    void resetBallCount(ActionEvent event) {
        gameplayController.resetBallCount();
    }

    /**Skips the level to the next level
     * @param event mouse click
     */
    @FXML
    void skipLevel(ActionEvent event) {
        gameplayController.setDebugSkip(true);
    }

    /**Sets the vertical speed of the ball when the slider is dragged
     * @param event mouse dragging
     */
    @FXML
    void OnDraggedSpeedY(MouseEvent event) {
        gameplayController.setBallYSpeed(setYSpeed.getValue());

    }
    /**Sets the horizontal speed of the ball when the slider is dragged
     * @param event mouse dragging
     */
    @FXML
    void onDraggedSpeedX(MouseEvent event) {
        gameplayController.setBallXSpeed(setXSpeed.getValue());

    }
}
