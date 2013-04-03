(ns metis.core
  (:use metis.file-reader
        metis.descriptive))


(def data
  (map
   #(parse-number (val %))
   (format-data (read-file "data/LifeExpTable"))))

(mean data)

(median data)

(first-quartile data)

(third-quartile data)

(variance data)

(iqr data)

(standard-deviation data)
