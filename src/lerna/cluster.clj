;;;; Copyright © 2011 Paul Stadig. All rights reserved.
;;;;
;;;; See LICENSE at the root of this project for terms of use and distribution.
(ns lerna.cluster
  (:require [clojure.tools.logging :as log]
            [lerna.hash :as hash]
            [lerna.ring :as ring]
            [lerna.node :as node]))

(defn create []
  {:ring (ring/create)})

(defn add [cluster documents]
  (log/debug "Adding" (pr-str (map :id documents)))
  (let [documents (group-by #(ring/find (:ring cluster) (hash/hash (:id %)))
                            documents)]
    (doall (pmap (bound-fn [[node docs]]
                   (node/add node docs))
                 documents))))

(defn delete [cluster document-ids]
  (log/debug "Deleting" (pr-str (map :id document-ids)))
  (doall (pmap (bound-fn [node]
                 (node/delete node document-ids))
               (get-in cluster [:ring :nodes]))))

(defn search [cluster query max-results]
  (log/debug "Searching with" (pr-str query))
  (let [nodes (get-in cluster [:ring :nodes])
        results (pmap (bound-fn [node]
                        (node/search node query max-results)) nodes)]
    (take max-results (apply concat results))))
