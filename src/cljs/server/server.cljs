(ns metis.server
  (:require-macros [hiccups.core :as hiccups])
  (:require [hiccups.runtime :as hiccupsrtm]))

;; Helper
(defn clj->js
  "Recursively transforms ClojureScript maps into Javascript objects,
   other ClojureScript colls into JavaScript arrays, and ClojureScript
   keywords into JavaScript strings. Credit:
   http://mmcgrana.github.com/2011/09/clojurescript-nodejs.html"
  [x]
  (cond
    (string? x) x
    (keyword? x) (name x)
    (map? x) (.-strobj (reduce (fn [m [k v]]
               (assoc m (clj->js k) (clj->js v))) {} x))
    (coll? x) (apply array (map clj->js x))
    :else x))


(defn log [& args] (apply (.-log js/console) (map str args)))

(def http (js/require "http"))
(def express (js/require "express"))
(def socket-io (js/require "socket.io"))

(defn page-template []
  (hiccups/html
   [:html
    [:head
     [:link {:rel "stylesheet" :href "/style.css"}]
     [:script {:type "text/javascript" :src "/cljs/goog/base.js"}]
     [:script {:type "text/javascript" :src "/cljs.js"}]
     [:script {:type "text/javascript"} "goog.require('metis.client');"]]
    [:body]]))

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
