package BrickDestroy.JavaFX_View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BrickDestroyMain extends Application {
    static private  Scene scene;
    @Override
    public void start(Stage primaryStage)  throws Exception{
        //System.out.println(getClass().getClassLoader().getResource("BrickDestroy_Menu.fxml"));
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("BrickDestroy_Menu.fxml"));
        primaryStage.setTitle("Brick Destroy");
        scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    static public void setNewScene(String fxml) throws IOException{
        scene.setRoot(FXMLLoader.load(BrickDestroyMain.class.getClassLoader().getResource(fxml+".fxml")));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
