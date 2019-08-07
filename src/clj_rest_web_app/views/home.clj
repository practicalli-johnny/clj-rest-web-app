(ns clj-rest-web-app.views.home
  (:require [hiccup.form :as form]
            [ring.util.anti-forgery :as anti-forgery]
            [clj-rest-web-app.views.layout :as layout]
            [clj-rest-web-app.models.user :as user]))

(defn show
  [user flash]
  (layout/page "Home"
               [:h1 "Welcome!"]
               (if (some? flash)
                 [:p {:class "alert"} flash])
               [:p "This project shows how to develop web apps using clojure"]
               [:p (str "Users in database: " (user/row-count))]
               (if (nil? user)
                 [:div
                   [:a {:href "/login"} "Login"]
                   [:p {:class "divider"} "|"]
                   [:a {:href "/users/signup"} "Sign Up"]]
                 (list
                   [:p "You are logged in as: " (:email user)]
                   [:br]
                   (form/form-to [:post "/logout"]
                                 (anti-forgery/anti-forgery-field)
                                 (form/submit-button "Logout"))))))
