(ns metis.client
  (:use [metis.repl :only [connect]]
        [domina :only [by-id value]]
        [webfui.dom :only [defdom]])
  (:require [clojure.browser.repl :as repl]))

; fire up a repl for the browser and eval namespace on top once connected
#_(do (ns metis.clojure.start)
      (require 'cljs.repl.browser)
      (cemerick.piggieback/cljs-repl
       :repl-env (doto (cljs.repl.browser/repl-env :port 9000)
                   cljs.repl/-setup)))
