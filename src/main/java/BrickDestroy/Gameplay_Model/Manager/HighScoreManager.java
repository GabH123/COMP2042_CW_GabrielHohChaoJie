package BrickDestroy.Gameplay_Model.Manager;

import BrickDestroy.Gameplay_Model.ScoreRecord;

import java.io.*;
import java.util.LinkedList;

/**Implements the interface of Manager for managing the files where the permanent list of high scores are stored.
 *
 */
public class HighScoreManager implements Manager{
    /**The name of the file where the permanent list of high scores are stored.
     *
     */
    private String highScoreFile;
    /**The linked list that contains the record for each high score and the name of the person.
     *
     */
    private LinkedList<ScoreRecord> highScores;

    /**Initialises the manager with the name of the file in question.
     * @param highScoreFileName name of the file with the high score list
     */
    public HighScoreManager(String highScoreFileName)  {
        this.highScoreFile = highScoreFileName;
        highScores = new LinkedList<>();
        createFileIfNotCreated(highScoreFileName);
    }


    /**Loads the list of high scores from the file to the manager.
     * <p>
     * The scores are stored as a linked list in the binary file.
     */
    @Override
    public void loadFromFile() {

        try {
            FileInputStream fileToLoad = new FileInputStream(highScoreFile);
            ObjectInputStream inputStream = new ObjectInputStream(fileToLoad);
            highScores = (LinkedList<ScoreRecord>) inputStream.readObject();
            fileToLoad.close();
            inputStream.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while loading data from file!");
            createFileIfNotCreated(highScoreFile);

        } catch (IOException e) {
            System.err.println("Error while finding file!");
            e.printStackTrace();
        }
    }

    /**Saves the list of scores current have into the file, overwritting any existing ones in the file.
     *
     */
    @Override
    public void saveToFile()  {
        try {
            FileOutputStream fileToSave = new FileOutputStream(highScoreFile);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileToSave);
            outputStream.writeObject(highScores);
            fileToSave.close();
            outputStream.close();
        }catch (IOException e){

        }
    }

    /**Adds the record of a new high score into the list.
     * @param name name of the high score holder
     * @param score the new high score
     */
    @Override
    public void addRecord(String name, Integer score){
        for (int i = 0; i < highScores.size(); i++) {
            if (highScores.get(i).getScore() < score) {
                highScores.add(i, new ScoreRecord(name, score));
                if (highScores.size() > 10) {
                    highScores.removeLast();
                }
                return;
            }
        }
        if (highScores.size()<10)
            highScores.add(new ScoreRecord(name, score));

    }

    /**Returns the index of the new high score in the list.
     * <p>
     * Returning -1 means the current score has not broken any of the highscores.
     * @param playerNewScore the current score
     * @return index of the new score in the list
     */
    public int indexOfNewScore(int playerNewScore){
        int i;
        for (i=0;i< getHighScores().size();i++) {
            if (getHighScores().get(i).getScore()<playerNewScore)
                return i;
        }
        if (getHighScores().size()<10)
            return i;
        else return -1;
    }

    /**Creates a new file if the file doesn't exist or has gone missing.
     * @param highScoreFile name of the file where the high scores are stored
     */
    private void createFileIfNotCreated(String highScoreFile){
        try {
            File file = new File(highScoreFile);
            if (file.canRead())
                file.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    /**Returns the linked list with the high scores and the names.
     * @return linked list with the high scores and the names
     */
    public LinkedList<ScoreRecord> getHighScores() {
        return highScores;
    }

}
