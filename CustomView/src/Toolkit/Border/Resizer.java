package Toolkit.Border;

import CustomView.ViewBase;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Resizer extends MouseAdapter {

    // View Owner
    private final ViewBase owner;

    // booleans for the start of the dragging, to prevent stopping the drag because of to fast mouse motion
    private boolean upperLeft, upper, upperRight, right, bottomRight, bottom, bottomLeft, left;

    // Snappoint to register the "border"
    private final Dimension snappoint = new Dimension(5,5);
    private Dimension prev;

    // for the hover
    private int Velocity = 0;

    /**Own Resize for better feel and Look
     *
     * need to be append as MouseListener and as MouseMotionListener
     * and the frame needs to be undecorated
     *
     * @param owner owner to apply it to
     */
    public Resizer (ViewBase owner) {
        if(owner == null)
            throw new NullPointerException("Owner can´t be null and is " + owner);

        this.owner = owner;
    }

    /**
     * {@inheritDoc}
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);

        // break if the Window should´nt resize
        if(!owner.isResizable())
            return;

        // save the start point for the "velocity"
        prev = new Dimension(e.getXOnScreen(), e.getYOnScreen());

        // Check where is clicked
        if(e.getY() - snappoint.height < 0 && e.getX() - snappoint.width < 0) {
            // upper left Corner
            upperLeft = true;
        } else if(e.getY() - snappoint.height < 0 && e.getX() > owner.getWidth() - snappoint.width) {
            // upper Right Corner
            upperRight = true;
        } else if(e.getY() - snappoint.height < 0) {
            // upper
            upper = true;
        } else if(e.getX() > owner.getWidth() - snappoint.width && e.getY() > owner.getHeight() - snappoint.height) {
            // bottomRight
            bottomRight = true;
        } else if(e.getX() > owner.getWidth() - snappoint.width) {
            // right
            right = true;
        } else if(e.getX() - snappoint.width < 0 && e.getY() > owner.getHeight() - snappoint.height) {
            // bottomLeft
            bottomLeft = true;
        } else if(e.getY() > owner.getHeight() - snappoint.height) {
            // bottom
            bottom = true;
        } else if(e.getX() - snappoint.width < 0) {
            // left
            left = true;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        // remove the start point
        prev = null;

        // reset the Booleans
        upperLeft = false;
        upper = false;
        upperRight = false;
        right = false;
        bottomRight = false;
        bottom = false;
        bottomLeft = false;
        left = false;
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

        if(!owner.isResizable())
            return;

        /**
         * To prevent stopping the current frame make the calculation in a extra Thread
         */
        class CalculationThread extends Thread {
            private final MouseEvent e;

            /**
             * @param e Mouseevent is the basic of  the calculations
             */
            public CalculationThread (MouseEvent e) {
                this.e = e;
                start();
            }

            @Override
            public void run() {
                super.run();

                // save the current as an backup
                Rectangle backup = owner.getBounds();

                // calculate the distance to the start point to make sure the hover don´t stop while dragging
                try {
                    Velocity = Math.abs(e.getXOnScreen() - prev.width) + Math.abs(e.getYOnScreen() - prev.height);
                } catch (Exception ignored) {
                    Velocity = 0;
                }

                // Calculate the new Positions
                int widthPos = owner.getX() - e.getXOnScreen();
                int heightPos = owner.getY() - e.getYOnScreen();
                int widthNeg = e.getXOnScreen() - (owner.getX() + owner.getWidth());
                int heightNeg = e.getYOnScreen() - (owner.getY() + owner.getHeight());

                // init the new Bounds
                Rectangle newBounds = owner.getBounds();

                // change bounds based on the previously calculated start point / boolean
                if (upperLeft) {
                    newBounds = new Rectangle(e.getXOnScreen(), e.getYOnScreen(), owner.getWidth() + widthPos, owner.getHeight() + heightPos);
                } else if (upperRight) {
                    newBounds = new Rectangle(owner.getX(), e.getYOnScreen(), owner.getWidth() + widthNeg, owner.getHeight() + heightPos);
                } else if (upper) {
                    newBounds = new Rectangle(owner.getX(), e.getYOnScreen(), owner.getWidth(), owner.getHeight() + heightPos);
                } else if(bottomRight) {
                    newBounds = new Rectangle(owner.getX(), owner.getY(), owner.getWidth() + widthNeg, owner.getHeight() + heightNeg);
                } else if(right) {
                    newBounds = new Rectangle(owner.getX(), owner.getY(), owner.getWidth() + widthNeg, owner.getHeight());
                } else if(bottomLeft) {
                    newBounds = new Rectangle(e.getXOnScreen(), owner.getY(), owner.getWidth() + widthPos, owner.getHeight() + heightNeg);
                } else if(bottom) {
                    newBounds = new Rectangle(owner.getX(), owner.getY(), owner.getWidth(), owner.getHeight() + heightNeg);
                } else if(left) {
                    newBounds = new Rectangle(e.getXOnScreen(), owner.getY(), owner.getWidth() + widthPos, owner.getHeight());
                }

                // prevent the frame from getting under the minimum size
                if (newBounds.width <= owner.getMinimumSize().width)
                    newBounds = new Rectangle(backup.x, newBounds.y, backup.width, newBounds.height);

                if (newBounds.height <= owner.getMinimumSize().height)
                    newBounds = new Rectangle(newBounds.x, backup.y, newBounds.width, backup.height);

                // apply the new Bounds
                owner.setBounds(newBounds);
            }
        }

        // make new Thread for calculation
        new CalculationThread(e);
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

        // break if the frame should´nt be able to resize
        if(!owner.isResizable())
            return;

        /**
         * To prevent stopping the current frame make the calculation in a extra Thread
         */
        class CalculationThread extends Thread {
            private final MouseEvent e;

            /**
             * @param e Mouseevent is the basic of  the calculations
             */
            public CalculationThread (MouseEvent e) {
                this.e = e;
                start();
            }

            @Override
            public void run() {
                super.run();

                // set the Correct Cursor based on the mouse Position
                if(e.getY()-snappoint.height-Velocity < 0 && e.getX()-snappoint.width-Velocity < 0) {
                    owner.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
                } else if(e.getY()-snappoint.height-Velocity < 0 && e.getX() > owner.getWidth()-snappoint.width-Velocity) {
                    owner.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
                } else if(e.getY()-snappoint.width-Velocity < 0) {
                    owner.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
                } else if(e.getX() > owner.getWidth()-snappoint.width-Velocity && e.getY() > owner.getHeight()-snappoint.height-Velocity) {
                    owner.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
                } else if(e.getX() > owner.getWidth()-snappoint.width-Velocity) {
                    owner.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
                } else if(e.getX()-snappoint.width-Velocity < 0 && e.getY() > owner.getHeight()-snappoint.height-Velocity) {
                    owner.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
                } else if(e.getY() > owner.getHeight()-snappoint.height-Velocity) {
                    owner.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
                } else if(e.getX() - snappoint.width - Velocity < 0) {
                    owner.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
                } else
                    owner.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                // reset the Distance
                Velocity = 0;

                e.consume();
            }
        }

        // start new Thread for Calculation
        new CalculationThread(e);
    }
    
}
