(ns clj-rest-web-app.views.home
  (:require [clj-rest-web-app.views.layout :as layout]
            [clj-rest-web-app.models.user :as user]))

(defn show
  [flash]
  (layout/page "Home"
               [:h1 "Welcome!"]
               (if (some? flash)
                 [:p {:style "color: red;"} flash])
               [:p "This project shows how to develop web apps using clojure"]
               [:p (str "Users in database: " (user/row-count))]
               [:a {:href "/login"} "Login"]))
