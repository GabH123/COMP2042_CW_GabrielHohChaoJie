package BrickDestroy.BrickDestroy_Model_JavaFX;

import java.io.*;
import java.util.LinkedList;

public class HighScoreManager implements Manager{
    private String highScoreFile;
    private LinkedList<ScoreRecord> highScores;

    public HighScoreManager(String highScoreFileName)  {
        this.highScoreFile = highScoreFileName;
        highScores = new LinkedList<>();
        createFileIfNotCreated(highScoreFileName);
    }


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
            createFileIfNotCreated(highScoreFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    private void createFileIfNotCreated(String highScoreFile){
        try {
            File file = new File(highScoreFile);
            if (file.canRead())
                file.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public LinkedList<ScoreRecord> getHighScores() {
        return highScores;
    }

}
