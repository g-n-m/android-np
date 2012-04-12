package com.itk.gasnami.catan;

import android.graphics.Canvas;

public class CatanThread extends Thread {
	private Panel panel;
	private boolean run = false;
	
	/**
     * Constructor.
     * 
     * @param panel View class on which we trigger the drawing.
     */
    public CatanThread(Panel panel) {
        this.panel = panel;
    }
    
    /**
     * @param run Should the game loop keep running? 
     */
    public void setRunning(boolean run) {
        this.run = run;
    }
    
    /**
     * @return If the game loop is running or not.
     */
    public boolean isRunning() {
        return run;
    }
    
    /**
     * Perform the game loop.
     * Order of performing:
     * 1. testDraw 4 debug
     * 2. ...
     * 3. ...
     */
    @Override
    public void run() {
        Canvas c;
        while (run) {
            c = null;
            try {
                c = panel.getHolder().lockCanvas(null);
                synchronized (panel.getHolder()) {
                    // panel.updatePhysics();
                    // panel.checkForWinners();
                    panel.onDraw(c);
                }
            } finally {
                // do this in a finally so that if an exception is thrown
                // during the above, we don't leave the Surface in an
                // inconsistent state
                if (c != null) {
                    panel.getHolder().unlockCanvasAndPost(c);
                }
            }
        }
    }
}
