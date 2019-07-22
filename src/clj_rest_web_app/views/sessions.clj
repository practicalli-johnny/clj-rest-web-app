(ns clj-rest-web-app.views.sessions
  (:require [clj-rest-web-app.views.layout :as layout]
            [hiccup.form :as form]
            [ring.util.anti-forgery :as anti-forgery]))

(defn login
  [flash]
  (layout/page "Login"
               [:h1 "Login"]
               (if (some? flash)
                 [:p {:style "color: red;"} flash])
               (form/form-to [:post "/login"]
                             (anti-forgery/anti-forgery-field)
                             (form/label "email" "Email")
                             (form/email-field "email")
                             [:br]
                             (form/label "password" "Password")
                             (form/password-field "password")
                             (form/submit-button "Login"))))
