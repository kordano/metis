(ns descriptive-test
  (:use clojure.test
        metis.file-reader
        metis.descriptive
        midje.sweet)(:require [clojure.math.numeric-tower :as math]))

(def data '(1 2 3 4 5))

(fact (mean data) => 3.0)

(fact (median data) => 3)

(fact (first-quartile data) => 2)

(fact (third-quartile data) => 4)

(fact (variance data) => 2.5)

(fact (iqr data) => 2)

(fact (standard-deviation data) => (math/sqrt 2.5))
