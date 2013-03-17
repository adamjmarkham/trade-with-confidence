/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tradewc.datasource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import org.encog.ml.data.market.MarketDataType;
import org.encog.ml.data.market.MarketMLDataSet;
import org.encog.ml.data.market.TickerSymbol;
import org.encog.ml.data.market.loader.LoadedMarketData;
import org.encog.ml.data.market.loader.MarketLoader;
import org.encog.ml.data.market.loader.YahooFinanceLoader;
import org.joda.time.DateTime;

/**
 * Class which represents a Yahoo Finance data source and gets stock data.
 * 
 * @author Adam John Markham <adamjmarkham@gmail.com>
 */
public class YahooFinanceDataSource implements NNStockMarketDataSource {
    
    private MarketLoader marketLoader;
    private static final Map<String, MarketDataType> marketTypeMap = getMapOfMarketTypes();
    private Set<MarketDataType> fieldsRequired;
    private String stockSymbol;
    private Collection<LoadedMarketData> loadedMarketData;
    private DateTime from;
    private DateTime to;
    private MarketMLDataSet trainingDataSet;
    private List<HashMap<String, Object>> stockDataAsMap;
    
    /**
     * Constructor for a YahooFinanceDataSource object.
     */
    public YahooFinanceDataSource() {
        
        this.stockSymbol = "RBS.L";
        this.marketLoader = new YahooFinanceLoader();
        this.fieldsRequired = new TreeSet<MarketDataType>();
        this.fieldsRequired.add(MarketDataType.LOW);
        this.fieldsRequired.add(MarketDataType.HIGH);
        this.fieldsRequired.add(MarketDataType.ADJUSTED_CLOSE);
        this.fieldsRequired.add(MarketDataType.CLOSE);
        this.fieldsRequired.add(MarketDataType.OPEN);
        this.fieldsRequired.add(MarketDataType.VOLUME);
        this.to = new DateTime();
        this.from = new DateTime().minusDays(30);
        this.stockDataAsMap = new ArrayList<HashMap<String, Object>>();
        this.trainingDataSet = new MarketMLDataSet(marketLoader, 10, 1);
        this.trainingDataSet.generate();
    }
    
    /**
     * Sets the fields required for this data source to retrieve.
     * 
     * @param fields a string of fields required
     */
    @Override
    public void setFieldsRequired(String fields) {
        
        this.fieldsRequired = new TreeSet<MarketDataType>();
        
        String[] splitFields = fields.split(" ");
        
        for (String field : splitFields) {
            
            MarketDataType marketType = this.marketTypeMap.get(field);
            this.fieldsRequired.add(marketType);
        }
    }
    
    /**
     * Sets the stock symbol to get data on.
     * 
     * @param stockSymbol the stock symbol to get data on.
     */
    @Override
    public void setStockSymbol(String stockSymbol){
        
        this.stockSymbol = stockSymbol;
    }
    
    /**
     * Gets the stock symbol being used.
     * 
     * @return stockSymbol as a string
     */
    public String getStockSymbol(){
        
        return this.stockSymbol;
    }

    /**
     * Sets the from date for the data source.
     * 
     * @param from the date
     */
    @Override
    public void setFromDate(Date from) {
        
        this.from = new DateTime(from);
    }

    /**
     * Sets the to date for the data source.
     * 
     * @param to the date
     */
    @Override
    public void setToDate(Date to) {
        
        this.to = new DateTime(to);
    }
    
    /**
     * Pulls data from the source.
     * 
     */
    @Override
    public void pullDataFromSource() {
        
        this.loadedMarketData = marketLoader.load(new TickerSymbol(stockSymbol), fieldsRequired, from.toDate(), to.toDate());   
        int inputWindow = 10;
        int predictWindow = 1;
        this.stockDataAsMap = getStockDataAsMap();
        this.trainingDataSet = new MarketMLDataSet(marketLoader, 10, 1);
    }
    
    /**
     * Gets the stock data as training data.
     * 
     * @return trainingDataSet the training data
     */
    @Override
    public MarketMLDataSet getTrainingData(){
        
        return this.trainingDataSet;
    }
    
    private List<HashMap<String, Object>> getStockDataAsMap(){
        
        List<HashMap<String, Object>> stockDataAsMap = new ArrayList<HashMap<String, Object>>();
        
        for (LoadedMarketData data : this.loadedMarketData) {
            
            HashMap<String,Object> stockEntry = new HashMap<String, Object>();
            stockEntry.put("date", data.getWhen());
            
            for (Entry<String, MarketDataType> entry : marketTypeMap.entrySet()) {
                
                double fieldValue = data.getData(entry.getValue());
                stockEntry.put(entry.getKey(), fieldValue);
            } 
            
            stockDataAsMap.add(stockEntry);
        } 
        
        return stockDataAsMap;
    }
    
    /**
     * Returns the stock data as a list of HashMaps.
     * 
     * @return stockDataAsMap returns a list of stock data maps.
     */
    @Override
    public  List<HashMap<String, Object>> getStockData() {
 
        return this.stockDataAsMap;
    }
    
    private static Set<MarketDataType> getReqFieldsAsSet(String fields) {
        
        Set<MarketDataType> setOfMarketTypes = new TreeSet<MarketDataType>();
        
        String[] splitFields = fields.split(" ");
        
        for (String field : splitFields) {
            
            MarketDataType marketType = marketTypeMap.get(field);
            setOfMarketTypes.add(marketType);
        }
        
        return setOfMarketTypes;
    }
    
    private static Map<String, MarketDataType> getMapOfMarketTypes() {
        
        Map<String, MarketDataType> map = new HashMap<String, MarketDataType>();
        
        map.put("open", MarketDataType.OPEN);
        map.put("close", MarketDataType.CLOSE);
        map.put("low", MarketDataType.LOW);
        map.put("high", MarketDataType.HIGH);
        map.put("volume", MarketDataType.VOLUME);
        map.put("adjclose", MarketDataType.ADJUSTED_CLOSE);
        
        return map;
    }

    /**
     * Returns the to date for this data source.
     * 
     * @return to the date.
     */
    public Date getToDate() {
        
        return this.to.toDate();
    }

    /**
     * Returns the from date for this data source.
     * 
     * @return from the date.
     */
    public Date getFromDate() {
        
        return this.from.toDate();
    }
}
