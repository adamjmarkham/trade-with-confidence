/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tradewc.neuralnetwork;

import com.tradewc.datasource.NNStockMarketDataSource;
import com.tradewc.datasource.YahooFinanceDataSource;

/**
 * Factory class which creates a neural network.
 *
 * @author adammarkham
 */
public class NeuralNetworkFactory {
    
    private static StockMarketNeuralNetwork neuralNetwork;
    
    /**
     * Returns a StockMarketNeuralNetwork from a data source, numNeurons in layer 1 and layer 2.
     * 
     * @param dataSource the data source with the training data.
     * @param numNeuronesLayer1 the number of neurones in layer 1.
     * @param numNeuronesLayer2 the number of neurones in layer 2.
     * @return 
     */
    public static StockMarketNeuralNetwork createStockMarketPriceNeuralNetwork(NNStockMarketDataSource dataSource, int numNeuronesLayer1, int numNeuronesLayer2){

        neuralNetwork = new StockPriceNeuralNetwork(dataSource, numNeuronesLayer2, numNeuronesLayer2);
        
       // neuralNetwork.generateTrainingData();
       // neuralNetwork.loadTrainingDataIntoNetwork();
        //neuralNetwork.setTrainingTime(60);
        //neuralNetwork.setPercentageTrainingError(0.001);
        //neuralNetwork.setTrainingTimeout(60);
        
        return neuralNetwork;
    }
}
