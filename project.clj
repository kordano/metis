(defproject metis "0.1.0-SNAPSHOT"
  :description "Some statistics and numerics with visualizations"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  ;; CLJ source code path
  :source-paths ["src" "src/clj"]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/math.numeric-tower "0.0.2"]
                 [ring/ring-jetty-adapter "1.1.0"]
                 [com.cemerick/piggieback "0.0.4"]
                 [hiccups "0.1.1"]
                 [domina "1.0.1"]
                 [webfui "0.2.1"]
                 [midje "1.5.0"]
                 [hiccup "1.0.2"]
                 [compojure "1.1.5"]]

  :ring {:handler metis.routes/app}

  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

  :plugins [[lein-cljsbuild "0.3.0"]
            [lein-ring "0.8.3"]]

  ;; cljsbuild tasks configuration
  :cljsbuild
  {
   :repl-listen-port 9000
   :repl-launch-commands
   {"firefox" ["firefox"
               :stdout ".repl-firefox-out"
               :sterr ".repl-firefox-err"]
    }

              :builds
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
                 :optimizations :simple
                 :output-dir "static/cljs"}}]})
