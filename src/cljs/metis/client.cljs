(ns metis.client
  (:use [metis.repl :only [connect]]
        [domina :only [by-id value set-html! append! xpath]]
        [webfui.dom :only [defdom]])
  (:require [clojure.browser.repl :as repl]
            [hiccups.runtime :as hiccupsrt])
  (:require-macros [hiccups.core :as hiccups]))

; fire up a repl for the browser and eval namespace on top once connected
#_(do (ns metis.clojure.start)
      (require 'cljs.repl.browser)
      (cemerick.piggieback/cljs-repl
       :repl-env (doto (cljs.repl.browser/repl-env :port 9000)
                   cljs.repl/-setup)))

(defn draw-boxplot [min max median first-quartile third-quartile]
  (let [canvas (by-id "theCanvas")
        ctx (.getContext canvas "2d")]
    (do
      (set! (.-globalCompositeOperation ctx) "source-over")
      (set! (.-fillStyle ctx) "rgba(0,0,0,0.5)")
      (.fillRect ctx 200 200 50 50))))


(defn init-canvas []
  (hiccups/html
   [:body
    [:canvas {:id "theCanvas" :width "400" :height "400"}]
    [:p "Boxplot"]]))

#_(set! (.-innerHTML (by-id "DIV1")) (init-canvas))

#_ (set! (.-onlad js/window) (draw-boxplot))


#_(js/alert "red alert! shields up now!")
