(ns metis.math.core)


(defn factorial [n]
  (if (> n 0)
    (* n (factorial (dec n)))
    1))
