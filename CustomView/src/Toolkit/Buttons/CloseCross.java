package Toolkit.Buttons;

import CustomView.ViewBase;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CloseCross extends JLabel {

    private final ViewBase vb;

    private Color base, accent, onHover;

    {
        // Create the Close Button
        setBackground(base);
        setBorder(new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                ((Graphics2D) g).setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(getBackground());
                g.drawLine(x, y, 10, 10);
                g.drawLine(10, y, x, 10);
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
                vb.dispose();
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
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                setBackground(onHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                setBackground(base);
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
    public CloseCross (ViewBase owner, Color base, Color accent, Color onHover) {

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
                setBounds(vb.getWidth()-15, 2, 12, 12);
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
}
