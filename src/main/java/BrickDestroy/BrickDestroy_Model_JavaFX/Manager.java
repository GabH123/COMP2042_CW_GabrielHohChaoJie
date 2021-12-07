package BrickDestroy.BrickDestroy_Model_JavaFX;

import java.io.IOException;

public interface Manager {
    void loadFromFile() throws IOException;
    void saveToFile() throws IOException;
    void addRecord(String name, Integer score);
}
