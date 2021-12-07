package BrickDestroy.JavaFX_StageController;

import BrickDestroy.BrickDestroy_Model_JavaFX.HighScoreManager;
import BrickDestroy.JavaFX_View.BrickDestroyMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class JavaFXHomeMenuController {

    @FXML
    private ImageView mainMenuBackground;


    @FXML
    private void initialize(){
        mainMenuBackground.requestFocus();
        //HighScoreManager test = new HighScoreManager();
    }
    @FXML
    void keyTest(KeyEvent event) {
        //System.out.println("TEST2");

    }
    @FXML
    void keyboardTest(KeyEvent event) {
        //System.out.println("TEST");
    }
    @FXML
    void startGame(ActionEvent event) throws IOException{
        BrickDestroyMain.setNewScene("BrickDestroy_GameBoard");
    }

    @FXML
    void openInfoMenu(ActionEvent event) throws IOException {
        BrickDestroyMain.setNewScene("BrickDestroy_Info");
    }

    @FXML
    void exitGame(ActionEvent event) {
        System.out.println("User clicked \'Exit\' !");
        System.exit(0);
    }



}
