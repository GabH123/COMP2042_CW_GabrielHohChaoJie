package BrickDestroy.Gameplay_Model;

import BrickDestroy.Gameplay_Model.Brick.Brick;
import BrickDestroy.Gameplay_Model.Brick.CementBrick;
import BrickDestroy.Gameplay_Model.Brick.ClayBrick;
import BrickDestroy.Gameplay_Model.Brick.SteelBrick;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;


/**LevelFactory is used to create (or build) different types of level for the game.
 * It also stores the info required for building each level.
 *
 */
public class LevelFactory {

    /**Constant to indicate to create brick type ClayBrick.
     *
     */
    private static final int CLAY = 1;
    /**Constant to indicate to create brick type SteelBrick.
     *
     */
    private static final int STEEL = 2;
    /**Constant to indicate to create brick type CementBrick.
     *
     */
    private static final int CEMENT = 3;
    /**Defines the maximum amount of levels.
     *
     */
    public static final int TOTAL_NUMBER_OF_LEVELS = 9;

    /**Amount of bricks in a level. (Note: Dues to how the level is generated, there will be extra bricks used to fill the sides.)
     *
     */
    private int brickCount;
    private int lineCount;
    private double brickDimensionRatio;
    private Pane drawArea;

    /**Initialises the LevelFactory with the corresponding draw area, number of bricks, lines and the ratio of the brick.
     * @param drawArea the pane where the game is drawn
     * @param brickCount the amount of bricks in the level
     * @param lineCount the number of lines of bricks in the game
     * @param brickDimensionRatio the ratio between the width and height of the brick
     */
    public LevelFactory(Pane drawArea, int brickCount, int lineCount, double brickDimensionRatio) {
        this.drawArea = drawArea;
        this.brickCount = roundBrickCnt(brickCount,lineCount);
        this.lineCount = lineCount;
        this.brickDimensionRatio = brickDimensionRatio;
    }

    /**Generates the level of the current number with its brick type.
     * @param level number of level to be generated
     * @return the generated level from the level number with the corresponding brick types
     */
    public Level getThisLevel(int level){
        switch (level){
            case 0:
                return makeLevel(CLAY,CLAY);
            case 1:
                return makeLevel(CEMENT,CLAY);
            case 2:
                return makeLevel(CLAY,CEMENT);
            case 3:
                return makeLevel(CEMENT,CEMENT);
            case 4:
                return makeLevel(STEEL,CLAY);
            case 5:
                return makeLevel(STEEL,CEMENT);
            case 6:
                return makeLevel(CLAY,STEEL);
            case 7:
                return makeLevel(CEMENT,STEEL);
            case 8:
                return makeLevel(STEEL,STEEL);
            default:
                return makeLevel(CLAY,CLAY);
        }
    }

    /**Makes the level with the corresponding brick types
     * @param typeA first type of brick (appears the least in the level)
     * @param typeB second type of brick (appears the most in the level)
     * @return the level generated
     */
    private Level makeLevel(int typeA, int typeB) {
        return new Level(makeLevel(getDrawArea(),getBrickCount(),getLineCount(),getBrickDimensionRatio(),typeA,typeB));
    }


