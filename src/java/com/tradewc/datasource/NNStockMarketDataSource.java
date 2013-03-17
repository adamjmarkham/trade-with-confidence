/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tradewc.datasource;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.encog.ml.data.market.MarketMLDataSet;

/**
 * An interface for a neural network stock market data source.
 * 
 * @author Adam John Markham <adamjmarkham@gmail.com>
 */
public interface NNStockMarketDataSource extends Serializable {

    List<HashMap<String, Object>> getStockData();

    MarketMLDataSet getTrainingData();

    void pullDataFromSource();

    void setFieldsRequired(String fields);
    
    void setStockSymbol(String stockSymbol);
    
    String getStockSymbol();

    void setToDate(Date to);

    void setFromDate(Date from);
    
    Date getToDate();
    
    Date getFromDate();
    
}
