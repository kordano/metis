(ns metis.server.views
  (:require
    [hiccup
      [page :refer [html5]]
      [element :refer [javascript-tag]]
      [page :refer [include-js]]]))

(defn- run-clojurescript [path init]
  (list
   (include-js path)
   (javascript-tag init)))

(defn index-page []
  (html5
   [:head
    [:title "Metis Math Services"]]
   [:body
    [:h1 "Welcome to Metis Math Services"]
    [:div {:id "Test"}]
    (run-clojurescript
     "js/cljs.js"
     "metis.repl.connect()")]))
