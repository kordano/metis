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
     [:script {:type "text/javascript" :src "/socket.io/socket.io.js"}]
     [:script {:type "text/javascript" :src "cljs/goog/base.js"}]
     [:script {:type "text/javascript" :src "/socket.io/socket.io.js"}]]
    [:body
     [:h1 "Hello Sailor!"]]]))

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
