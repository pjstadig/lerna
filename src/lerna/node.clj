;;;; Copyright © 2011 Paul Stadig. All rights reserved.
;;;;
;;;; See LICENSE at the root of this project for terms of use and distribution.
(ns lerna.node)

(defprotocol Node
  (add [this documents])
  (delete [this document-ids])
  (search [this query max-results]))
