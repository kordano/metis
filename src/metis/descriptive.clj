(ns metis.descriptive
  (:use metis.file-reader)
  (:require [clojure.math.numeric-tower :as math]))


(defn- after-decimal-point
  "Returns numbers after decimal point"
  [number]
  (- number (math/floor (float number))))


(defn- halfway
  "Compute values between given data values"
  [data index ]
  (let [sorted-data (vec (sort data))]
    (+ (get sorted-data (int index))
     (*
         (-
          (get sorted-data (inc (int index)))
          (get sorted-data (int index)))
         (after-decimal-point index)))))


(defn first-quartile
  "Computes first-quartile of data values"
  [data]
  (let [index (/ (dec (count data)) 4)]
    (if (integer? index)
      (get (vec (sort data)) index)
      (halfway data index))))


(defn third-quartile
  "Computes third-quartile of data values"
  [data]
  (let [index (/ (* (dec (count data)) 3) 4)]
    (if ( integer? index)
        (get (vec (sort data)) index)
        (halfway data index))))


(defn median
  "Computes median of data values"
  [data]
  (let [index (/ (dec (count data)) 2)]
    (if (integer? index)
      (get (vec (sort data)) index)
      (halfway data index))))


(defn mean
  "Computes mean of data values"
  [data]
  (float (/ (reduce + (vec data)) (count data))))


(defn iqr
  "Computes the interquartile range of data values"
  [data]
  (- (third-quartile data) (first-quartile data)))


(defn data-range
  "Computes range of data values"
  [data]
  (- (reduce max data) (reduce min data)))


(defn lower-innerfence
  "Computes upper innerfence of data values"
  [data]
  (- (first-quartile data) (* 1.5 (iqr data))))


(defn upper-innerfence
  "Computes upper innerfence of data values"
  [data]
  (+ (third-quartile data) (* 1.5 (iqr data))))


(defn variance
  "Computes variance of data values"
  [data]
  (/ (reduce + (map #(math/expt (- % (mean data)) 2) data)) (dec (count data))))


(defn standard-deviation
  "Computes standard deviantion of data values"
  [data]
  (math/sqrt (variance data)))


(defn alt-variance
  "Alternative computation of variance"
  [data]
  (- (/ (reduce + (map #(math/expt % 2) data)) (dec (count data)))) (math/expt (mean data) 2))


(defn sample-covariance
  [data1 data2]
  (let [diff1 (map #(- % (mean data1)) data1)
        diff2 (map #(- % (mean data2)) data2)]
    (/
     (reduce + (map
                #(* (first %) (last %))
                (map list diff1 diff2)))
     (dec (count data1)))))


(defn sample-correlation
  [data1 data2]
  (/ (sample-covariance data1 data2) (* (variance data1) (variance data2))))


(defn freq
  "Returns frequencies of values"
  [data interval-bounds]
  (let [sorted (sort data)
        counted (vec (map #(let [bound %]
                             (count (take-while (partial > bound) sorted)))
                          interval-bounds))]
    (map - (conj counted (count sorted)) (cons 0 counted))))


(defn freq2
  "Returns frequencies of values"
  [data interval-bounds]
  (loop [i 0
         n (count interval-bounds)
         rmd-data (sort data)
         result []]
    (if-not (< i n)
      (conj result (count rmd-data))
      (let [bound (nth interval-bounds i)
            split-data (split-with (partial > bound) rmd-data)]
        (recur (inc i)
               n
               (peek split-data)
               (conj result (count (first split-data))))))))
