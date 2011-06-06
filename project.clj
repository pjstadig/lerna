(defproject lerna "0.1.0-SNAPSHOT"
  :description "Dynamo clustering for Lucene."
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [org.clojure/tools.logging "0.1.3-SNAPSHOT"]
                 [log4j "1.2.16"]
                 [commons-codec "1.4"]
                 [clucy "0.2.0"]]
  :dev-dependencies [[lein-difftest "1.3.3"]]
  :hooks [leiningen.hooks.difftest])
