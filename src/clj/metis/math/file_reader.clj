(ns metis.file-reader
  (:require [clojure.string :as string]))


; only for test purposes
(defn format-data
  "Formats data into hashmap from testfile"
  [data]
  (apply hash-map
         (string/split (reduce #(str %1 " " %2)
                        (string/split data #"\n"))#"\s")))


(defn- parse-number
  "Reads a number from a string. Returns nil if not a number."
  [s]
  (if (re-find #"^-?\d+\.?\d*$" s)
    (read-string s)))



(defn format-gapminder-data
  "Formats the gapminder data to a readable data map"
  [raw-data]
  (let [data (map #(string/split % #",") (rest (string/split raw-data #"\n")))
        topics (rest (string/split (first (string/split raw-data #"\n")) #","))]
    (reduce conj
     (map #(assoc {}
         (first %)
         (zipmap topics (rest (map parse-number %)))) ;; nil means that there are no data for this topic
      data))))
