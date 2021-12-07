package BrickDestroy.BrickDestroy_Model_JavaFX;

import java.io.*;
import java.util.HashMap;

public class HighScoreManager implements Manager{
    private String highScoreFile;
    private HashMap<String ,Integer> highScores;

    public HighScoreManager()  {
        highScoreFile = "BrickDestroy_HighScore.bin";
        highScores = new HashMap<>();
        createFileIfNotCreated(highScoreFile);
    }


    @Override
    public void loadFromFile() throws IOException{
        FileInputStream fileToLoad = new FileInputStream(highScoreFile);
        ObjectInputStream inputStream = new ObjectInputStream(fileToLoad);
        try {
            highScores = (HashMap<String, Integer>) inputStream.readObject();
        } catch (ClassNotFoundException e){
            createFileIfNotCreated(highScoreFile);

        }
        fileToLoad.close();
        inputStream.close();
    }

    @Override
    public void saveToFile() throws IOException {
        FileOutputStream fileToSave = new FileOutputStream(highScoreFile);
        ObjectOutputStream outputStream = new ObjectOutputStream(fileToSave);
        outputStream.writeObject(highScores);
        fileToSave.close();
        outputStream.close();
    }

    @Override
    public void addRecord(String name, Integer score){
        highScores.put(name,score);
    }

    private void createFileIfNotCreated(String highScoreFile){
        try {
            File file = new File(highScoreFile);
            file.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public HashMap<String, Integer> getHighScores() {
        return highScores;
    }

}
