package Toolkit.Buttons;

import Toolkit.Border.ViewBorder;
import CustomView.ViewBase;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class FullScreenToggle extends JLabel implements Border{

    private final ViewBase owner;

    private Rectangle boundsBeforeFull;

    private boolean fullscreen = false;

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
                execute();
            }

            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
            }

            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
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
                super.mouseExited(e);
                owner.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            /**
             * {@inheritDoc}
             *
             * @param e
             * @since 1.6
             */
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                super.mouseWheelMoved(e);
            }

            /**
             * {@inheritDoc}
             *
             * @param e
             * @since 1.6
             */
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
            }

            /**
             * {@inheritDoc}
             *
             * @param e
             * @since 1.6
             */
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
            }
        };
        addMouseListener(ma);

        setBorder(this);
    }

    /**
     * Creates the Toggle button for Fullscreen / not Fullscreen
     * @param owner the View | not null
     */
    public FullScreenToggle (ViewBase owner) {
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


                Rectangle size = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
                if(owner.getBounds().equals(size))
                    fullscreen = true;
                else
                    fullscreen = false;

                repaint();

                setBounds(owner.getWidth()-15-25, 2, 12, 12);
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
        if(!fullscreen)
            g.drawRect(0,0,width-1,height-1);
        else {
            g.drawRect(2, 0, width - 1 - 3, height - 1 - 3);
            g.clearRect(0, 2, width - 1 - 3, height - 1 - 3);
            g.setColor(((ViewBorder) owner.getRootPane().getBorder()).getColor());
            g.fillRect(0, 2, width - 1 - 3, height - 1 - 3);
            g.setColor(Color.black);
            g.drawRect(0, 2, width - 1 - 3, height - 1 - 3);
        }
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
        return new Insets(1,1,1,1);
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

    public void execute() {
        fullscreen = !fullscreen;

        Rectangle size = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

        if(fullscreen) {
            boundsBeforeFull = owner.getBounds();
            owner.setBounds(size);
        } else {
            if(boundsBeforeFull != null)
                owner.setBounds(boundsBeforeFull);
            else {
                System.out.println("Fallback");
                int height = getMinimumSize().height;
                int width = getMinimumSize().width;
                owner.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width+width)/2, (Toolkit.getDefaultToolkit().getScreenSize().height+height)/2, width, height);
            }
        }
        repaint();
    }
}
