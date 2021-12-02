package BrickDestroy.JavaFX_StageController;

import BrickDestroy.JavaFX_View.BrickDestroyMain;
import BrickDestroy.View.DebugConsole;
import javafx.scene.Parent;

import java.io.IOException;

public class SceneManager {

    BrickDestroyMain main;
    public SceneManager(BrickDestroyMain main) {
        this.main=main;
    }

    public void changeScene(String scene) throws IOException {
        main.setNewScene(scene);
    }
}
