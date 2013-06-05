(ns metis.math.distributions
  (:use metis.math.numeric
        metis.math.core)
  (:require [clojure.math.numeric-tower :as math]))


(defn gaussian
  "Computes the normal distribution"
  [mean variance x]
  (/ 1 (* (math/sqrt (* 2 variance Math/PI))
          (math/expt Math/E (/ (math/expt (- x mean) 2) (* 2 variance))))))

(def std-gaussian (partial gaussian 0 1))


(defn exponential
  "Computes the exponential distribution"
  [lambda x]
  (if (neg? x) 0 (/ lambda (math/expt Math/E (* lambda x)))))


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
  "Computes the bernoulli distribution for X = k.
   Returns false if k is not in {0, 1}"
  [p k]
  (if (= k 0) (- 1 p)
      (if (= k 1) p false)))


(defn geometric2
  "Computes the geometric distribution used for modeling
   the number of failures until the first success"
  [p k]
  (* (math/expt (- 1 p) k) p))


(defn geometric
  "Computes the geometric distribution used for modeling
   the number of trials until the first success"
  [p k]
  (geometric2 p (- k 1)))


(defn m-coef
  "Compute the multinomial"
  [n n-list]
  (if (= n (reduce + n-list))
    (/ (factorial n)
       (reduce * (map factorial n-list)))
    0))


(defn multinomial [n p-list n-list]
  (let [cross-list (map vector p-list n-list)]
    (* (m-coef n n-list)
       (reduce * (map #(math/expt (first %) (second %)) cross-list)))))
