(ns ^:figwheel-no-load sh.roosta.dev
  (:require
    [sh.roosta.core :as core]
    [devtools.core :as devtools]))

(devtools/install!)

(enable-console-print!)

(core/init!)
