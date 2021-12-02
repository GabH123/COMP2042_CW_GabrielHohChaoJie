package BrickDestroy.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InfoBoard extends JComponent implements MouseListener, MouseMotionListener {

    private GameFrame owner;

    private Dimension area;

    public InfoBoard(GameFrame owner,Dimension area) {
        this.owner = owner;
        this.area = area;

        initialise();
    }

    void initialise(){
        this.setFocusable(true);
        this.setPreferredSize(area);
        this.requestFocusInWindow();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
