package BrickDestroy.Gameplay_Model;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;


/**LevelFactory is a class use to create (or build) different types of level for Wall. It also stores the info required for building each level.
 *
 */
public class LevelFactory {

    private static final int CLAY = 1;
    private static final int STEEL = 2;
    private static final int CEMENT = 3;

    public static final int TOTAL_NUMBER_OF_LEVELS = 9;

    private int brickCount;
    private int lineCount;
    private double brickDimensionRatio;
    private Pane drawArea;

    public LevelFactory(Pane drawArea, int brickCount, int lineCount, double brickDimensionRatio) {
        this.drawArea = drawArea;
        this.brickCount = roundBrickCnt(brickCount,lineCount);
        this.lineCount = lineCount;
        this.brickDimensionRatio = brickDimensionRatio;
    }

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

    public Level makeLevel(int typeA, int typeB) {
        return new Level(makeChessboardLevel(getDrawArea(),getBrickCount(),getLineCount(),getBrickDimensionRatio(),typeA,typeB));
    }


    private Brick[] makeChessboardLevel(Pane drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB){

        brickCnt=roundBrickCnt(brickCnt,lineCnt);

        int brickOnLine = brickCnt / lineCnt;

        int centerLeft = brickOnLine / 2 - 1;
        int centerRight = brickOnLine / 2 + 1;

        double brickLen = drawArea.getPrefWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;
        brickCnt += lineCnt / 2; //??

        Brick[] tmp  = new Brick[brickCnt];

        Dimension2D brickSize = new Dimension2D( brickLen, brickHgt);

        //Second, set the location of each brickJavaFX
        Point2D p = new Point2D(0,0);
        int i;

        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;

            int posX = i % brickOnLine;

            double x = posX * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));

            double y = (line) * brickHgt;


            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));

            tmp[i] = b ?  makeBrick(new Point2D(x,y),brickSize,typeA) : makeBrick(new Point2D(x,y),brickSize,typeB);

        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            tmp[i] = makeBrick(new Point2D(x,y),brickSize,typeA);
        }
        return tmp;
    }

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

    private Brick[] setBrickLocation(int brickCnt, int lineCnt, int brickOnLine, Dimension2D brickSize, int type){
        Brick[] brickJavaFXES = new Brick[brickCnt];
        Point2D p = new Point2D(0,0);

        int brickNo;
        for(brickNo = 0; brickNo < brickJavaFXES.length; brickNo++){
            int line = brickNo / brickOnLine;
            if(line == lineCnt)
                break;
            double x = (brickNo % brickOnLine) * brickSize.getWidth();
            x =(line % 2 == 0) ? x : (x - (brickSize.getWidth() / 2));
            double y = (line) * brickSize.getHeight();
            p.add(x,y);
            brickJavaFXES[brickNo] = makeBrick(p,brickSize,type);
            //System.out.println("Brick: "+i+" X: "+x+" Y: "+y);
        }

        for(double y = brickSize.getHeight(); brickNo < brickJavaFXES.length; brickNo++, y += 2*brickSize.getHeight()){
            double x = (brickOnLine * brickSize.getWidth()) - (brickSize.getWidth() / 2);
            p.add(x,y);
            brickJavaFXES[brickNo] = new ClayBrick(p,brickSize);
        }
        return brickJavaFXES;
    }

    private Dimension2D setBrickDimension (Rectangle drawArea, int brickOnLine, double brickSizeRatio){
        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;
        return new Dimension2D((int) brickLen,(int) brickHgt);
    }

    //Rounds the amount of Bricks so that it fits exactly in the screen according to the number of lines
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
