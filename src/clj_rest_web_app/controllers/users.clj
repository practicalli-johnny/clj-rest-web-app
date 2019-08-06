(ns clj-rest-web-app.controllers.users
  (:require [compojure.core :refer [defroutes GET POST]]
            [clj-rest-web-app.views.users :as view]
            [clj-rest-web-app.models.user :as user]))

(def successful-signup
  {:status 302
   :headers {"Location" "/"}
   :body ""
   :flash "Successfully signed up"})

(defn signup-form
  ([]
   (view/signup nil nil))
  ([email error]
   (view/signup email error)))

(defn create-user
  [email password]
  (if (user/insert-one {:email email :password password})
      successful-signup
      (signup-form email "Email is already taken")))

(defn signup
  [email password]
  (if (or (clojure.string/blank? email) (clojure.string/blank? password))
    (signup-form email "User could not be created")
    (create-user email password)))

(defroutes routes
  (GET "/users/signup" [] (signup-form))
  (POST "/users" [email password] (signup email password)))
