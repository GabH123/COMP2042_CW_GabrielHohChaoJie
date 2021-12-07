package BrickDestroy.BrickDestroy_Model_JavaFX;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class HighScoreManagerTest {
    HighScoreManager test = new HighScoreManager();
    @Test
    void loadFromFile() {

        try {
            test.loadFromFile();
        }catch (IOException e){

        }
        HashMap<String,Integer> highScores = test.getHighScores();
        System.out.println(highScores);

    }

    @Test
    void saveToFile() {
        test.addRecord("Sam",100);
        test.addRecord("Nik",200);
        test.addRecord("Rose",300);
        test.addRecord("Test",400);
        try {
            test.saveToFile();
        }catch (IOException e){

        }
    }

    @Test
    void addRecord() {
    }
}