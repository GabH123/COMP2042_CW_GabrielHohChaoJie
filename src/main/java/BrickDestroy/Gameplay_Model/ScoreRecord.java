package BrickDestroy.Gameplay_Model;

import java.io.Serializable;

/**Class for holding the name and score of the player with the high score.
 *
 */
public class ScoreRecord implements Serializable {
    /**Name of the player.
     *
     */
    private String name;
    /**Score of the player.
     *
     */
    final private int score;

    /**Initialises the object with the name and high score of the player.
     * @param name name of the player
     * @param score high score of the player
     */
    public ScoreRecord(String name, int score) {
        this.name = name;
        this.score = score;

    }

    /**Get the name of the player.
     * @return name of the player
     */
    public String getName() {
        return name;
    }

    /**Get the high score of the player.
     * @return high score of the player
     */
    public int getScore() {
        return score;
    }

    /**Set the name of the player.
     * @param name new name of the player
     */
    public void setName(String name) {
        this.name = name;
    }
}
