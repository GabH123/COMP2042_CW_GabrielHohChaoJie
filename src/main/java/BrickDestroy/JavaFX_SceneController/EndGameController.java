package BrickDestroy.JavaFX_SceneController;

import BrickDestroy.Gameplay_Model.Manager.HighScoreManager;
import BrickDestroy.JavaFX_View.BrickDestroyMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;

/**Controller for handling the end game part of the View.
 * It provides the high score list to the view and get the name of the player, to save the new high score.
 */
public class EndGameController {
    /**Index for the column with the names of the players in the grid pane score board.
     *
     */
    private static final int NAME_COLUMN_INDEX=1;
    /**Index for the column with the score of the players in the grid pane score board.
     *
     */
    private static final int SCORE_COLUMN_INDEX=2;

    /**For managing the high scores and saves and loads the list of high scores.
     *
     */
    private HighScoreManager highScoreManager;
    /**Player's new score
     *
     */
    private int playerNewScore;
    /**Index of the player's new score in the list of high scores.
     *
     */
    private int indexOfNewRecord;
    /**Indicates whether any new record is added or not.
     *
     */
    private boolean addedNewRecord;

    /**To display the score of the player.
     *
     */
    @FXML
    private Text newHighScore;

    /**GridPane with the list of scores and players.
     *
     */
    @FXML
    private GridPane scorePane;

    /**Displays the instruction for the player.
     *
     */
    @FXML
    private Text endMessage;

    /**Allows the player input their name.
     *
     */
    private TextField newName;

    /**Intialises the controller with the corresponding high score manager and the plaer's new score
     * @param highScoreManager manager for handling the high scores
     * @param playerNewScore player's new score
     */
    public void initialiseController(HighScoreManager highScoreManager,int playerNewScore){
        this.highScoreManager=highScoreManager;
        this.playerNewScore=playerNewScore;
        newName = new TextField();
        newName.setFont(newHighScore.getFont());
        newName.getStylesheets().add("TextField_Transparent.css");
    }

    /**Sets up the GridPane scorePane to display the names and scores of person with the record of high scores.
     *<p>
     * A TextField will be displayed on the row where the position of the players new score will be, to allow the player to enter their name.
     */
    public void setupScorePane(){
        indexOfNewRecord = highScoreManager.indexOfNewScore(playerNewScore);
        Text rowName;
        Text rowScore;
        Node nodeToRemove = new TextField();

        highScoreManager.addRecord("",playerNewScore);
        for (Node n: scorePane.getChildren()) {

            Integer rowIndex=GridPane.getRowIndex(n);
            Integer columnIndex=GridPane.getColumnIndex(n);

            rowIndex=zeroIfNull(rowIndex);
            columnIndex=zeroIfNull(columnIndex);

            if (checkIndexOutOfBounds(rowIndex))
                continue;
            if (((rowIndex-1)==indexOfNewRecord)&&(columnIndex==1)){
                nodeToRemove = n;
            }
            else if (columnIndex==NAME_COLUMN_INDEX){
                rowName = (Text) n;
                rowName.setText(highScoreManager.getHighScores().get(rowIndex-1).getName());
            }
            else if (columnIndex==SCORE_COLUMN_INDEX){
                rowScore = (Text) n;
                rowScore.setText(Integer.toString(highScoreManager.getHighScores().get(rowIndex-1).getScore()));
            }

        }
        updateScorePane(indexOfNewRecord,nodeToRemove);
    }

    /**Checks whether the current index is out of bounds in the linked list of high scores and names.
     * @param index current GridPane row index
     * @return whether it is out of bounds or not
     */
    private boolean checkIndexOutOfBounds(Integer index){
        return (index-1)>=highScoreManager.getHighScores().size()||0>(index-1);
    }

    /**Returns 0 if the integer is null
     * @param number Integer object in question
     * @return 0 if the Integer object is null, itself if it's not
     */
    private int zeroIfNull(Integer number){
       if (number==null)
           return 0;
       else return number;
    }

    /**Based on whether the player has made a new high score or not, it updates the texts and scorePane.
     * <p>
     * If the player has made a new high score, the Text node on the row with the position of the players new record will be replaced with
     * a TextField to allow the player to input their name.
     * @param indexOfNewRecord
     * @param nodeToRemove
     */
    private void updateScorePane(int indexOfNewRecord, Node nodeToRemove){
        if (indexOfNewRecord==-1){
            newHighScore.setText("YOUR FINAL SCORE:"+playerNewScore);
            endMessage.setText("Try again next time!");
            addedNewRecord=false;
        }
        else {
            scorePane.getChildren().remove(nodeToRemove);
            scorePane.add(newName,NAME_COLUMN_INDEX,indexOfNewRecord+1,1,1);
            newHighScore.setText("YOUR NEW HIGH SCORE:"+playerNewScore);

            addedNewRecord=true;
        }
    }

    /**Updates the name of the player into the high score list from the TextField newName where the player typed their message.
     * @param addedNewRecord flag to indicate whether a new record is added or not
     */
    private void updateNewNameInHighScoreList(boolean addedNewRecord){
        if (addedNewRecord)
            highScoreManager.getHighScores().get(indexOfNewRecord).setName(newName.getText());
    }

    /**Saves the list of high scores and exits into the main menu.
     * @param event mouse clicked
     * @throws IOException
     */
    @FXML
    void exit(ActionEvent event) throws IOException {
        updateNewNameInHighScoreList(addedNewRecord);
        highScoreManager.saveToFile();
        BrickDestroyMain.setNewScene("BrickDestroy_Menu");
    }



}
