(ns sh.roosta.prod
  (:require [sh.roosta.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
