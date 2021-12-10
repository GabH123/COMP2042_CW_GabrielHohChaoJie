package BrickDestroy.Gameplay_Model;

import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrackTest {
    Crack crack = new Crack(1,35);
    @Test
    void makeCrack() {
        crack.makeCrack(new Point2D(50,0),Crack.UP,new Rectangle(0,0,100,20));
        ObservableList<PathElement> list = crack.getCrackPath().getElements();
        MoveTo start = (MoveTo) list.get(0);
        LineTo end = (LineTo) list.get(35);
        assertEquals(start.getX(),50);
        assertEquals(start.getY(),0);
        assertTrue(0<=end.getX()&&end.getX()<=100);
        assertEquals(end.getY(),20);
        System.out.println("End: "+end);
        list.forEach(path->{
            System.out.println(path);
            if (path instanceof MoveTo) {
                System.out.println("");
            }
            else {
                double x = ((LineTo)path).getX();
                double y = ((LineTo)path).getY();
                assertTrue((start.getX()<=x&&x<= end.getX())||(start.getX()>=x&&x>= end.getX()));
                assertTrue((start.getY()<=y&&y<= end.getY())||(start.getY()>=y&&y>= end.getY()));
            }
        });
    }
}