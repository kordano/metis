(ns metis.distributions
  (:use metis.numeric)
  (:require [clojure.math.numeric-tower :as math]))

(defn gaussian
  "Computes the normal distribution"
  [mean variance x]
  (/ 1 (* (math/sqrt (* 2 variance Math/PI))
          (math/expt Math/E (/ (math/expt (- x mean) 2) (* 2 variance))))))


(defn exponential-distribution
  "Computes the exponential distribution"
  [x]
  (* x (math/expt )))


;;TODO discrete and continous distributions:
