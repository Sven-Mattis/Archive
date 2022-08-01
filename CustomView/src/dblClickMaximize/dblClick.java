package dblClickMaximize;

import CustomView.ViewBase;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class dblClick extends MouseAdapter {

    private final ViewBase owner;
    private boolean click1 = false;
    
    public dblClick (ViewBase v) {
        this.owner = v;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if(!owner.isResizable())
            return;

        if(!click1) {
            new Thread(() -> {
                try {
                    click1 = true;
                    Thread.sleep(500);
                    click1 = false;
                } catch (Exception interruptedException) {
                    interruptedException.printStackTrace();
                }
            }).start();
        } else {
            owner.getMaximizedAction().execute();
        }
    }
}
