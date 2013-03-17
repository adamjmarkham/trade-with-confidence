package com.tradewc.main;

import com.tradewc.datasource.NNStockMarketDataSource;
import com.tradewc.datasource.StockMarketDataSourceFactory;
import com.tradewc.neuralnetwork.NeuralNetworkFactory;
import com.tradewc.neuralnetwork.StockMarketNeuralNetwork;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import org.joda.time.DateTime;


/**
 * Hello world!
 *
 */
public class TradeWithConfidence 
{
    public static void main( String[] args ) throws Exception
    {
       List<Observer> observers = new ArrayList<Observer>();
       observers.add(new Obs());
       DateTime from = new DateTime(2004,6,1,0,0,0);
       DateTime to = new DateTime(2010,7,1,0,0,0);
       NNStockMarketDataSource dataSource = StockMarketDataSourceFactory.getNNStockMarketDataSource("RBS.L", from.toDate(), to.toDate());
       StockMarketNeuralNetwork neuralNetwork  = NeuralNetworkFactory.createStockMarketPriceNeuralNetwork(dataSource,50,50);
       neuralNetwork.setTrainingTime(5);
       
       for(Observer observer : observers)
           neuralNetwork.addObserver(observer);
       
      // neuralNetwork.trainingComplete();
     //  FileOutputStream fos = new FileOutputStream("t.tmp");
      // ObjectOutputStream out = new ObjectOutputStream(fos);
       
       //out.writeObject(neuralNetwork);
       
       neuralNetwork.trainForTime();
       System.out.println(neuralNetwork.predict("RBS.L")*100);
      // neuralNetwork.getPredictions(null);
       //neuralNetwork.determineBestNeuralNetwork(10, 5);
    }
}
