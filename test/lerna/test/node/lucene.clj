;;;; Copyright © 2011 Paul Stadig. All rights reserved.
;;;;
;;;; See LICENSE at the root of this project for terms of use and distribution.
(ns lerna.test.node.lucene
  (:use [clojure.test]
        [clojure.contrib.io :only [delete-file-recursively]]
        [lerna.node.lucene])
  (:require [lerna.node :as node]
            [lerna.hash :as hash]))

(defmacro with-nodes [[name id & nodes] & body]
  `(do
     (delete-file-recursively (index-path ~id) true)
     (let [~name (create ~id)]
       ~(if (seq nodes)
          `(with-nodes ~nodes ~@body)
          `(do ~@body)))))

(deftest test-foo
  (with-nodes [node 1]
    (is true)))

(deftest test-add
  (with-nodes [node 1]
    (is (empty? (node/search node "id:bar" 1)))
    (node/add node [{:id "bar"}])
    (is (= {:_source "{:id \"bar\"}"
            :_hash (str (hash/hash "bar"))
            :id "bar"}
           (first (node/search node "id:bar" 1))))))

(deftest test-delete
  (with-nodes [node 1]
    (node/add node [{:id "bar"}])
    (node/delete node ["bar"])
    (is (empty? (node/search node "id:bar" 1)))))

(deftest test-search
  (with-nodes [node 1]
    (is (empty? (node/search node "id:bar" 1)))))
