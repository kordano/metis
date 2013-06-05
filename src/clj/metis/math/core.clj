(ns metis.core
  (:use metis.file-reader
        metis.descriptive))


(defn five-point-summary
  [data]
  {:Minimum (reduce min data)
   :First-Quartile (p-quantile data 0.25)
   :Median (median data)
   :Third-Quartile (p-quantile data 0.75)
   :Maximum (reduce max data)})


;; Example hour compensation
(def hour-comp-data (format-gapminder-data (slurp "data/hour_comp.csv")))

(mean (filter #(not (nil? %)) (vals (hour-comp-data "Germany")))) ;; mean hour compensation in germany

(def raw-hour-comp-06 (apply hash-map (flatten (map #(list (key %) ((val %) "2006")) hour-comp-data))))

(def hour-comp-06 (apply hash-map (flatten (filter #(not (nil? (val %))) raw-hour-comp-06))))

(apply min-key val hour-comp-06) ;; Country with lowest hour compensation

(apply max-key val hour-comp-06) ;; Country with highest hour compensation

(five-point-summary (vals hour-comp-06))

(freq2 (vals hour-comp-06) '(0 10 20 30 40 50))


;; Example working hours per week
(def hours-week-data (format-gapminder-data (slurp "data/hours_week.csv")))

(mean (filter #(not (nil? %)) (vals (hours-week-data "Germany")))) ;; mean working hours per week

(def raw-hours-week-06 (apply hash-map (flatten (map #(list (key %) ((val %) "2006")) hours-week-data))))

(def hours-week-06 (apply hash-map (flatten (filter #(not (nil? (val %))) raw-hours-week-06))))

(apply min-key val hours-week-06) ;; Country with lowest working hours per week

(apply max-key val hours-week-06) ;; Country with highest working hours per week

(five-point-summary (vals hours-week-06))

(freq2 (vals hours-week-06) '(25 27 29 31 33 35 37 39 41))
