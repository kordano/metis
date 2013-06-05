(ns metis.math.numeric)


;; Following functions are taken from:
;; http://java.ociweb.com/mark/clojure/article.html
(defn- polynomial
  "Computes the value of a polynomial
   with the given coefficients for a given value x"
  [coefs x]
  (reduce #(+ (* x %1) %2) coefs))

(defn- derivative
  "Ccomputes the value of the derivative of a polynomial
   with the given coefficients for a given value x"
  [coefs x]
  (let [exponents (reverse (range (count coefs)))
        derivative-coefs (map #(* %1 %2) (butlast coefs) exponents)]
    (polynomial derivative-coefs x)))

;; so the antiderivative should be:
(defn- antiderivative
  "Computes the value of the antiderivative of a polynomial
   with the given coefficients for a given value x"
  [coefs c x]
  (let [exponents (reverse (range 1 (inc (count coefs))))
        ad-coefs (conj (vec (map #(/ %1 %2) coefs exponents)) 0)]
    (polynomial ad-coefs x)))

;; integration
(defn polynomial-integral
  "Computes the value of the integral for a polynomial"
  [coefs a b]
  (- (antiderivative coefs 0 b) (antiderivative coefs 0 a)))

(defn defac-rec
  "Returns the new coefficients of a polynomial with
   the given coefficients multiplied by (x - c):
   [a b] c -> (ax + b)(x - c) -> [a b-ac -bc]"
  [coefs c]
  (let [x (cons 0 (conj coefs 0))]
    (vec (map + (next x) (map #(* % c) x)))))

(defn defactor
  "Defactors a polynomial and returns the coefficients:
   (x-a)(x-b) -> (defactor [a b]) -> [1 -a-b a*b]"
  [v]
  (let [n (count v)
        x (map unchecked-negate v)]
    (loop [i 1
           coefs [1 (nth x 0)]]
      (if (< i n)
        (recur (inc i) (defac-rec coefs (nth x i)))
        coefs)
      )))

(defn lagrange-weights
  "Computes the Lagrange weights for n points (not n-th degree);
   (Lagrange 3)*((b-a)/2) -> weights for Simpson's rule"
  [n]
  (let [coefs (map #(into (range %) (range (inc %) n))
                   (range n))
        div (map #(reduce * (map - (repeat %) (nth coefs %)))
                 (range n))
        integral (map #(polynomial-integral (defactor %) 0 (dec n)) coefs)]
      (map / integral div)))

(defn newton-cotes
  "Computes the integral between a and b for a function f
   with the closed Newton-Cotes formula of degree d" ;; d < 7 recommended (else rounding errors and overflow)
  [f a b d]
  (let [h (/ (- b a) d)
        w (lagrange-weights (inc d))
        y (map f (range a (inc b) h))
        res (reduce + (map * w y))]
    (* h res)))

;; likely to prove inefficient
(defn newton-cotes-summed
  "Computes the integral between a and b for a function f
   with the closed Newton-Cotes formula of degree d applied
   to n partitions independently"
  [f a b d n]
  (let [h (/ (- b a) n)
        parts (range a (inc b) h)]
    (reduce + (map #(newton-cotes f %1 %2 d) parts (next parts)))))


(defn integral
  "Computes the integral between a and b (a < b) for any function f
   (current method: Simpson's rule)"
  [f a b]
  (newton-cotes f a b 3))

;; TODO: test integral functions


(def f (partial polynomial [2 1 3])) ; f(x) = 2x^2 + x + 3
(def f-prime (partial derivative [2 1 3])) ; f'(x) = 4x + 1
(def F (partial antiderivative [2 1 3] 5)) ; F(x) = 2/3*x^3 + 1/2*x^2 + 3x + 5

(-> (defactor [2 3])) ; [1 -5 6]
(-> (lagrange-weights 3)) ; 1/3 4/3 1/3

(-> (polynomial-integral [2 1 3] 0 2)) ; 40/3
(-> (newton-cotes f 0 2 3)) ; 40/3
(-> (newton-cotes-summed f 0 2 3 5)) ; 40/3
