/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tradewc.observer;

import java.util.Observer;

/**
 *
 * @author Adam John Markham <adamjmarkham@gmail.com>
 */
public interface Observable{
    
    /**
     * 
     * @param obsrvr
     */
    void addObserver(Observer obsrvr);

    /**
     * 
     * @param obsrvr
     */
    void deleteObserver(Observer obsrvr);
    
    /**
     * 
     */
    void notifyObservers();

    /**
     * 
     * @param o
     */
    void notifyObservers(Object o);

    /**
     * 
     */
    void deleteObservers();

    /**
     * 
     */
    void setChanged();

    /**
     * 
     */
    void clearChanged();

    /**
     * 
     * @return
     */
    boolean hasChanged();

    /**
     * 
     * @return
     */
    int countObservers();
}
