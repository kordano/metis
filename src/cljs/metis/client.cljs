(ns metis.client
  (:use [metis.repl :only [connect]]
        [webfui.dom :only [defdom]])
  (:require [clojure.browser.repl :as repl]
            [clojure.browser.event :as event]
            [hiccups.runtime :as hiccupsrt]
            [domina :as dom]
            [metis.html.canvas :as canvas])
  (:require-macros [hiccups.core :as hiccups]))

; fire up a repl for the browser and eval namespace on top once connected
#_(do (ns metis.clojure.start)
      (require 'cljs.repl.browser)
      (cemerick.piggieback/cljs-repl
       :repl-env (doto (cljs.repl.browser/repl-env :port 9000)
                   cljs.repl/-setup)))


(def background-width 400)
(def background-height 400)

(defn rgba-string [r g b a]
  (str "rgba(" r "," g "," b "," a ")"))


(defn draw-upper-whiskers [ctx]
  (do
    (canvas/draw-line ctx '([200 185] [200 165]))
    (canvas/draw-line ctx '([190 165] [210 165]))))

(defn draw-lower-whiskers [ctx]
  (do
    (canvas/draw-line ctx '([200 215] [200 255]))
    (canvas/draw-line ctx '([190 255] [210 255]))))


(defn draw-median [ctx]
  (canvas/draw-line ctx '([185 205] [215 205])))


(defn draw-boxplot [r g b]
  (let [canvas (dom/by-id "theCanvas")
        ctx (.getContext canvas "2d")]
    (do
      (set! (.-strokeStyle ctx) (rgba-string r g b 1))
      (.strokeRect ctx 185 185 30 30)
      (draw-upper-whiskers ctx)
      (draw-lower-whiskers ctx)
      (draw-median ctx))))


(defn draw-background []
  (let [canvas (dom/by-id "theCanvas")
        ctx (.getContext canvas "2d")]
    (do
      (set! (.-globalCompositeOperation ctx) "source-over")
      (set! (.-fillStyle ctx) (rgba-string 20 20 20 1))
      (.fillRect ctx 0 0 background-width background-height))))


(defn init-canvas []
  (hiccups/html
   [:body
    [:canvas {:id "theCanvas" :width background-width :height background-height}]
    [:p
     [:input {:type "button" :id "init-btn" :name "button1" :value "Init"}]
     [:input {:type "button" :id "draw-btn" :name "button2" :value "Draw!"}]]
    [:p "r  "
     [:input {:type "range" :name "points" :id "rRange" :min "0" :max "255" :value "0"}]
     [:span {:id "current-r"}]]
    [:p "g  "
     [:input {:type "range" :name "points" :id "gRange" :min "0" :max "255" :value "0"}]
     [:span {:id "current-g"}]]
    [:p "b  "
     [:input {:type "range" :name "points" :id "bRange" :min "0" :max "255" :value "0"}]
     [:span {:id "current-b"}]]

    ]))


#_ (do
     (set! (.-innerHTML (dom/by-id "DIV1")) (init-canvas))
     (set! (.-onclick (dom/by-id "init-btn")) (fn [] (draw-background)))
     (set! (.-onchange (dom/by-id "rRange"))
           (fn []
             (let [r-val (.-value (dom/by-id "rRange"))
                   g-val (.-value (dom/by-id "gRange"))
                   b-val (.-value (dom/by-id "bRange"))]
                  (do
                    (draw-boxplot r-val g-val b-val)
                    (set! (.-innerHTML (dom/by-id "current-r")) r-val)))))
     (set! (.-onchange (dom/by-id "gRange"))
           (fn []
             (let [r-val (.-value (dom/by-id "rRange"))
                   g-val (.-value (dom/by-id "gRange"))
                   b-val (.-value (dom/by-id "bRange"))]
                  (do
                    (draw-boxplot r-val g-val b-val)
                    (set! (.-innerHTML (dom/by-id "current-g")) g-val)))))
     (set! (.-onchange (dom/by-id "bRange"))
           (fn []
             (let [r-val (.-value (dom/by-id "rRange"))
                   g-val (.-value (dom/by-id "gRange"))
                   b-val (.-value (dom/by-id "bRange"))]
                  (do
                    (draw-boxplot r-val g-val b-val)
                    (set! (.-innerHTML (dom/by-id "current-b")) b-val)))))
     (set! (.-onclick (dom/by-id "draw-btn")) (fn [] (draw-boxplot))))

#_(js/alert "red alert!")
