;;;; Copyright © 2011 Paul Stadig. All rights reserved.
;;;;
;;;; See LICENSE at the root of this project for terms of use and distribution.
(ns lerna.test.cluster
  (:use [clojure.test]
        [lerna.cluster]
        [lerna.test.node.lucene :only [with-nodes]])
  (:require [lerna.hash :as hash]
            [lerna.ring :as ring]
            [lerna.node :as node]))

(deftest test-add
  (with-nodes [node1 1
               node2 3
               node3 5]
    (let [cluster (update-in (create) [:ring] ring/add [node1 node2 node3])]
      (add cluster [{:id "foo"}])
      (let [result [{:_source "{:id \"foo\"}"
                     :_hash (str (hash/hash "foo"))
                     :id "foo"}]]
        (is (empty? (node/search node1 "id:foo" 1)))
        (is (empty? (node/search node2 "id:foo" 1)))
        (is (= result (node/search node3 "id:foo" 1)))
        (is (= result (search cluster "id:foo" 1)))))))

(deftest test-delete
  (with-nodes [node1 1
               node2 3
               node3 5]
    (let [cluster (update-in (create) [:ring] ring/add [node1 node2 node3])]
      (add cluster [{:id "foo"}])
      (delete cluster ["foo"])
      (is (empty? (node/search node1 "id:foo" 1)))
      (is (empty? (node/search node2 "id:foo" 1)))
      (is (empty? (node/search node3 "id:foo" 1)))
      (is (empty? (search cluster "id:foo" 1))))))
