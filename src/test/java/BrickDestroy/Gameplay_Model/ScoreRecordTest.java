package BrickDestroy.Gameplay_Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreRecordTest {
    ScoreRecord record1 = new ScoreRecord("Gabriel",1000);
    ScoreRecord record2 = new ScoreRecord("",0);

    @Test
    void getName() {
        assertEquals(record1.getName(),"Gabriel");
        assertEquals(record2.getName(),"");
    }

    @Test
    void getScore() {
        assertEquals(record1.getScore(),1000);
        assertEquals(record2.getScore(),0);
    }

    @Test
    void setName() {
        record1.setName("Sam");
        assertEquals(record1.getName(),"Sam");
        record1.setName("");
        assertEquals(record1.getName(),"");

        record2.setName("Simon");
        assertEquals(record2.getName(),"Simon");
        record2.setName("");
        assertEquals(record2.getName(),"");
    }
}