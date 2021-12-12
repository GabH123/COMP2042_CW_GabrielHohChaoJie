package BrickDestroy.JavaFX_SceneController;

import BrickDestroy.JavaFX_View.BrickDestroyMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

/**Controller for handling the Info section in VIew
 *
 */
public class InfoMenuController {
    /**Goes back to the home menu.
     * @param event mouse clicked.
     * @throws IOException
     */
    @FXML
    void backToMenu(ActionEvent event) throws IOException {
        BrickDestroyMain.setNewScene("BrickDestroy_Menu");
    }
}
