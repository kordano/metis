(ns metis.distributions
  (:use metis.numeric)
  (:require [clojure.math.numeric-tower :as math]))

(defn gaussian
  "Computes the normal distribution"
  [mean variance x]
  (/ 1 (* (math/sqrt (* 2 variance Math/PI))
          (math/expt Math/E (/ (math/expt (- x mean) 2) (* 2 variance))))))

(def std-gaussian (partial gaussian 0 1))


(defn exponential-distribution
  "Computes the exponential distribution"
  [x]
  (* x (math/expt )))
  

(defn b-coef
  "Computes the binomial coefficient n over k"
  [n k]
  (/ (reduce * (range (inc (- n k)) (inc n)))
     (reduce * (range 1 (inc k)))))

(defn binomial
  "Computes the binomial distribution for X = k"
  [n p k]
  (* (b-coef n k) (math/expt p k) (math/expt (- 1 p) (- n k))))


(defn bernoulli
  "Computes the bernoulli distribution for X = k (in {0, 1})"
  [p k]
  (if (= k 1) p (- 1 p)))


;;TODO discrete and continous distributions:
