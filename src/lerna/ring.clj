;;;; Copyright © 2011 Paul Stadig. All rights reserved.
;;;;
;;;; See LICENSE at the root of this project for terms of use and distribution.
(ns lerna.ring
  (:refer-clojure :exclude [seq find next])
  (:require [clojure.tools.logging :as log]
            [lerna.node :as node]))

(defn create []
  (log/trace "Creating ring")
  {})

(defn add [ring nodes]
  (log/debug "Adding nodes" (pr-str (map :id nodes)))
  (update-in ring [:nodes] #(sort-by :id (set (concat % nodes)))))

(defn seq [ring hash]
  (letfn [(seq* [[f & s] h]
            (if (or (> (:id f) (:id (first s)))
                    (< h (:id (first s))))
              (cons f s)
              (recur s h)))]
    (seq* (cycle (:nodes ring)) hash)))

(defn find [ring hash]
  (first (seq ring hash)))

(defn next [ring node]
  (fnext (seq ring (:id node))))
