package BrickDestroy.BrickDestroy_Model_JavaFX;

import java.io.Serializable;

public class ScoreRecord implements Serializable {
    final private String name;
    final private int score;

    public ScoreRecord(String name, int score) {
        this.name = name;
        this.score = score;

    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
