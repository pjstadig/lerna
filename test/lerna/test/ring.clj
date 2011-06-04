;;;; Copyright © 2011 Paul Stadig. All rights reserved.
;;;;
;;;; See LICENSE at the root of this project for terms of use and distribution.
(ns lerna.test.ring
  (:refer-clojure :exclude [seq find next])
  (:use [clojure.test]
        [lerna.ring]
        [lerna.test.node.lucene :only [with-nodes]])
  (:require [lerna.node.lucene :as lucene]))

(deftest test-add
  (with-nodes [node1 1 node2 3 node3 5]
    (let [ring (create)]
      (is (= [node1 node2 node3]
             (:nodes (add ring [node1 node3 node2 node1])))))))

(deftest test-seq
  (with-nodes [node1 1 node2 3 node3 5]
    (let [ring (add (create) [node1 node2 node3])]
      (is (= [node1 node2 node3] (take 3 (seq ring 2))))
      (is (= [node2 node3 node1] (take 3 (seq ring 3))))
      (is (= [node3 node1 node2] (take 3 (seq ring 6)))))))

(deftest test-find
  (with-nodes [node1 1 node2 3 node3 5]
    (let [ring (add (create) [node1 node2 node3])]
      (is (= node1 (find ring 2))))))

(deftest test-next
  (with-nodes [node1 1 node2 3 node3 5]
    (let [ring (add (create) [node1 node2 node3])]
      (is (= node2 (next ring node1)))
      (is (= node1 (next ring node3))))))
