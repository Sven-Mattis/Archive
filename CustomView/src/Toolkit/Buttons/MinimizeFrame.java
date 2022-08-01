package Toolkit.Buttons;

import CustomView.ViewBase;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MinimizeFrame extends JLabel implements Border {

    private final ViewBase owner;

    private Color color = Color.BLACK;

    {
        MouseAdapter ma = new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                run();
            }

            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                owner.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseEntered(e);
                owner.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        };

        this.addMouseListener(ma);
    }

    public MinimizeFrame (ViewBase owner) {
        if(owner == null)
            throw new NullPointerException("Owner can´t be null and is " + owner);

        this.owner = owner;


        owner.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                if(!owner.isResizable()) {
                    setBounds(0,0,0,0);
                    return;
                }

                setBounds(owner.getWidth()-15-25-25, 2, 12, 12);
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });


        setBorder(this);
    }

    /**
     * Minimize the Frame
     */
    private void run() {
        owner.setState(Frame.ICONIFIED);
    }
    
    /**
     * Paints the border for the specified component with the specified
     * position and size.
     *
     * @param c      the component for which this border is being painted
     * @param g      the paint graphics
     * @param x      the x position of the painted border
     * @param y      the y position of the painted border
     * @param width  the width of the painted border
     * @param height the height of the painted border
     */
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        int middleY = height/2;
        g.drawLine(0,middleY,width,middleY);
    }

    /**
     * Returns the insets of the border.
     *
     * @param c the component for which this border insets value applies
     * @return an {@code Insets} object containing the insets from top, left,
     * bottom and right of this {@code Border}
     */
    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(0,0,0,0);
    }

    /**
     * Returns whether or not the border is opaque.  If the border
     * is opaque, it is responsible for filling in it's own
     * background when painting.
     *
     * @return true if this {@code Border} is opaque
     */
    @Override
    public boolean isBorderOpaque() {
        return true;
    }

    /**
     * Set the Color for the icon
     * @param color the new color for the icon
     */
    public void setColor (Color color) {
        this.color = color;
        repaint();
    }
}
