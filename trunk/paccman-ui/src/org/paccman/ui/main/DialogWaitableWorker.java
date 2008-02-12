/*
 *
 *  Copyright (C)    2007 Joao F. (joaof@sourceforge.net)
 *                   http://paccman.sourceforge.net 
 *
 *  This program is free software; you can redistribute it and/or modify      
 *  it under the terms of the GNU General Public License as published by      
 *  the Free Software Foundation; either version 2 of the License, or         
 *  (at your option) any later version.                                       
 *
 *  This program is distributed in the hope that it will be useful,           
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of            
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             
 *  GNU General Public License for more details.                              
 *
 *  You should have received a copy of the GNU General Public License         
 *  along with this program; if not, write to the Free Software               
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 *
 */

package org.paccman.ui.main;

import java.awt.Frame;
import javax.swing.SwingWorker;

/**
 * SwingWorker that has an associated progress (waitable) dialog.
 * @author jfer
 * @param T
 * @param V
 */
public abstract class DialogWaitableWorker<T, V> extends SwingWorker<T, V> {

    /**
     * Called in the EDT when the task has sucessfully finished.
     */
    public abstract void whenDone();

    /**
     * Called in the EDT when the task has raised an exception.
     */
    public abstract void whenFailed(Exception e);

    /**
     * The task routine of the worker thread performed in background.
     * @return T
     */
    public abstract T backgroundTask() throws Exception;

    /**
     * Start the worker thread.
     */
    public void start() {
        waitDialog = new WaitDialog(this, parent, title);
        if (cntSteps == -1) {
            waitDialog.setIndeterminate();
        }
        addPropertyChangeListener(waitDialog);
        execute();
        // Display dialog after starting the thread because the dialog is modal.
        waitDialog.setVisible(true);
    }

    public class Step {

        public String getDescription() {
            return description;
        }

        public int getStep() {
            return step;
        }
        String description;
        int step = -1;

        int getProgress() {
            return (step * 100) / cntSteps;
        }
    }
    Step currentStep = new Step();

    /**
     * Set the next step for this task.
     * @param stepDescription A string describing the step currently performed 
     * in the worker thread. Should be called in the worker thread routine.
     */
    protected void nextStep(String stepDescription) {
        currentStep.step++;
        currentStep.description = stepDescription;
        firePropertyChange("step", null, currentStep);
        // try { Thread.sleep(10000); } catch (Exception e) {} //:TODO:for debug only
    }
    private WaitDialog waitDialog;
    private int cntSteps;
    private String title;
    private Frame parent;

    /**
     * 
     * @param title
     * @param cntSteps The number of steps of the task (for progress bar). -1 if 
     * undefined progress bar is wanted.
     * @param parent
     */
    public DialogWaitableWorker(String title, int cntSteps, Frame parent) {
        this.cntSteps = cntSteps;
        this.title = title;
        this.parent = parent;
    }

    @Override
    protected void done() {
        waitDialog.setVisible(false);
        waitDialog.dispose();
        try {
            get();
            whenDone();
        } catch (Exception e) {
            whenFailed(e);
        }
    }

    @Override
    protected T doInBackground() throws Exception {
        return backgroundTask();
    }
}
