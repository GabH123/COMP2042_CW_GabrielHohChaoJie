package BrickDestroy.BrickDestroy_Model_JavaFX;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class HighScoreManagerTest {
    HighScoreManager test = new HighScoreManager("BrickDestroy_HighScore.bin");
    @Test
    void loadFromFile() {
        test.loadFromFile();
        //LinkedList<ScoreRecord> highScores = test.getHighScores();
        System.out.println(test.getHighScores());
        assertEquals(test.getHighScores().size(),1);
    }

    @Test
    void saveToFile() {
        test.addRecord("Sam",100);
        test.addRecord("Nik",200);
        test.addRecord("Rose",300);
        test.addRecord("Test",400);
        test.saveToFile();

    }

    @Test
    void addRecord() {
        test.addRecord("Kevin",100);
        test.addRecord("Sam",200);
        test.addRecord("Nik",300);
        test.addRecord("Rose",400);
        test.addRecord("Jake",500);
        test.addRecord("Thomas",800);
        test.addRecord("Peter",1000);

        System.out.println(test.getHighScores());
        test.saveToFile();
        assertEquals(test.getHighScores().size(),7);
    }

    @Test
    void indexOfNewScore(){
        test.loadFromFile();
        test.printList();
        //assertEquals(test.indexOfNewScore(150),5);
    }
}