package com.tradewc.neuralnetwork;

import com.tradewc.datasource.NNStockMarketDataSource;
import com.tradewc.observer.ObservableStatusReportable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observer;
import java.util.Set;
import java.util.TreeSet;
import org.encog.Encog;
import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.market.MarketDataDescription;
import org.encog.ml.data.market.MarketDataType;
import org.encog.ml.data.market.MarketMLDataSet;
import org.encog.ml.data.market.TickerSymbol;
import org.encog.ml.data.market.loader.LoadedMarketData;
import org.encog.ml.data.market.loader.MarketLoader;
import org.encog.ml.data.market.loader.YahooFinanceLoader;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.propagation.Propagation;
import org.encog.neural.networks.training.propagation.TrainingContinuation;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.neural.pattern.FeedForwardPattern;
import org.encog.neural.prune.PruneIncremental;
import org.encog.util.simple.EncogUtility;
import org.joda.time.DateTime;

/**
 * Represents a Neural Network which predicts future stock prices.
 * 
 * The neural network created is a feed-forward network using the back-propagation 
 * algorithm to train the network with stock data.
 * 
 * Data is pulled in from Yahoo Finance for a particular stock symbol and normalized
 * into training data. 
 *
 * @author adammarkham
 */
public class StockPriceNeuralNetwork implements StockMarketNeuralNetwork {
    
    private TickerSymbol stockSymbol;
    private Set<MarketDataType> fieldsRequired;
    //private DateTime from;
    // private DateTime to;
    private Collection<LoadedMarketData> loadedMarketData;
    private Map<String, MarketDataType> fieldToMarketType;
    private HashMap<String, Double> results;
    private BasicNetwork neuralNetwork;
    private MarketMLDataSet trainingDataSet;
    private Propagation trainingMethod;
    private ActivationFunction activationFunction = new ActivationSigmoid();
    private TrainingContinuation trainingStage = new TrainingContinuation();
    private int trainingTime;
    private List<Integer> layers;
    private List<Observer> observers;
    private boolean observableChanged = false;
    private double percentageTrainingError;
    private int trainingTimeoutSeconds;
    private NNStockMarketDataSource dataSource;
    private int numNeuronesLayer1;
    private int numNeuronesLayer2;
    private boolean trainingFinished = false;

    /**
     * Constructor which creates a feed-forward StockPriceNeuralNetwork
     * 
     * @param dataSource the data source.
     * @param numNeuronesLayer1 the number of neurones in layer 1.
     * @param numNeuronesLayer2 the number of neurones in layer 2.
     * 
     */
    public StockPriceNeuralNetwork(NNStockMarketDataSource dataSource, int numNeuronesLayer1, int numNeuronesLayer2) {
        
        if (numNeuronesLayer1 < 10 || numNeuronesLayer2 < 10) {
            throw new IllegalArgumentException("Number of neurones must be at least 10");
        }
        
        this.numNeuronesLayer1 = numNeuronesLayer1;
        this.numNeuronesLayer2 = numNeuronesLayer2;
        this.dataSource = dataSource;
        this.stockSymbol = new TickerSymbol(dataSource.getStockSymbol());
        this.fieldToMarketType = mapFieldToMarketType();
        this.fieldsRequired = getFieldsRequired(getDefaultFields());
        // this.to = new DateTime();
        // this.from = new DateTime().minusDays(30);
        this.loadedMarketData = new ArrayList<LoadedMarketData>();
        // this.dataSource.pullDataFromSource();
        generateTrainingData();
        loadTrainingDataIntoNetwork();
        // this.trainingMethod = new Backpropagation(neuralNetwork, trainingDataSet);
        //  this.layers = getNumNeuronesForLayers();
        this.trainingTime = 60;
        this.observers = new ArrayList<Observer>();
        this.percentageTrainingError = 0.1;
        this.trainingTimeoutSeconds = 30;
        this.trainingMethod = new ResilientPropagation(neuralNetwork, trainingDataSet);
    }
    
