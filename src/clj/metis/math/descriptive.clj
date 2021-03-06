(ns metis.math.descriptive
  (:use metis.math.file-reader
        metis.math.numeric)
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


(defn p-quantile
  "Computes the p-quantile of data values"
  [data p]
  (let [index (* (dec (count data)) p)]
    (if (integer? index)
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
  (- (p-quantile data 0.75) (p-quantile data 0.25)))


(defn data-range
  "Computes range of data values"
  [data]
  (- (reduce max data) (reduce min data)))


(defn lower-innerfence
  "Computes upper innerfence of data values"
  [data]
  (- (p-quantile data 0.25) (* 1.5 (iqr data))))


(defn upper-innerfence
  "Computes upper innerfence of data values"
  [data]
  (+ (p-quantile data 0.75) (* 1.5 (iqr data))))


(defn sample-variance
  "Alternative computation of variance"
  [data]
  (- (/ (reduce + (map #(math/expt % 2) data)) (dec (count data)))) (math/expt (mean data) 2))


(defn standard-deviation
  "Computes standard deviation of data values"
  [data]
  (math/sqrt (sample-variance data)))


(defn sample-covariance
  "Computes the covariance between two data sets"
  [data1 data2]
  (let [diff1 (map #(- % (mean data1)) data1)
        diff2 (map #(- % (mean data2)) data2)]
    (/ (reduce + (map
                #(* (first %) (last %))
                (map list diff1 diff2)))
     (dec (count data1)))))


(defn sample-correlation
  "Computes the correlation coefficient between two data sets"
  [data1 data2]
  (/ (sample-covariance data1 data2) (* (standard-deviation data1) (standard-deviation data2))))


(defn freq
  "Returns frequencies of values"
  ( [data] (map val (frequencies (sort data))))
  ( [data interval-bounds]
      (let [sorted (sort data)
            counted (vec (map #(let [bound %]
                                 (count (take-while (partial > bound) sorted)))
                              interval-bounds))]
        (map - (conj counted (count sorted)) (cons 0 counted)))))


(defn freq2
  "Returns frequencies of values"
  ( [data] (frequencies data))
  ( [data interval-bounds]
      (let [bounds (sort interval-bounds)
            n (count bounds)]
        (loop [i 0
               rmd-data (sort data)
               result {}]
          (if-not (< i n)
            (conj result {[(last bounds) (last rmd-data)] (count rmd-data)})
            (let [rbound (nth bounds i)
                  lbound (if (= i 0) (first rmd-data) (nth bounds (- i 1)))
                  split-data (split-with (partial > rbound) rmd-data)]
              (recur (inc i)
                     (peek split-data)
                     (conj result {[lbound rbound] (count (first split-data))}))))))))


(defn pearson
  "Computes Pearsons skewness coefficient"
  [data bounds]
  (/ (- (mean data) (mean (key (first (max-key val (freq2 data bounds))))))
     (standard-deviation data)))


;; Kleinster Quadrate Schätzer
(defn linear-regression
  "Computes the linear regression function for a given data set: make sure there is 2-dim data"
  [x y]
  (let [beta (/ (sample-covariance x y) (sample-variance x))
        alpha (- (mean y) (* beta (mean x)))]
    {:alpha alpha :beta beta}))

(def x [0.3 2.2 0.5 0.7 1.0 1.8 3.0 0.2 2.3])
(def y [5.8 4.4 6.5 5.8 5.6 5.0 4.8 6.0 6.1])

(linear-regression x y)
