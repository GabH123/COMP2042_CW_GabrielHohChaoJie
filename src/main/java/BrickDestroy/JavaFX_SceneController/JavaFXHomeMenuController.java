package BrickDestroy.JavaFX_SceneController;

import BrickDestroy.JavaFX_View.BrickDestroyMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

/**Controller for handling the home menu in VIew.
 *
 */
public class JavaFXHomeMenuController {

    /**Background image for the home menu.
     *
     */
    @FXML
    private ImageView mainMenuBackground;


    /**Intialises the home menu controller.
     *
     */
    @FXML
    private void initialize(){
        mainMenuBackground.requestFocus();
    }

    /**Starts the game
     * @param event mouse clicked
     * @throws IOException
     */
    @FXML
    void startGame(ActionEvent event) throws IOException{
        BrickDestroyMain.setNewScene("BrickDestroy_GameBoard");
    }

    /**Opens the info menu
     * @param event mouse clicked
     * @throws IOException
     */
    @FXML
    void openInfoMenu(ActionEvent event) throws IOException {
        BrickDestroyMain.setNewScene("BrickDestroy_Info");
    }

    /**Exits the game
     * @param event mouse clicked
     */
    @FXML
    void exitGame(ActionEvent event) {
        System.out.println("User clicked \'Exit\' !");
        System.exit(0);
    }



}