    /**Makes the level based on the size of the draw area, number of bricks and line, ratio of brick size and the types of brick
     * @param drawArea the area where the bricks are drawn
     * @param brickCnt number of bricks (it will be increased by some to fill the edges)
     * @param numberOfLines number of lines of bricks
     * @param brickSizeRatio width to height ratio of the size of the brick
     * @param typeA first type of brick (appears the least in the level)
     * @param typeB second type of brick (appears the most in the level)
     * @return the array of bricks for the levels
     */
    private Brick[] makeLevel(Pane drawArea, int brickCnt, int numberOfLines, double brickSizeRatio, int typeA, int typeB){

        int bricksPerLine = brickCnt / numberOfLines;
        brickCnt += numberOfLines / 2;

        Brick[] level  = new Brick[brickCnt];
        Dimension2D brickSize = setBrickDimension(drawArea,bricksPerLine,brickSizeRatio);

        int i;

        for(i = 0; i < level.length; i++){
            int currentLine = i / bricksPerLine;
            if(currentLine == numberOfLines)
                break;

            int brickIndexOnRow = i % bricksPerLine;

            double x = brickIndexOnRow * brickSize.getWidth();
            x =(currentLine % 2 == 0) ? x : (x - (brickSize.getWidth() / 2));

            double y = (currentLine) * brickSize.getHeight();


            boolean b =decideBricksPattern(i,currentLine,brickIndexOnRow,bricksPerLine);

            level[i] = b ?  makeBrick(new Point2D(x,y),brickSize,typeA) : makeBrick(new Point2D(x,y),brickSize,typeB);

        }

        for(double y = brickSize.getHeight();i < level.length;i++, y += 2*brickSize.getHeight()){
            double x = (bricksPerLine * brickSize.getWidth()) - (brickSize.getWidth() / 2);
            level[i] = makeBrick(new Point2D(x,y),brickSize,typeB);
        }
        return level;
    }

    /**Decides what type of brick to generate based on the pattern of the level intended.
     * <p>
     * Odd numbered rows contains bricks arranged in a chessboard manner.
     * Even numbered rows contains bricks arranged with the middle containing second type and the edges contains the first type.
     * @param index index of the current brick in question
     * @param currentLine current line number
     * @param brickIndexOnRow index of the current brick on the current row
     * @param bricksPerLine number of bricks on one line (excluding the edge filler bricks)
     * @return indicator for first type or second type of brick
     */
    private boolean decideBricksPattern(int index, int currentLine, int brickIndexOnRow, int bricksPerLine){
        int centerLeftIndex = bricksPerLine / 2 - 1;
        int centerRightIndex = bricksPerLine / 2 + 1;

        return  ((currentLine % 2 == 0 && index % 2 == 0) || (currentLine % 2 != 0 && brickIndexOnRow > centerLeftIndex && brickIndexOnRow <= centerRightIndex));
    }

    /**Makes the corresponding brick type with the intended location and size.
     * @param point upper-left of the brick
     * @param size size of brick
     * @param type type of brick
     * @return brick object of the intended type
     */
    private Brick makeBrick(Point2D point, Dimension2D size, int type){
        //Switch CASE for subclasses not allowed
        Brick out;
        switch(type){
            case CLAY:
                out = new ClayBrick(point,size);
                break;
            case STEEL:
                out = new SteelBrick(point,size);
                break;
            case CEMENT:
                out = new CementBrick(point, size);
                break;
            default:
                throw  new IllegalArgumentException(String.format("Unknown Type:%d\n",type));
        }
        return  out;
    }

    /**Calculates the dimension of the brick.
     * @param drawArea area of where the bricks are drawn
     * @param brickOnLine number of bricks per line
     * @param brickSizeRatio width to height ratio of brick
     * @return dimension object of the brick
     */
    private Dimension2D setBrickDimension (Pane drawArea, int brickOnLine, double brickSizeRatio){
        double brickLen = drawArea.getPrefWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;
        return new Dimension2D( brickLen, brickHgt);
    }

    /**Rounds the amount of Bricks so that it fits exactly in the screen according to the number of lines
     * @param brickCnt number of bricks on the leve;
     * @param lineCnt number of lines
     * @return rounded number of bricks that fits the draw area with the intended number of lines
     */
    private int roundBrickCnt (int brickCnt, int lineCnt){
        return brickCnt -= brickCnt % lineCnt;
    }



    public int getBrickCount() {
        return brickCount;
    }

    public int getLineCount() {
        return lineCount;
    }

    public double getBrickDimensionRatio() {
        return brickDimensionRatio;
    }

    public Pane getDrawArea() {
        return drawArea;
    }
}
