/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tradewc.datasource;

import java.util.Date;

/**
 * Factory for creating a stock market data source.
 * 
 * @author Adam John Markham <adamjmarkham@gmail.com>
 */
public class StockMarketDataSourceFactory {
    
    /**
     * Configures and returns a NNStockMarketDataSource.
     * 
     * @param stockSymbol the stockSymbol to get a data source for
     * @param from the date to get data from
     * @param to the date to get data to
     * @return NNStockMarketSource the data source
     */
    public static NNStockMarketDataSource getNNStockMarketDataSource(String stockSymbol, Date from, Date to){

        NNStockMarketDataSource dataSource = new YahooFinanceDataSource();
        dataSource.setStockSymbol(stockSymbol);
        dataSource.setFieldsRequired("high low close volume open adjclose");
        
        try{
            
            dataSource.setFromDate(from);
            dataSource.setToDate(to);
        }catch(NumberFormatException nfe){
            
            throw new IllegalArgumentException("dateFrom and dateTo must only contains numbers in the format dd/mm/yyyy seperated by forward slashes");
        }
        
        return dataSource;
    }
}
