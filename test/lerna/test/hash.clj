;;;; Copyright © 2011 Paul Stadig. All rights reserved.
;;;;
;;;; See LICENSE at the root of this project for terms of use and distribution.
(ns lerna.test.hash
  (:refer-clojure :exclude [hash])
  (:use [clojure.test]
        [lerna.hash]))

(deftest test-hash
  (is (= (BigInteger. (str "199701507362397137060884445701465463541466850966734"
                           "08908105596072151101138862"))
         (hash "foo"))))
