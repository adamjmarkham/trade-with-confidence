{:namespaces
 ({:source-url nil,
   :wiki-url "tradewc.charts.stock-charts-api.html",
   :name "tradewc.charts.stock-charts",
   :author "Adam Markham",
   :doc
   "Namespace that contains core functions for processing and viewing stock charts."}
  {:source-url nil,
   :wiki-url "tradewc.controllers.chart-controller-api.html",
   :name "tradewc.controllers.chart-controller",
   :author "Adam Markham",
   :doc
   "Namespace that contains core functions for generating charts to\nrepresent stock data with."}
  {:source-url nil,
   :wiki-url "tradewc.controllers.main-controller-api.html",
   :name "tradewc.controllers.main-controller",
   :author "Adam Markham",
   :doc
   "Namespace that contains core functions starting the application."}
  {:source-url nil,
   :wiki-url "tradewc.controllers.neuralnet-controller-api.html",
   :name "tradewc.controllers.neuralnet-controller",
   :doc nil}
  {:source-url nil,
   :wiki-url "tradewc.controllers.observers-api.html",
   :name "tradewc.controllers.observers",
   :doc nil}
  {:source-url nil,
   :wiki-url "tradewc.controllers.prediction-list-controller-api.html",
   :name "tradewc.controllers.prediction-list-controller",
   :author "Adam Markham",
   :doc
   "Namespace that contains core functions for controlling the list of predictions"}
  {:source-url nil,
   :wiki-url "tradewc.controllers.query-table-controller-api.html",
   :name "tradewc.controllers.query-table-controller",
   :doc nil}
  {:source-url nil,
   :wiki-url "tradewc.controllers.start-controller-api.html",
   :name "tradewc.controllers.start-controller",
   :author "Adam Markham",
   :doc
   "Namespace that contains core functions for starting off the applications main menu."}
  {:source-url nil,
   :wiki-url "tradewc.controllers.stock-data-controller-api.html",
   :name "tradewc.controllers.stock-data-controller",
   :doc nil}
  {:source-url nil,
   :wiki-url "tradewc.controllers.stock-table-controller-api.html",
   :name "tradewc.controllers.stock-table-controller",
   :doc nil}
  {:source-url nil,
   :wiki-url "tradewc.main.core-api.html",
   :name "tradewc.main.core",
   :author "Adam Markham",
   :doc
   "Namespace that contains core functions for starting the application. Its\nis where the main method is invoked from."}
  {:source-url nil,
   :wiki-url "tradewc.stock-model.stock-data-api.html",
   :name "tradewc.stock-model.stock-data",
   :doc nil}
  {:source-url nil,
   :wiki-url "tradewc.views.generic-comp-api.html",
   :name "tradewc.views.generic-comp",
   :doc nil}
  {:source-url nil,
   :wiki-url "tradewc.views.main-view-api.html",
   :name "tradewc.views.main-view",
   :doc nil}
  {:source-url nil,
   :wiki-url "tradewc.views.start-view-api.html",
   :name "tradewc.views.start-view",
   :author "Adam Markham",
   :doc
   "Namespace that contains core functions for viewing the start view."}
  {:source-url nil,
   :wiki-url "tradewc.views.stock-config-view-api.html",
   :name "tradewc.views.stock-config-view",
   :doc nil}
  {:source-url nil,
   :wiki-url "tradewc.views.table-view-api.html",
   :name "tradewc.views.table-view",
   :doc nil}),
 :vars
 ({:arglists ([data stock-name stock-symbol]),
   :name "candle-stick",
   :namespace "tradewc.charts.stock-charts",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.charts.stock-charts-api.html#tradewc.charts.stock-charts/candle-stick",
   :doc "Generates a candlestick chart",
   :var-type "function",
   :line 28,
   :file "src/tradewc/charts/stock_charts.clj"}
  {:arglists ([data]),
   :name "map-to-dataset",
   :namespace "tradewc.charts.stock-charts",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.charts.stock-charts-api.html#tradewc.charts.stock-charts/map-to-dataset",
   :doc "Converts data to an incanter dataset.",
   :var-type "function",
   :line 14,
   :file "src/tradewc/charts/stock_charts.clj"}
  {:arglists ([chart save-to]),
   :name "save-chart",
   :namespace "tradewc.charts.stock-charts",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.charts.stock-charts-api.html#tradewc.charts.stock-charts/save-chart",
   :doc "Function for saving a chart.",
   :var-type "function",
   :line 9,
   :file "src/tradewc/charts/stock_charts.clj"}
  {:arglists ([data-index]),
   :name "get-candlestick",
   :namespace "tradewc.controllers.chart-controller",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.controllers.chart-controller-api.html#tradewc.controllers.chart-controller/get-candlestick",
   :doc
   "Generates a candlestick chart for a particular stock at a stock-index",
   :var-type "function",
   :line 10,
   :file "src/tradewc/controllers/chart_controller.clj"}
  {:arglists ([]),
   :name "build-main-controller",
   :namespace "tradewc.controllers.main-controller",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.controllers.main-controller-api.html#tradewc.controllers.main-controller/build-main-controller",
   :doc "Builds the main frame after the application is started.",
   :var-type "function",
   :line 8,
   :file "src/tradewc/controllers/main_controller.clj"}
  {:arglists ([]),
   :name "cancel-neural-net",
   :namespace "tradewc.controllers.neuralnet-controller",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.controllers.neuralnet-controller-api.html#tradewc.controllers.neuralnet-controller/cancel-neural-net",
   :doc
   "Function which cancels the current neural network which is being trained.",
   :var-type "function",
   :line 60,
   :file "src/tradewc/controllers/neuralnet_controller.clj"}
  {:arglists ([]),
   :name "create-neural-net",
   :namespace "tradewc.controllers.neuralnet-controller",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.controllers.neuralnet-controller-api.html#tradewc.controllers.neuralnet-controller/create-neural-net",
   :doc "Function that constructs a neural network.",
   :var-type "function",
   :line 31,
   :file "src/tradewc/controllers/neuralnet_controller.clj"}
  {:arglists ([component-to-update]),
   :name "create-observer",
   :namespace "tradewc.controllers.neuralnet-controller",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.controllers.neuralnet-controller-api.html#tradewc.controllers.neuralnet-controller/create-observer",
   :doc
   "Function that creates an object which implements the Java Observer interface.",
   :var-type "function",
   :line 16,
   :file "src/tradewc/controllers/neuralnet_controller.clj"}
  {:arglists ([stock-index from]),
   :name "evaluate-neural-net",
   :namespace "tradewc.controllers.neuralnet-controller",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.controllers.neuralnet-controller-api.html#tradewc.controllers.neuralnet-controller/evaluate-neural-net",
   :doc "Function which evaluates the current neural network.",
   :var-type "function",
   :line 102,
   :file "src/tradewc/controllers/neuralnet_controller.clj"}
  {:arglists ([old-task progress-component training-time]),
   :name "get-task",
   :namespace "tradewc.controllers.neuralnet-controller",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.controllers.neuralnet-controller-api.html#tradewc.controllers.neuralnet-controller/get-task",
   :doc
   "Function which returns a background future task for running the old neural net\nin the background.",
   :var-type "function",
   :line 45,
   :file "src/tradewc/controllers/neuralnet_controller.clj"}
  {:arglists ([stock-index]),
   :name "predict-neural-net",
   :namespace "tradewc.controllers.neuralnet-controller",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.controllers.neuralnet-controller-api.html#tradewc.controllers.neuralnet-controller/predict-neural-net",
   :doc "Function which gets a prediction from the neural network.",
   :var-type "function",
   :line 92,
   :file "src/tradewc/controllers/neuralnet_controller.clj"}
  {:arglists ([stock stock-symbol val]),
   :name "prediction-dialog",
   :namespace "tradewc.controllers.neuralnet-controller",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.controllers.neuralnet-controller-api.html#tradewc.controllers.neuralnet-controller/prediction-dialog",
   :doc "Function which shows a dialog with a price prediction.",
   :var-type "function",
   :line 74,
   :file "src/tradewc/controllers/neuralnet_controller.clj"}
  {:arglists ([old-net new-net]),
   :name "swap-neural-net",
   :namespace "tradewc.controllers.neuralnet-controller",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.controllers.neuralnet-controller-api.html#tradewc.controllers.neuralnet-controller/swap-neural-net",
   :doc "Function which swaps an old neural net for a new one.",
   :var-type "function",
   :line 40,
   :file "src/tradewc/controllers/neuralnet_controller.clj"}
  {:arglists
   ([data-index from to progress-component train-time layer1 layer2]),
   :name "train-neural-net",
   :namespace "tradewc.controllers.neuralnet-controller",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.controllers.neuralnet-controller-api.html#tradewc.controllers.neuralnet-controller/train-neural-net",
   :doc
   "Function which trains a neural net given a training-time and num of neurones\nin hidden layers.",
   :var-type "function",
   :line 83,
   :file "src/tradewc/controllers/neuralnet_controller.clj"}
  {:arglists ([]),
   :name "training-not-complete",
   :namespace "tradewc.controllers.neuralnet-controller",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.controllers.neuralnet-controller-api.html#tradewc.controllers.neuralnet-controller/training-not-complete",
   :doc "Function which displays a training not complete dialog",
   :var-type "function",
   :line 65,
   :file "src/tradewc/controllers/neuralnet_controller.clj"}
  {:arglists ([pos]),
   :name "remove-prediction",
   :namespace "tradewc.controllers.prediction-list-controller",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.controllers.prediction-list-controller-api.html#tradewc.controllers.prediction-list-controller/remove-prediction",
   :doc "Removes a prediction at a particular position pos.",
   :var-type "function",
   :line 7,
   :file "src/tradewc/controllers/prediction_list_controller.clj"}
  {:arglists ([data date-from date-to prediction-list frame]),
   :name "add-to-predictions",
   :namespace "tradewc.controllers.query-table-controller",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.controllers.query-table-controller-api.html#tradewc.controllers.query-table-controller/add-to-predictions",
   :doc "Adds data from the query table to a list of predictions.",
   :var-type "function",
   :line 33,
   :file "src/tradewc/controllers/query_table_controller.clj"}
  {:arglists ([parent symbol]),
   :name "search-symbol",
   :namespace "tradewc.controllers.query-table-controller",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.controllers.query-table-controller-api.html#tradewc.controllers.query-table-controller/search-symbol",
   :doc "Searches for a particular symbol in the stock table.",
   :var-type "function",
   :line 20,
   :file "src/tradewc/controllers/query_table_controller.clj"}
  {:arglists ([root table-id data]),
   :name "update-stock-table",
   :namespace "tradewc.controllers.query-table-controller",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.controllers.query-table-controller-api.html#tradewc.controllers.query-table-controller/update-stock-table",
   :doc
   "Updates the stock-table given a root component from the view and some stock-data.",
   :var-type "function",
   :line 9,
   :file "src/tradewc/controllers/query_table_controller.clj"}
  {:arglists ([]),
   :name "start-tradewc",
   :namespace "tradewc.controllers.start-controller",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.controllers.start-controller-api.html#tradewc.controllers.start-controller/start-tradewc",
   :doc
   "Function responsible for starting the application and displaying the main view.",
   :var-type "function",
   :line 7,
   :file "src/tradewc/controllers/start_controller.clj"}
  {:arglists ([prediction-list]),
   :name "build-view",
   :namespace "tradewc.controllers.stock-data-controller",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.controllers.stock-data-controller-api.html#tradewc.controllers.stock-data-controller/build-view",
   :doc "Builds the add stock prediction view given a prediction-list",
   :var-type "function",
   :line 11,
   :file "src/tradewc/controllers/stock_data_controller.clj"}
  {:arglists ([pos]),
   :name "get-dates",
   :namespace "tradewc.controllers.stock-data-controller",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.controllers.stock-data-controller-api.html#tradewc.controllers.stock-data-controller/get-dates",
   :doc
   "Gets the dates from the stock model for a particular stock at\na specific position pos.",
   :var-type "function",
   :line 37,
   :file "src/tradewc/controllers/stock_data_controller.clj"}
  {:arglists ([prediction-list file]),
   :name "load-all-predictions",
   :namespace "tradewc.controllers.stock-data-controller",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.controllers.stock-data-controller-api.html#tradewc.controllers.stock-data-controller/load-all-predictions",
   :doc "Loads all predictions into the prediction-list given a file.",
   :var-type "function",
   :line 29,
   :file "src/tradewc/controllers/stock_data_controller.clj"}
  {:arglists ([pos]),
   :name "remove-prediction",
   :namespace "tradewc.controllers.stock-data-controller",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.controllers.stock-data-controller-api.html#tradewc.controllers.stock-data-controller/remove-prediction",
   :doc
   "Removes a prediction from the model at a particular position pos.",
   :var-type "function",
   :line 16,
   :file "src/tradewc/controllers/stock_data_controller.clj"}
  {:arglists ([file-path]),
   :name "save-all-predictions",
   :namespace "tradewc.controllers.stock-data-controller",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.controllers.stock-data-controller-api.html#tradewc.controllers.stock-data-controller/save-all-predictions",
   :doc "Saves all predictions currently loaded.",
   :var-type "function",
   :line 21,
   :file "src/tradewc/controllers/stock_data_controller.clj"}
  {:arglists ([file stock-index]),
   :name "save-to-csv",
   :namespace "tradewc.controllers.stock-data-controller",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.controllers.stock-data-controller-api.html#tradewc.controllers.stock-data-controller/save-to-csv",
   :doc "Saves the evaluation of the neural network to a file.",
   :var-type "function",
   :line 44,
   :file "src/tradewc/controllers/stock_data_controller.clj"}
  {:arglists ([selected-index]),
   :name "get-stock-table",
   :namespace "tradewc.controllers.stock-table-controller",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.controllers.stock-table-controller-api.html#tradewc.controllers.stock-table-controller/get-stock-table",
   :doc "Gets the stock table once stocks have been loaded into it.",
   :var-type "function",
   :line 9,
   :file "src/tradewc/controllers/stock_table_controller.clj"}
  {:arglists ([col prediction]),
   :name "add-to-predictions",
   :namespace "tradewc.stock-model.stock-data",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.stock-model.stock-data-api.html#tradewc.stock-model.stock-data/add-to-predictions",
   :doc "Adds a prediction to the end of a list col.",
   :var-type "function",
   :line 44,
   :file "src/tradewc/stock_model/stock_data.clj"}
  {:arglists ([lst]),
   :name "convert-stock-data",
   :namespace "tradewc.stock-model.stock-data",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.stock-model.stock-data-api.html#tradewc.stock-model.stock-data/convert-stock-data",
   :doc "Converts any Java map to a Clojure map.",
   :var-type "function",
   :line 80,
   :file "src/tradewc/stock_model/stock_data.clj"}
  {:arglists ([stock symbol date-from date-to stock-data predicted]),
   :name "create-prediction",
   :namespace "tradewc.stock-model.stock-data",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.stock-model.stock-data-api.html#tradewc.stock-model.stock-data/create-prediction",
   :doc
   "Creates a Stock defrecord for a stock, consiting of name, symbol, date,\nstock-data and the predicted value.",
   :var-type "function",
   :line 38,
   :file "src/tradewc/stock_model/stock_data.clj"}
  {:arglists ([col pos]),
   :name "get-prediction-at",
   :namespace "tradewc.stock-model.stock-data",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.stock-model.stock-data-api.html#tradewc.stock-model.stock-data/get-prediction-at",
   :doc
   "Gets a stock prediction from a list col at a specific position pos.",
   :var-type "function",
   :line 33,
   :file "src/tradewc/stock_model/stock_data.clj"}
  {:arglists ([]),
   :name "get-predictions",
   :namespace "tradewc.stock-model.stock-data",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.stock-model.stock-data-api.html#tradewc.stock-model.stock-data/get-predictions",
   :doc "Returns all stock predictions from the current list.",
   :var-type "function",
   :line 23,
   :file "src/tradewc/stock_model/stock_data.clj"}
  {:arglists ([stock-symbol date-from date-to]),
   :name "get-stock-data-as-map",
   :namespace "tradewc.stock-model.stock-data",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.stock-model.stock-data-api.html#tradewc.stock-model.stock-data/get-stock-data-as-map",
   :doc
   "Gets a map of stock-data from a stock-symbol, date-from and date-to.",
   :var-type "function",
   :line 94,
   :file "src/tradewc/stock_model/stock_data.clj"}
  {:arglists ([stock-symbol date-from date-to]),
   :name "get-stockmarket-data-src",
   :namespace "tradewc.stock-model.stock-data",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.stock-model.stock-data-api.html#tradewc.stock-model.stock-data/get-stockmarket-data-src",
   :doc
   "Returns a stock market data source given a stock symbol and date-from and date-to.",
   :var-type "function",
   :line 89,
   :file "src/tradewc/stock_model/stock_data.clj"}
  {:arglists ([complete-file-path]),
   :name "load-json-map",
   :namespace "tradewc.stock-model.stock-data",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.stock-model.stock-data-api.html#tradewc.stock-model.stock-data/load-json-map",
   :doc
   "Loads a Clojure data structure from a JSON file at a specified file-path.",
   :var-type "function",
   :line 126,
   :file "src/tradewc/stock_model/stock_data.clj"}
  {:arglists ([file-path]),
   :name "load-predictions",
   :namespace "tradewc.stock-model.stock-data",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.stock-model.stock-data-api.html#tradewc.stock-model.stock-data/load-predictions",
   :doc
   "Loads data predictions from a specified file-path, overwrites the current list of predictions.",
   :var-type "function",
   :line 113,
   :file "src/tradewc/stock_model/stock_data.clj"}
  {:arglists ([data-as-map complete-file-path]),
   :name "map-to-json",
   :namespace "tradewc.stock-model.stock-data",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.stock-model.stock-data-api.html#tradewc.stock-model.stock-data/map-to-json",
   :doc
   "Writes a Clojure data structure to JSON file at a specified file-path.",
   :var-type "function",
   :line 119,
   :file "src/tradewc/stock_model/stock_data.clj"}
  {:arglists ([symbol]),
   :name "query-stock-symbol",
   :namespace "tradewc.stock-model.stock-data",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.stock-model.stock-data-api.html#tradewc.stock-model.stock-data/query-stock-symbol",
   :doc
   "Returns a vector of maps containing stock-data matching a particular stock-symbol given a symbol to query",
   :var-type "function",
   :line 131,
   :file "src/tradewc/stock_model/stock_data.clj"}
  {:arglists ([col pos]),
   :name "remove-prediction",
   :namespace "tradewc.stock-model.stock-data",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.stock-model.stock-data-api.html#tradewc.stock-model.stock-data/remove-prediction",
   :doc
   "Removes a prediction from a list col at a specified position pos.",
   :var-type "function",
   :line 54,
   :file "src/tradewc/stock_model/stock_data.clj"}
  {:arglists ([orig-predict new-predict]),
   :name "replace-predict",
   :namespace "tradewc.stock-model.stock-data",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.stock-model.stock-data-api.html#tradewc.stock-model.stock-data/replace-predict",
   :doc
   "Replaces an original prediciton orig-predict with a new prediction new-predict.",
   :var-type "function",
   :line 60,
   :file "src/tradewc/stock_model/stock_data.clj"}
  {:arglists ([predictions file-path]),
   :name "save-predictions",
   :namespace "tradewc.stock-model.stock-data",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.stock-model.stock-data-api.html#tradewc.stock-model.stock-data/save-predictions",
   :doc "Saves data predictions to a specified file-path.",
   :var-type "function",
   :line 108,
   :file "src/tradewc/stock_model/stock_data.clj"}
  {:arglists ([data file-path]),
   :name "save-to",
   :namespace "tradewc.stock-model.stock-data",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.stock-model.stock-data-api.html#tradewc.stock-model.stock-data/save-to",
   :doc "Saves any data to a specified file-path",
   :var-type "function",
   :line 28,
   :file "src/tradewc/stock_model/stock_data.clj"}
  {:arglists ([col pos data]),
   :name "update-actual-eval",
   :namespace "tradewc.stock-model.stock-data",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.stock-model.stock-data-api.html#tradewc.stock-model.stock-data/update-actual-eval",
   :doc "",
   :var-type "function",
   :line 70,
   :file "src/tradewc/stock_model/stock_data.clj"}
  {:arglists ([col pos data]),
   :name "update-predicted-eval",
   :namespace "tradewc.stock-model.stock-data",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.stock-model.stock-data-api.html#tradewc.stock-model.stock-data/update-predicted-eval",
   :doc "",
   :var-type "function",
   :line 75,
   :file "src/tradewc/stock_model/stock_data.clj"}
  {:arglists ([col pos predicted-val]),
   :name "update-prediction",
   :namespace "tradewc.stock-model.stock-data",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.stock-model.stock-data-api.html#tradewc.stock-model.stock-data/update-prediction",
   :doc
   "Updates a prediction value from a list col at a specified position pos with predicted-val.",
   :var-type "function",
   :line 65,
   :file "src/tradewc/stock_model/stock_data.clj"}
  {:arglists ([content type option-type]),
   :name "msg",
   :namespace "tradewc.views.generic-comp",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.views.generic-comp-api.html#tradewc.views.generic-comp/msg",
   :doc "Creates a message dialog.",
   :var-type "function",
   :line 7,
   :file "src/tradewc/views/generic_comp.clj"}
  {:arglists ([title]),
   :name "main-frame",
   :namespace "tradewc.views.main-view",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.views.main-view-api.html#tradewc.views.main-view/main-frame",
   :doc "Builds the main frame given a title.",
   :var-type "function",
   :line 100,
   :file "src/tradewc/views/main_view.clj"}
  {:arglists ([stock-list]),
   :name "menu-items",
   :namespace "tradewc.views.main-view",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.views.main-view-api.html#tradewc.views.main-view/menu-items",
   :doc "Builds a map of menu items and updates the stock-list.",
   :var-type "function",
   :line 32,
   :file "src/tradewc/views/main_view.clj"}
  {:arglists ([items]),
   :name "menus",
   :namespace "tradewc.views.main-view",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.views.main-view-api.html#tradewc.views.main-view/menus",
   :doc "Returns a vector of menus given some items.",
   :var-type "function",
   :line 87,
   :file "src/tradewc/views/main_view.clj"}
  {:arglists ([]),
   :name "no-stock-dialog",
   :namespace "tradewc.views.main-view",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.views.main-view-api.html#tradewc.views.main-view/no-stock-dialog",
   :doc
   "A dialog box used to display a please select stock error message.",
   :var-type "function",
   :line 78,
   :file "src/tradewc/views/main_view.clj"}
  {:arglists ([renderer {:keys [value]}]),
   :name "render-item",
   :namespace "tradewc.views.main-view",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.views.main-view-api.html#tradewc.views.main-view/render-item",
   :doc "Renders an item in the GUI.",
   :var-type "function",
   :line 94,
   :file "src/tradewc/views/main_view.clj"}
  {:arglists ([component]),
   :name "show",
   :namespace "tradewc.views.main-view",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.views.main-view-api.html#tradewc.views.main-view/show",
   :doc "Displays a component.",
   :var-type "function",
   :line 27,
   :file "src/tradewc/views/main_view.clj"}
  {:arglists ([component]),
   :name "show",
   :namespace "tradewc.views.start-view",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.views.start-view-api.html#tradewc.views.start-view/show",
   :doc "Displays a GUI component on the screen.",
   :var-type "function",
   :line 26,
   :file "src/tradewc/views/start_view.clj"}
  {:arglists ([title]),
   :name "start-frame",
   :namespace "tradewc.views.start-view",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.views.start-view-api.html#tradewc.views.start-view/start-frame",
   :doc "Displays the start-frame given a frame title.",
   :var-type "function",
   :line 8,
   :file "src/tradewc/views/start_view.clj"}
  {:arglists ([title prediction-list]),
   :name "build-prediction-frame",
   :namespace "tradewc.views.stock-config-view",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.views.stock-config-view-api.html#tradewc.views.stock-config-view/build-prediction-frame",
   :doc "Builds a prediction frame given a title and prediction-list.",
   :var-type "function",
   :line 25,
   :file "src/tradewc/views/stock_config_view.clj"}
  {:arglists ([frame search-item]),
   :name "search-stock",
   :namespace "tradewc.views.stock-config-view",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.views.stock-config-view-api.html#tradewc.views.stock-config-view/search-stock",
   :doc "Searches for a particular stock given a search-item.",
   :var-type "function",
   :line 20,
   :file "src/tradewc/views/stock_config_view.clj"}
  {:arglists ([column-names rows]),
   :name "table-columns",
   :namespace "tradewc.views.stock-config-view",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.views.stock-config-view-api.html#tradewc.views.stock-config-view/table-columns",
   :doc "Gets the table columns as a vector.",
   :var-type "function",
   :line 13,
   :file "src/tradewc/views/stock_config_view.clj"}
  {:arglists ([]),
   :name "menus",
   :namespace "tradewc.views.table-view",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.views.table-view-api.html#tradewc.views.table-view/menus",
   :doc "Returns a vector of menu items.",
   :var-type "function",
   :line 7,
   :file "src/tradewc/views/table_view.clj"}
  {:arglists ([column-names rows]),
   :name "stock-table",
   :namespace "tradewc.views.table-view",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "/tradewc.views.table-view-api.html#tradewc.views.table-view/stock-table",
   :doc "Builds a stock table inside a frame and displays it.",
   :var-type "function",
   :line 15,
   :file "src/tradewc/views/table_view.clj"})}
