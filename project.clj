(defproject sh.roosta "0.1.0-SNAPSHOT"
  :description "Roosta's homepage root"
  :url "http://roosta.sh"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [ring-server "0.5.0"]
                 [reagent "0.7.0"]
                 [reagent-utils "0.2.1"]
                 [ring "1.6.3"]
                 [ring/ring-defaults "0.3.1"]
                 [compojure "1.6.0"]
                 [hiccup "1.0.5"]
                 [yogthos/config "0.9"]
                 [org.clojure/clojurescript "1.9.946"
                  :scope "provided"]
                 [secretary "1.2.3"]
                 [venantius/accountant "0.2.3"
                  :exclusions [org.clojure/tools.reader]]]

  :plugins [[lein-environ "1.1.0"]
            [lein-cljsbuild "1.1.7"]
            [lein-asset-minifier "0.4.4"
             :exclusions [org.clojure/clojure]]]

  :ring {:handler sh.roosta.handler/app
         :uberwar-name "sh.roosta.war"}

  :min-lein-version "2.5.0"

  :uberjar-name "sh.roosta.jar"

  :main sh.roosta.server

  :clean-targets ^{:protect false}
  [:target-path
   [:cljsbuild :builds :app :compiler :output-dir]
   [:cljsbuild :builds :app :compiler :output-to]]

  :source-paths ["src/clj" "src/cljc"]
  :resource-paths ["resources" "target/cljsbuild"]

  :minify-assets
  {:assets
   {"resources/public/css/site.min.css" "resources/public/css/site.css"}}

  :cljsbuild {:builds {:min {:source-paths ["src/cljs" "src/cljc" "env/prod/cljs"]
                             :compiler {:output-to "target/cljsbuild/public/js/app.js"
                                        :output-dir "target/cljsbuild/public/js"
                                        :source-map "target/cljsbuild/public/js/app.js.map"
                                        :optimizations :advanced
                                        :pretty-print false}}

                       :app {:source-paths ["src/cljs" "src/cljc" "env/dev/cljs"]
                             :figwheel {:on-jsload "sh.roosta.core/mount-root"}
                             :compiler {:main "sh.roosta.dev"
                                        :asset-path "/js/out"
                                        :output-to "target/cljsbuild/public/js/app.js"
                                        :output-dir "target/cljsbuild/public/js/out"
                                        :source-map true
                                        :optimizations :none
                                        :pretty-print  true}}}}

  :figwheel
  {:http-server-root "public"
   :server-port 3449
   :nrepl-port 7002
   :nrepl-middleware ["cemerick.piggieback/wrap-cljs-repl"]
   :css-dirs ["resources/public/css"]
   :ring-handler sh.roosta.handler/app}

  :profiles {:dev {:repl-options {:init-ns sh.roosta.repl
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

                   :dependencies [[binaryage/devtools "0.9.8"]
                                  [ring/ring-mock "0.3.2"]
                                  [ring/ring-devel "1.6.3"]
                                  [prone "1.1.4"]
                                  [figwheel-sidecar "0.5.14"]
                                  [org.clojure/tools.nrepl "0.2.13"]
                                  [com.cemerick/piggieback "0.2.2"]
                                  [pjstadig/humane-test-output "0.8.3"]]

                   :source-paths ["env/dev/clj" "script"]
                   :plugins [[lein-figwheel "0.5.14"]]

                   :injections [(require 'pjstadig.humane-test-output)
                                (pjstadig.humane-test-output/activate!)]

                   :env {:dev true}}

             :uberjar {:hooks [minify-assets.plugin/hooks]
                       :source-paths ["env/prod/clj"]
                       :prep-tasks ["compile" ["cljsbuild" "once" "min"]]
                       :env {:production true}
                       :aot :all
                       :omit-source true}})
