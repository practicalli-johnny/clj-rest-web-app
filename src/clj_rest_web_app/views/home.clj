(ns clj-rest-web-app.views.home
  (:require [clj-rest-web-app.views.layout :as layout]
            [clj-rest-web-app.models.user :as user]
            [hiccup.core :refer [h]]))

(defn show
  []
  (layout/page "Home"
               [:h1 "Welcome!"]
               [:p "This project shows how to develop web apps using clojure"]
               [:p (str "Users in database: " (user/row-count))]))
