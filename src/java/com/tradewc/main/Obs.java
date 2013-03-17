/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tradewc.main;

import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Adam John Markham <adamjmarkham@gmail.com>
 */
public class Obs implements Observer{

    public void update(Observable o, Object o1) {
        System.out.println(o1);
    }
    
}