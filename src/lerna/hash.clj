;;;; Copyright © 2011 Paul Stadig. All rights reserved.
;;;;
;;;; See LICENSE at the root of this project for terms of use and distribution.
(ns lerna.hash
  (:refer-clojure :exclude [hash])
  (:import (java.util Random)
           (org.apache.commons.codec.digest DigestUtils)))

(def MAX (BigInteger. (str "115792089237316195423570985008687907853269984665640"
                           "564039457584007913129639936")))

(defn generate []
  (BigInteger. 256 (Random.)))

(defn hash [id]
  (BigInteger. (into-array Byte/TYPE (cons (byte 0) (DigestUtils/sha256 id)))))
