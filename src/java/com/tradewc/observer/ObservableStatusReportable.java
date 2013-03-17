/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tradewc.observer;

import java.util.List;
import java.util.Observer;
import org.encog.StatusReportable;

/**
 *
 * @author Adam John Markham <adamjmarkham@gmail.com>
 */
public class ObservableStatusReportable implements StatusReportable, Observable {

    private List<Observer> observers;
    private boolean observableChanged;
    
    public void report(final int total, final int current,
            final String message) {
        
        if (total == 0) {
            System.out.println(current + " : " + message);
        } else {
            System.out.println(current + "/" + total + " : " + message);
        }
    }

    /**
     * 
     * @param obsrvr
     */
    public void addObserver(Observer obsrvr) {
        this.observers.add(obsrvr);
    }

    /**
     * 
     * @param obsrvr
     */
    public void deleteObserver(Observer obsrvr) {
        this.observers.remove(obsrvr);
    }

    /**
     * 
     */
    public void notifyObservers() {

        if (hasChanged()) {
            for (Observer observer : observers) {
                observer.update(new java.util.Observable(), "updated");
            }

            clearChanged();
        }

    }

    /**
     * 
     * @param o
     */
    public void notifyObservers(Object o) {

        if (hasChanged()) {
            for (Observer observer : observers) {
                observer.update(new java.util.Observable(), o);
            }

            clearChanged();
        }
    }

    /**
     * 
     */
    public void deleteObservers() {
        this.observers.removeAll(observers);
    }

    /**
     * 
     */
    public void setChanged() {
        this.observableChanged = true;
    }

    /**
     * 
     */
    public void clearChanged() {
        this.observableChanged = false;
    }

    /**
     * 
     * @return
     */
    public boolean hasChanged() {
        return this.observableChanged;
    }

    /**
     * 
     * @return
     */
    public int countObservers() {
        return this.observers.size();
    }
}
