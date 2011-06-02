(defproject lerna "0.1.0-SNAPSHOT"
  :description "Dynamo clustering for Lucene."
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [org.clojure/tools.logging "0.1.2"]
                 [log4j "1.2.16"]
                 [commons-codec "1.4"]]
  :dev-dependencies [[lein-difftest "1.3.3"]]
  :hooks [leiningen.hooks.difftest])