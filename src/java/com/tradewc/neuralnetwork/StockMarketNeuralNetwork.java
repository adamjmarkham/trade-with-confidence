/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tradewc.neuralnetwork;

import com.tradewc.observer.Observable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * An interface which gives common methods for a stock market neural network.
 * 
 * @author Adam John Markham <adamjmarkham@gmail.com>
 */
public interface StockMarketNeuralNetwork extends Observable, Runnable, Serializable {
    
    boolean trainingComplete();


    void generateTrainingData();


    int getNumInputNeurones();

    int getNumLayers();


    List<Integer> getNumNeuronesForLayers();


    int getNumOutputNeurones();

  
    List<HashMap<String, Double>> getTrainingData();

  
    int getTrainingTime(int seconds);

   
    void loadTrainingDataIntoNetwork();

   
    List<HashMap<String,Double>> getPredictions(List<HashMap<String,Double>> data);


    void pauseTraining();


    void resumeTraining();
    
    List<ArrayList<Double>> evaluate(String stockSymbol, Date from, Date to);
    
    double predict(String stockSymbol);


    void run();


    void setFieldsRequired(List<String> fieldsRequired);

    void setPercentageTrainingError(double percentError);

  
    void setStockSymbol(String stockSymbol);

   
    void setTrainingTime(int seconds);

    void setTrainingTimeout(int seconds);

    void trainForTime();

  
    void trainToError();
    
    void determineBestNeuralNetwork(int iterationsPerNetwork, int numNetworksTried);
   
    
}
