(defproject tradewc "1.0.0-SNAPSHOT"
  :description "Agile Stock Prediction Tool"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/data.json "0.1.2"]
                 [org.clojure/data.csv "0.1.2"]
                 [org.encog/encog-core "3.0.1"]
                 [incanter "1.3.0"]
                 [seesaw "1.4.0"]
                 [com.lowagie/itext "2.1.7"]
                 [clj-time "0.4.0"]]
  :plugins [[lein-swank "1.4.4"]]
  :dev-dependencies [[lein-autodoc "0.9.0"]]
  :main tradewc.main.core
  :aot [tradewc.main.core]
  :jar-name "tradewc.jar"
  :uberjar-name "tradewc-standalone.jar"
  :source-paths ["src/"]
  :java-source-paths ["src/java"]
  :javac-options {:debug "true"}
  :manifest {"SplashScreen-Image" "images/splash.png"}
  ;; You can set JVM-level options here.
  :jvm-opts ["-splash:src/images/splash.png"]
  ;:warn-on-reflection true 
  ;for debugging purposes

  :autodoc { :name "Trade With Confidence (TWC)"
            :page-title "TWC API Documentation"
            :description "<p>TWC is a stock price prediciton tool which uses neural networks
                          to predict future stock prices for any stock listed on the stock market.</p>
                          <p>TWC uses the following libraries, click on any link to go straight to an explanation of the library:</p>
<ul>
<li><a href=\"http://incanter.org/\">Incanter</a></li>
<li><a href=\"http://www.heatonresearch.com/encog\">Encog</a></li>
<li><a href=\"http://github.com/daveray/seesaw/wiki\">Seesaw</a></li>
<li><a
 href=\"http://joda-time.sourceforge.net/\">Joda-Time</a></li>
<li><a href=\"http://itextpdf.com/\">iText</a></li>
</ul>"})


