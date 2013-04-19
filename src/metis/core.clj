(ns metis.core
  (:use metis.file-reader
        metis.descriptive))


(def data
  (map
   #(parse-number (val %))
   (format-data (read-file "data/LifeExpTable"))))

(mean data)

(median data)

(p-quantile data 0.5)

(variance data)

(iqr data)

(standard-deviation data)

(pearson data '(50 60 70 80))
