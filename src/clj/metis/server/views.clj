(ns metis.server.views
  (:require
    [hiccup
nil      [page :refer [html5]]
      [element :refer [javascript-tag]]
     [page :refer [include-js]]]
    [garden.core :refer [css]]))

(defn- run-clojurescript [path init]
  (list
   (include-js path)
   (javascript-tag init)))

(def html-background-color "#e5e5e5")
(def background-color "#1a1a1a")
(def hover-background-color "#8C0806")

(defn body-css []
  [:body :html
    {:font "16px sans-serif"
     :margin "0"
     :padding "0"
     :color "#000"
     :background html-background-color}])


(defn wrap-css []
  [:#wrap
   {:width "1000px"
    :margin "0 auto"
    :background "#99c"}])


(defn header-css []
  [:#header
   {:background background-color
    :color "#ffffff"
    :margin "0"
    :text-align "center"
    :font-size "large"}])


(def controls-css []
  [:#controls])


(defn sidebar-css []
  [:#sidebar
   {:float "left"
    :width "100px"
    }
    [:ul
     {:padding "0px"
      :margin "0px"
      :font-family "Arial, Helvetica, sans-serif"
      :font-size "15px"
      :color "#FFF"
      :list-style "none"
      :text-indent "15px"}
     [:li
      {:background background-color
       :line-height "28px"
       :border-bottom "1px solid #333"}
      [:a
       {:text-decoration "none"
        :color "#7A7A7A"
        :display "block"}
       [:&:hover
        {:background hover-background-color
         :color "#FFFFFF"
         :text-shadow "1px 1px 1px rgba(0,0,0,0.75)"
         }]]
      [:&#active
       {:background hover-background-color
        :color "#FFFFFF"
        :text-shadow "1px 1px 1px rgba(0,0,0,0.75)"
        }]]]])


(defn plottings-css []
  [:#plottings
   {:float "right"
    :width "600px"}])


(defn default-css []
  (css
   (body-css)
   (wrap-css)
   (header-css)
   (sidebar-css)
   (plottings-css)
   ))


(defn index-page []
  (html5
   [:head
    [:title "Metis"]
    [:style {:type "text/css"} (default-css)]]
   [:body
    [:div {:id "wrap"}
     [:div {:id "header"} [:h1 "METIS"] [:p "Fun with statistics"]]
     [:div {:id "plottings"}]
     [:div {:id "sidebar"}
      [:ul
       [:li {:id "active"} [:h3 [:span {:href "#"} "Home"]]]
       [:li [:a {:id "boxplotter"} "Boxplot"]]
       [:li [:a {:href "#"} "Github"]]
       [:li [:a {:href "#"} "About Us"]]
       ]]]
    (run-clojurescript
     "js/cljs.js"
     "metis.repl.connect()")]))
