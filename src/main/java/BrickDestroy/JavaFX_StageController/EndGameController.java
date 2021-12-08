package BrickDestroy.JavaFX_StageController;

import BrickDestroy.BrickDestroy_Model_JavaFX.HighScoreManager;
import BrickDestroy.JavaFX_View.BrickDestroyMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class EndGameController {
    private HighScoreManager highScoreManager;
    private int playerNewScore;
    private int indexOfNewRecord;
    private boolean addedNewRecord;

    @FXML
    private Text newHighScore;

    @FXML
    private Button exitGame;

    @FXML
    private VBox highScoreBoard;

    @FXML
    private GridPane scorePane;

    @FXML
    private Text endMessage;


    private TextField newName;

    @FXML
    private void initialize(){

    }
    public void initialiseController(HighScoreManager highScoreManager,int playerNewScore){
        this.highScoreManager=highScoreManager;
        this.playerNewScore=playerNewScore;
        newName = new TextField();
        newName.setFont(newHighScore.getFont());
    }

    public void highScorePaneSetup(){
        indexOfNewRecord = highScoreManager.indexOfNewScore(playerNewScore);
        Text rowName;
        Text rowScore;
        Node nodeToRemove = new TextField();

        highScoreManager.addRecord("",playerNewScore);
        highScoreManager.printList();
        for (Node n: scorePane.getChildren()) {

            Integer rowIndex=GridPane.getRowIndex(n);
            Integer columnIndex=GridPane.getColumnIndex(n);

            if (rowIndex==null)
                rowIndex=0;
            if (columnIndex==null)
                columnIndex=0;

            System.out.println("Index: "+columnIndex+" "+rowIndex);
            if ((rowIndex-1)>=highScoreManager.getHighScores().size()||0>(rowIndex-1))
                continue;
            if (((rowIndex-1)==indexOfNewRecord)&&(columnIndex==1)){
                nodeToRemove = n;
                System.out.println("Test");
                //scorePane.add(newName,columnIndex,rowIndex,1,1);
            }
            else if (columnIndex==1){
                rowName = (Text) n;
                 System.out.println("Name: "+ highScoreManager.getHighScores().get(rowIndex-1).getName());
                rowName.setText(highScoreManager.getHighScores().get(rowIndex-1).getName());
            }
            else if (columnIndex==2){
                rowScore = (Text) n;
                System.out.println("Score: "+ highScoreManager.getHighScores().get(rowIndex-1).getScore());
                rowScore.setText(Integer.toString(highScoreManager.getHighScores().get(rowIndex-1).getScore()));
            }

        }


        if (indexOfNewRecord==-1){
            newHighScore.setText("YOUR FINAL SCORE:"+playerNewScore);
            endMessage.setText("Try again next time!");
            addedNewRecord=false;
        }
        else {
            scorePane.getChildren().remove(nodeToRemove);
            scorePane.add(newName,1,indexOfNewRecord+1,1,1);
            newHighScore.setText("YOUR NEW HIGH SCORE:"+playerNewScore);

            addedNewRecord=true;
        }
    }
    @FXML
    void exit(ActionEvent event) throws IOException {
        if (addedNewRecord)
            highScoreManager.getHighScores().get(indexOfNewRecord).setName(newName.getText());
        highScoreManager.saveToFile();
        BrickDestroyMain.setNewScene("BrickDestroy_Menu");
    }

}
