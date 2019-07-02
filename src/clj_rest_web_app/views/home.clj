(ns clj-rest-web-app.views.home
  (:require [clj-rest-web-app.views.layout :as layout]
            [hiccup.core :refer [h]]))

(defn show
  []
  (layout/page "Home"
               [:h1 "Welcome!"]
               [:p "This project shows how to develop web apps using clojure"]))
