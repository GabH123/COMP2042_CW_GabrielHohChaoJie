package BrickDestroy.Gameplay_Model;

import org.junit.jupiter.api.Test;

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
        test.addRecord("KEVIN",100);
        test.addRecord("SAM",200);
        test.addRecord("NICK",300);
        test.addRecord("MIKA",400);
        test.addRecord("JAKE",500);
        test.addRecord("THOMAS",800);
        test.addRecord("JACK",1000);

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