    /**
     * Sets the percentage training error for the neural network.
     * 
     * @param percentError the percentage error
     */
    @Override
    public void setPercentageTrainingError(double percentError) {
        
        this.percentageTrainingError = percentError;
    }

    /**
     * Sets the training time in seconds.
     * 
     * @param seconds training time
     */
    @Override
    public void setTrainingTime(int seconds) {
        
        this.trainingTime = seconds;
    }

    /**
     * Gets the training time in seconds.
     * 
     * @param seconds training time
     *
     */
    @Override
    public int getTrainingTime(int seconds) {
        
        return this.trainingTime;
    }

    /**
     * Returns the number of layers in the neural network.
     * 
     * @return numLayers the number of layers.
     */
    @Override
    public int getNumLayers() {
        
        return this.neuralNetwork.getLayerCount();
    }

    /**
     * Returns the number of input neurones.
     * 
     * @return numInputNeurones the number of inputs into the neural network.
     */
    @Override
    public int getNumInputNeurones() {
        
        return this.neuralNetwork.getInputCount();
    }

    /**
     * Returns the number of output neurones from the neural network.
     * 
     * @return numOutputNeurones the number of output neurones from the neural network.
     */
    @Override
    public int getNumOutputNeurones() {
        
        return this.neuralNetwork.getOutputCount();
    }

    /**
     * Gets the number of neurones in each layer.
     * 
     * @return layers the hidden layers
     */
    @Override
    public List<Integer> getNumNeuronesForLayers() {
        
        ArrayList<Integer> layers = new ArrayList<Integer>();
        
        for (int layer = 0; layer < getNumLayers(); layer++) {
            layers.add(neuralNetwork.getLayerNeuronCount(layer));
        }
        
        return layers;
    }

    /**
     * Loads the training data into the network.
     * 
     */
    @Override
    public void loadTrainingDataIntoNetwork() {
        
        this.neuralNetwork = EncogUtility.simpleFeedForward(trainingDataSet.getInputSize(), this.numNeuronesLayer1, this.numNeuronesLayer2, trainingDataSet.getIdealSize(), true);

        // EncogUtility.trainConsole(this.neuralNetwork, this.trainingDataSet, Config.TRAINING_MINUTES);

        //evaluate(this.neuralNetwork);
    }

    /**
     * Sets the fields required for input into the neural network.
     * 
     * @param fieldsRequired the fields that are required for input.
     */
    @Override
    public void setFieldsRequired(List<String> fieldsRequired) {
        
        this.fieldsRequired = getFieldsRequired(fieldsRequired);
    }

    /**
     * Sets the stock symbol the neural network is used for.
     * 
     * @param stockSymbol the stock symbol
     */
    @Override
    public void setStockSymbol(String stockSymbol) {
        
        this.stockSymbol = new TickerSymbol(stockSymbol);
    }

    /**
     * Generates the training data for the neural network.
     * 
     */
    @Override
    public void generateTrainingData() {

        //this.dataSource.pullDataFromSource();
        this.trainingDataSet = dataSource.getTrainingData();
        MarketDataDescription desc = new MarketDataDescription(stockSymbol, MarketDataType.ADJUSTED_CLOSE, true, true);
        trainingDataSet.addDescription(desc);
        trainingDataSet.load(this.dataSource.getFromDate(), this.dataSource.getToDate());
        trainingDataSet.generate();
        
        
    }
    
    private static String buildUpdate(int iterationNum, double error, int timeRemaining) {
        
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("Iteration Number: ");
        strBuilder.append(iterationNum);
        strBuilder.append("\nCurrent Error: ");
        BigDecimal round = new BigDecimal(error);
        round = round.round(new MathContext(4));
        double roundedError = round.doubleValue();
        strBuilder.append(roundedError);
        strBuilder.append("%\nTime remaining: ");
        strBuilder.append(Math.abs(timeRemaining));
        strBuilder.append("s\n--------------------------------------\n");
        
        return strBuilder.toString();
    }

