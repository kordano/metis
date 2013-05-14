(ns metis.server
  (:require-macros [hiccups.core :as hiccups])
  (:require [hiccups.runtime :as hiccupsrtm]))

(defn log [& args] (apply (.-log js/console) (map str args)))

(def http (js/require "http"))
(def express (js/require "express"))
(def socket-io (js/require "socket.io"))

(defn page-template []
  (hiccups/html
   [:html
    [:head
     [:link {:rel "stylesheet" :href "/style.css"}]
     [:script {:type "text/javascript" :src "/cljs.js"}]
     ]
    [:body
     [:div
      [:p "I've seen things you people wouldn't believe. Attack ships on fire off the shoulder of Orion. I watched c-beams glitter in the dark near the Tannhäuser Gate. All those moments will be lost in time, like tears in rain. Time to die."]
      [:img {:id "anotherImage" :style "display:none;" :src "/avatar.png"}]
      [:canvas {:id "aCanvas":style"border: 1px solid black; width: 300px; height: 431px;" :width "300" :height "431"}]
      [:br]
      [:img {:id "otherImage" :style "display:none;" :src "/avatar2.png"}]
      [:canvas {:id "anotherCanvas" :style "border: 1px solid black; width: 300px; height: 396px;":width "300" :height "396"}]]]]))

(defn get-main
  [req res]
  (.send res (page-template)))

(defn -main [& args]
  (let [app (express)
        server (.createServer http app)]
    (-> app
        (.use (. express (logger)))
        (.use ((aget express "static") "static"))
        (.get "/" get-main)
        (.listen 3000)))
  (log "Listening on http://localhost:3000/"))

(set! *main-cli-fn* -main)
