package BrickDestroy.Gameplay_Model;

public interface Manager {
    void loadFromFile() ;
    void saveToFile() ;
    void addRecord(String name, Integer score);
}
