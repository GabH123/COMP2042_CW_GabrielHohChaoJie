package BrickDestroy.BrickDestroy_Model_JavaFX;

import java.io.File;
import java.io.IOException;

public class HighScoreManager implements Manager{
    File highScoreFile;
    public HighScoreManager()  {
        highScoreFile = new File("BrickDestroy_HighScore.txt");
        try {
                highScoreFile.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void loadFromFile() {

    }

    @Override
    public void saveToFile() {

    }

    @Override
    public void addRecord() {

    }
}
