package Toolkit.Resize;

import CustomView.ViewBase;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MoveFrame extends MouseAdapter {

    private final ViewBase owner;

    public MouseEvent prev = null;
    private boolean drag = false;


    public MoveFrame (ViewBase owner) {
        if(owner == null)
            throw new NullPointerException("Owner can´t be null and is " + owner);

        this.owner = owner;
    }


    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if(e.getY() > 5 && e.getY() < 20 &&
                e.getX() > 5 && e.getX() < owner.getWidth()-5)
            drag = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        prev = null;
        drag = false;
    }

    /**
     * {@inheritDoc}
     *
     * @param e
     * @since 1.6
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseMoved(e);

        if(drag) {
            if(prev != null) {
                if(prev.getX() == e.getX() && prev.getY() == e.getY())
                    return;
                int x = owner.getLocationOnScreen().x + (e.getXOnScreen() - prev.getXOnScreen());
                int y = owner.getLocationOnScreen().y + (e.getYOnScreen() - prev.getYOnScreen());

                owner.setBounds(x, y, owner.getWidth(), owner.getHeight());
            }
            prev = e;
        }
    }
}
