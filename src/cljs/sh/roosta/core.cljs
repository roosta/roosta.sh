(ns sh.roosta.core
    (:require [reagent.core :as r]
              [sh.roosta.home :as home]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]))

(defn about-page []
  [:div [:h2 "About sh.roosta"]
   [:div [:a {:href "/"} "go to the home page"]]])

;; -------------------------
;; Routes

(def page (r/atom #'home/main))

(defn current-page []
  [:div [@page]])

(secretary/defroute "/" []
  (reset! page #'home/main))

(secretary/defroute "/about" []
  (reset! page #'about-page))

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!
    {:nav-handler
     (fn [path]
       (secretary/dispatch! path))
     :path-exists?
     (fn [path]
       (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (mount-root))
