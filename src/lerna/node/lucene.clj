;;;; Copyright © 2011 Paul Stadig. All rights reserved.
;;;;
;;;; See LICENSE at the root of this project for terms of use and distribution.
(ns lerna.node.lucene
  (:require [lerna.node :as node]
            [lerna.hash :as hash]
            [clucy.core :as clucy])
  (:import (org.apache.lucene.store NoSuchDirectoryException)))

(defrecord Node [index]
  node/Node
  (add [this documents]
    (doseq [doc documents]
      (clucy/add index (assoc doc
                         :_hash (hash/hash (str (:id doc)))
                         :_source (pr-str doc)))))
  (delete [this document-ids]
    (doseq [doc-id document-ids]
      (clucy/search-and-delete index (str "id:" doc-id))))
  (search [this query max-results]
    (try
      (clucy/search index query max-results)
      (catch NoSuchDirectoryException e))))

(defn index-path [id]
  (str "/tmp/" id))

(defn make-index [id]
  (clucy/disk-index (index-path id)))

(defn create [id]
  (assoc (Node. (make-index id))
    :id id))
