package Toolkit.Border;

import CustomView.ViewBase;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

public class ViewBorder implements javax.swing.border.Border {

    /**
     * Set the Color of this border
     *
     * Multiple borders with different Colors are possible
     */
    private Color color = Color.LIGHT_GRAY;

    /**
     * The Owner of this border
     */
    private final ViewBase owner;

    /**
     *
     * @param owner
     */
    public ViewBorder (ViewBase owner) {
        this.owner = owner;
        this.owner.addMouseListener(new MouseListener() {
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
                Cursor.getDefaultCursor();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Cursor.getDefaultCursor();
            }
        });
    }

    /**
     * Set the Color of the border
     * @param COLOR the new color to apply to this border
     */
    public void setColor(Color COLOR) {
        this.color = COLOR;
        this.owner.repaint();
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
        g.setColor(color);
        g.fillRect(x,y,width,height);
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
        return new Insets(16, 1, 1, 1);
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

    public Color getColor() {
        return this.color;
    }
}