    /**
     * Trains the neural network for a previously specified time.
     */
    @Override
    public void trainForTime() {

        // EncogUtility.trainConsole(this.neuralNetwork, this.trainingDataSet, this.trainingTime/60);

        this.trainingFinished = false;

        // generateTrainingData();
        // loadTrainingDataIntoNetwork();
        this.trainingMethod = new ResilientPropagation(neuralNetwork, trainingDataSet);
        trainingMethod.setThreadCount(0);
        
        int startTime = DateTime.now().getSecondOfDay();
        int timeRemaining = 0;
        int iterationNum = 0;
        
        
        do {
            
            trainingMethod.iteration();
            iterationNum = trainingMethod.getIteration();
            int currentTime = DateTime.now().getSecondOfDay();
            int timeElapsed = currentTime - startTime;
            timeRemaining = trainingTime - timeElapsed;
            double currentError = this.trainingMethod.getError();
            this.setChanged();
            this.notifyObservers(buildUpdate(iterationNum, currentError, timeRemaining));
            
        } while (timeRemaining > 0);
        
        trainingMethod.finishTraining();
        
        Encog.getInstance().shutdown();
        
        this.trainingFinished = true;
    }

    /**
     * Trains the neural network to a specified training error.
     * 
     */
    @Override
    public void trainToError() {
        
        this.trainingFinished = false;
        
        this.trainingMethod = new ResilientPropagation(neuralNetwork, trainingDataSet);
        trainingMethod.setThreadCount(0);
        
        int startTime = DateTime.now().getSecondOfDay();
        int timeRemaining = 0;
        int iterationNum = 0;
        int timeElapsed = 0;
        
        double acceptableError = this.percentageTrainingError;
        double currentError = 0.0;
        
        do {
            
            trainingMethod.iteration();
            iterationNum = trainingMethod.getIteration();
            int currentTime = DateTime.now().getSecondOfDay();
            timeElapsed = currentTime - startTime;
            timeRemaining = trainingTime - timeElapsed;
            currentError = trainingMethod.getError();
            
            this.setChanged();
            this.notifyObservers(buildUpdate(iterationNum, currentError, timeRemaining));
            
        } while (timeRemaining > 0 && currentError > acceptableError && timeElapsed < trainingTimeoutSeconds);
        
        if (timeRemaining > 0) {
            
            this.setChanged();
            this.notifyObservers("Training timed out.....\nFinal % error is " + currentError);
        }
        
        trainingMethod.finishTraining();
        
        Encog.getInstance().shutdown();
        
        this.trainingFinished = true;
    }
    
    /**
     * Returns true if training has been completed.
     * 
     * @return true is training has completed
     */
    @Override
    public boolean trainingComplete() {
        
        return this.trainingFinished;
    }
    
    /**
     * Determines the best neural network
     * 
     * @param iterationsPerNetwork the number of iterations to try
     * @param numNetworksTried the number of networks to try
     */
    @Override
    public void determineBestNeuralNetwork(int iterationsPerNetwork, int numNetworksTried) {
        
        if (iterationsPerNetwork < 1) {
            throw new IllegalArgumentException("iterationsPerNetwork must be greater than 0");
        } else if (numNetworksTried < 1) {
            throw new IllegalArgumentException("numNetworksTried must be greater than 0");
        }
        
        
        FeedForwardPattern pattern = new FeedForwardPattern();
        pattern.setInputNeurons(trainingDataSet.getInputSize());
        pattern.setOutputNeurons(trainingDataSet.getIdealSize());
        pattern.setActivationFunction(activationFunction);
        
        PruneIncremental prune = new PruneIncremental(trainingDataSet, pattern, iterationsPerNetwork, 1, numNetworksTried, new ObservableStatusReportable());
        prune.addHiddenLayer(5, 50);
        prune.addHiddenLayer(0, 50);
        prune.process();
        
        this.neuralNetwork = prune.getBestNetwork();
        Encog.getInstance().shutdown();
    }
    
    /**
     * Sets a timeout for training.
     * 
     * @param seconds the timeout for training.
     */
    @Override
    public void setTrainingTimeout(int seconds) {
        
        this.trainingTimeoutSeconds = seconds;
    }

