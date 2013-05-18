(ns metis.html.canvas
  (:require [domina :as dom]))


(defn draw-line [ctx points]
  (do
    (.beginPath ctx)
    (.moveTo ctx (ffirst points) (second (first points)))
    (loop [path (rest points)]
      (let [x (ffirst path)
            y (second (first path))]
        (if (empty? path)
          (.log js/console (str "drawing finished"))
          (do
            (.log js/console (str "drawing" x " " y))
            (.lineTo ctx x y)
            (recur (rest path))))))
    (.closePath ctx)
    (.stroke ctx)))
