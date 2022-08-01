package CustomView;

import Toolkit.Border.Resizer;
import Toolkit.Border.ViewBorder;
import Toolkit.Buttons.CloseCross;
import Toolkit.Buttons.FullScreenToggle;
import Toolkit.Buttons.MinimizeFrame;
import Toolkit.Buttons.Pin;
import Toolkit.Resize.MoveFrame;
import dblClickMaximize.dblClick;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.io.InvalidClassException;
import java.io.Serializable;

public abstract class ViewBase extends JFrame implements Serializable {

    private boolean validBorder = false;
    private boolean showError = true;
    private Border border = new ViewBorder(this);
    private JLabel pin = new Pin(this, Color.BLACK, Color.gray, Color.gray);
    private FullScreenToggle borderButton_max = new FullScreenToggle(this);
    private MinimizeFrame borderButton_min = new MinimizeFrame(this);
    private CloseCross borderButton_close = new CloseCross(this, Color.red, new Color(200,0,0), new Color(150,0,0));

    {
        setUndecorated(true);

        addBorderListener();

        // Set the Border
        getRootPane().setBorder(border);


        // Add resize event
        Resizer resizer = new Resizer(this);

        addMouseListener(resizer);
        addMouseMotionListener(resizer);


        // Add move event
        MouseAdapter mv = new MoveFrame(this);

        addMouseListener(mv);
        addMouseMotionListener(mv);
        addMouseListener(new dblClick(this));

        // Add fullscreen Button
        getRootPane().add(borderButton_max);
        // Add minimize button
        getRootPane().add(borderButton_min);
        // Add close button
        getRootPane().add(borderButton_close);
        // Add Pin
        getRootPane().add(pin);
    }

    /**
     * Get if the border is of valid type
     * @return true if the border is valid
     */
    public boolean isValidBorder() {
        return this.validBorder;
    }

    /**
     * Set the boolean to false to ignore Exceptions | NOT RECOMMENDED
     * @param b the new boolean value
     */
    public void logException(boolean b) {
        this.showError = b;
    }

    /**
     * Validate the new border type
     * @return true if the new border is valid
     */
    private boolean validateBorderType() {
        return getRootPane().getBorder().getClass().equals(ViewBorder.class);
    }

    /**
     * Adds a PropertyChangeListener to the border and check if the new border is valid
     */
    private void addBorderListener() {
        // Everytime there is a change of the Border check if it is a Valid Border
        getRootPane().addPropertyChangeListener("border",(PropertyChangeListener & Serializable) evt -> {
            // Validate the border
            validBorder = validateBorderType();
            border = getRootPane().getBorder();

            // if the border isn´t valid throw an Exception to declare that the border should be type of ViewBorder
            if(!validBorder && showError)
                try {
                    throw new InvalidClassException("Border should be type of " + ViewBorder.class + " -----> is type of " + getRootPane().getBorder().getClass() );
                } catch (InvalidClassException e) {
                    e.printStackTrace();
                }
        });
    }


    @Override
    public void setResizable(boolean resizable) {
        super.setResizable(resizable);

        setBounds(getX(), getY(), getWidth(), getHeight()+1);
        setBounds(getX(), getY(), getWidth(), getHeight()-1);
    }

    /**
     * Set the mainColor of the border
     * @param color the new Color
     */
    public void setBorderAccent(Color color) {
        if(validBorder)
            ((ViewBorder) border).setColor(color);
    }

    /**
     * Set the new Main Color to every button
     * @param color the new color
     * @throws InvalidClassException if border is invalid
     */
    public void setBorderButtonColor (Color color) throws InvalidClassException {
        if(!validBorder)
            throw new InvalidClassException("Border isn´t valid to do this, needs to type be of " + ViewBorder.class + " and is " + border.getClass());

        borderButton_max.setColor(color);
        borderButton_min.setColor(color);
        borderButton_close.setColorBase(color);
    }

    /**
     * set the new Colors for the close button
     * @param base the base color | no event
     * @param accent the accent color | on click
     * @param onHover the onHover color | on Hover
     */
    public void setBorderButtonCloseColor (Color base, Color accent, Color onHover) {
        borderButton_close.setColorBase(base);
        borderButton_close.setColorAccent(accent);
        borderButton_close.setColorOnHover(onHover);
    }

    public FullScreenToggle getMaximizedAction()  {
        return borderButton_max;
    }
}
