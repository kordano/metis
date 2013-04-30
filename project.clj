(defproject metis "0.1.0-SNAPSHOT"
  :description "Simple statistic library"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  ;; CLJ source code path
  :source-paths ["src"]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/math.numeric-tower "0.0.2"]
                 [hiccups "0.1.1"]
                 [webfui "0.2.1"]
                 [midje "1.5.0"]]

  :plugins [[lein-cljsbuild "0.3.0"]]

  ;; cljsbuild tasks configuration
  :cljsbuild {:builds
              [{:source-paths ["src/cljs/server"]
                :compiler
                {:output-to "js/main.js"
                 :output-dir "js"
                 :target :nodejs
                 :optimizations :simple
                 :prettyprint true}}
               {:source-paths ["src/cljs/client"]
                :compiler
                {:output-to "static/cljs.js"
                 :output-dir "static/cljs"}}]})
