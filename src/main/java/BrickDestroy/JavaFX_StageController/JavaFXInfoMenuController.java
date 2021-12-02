package BrickDestroy.JavaFX_StageController;

import BrickDestroy.JavaFX_View.BrickDestroyMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class JavaFXInfoMenuController {
    @FXML
    void backToMenu(ActionEvent event) throws IOException {
        BrickDestroyMain.setNewScene("BrickDestroy_Menu");
    }
}
