(ns metis.core
  (:use metis.file-reader
        metis.descriptive)
  (:require [clojure.data.csv :as csv]))


(defn five-point-summary
  [data]
  {:Minimum (reduce min data) :First-Quartile (first-quartile data) :Median (median data) :Third-Quartile (third-quartile data) :Maximum (reduce max data)})


(def formated-data
  (reduce conj
          (map
           #(assoc {} (key %) (parse-number (val %)))
           (format-data (slurp "data/LifeExpTable")))))

(five-point-summary (vals formated-data))
