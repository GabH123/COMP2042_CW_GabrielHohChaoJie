package BrickDestroy.Gameplay_Model;

/**Interface Manager defines the methods needed handle file input of high score lists and then save them.
 *
 */
public interface Manager {
    /**Loads the list of high score from the file to the list.
     *
     */
    void loadFromFile() ;

    /**Saves the list of high scores to the file.
     *
     */
    void saveToFile() ;

    /**Add a new record into the list
     * @param name name of person
     * @param score score of the person
     */
    void addRecord(String name, Integer score);
}
