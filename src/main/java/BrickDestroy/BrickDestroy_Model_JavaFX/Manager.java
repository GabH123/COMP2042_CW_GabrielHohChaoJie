package BrickDestroy.BrickDestroy_Model_JavaFX;

import java.io.IOException;

public interface Manager {
    void loadFromFile() ;
    void saveToFile() ;
    void addRecord(String name, Integer score);
}
