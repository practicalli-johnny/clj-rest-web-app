(ns clj-rest-web-app.views.home
  (:require [clj-rest-web-app.views.layout :as layout]
            [clj-rest-web-app.models.user :as user]))

(defn show
  [user flash]
  (layout/page "Home"
               [:h1 "Welcome!"]
               (if (some? flash)
                 [:p {:style "color: red;"} flash])
               [:p "This project shows how to develop web apps using clojure"]
               [:p (str "Users in database: " (user/row-count))]
               (if (nil? user)
                 (list
                   [:a {:href "/login"} "Login"]
                   [:a {:href "/users/signup"} "Sign Up"])
                 [:p "You are logged in as: " (:email user)])))
