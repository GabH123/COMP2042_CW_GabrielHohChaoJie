package BrickDestroy.Model;

import java.awt.*;

/**LevelFactory is a class use to create (or build) different types of level for Wall. It also stores the info required for building each level.
 *
 */
public class LevelFactory {

    private static final int CLAY = 1;
    private static final int STEEL = 2;
    private static final int CEMENT = 3;

    public static final int TOTAL_NUMBER_OF_LEVELS = 4;

    private int brickCount;
    private int lineCount;
    private double brickDimensionRatio;
    private Rectangle drawArea;

    public LevelFactory(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio) {
        this.drawArea = drawArea;
        this.brickCount = roundBrickCnt(brickCount,lineCount);
        this.lineCount = lineCount;
        this.brickDimensionRatio = brickDimensionRatio;
    }

    public Level getThisLevel(int level){
        switch (level){
            case 1:
                return makeLevel(CLAY,CEMENT);
            case 2:
                return makeLevel(CLAY,STEEL);
            case 3:
                return makeLevel(CEMENT,STEEL);
            default:
                return makeLevel(CLAY,CLAY);
        }
    }

    public Level makeLevel(int typeA,int typeB) {
        return new Level(makeChessboardLevel(getDrawArea(),getBrickCount(),getLineCount(),getBrickDimensionRatio(),typeA,typeB));
    }


    private Brick[] makeChessboardLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt=roundBrickCnt(brickCnt,lineCnt);

        //First, decide dimensions of the brick
        int brickOnLine = brickCnt / lineCnt;

        int centerLeft = brickOnLine / 2 - 1;
        int centerRight = brickOnLine / 2 + 1;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2; //??

        Brick[] tmp  = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);

        //Second, set the location of each brick
        Point p = new Point();
        int i;

        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;

            int posX = i % brickOnLine;

            double x = posX * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));

            double y = (line) * brickHgt;

            p.setLocation(x,y);

            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
            tmp[i] = b ?  makeBrick(p,brickSize,typeA) : makeBrick(p,brickSize,typeB);
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,typeA);
        }
        return tmp;
    }

    private Brick makeBrick(Point point, Dimension size, int type){
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

    private Brick [] setBrickLocation(int brickCnt, int lineCnt, int brickOnLine,Dimension brickSize, int type){
        Brick [] bricks = new Brick [brickCnt];
        Point p = new Point();

        int brickNo;
        for(brickNo = 0; brickNo < bricks.length; brickNo++){
            int line = brickNo / brickOnLine;
            if(line == lineCnt)
                break;
            double x = (brickNo % brickOnLine) * brickSize.getWidth();
            x =(line % 2 == 0) ? x : (x - (brickSize.getWidth() / 2));
            double y = (line) * brickSize.getHeight();
            p.setLocation(x,y);
            bricks[brickNo] = makeBrick(p,brickSize,type);
            //System.out.println("Brick: "+i+" X: "+x+" Y: "+y);
        }

        for(double y = brickSize.getHeight();brickNo < bricks.length;brickNo++, y += 2*brickSize.getHeight()){
            double x = (brickOnLine * brickSize.getWidth()) - (brickSize.getWidth() / 2);
            p.setLocation(x,y);
            bricks[brickNo] = new ClayBrick(p,brickSize);
        }
        return bricks;
    }

    private Dimension setBrickDimension (Rectangle drawArea, int brickOnLine, double brickSizeRatio){
        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;
        return new Dimension((int) brickLen,(int) brickHgt);
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

    public Rectangle getDrawArea() {
        return drawArea;
    }
}
