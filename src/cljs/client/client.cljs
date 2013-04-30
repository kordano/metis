(ns metis.client
  (:use [metis.helpers :only [clj->js]]
        [metis.dom :only [domready watch data target q] :as dom]
        [webfui.dom :only [defdom]]))

(def my-dom (atom nil))
(def state (atom []))
(defdom my-dom)

(defn render-all
  [old-dom]
  [:div
   [:h1 "I've seen things you people wouldn't believe. Attack ships on fire off the shoulder of Orion. I watched c-beams glitter in the dark near the Tannhäuser Gate. All those moments will be lost in time, like tears in rain. Time to die."]])


(defn update-dom []
  (swap! my-dom render-all))


(domready
 (fn []
   (update-dom)))
