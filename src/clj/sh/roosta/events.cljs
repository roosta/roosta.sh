(ns sh.roosta.events
  (:import [goog.dom ViewportSizeMonitor])
  (:require  [goog.events.EventType :as event-type]
             [re-frame.core :as rf :refer [reg-event-db reg-event-fx dispatch after]]
             [sh.roosta.db :as db]
             [goog.events :as gevents]))

(defonce vsm (ViewportSizeMonitor.))

(reg-event-fx
 :initialize-db
 (fn []
   (let [size (.getSize vsm)]
     {:db db/default-db
      :dispatch [:set-viewport-size [(.-width size) (.-height size) ]]})))

(defonce vsm-listener
  (gevents/listen vsm
                  event-type/RESIZE
                  (fn [e]
                    (let [size (.getSize vsm)]
                      (dispatch [:set-viewport-size [(.-width size) (.-height size) ]])))))
(reg-event-db
 :set-viewport-size
 (fn [db [_ new]]
   (assoc db :viewport-size new)))
