package Toolkit.Buttons;

import CustomView.ViewBase;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Pin extends JLabel {

    private final ViewBase vb;

    private Color base, accent, onHover;

    {
        // Create the Close Button
        setBackground(base);
        setBorder(new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {

                int r = 6;

                ((Graphics2D) g).setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(getBackground());
                g.fillArc((width-r)/2,(height-r)/2, r, r, 0, 360);
                g.fillPolygon(new int[]{(width-r)/2,width/2,(width+r)/2}, new int[]{height/2,height,height/2}, 3);
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(1,1,1,1);
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        });

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                vb.setAlwaysOnTop(!vb.isAlwaysOnTop());
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(accent);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });
    }


    /**
     * Creates the little close button in the upper right corner
     * @param owner the View owner | not null
     * @param base the base color | no event
     * @param accent the accent color | onClick
     * @param onHover the onHoverColor | onHover
     */
    public Pin (ViewBase owner, Color base, Color accent, Color onHover) {

        if(owner == null)
            throw new NullPointerException("Owner can´t be Null but is " + owner);

        this.vb = owner;
        this.base = base;
        this.accent = accent;
        this.onHover = onHover;

        setBackground(base);

        vb.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                new Thread(() -> {
                    try {
                        Thread.sleep(100);
                        setBounds(2, 2, 11, 11);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }).start();
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

        vb.repaint();
    }

    /**
     * set the base color | no event
     * @param color
     */
    public void setColorBase(Color color) {
        this.base = color;
    }

    /**
     * Set the accent color | on click
     * @param color
     */
    public void setColorAccent(Color color) {
        this.accent = color;
    }

    /**
     * Set the onHover color | on hover
     * @param color
     */
    public void setColorOnHover(Color color) {
        this.onHover = color;
    }

    @Override
    public void repaint() {
        if(vb == null)
            return;

        if(vb.isAlwaysOnTop())
            setBackground(onHover);
        else
            setBackground(base);

        super.repaint();
    }
}
