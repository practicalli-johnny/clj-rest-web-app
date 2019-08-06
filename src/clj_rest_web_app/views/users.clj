(ns clj-rest-web-app.views.users
  (:require [clj-rest-web-app.views.layout :as layout]
            [hiccup.form :as form]
            [ring.util.anti-forgery :as anti-forgery]))

(defn signup
  [email error]
  (layout/page "Sign Up"
               [:h1 "Sign Up"]
               (if (some? error)
                   [:p {:style "color: red;"} error])
               (form/form-to [:post "/users"]
                             (anti-forgery/anti-forgery-field)
                             (form/label "email" "Email")
                             (form/email-field "email" email)
                             [:br]
                             (form/label "password" "Password")
                             (form/password-field "password")
                             (form/submit-button "Sign Up"))))
