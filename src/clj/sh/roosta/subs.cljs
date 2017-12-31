(ns sh.roosta.subs
  (:require [re-frame.core :refer [reg-sub]]
            [reagent.debug :as d]
            [goog.object :as gobj]))

(reg-sub
 :viewport-size
 (fn [db _]
   (:viewport-size db)))
