package BrickDestroy.JavaFX_StageController;

import BrickDestroy.JavaFX_View.BrickDestroyMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class JavaFXHomeMenuController {

    @FXML
    private void initialize(){
        
    }

    @FXML
    void startGame(ActionEvent event) {

    }

    @FXML
    void openInfoMenu(ActionEvent event) throws IOException {
        BrickDestroyMain.setNewScene("BrickDestroy_Info");
    }

    @FXML
    void exitGame(ActionEvent event) {

    }



}