    /**
     * Gets the training data.
     * 
     * @return trainingData a list of stock maps as HashMaps
     */
    @Override
    public List<HashMap<String, Double>> getTrainingData() {
        
        return getResults();
    }

    /**
     * Pauses the neural network currently being trained.
     * 
     */
    @Override
    public void pauseTraining() {
        
        this.trainingStage = trainingMethod.pause();
    }

    /**
     * Resumes training the neural network.
     * 
     */
    @Override
    public void resumeTraining() {
        
        trainingMethod.resume(this.trainingStage);
    }

    /**
     * Generates a prediction for the neural network.
     * 
     * @param stockSymbol the stock symbol being predicted.
     * @return predict the prediction as a double.
     */
    @Override
    public double predict(String stockSymbol) {
        
        MarketLoader loader = new YahooFinanceLoader();
        MarketMLDataSet result = new MarketMLDataSet(loader,
                10, 1);
        MarketDataDescription desc = new MarketDataDescription(new TickerSymbol(stockSymbol),
                MarketDataType.ADJUSTED_CLOSE, true, true);
        
        result.addDescription(desc);
        
        DateTime now = DateTime.now();
        int fromMonth = now.monthOfYear().get();
        int fromDay = now.dayOfMonth().get();
        int fromYear = now.year().get();
        
        DateTime to = now.plusDays(30);
        int toMonth = to.monthOfYear().get();
        int toDay = to.dayOfMonth().get();
        int toYear = to.year().get();
        
        result.load(DateTime.now().minusDays(30).toDate(), DateTime.now().toDate());
        result.generate();
        
        MLDataPair pair = result.getData().get(result.getData().size() - 1);
        MLData input = pair.getInput();
        
        input = this.neuralNetwork.compute(input);
        double predicted = input.getData(0);
        
        return predicted;
    }
    
    /**
     * Evaluates the neural network to obtain actual and predicted values.
     * 
     * @param stockSymbol the stock symbol to evaluate
     * @param from the date to evaluate from
     * @param to the date to evaluate to
     * @return evaluation a list of actual and predicted values.
     */
    @Override
    public List<ArrayList<Double>> evaluate(String stockSymbol, Date from, Date to) {
        
        List<ArrayList<Double>> evaluations = new ArrayList<ArrayList<Double>>();
        
        MarketLoader loader = new YahooFinanceLoader();
        MarketMLDataSet result = new MarketMLDataSet(loader,
                10, 1);
        MarketDataDescription desc = new MarketDataDescription(new TickerSymbol(stockSymbol),
                MarketDataType.ADJUSTED_CLOSE, true, true);
        
        result.addDescription(desc);
        
        result.load(from, to);
        result.generate();
        
        for (MLDataPair pair : result) {
            
            MLData input = pair.getInput();
            MLData actualData = pair.getIdeal();
            MLData predictData = this.neuralNetwork.compute(input);
            
            double actual = actualData.getData(0);
            double predict = predictData.getData(0);
            // double diff = Math.abs(predict - actual);

            ArrayList<Double> evalPair = new ArrayList<Double>();
            evalPair.add(actual);
            evalPair.add(predict);
            evaluations.add(evalPair);
        }
        
        return evaluations;
        
    }

    /**
     * Returns a prediction from the neural network.
     * 
     * @param dataUsedInPredictions the data to be used in predictions
     * @return predictions a list of predictions
     */
    @Override
    public List<HashMap<String, Double>> getPredictions(List<HashMap<String, Double>> dataUsedInPredictions) {


        /*File file = new File(dataDir,Config.NETWORK_FILE);
        
        if (!file.exists()) {
        System.out.println("Can't read file: " + file.getAbsolutePath());
        return;
        }*/

        //BasicNetwork network = (BasicNetwork)EncogDirectoryPersistence.loadObject(file);

        /*
        MarketMLDataSet data = grabData();
        
        // BasicNetwork network = EncogUtility.simpleFeedForward(data.getInputSize(), Config.HIDDEN1_COUNT, Config.HIDDEN2_COUNT, data.getIdealSize(), true);
        
        DecimalFormat format = new DecimalFormat("#0.0000");
        int count = 0;
        int correct = 0;
        for (MLDataPair pair : data) {
        MLData input = pair.getInput();
        MLData actualData = pair.getIdeal();
        MLData predictData = this.neuralNetwork.compute(input);
        
        double actual = actualData.getData(0);
        double predict = predictData.getData(0);
        double diff = Math.abs(predict - actual);
        
        //Direction actualDirection = determineDirection(actual);
        //Direction predictDirection = determineDirection(predict);
        
        
        
        count++;
        
        System.out.println("Day " + count + ":actual="
        + format.format(actual) + "()"
        + ",predict=" + format.format(predict) + "("
        + ")" + ",diff=" + diff);
        
        }*/
        
        return new ArrayList<HashMap<String, Double>>();
    }
    
    private List<HashMap<String, Double>> getResults() {
        
        List<HashMap<String, Double>> results = new ArrayList<HashMap<String, Double>>();
        
        for (LoadedMarketData data : loadedMarketData) {
            
            for (MarketDataType type : fieldsRequired) {
                
                HashMap<String, Double> map = new HashMap<String, Double>();
                String field = mapMarketTypeToField(type);
                double value = data.getData(type);
                map.put(field, value);
                results.add(map);
            }
        }
        
        return results;
    }
    
    private String mapMarketTypeToField(MarketDataType dataType) {
        
        for (Entry<String, MarketDataType> entry : fieldToMarketType.entrySet()) {
            
            if (entry.getValue().equals(dataType)) {
                return entry.getKey();
            }
        }
        
        throw new IllegalArgumentException(dataType.toString() + " is not a valid data type for class MarketDataType");
    }
    
    private Set<MarketDataType> getFieldsRequired(List<String> fieldsRequired) {
        
        Set<MarketDataType> dataRequired = new TreeSet<MarketDataType>();
        
        for (String field : fieldsRequired) {
            
            MarketDataType data = fieldToMarketType.get(field);
            dataRequired.add(data);
        }
        
        return dataRequired;
    }
    
    private static Map<String, MarketDataType> mapFieldToMarketType() {
        
        Map<String, MarketDataType> map = new HashMap<String, MarketDataType>();
        
        map.put("open", MarketDataType.OPEN);
        map.put("close", MarketDataType.CLOSE);
        map.put("low", MarketDataType.LOW);
        map.put("high", MarketDataType.HIGH);
        map.put("volume", MarketDataType.VOLUME);
        map.put("adjclose", MarketDataType.ADJUSTED_CLOSE);
        
        return map;
    }
    
    private static List<String> getDefaultFields() {
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("close");
        fields.add("volume");
        fields.add("high");
        fields.add("open");
        fields.add("adjclose");
        
        return fields;
    }

    /**
     * Runs the neural network as a thread.
     * 
     */
    @Override
    public void run() {
        this.trainForTime();
    }

    /**
     * Adds an observer to the neural network.
     * 
     * @param obsrvr the observer
     */
    public void addObserver(Observer obsrvr) {
        this.observers.add(obsrvr);
    }

    /**
     * Deletes an observer from the neural network.
     * 
     * @param obsrvr the observer
     */
    public void deleteObserver(Observer obsrvr) {
        this.observers.remove(obsrvr);
    }

    /**
     * Notifies all observers to a state change.
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
     * Notifies observers to a state change.
     * 
     * @param o the object to notify on
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
     * Deletes all observers.
     * 
     */
    public void deleteObservers() {
        this.observers.removeAll(observers);
    }

    /**
     * Sets the neural network to changed.
     */
    public void setChanged() {
        this.observableChanged = true;
    }

    /**
     * Clears the changed value.
     */
    public void clearChanged() {
        this.observableChanged = false;
    }

    /**
     * Determines if the object has changed.
     * 
     * @return changed true if the object has changed state.
     */
    public boolean hasChanged() {
        return this.observableChanged;
    }

    /**
     * The number of observers.
     * 
     * @return numObservers counts the number of observers
     */
    public int countObservers() {
        return this.observers.size();
    }
}
