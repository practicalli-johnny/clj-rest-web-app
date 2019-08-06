(ns clj-rest-web-app.controllers.sessions
  (:require [compojure.core :refer [defroutes GET POST]]
            [clj-rest-web-app.views.sessions :as view]
            [clj-rest-web-app.models.user :as user]))

(defn successful-login
  [user]
  {:status 302
   :headers {"Location" "/"}
   :body ""
   :flash "Successfully logged in"
   :session {:user-id (:id user)}})

(def unsuccessful-login
  {:status 302
   :headers {"Location" "/login"}
   :body ""
   :flash "Login failure"})

(def successful-logout
  {:status 302
   :headers {"Location" "/"}
   :body ""
   :flash "Successfully logged out"
   :session nil})

(defn login-form
  [flash]
  (view/login flash))

(defn authenticate-user
  [email password]
  (let [user (user/find-one "email = ?" email)]
    (if (user/valid-password? user password)
      (successful-login user)
      unsuccessful-login)))

(defn login
  [email password]
  (if (and (some? email) (some? password))
    (authenticate-user email password)
    unsuccessful-login))

(defroutes routes
  (GET "/login" [:as {flash :flash}] (login-form flash))
  (POST "/login" [email password] (login email password))
  (POST "/logout" [] successful-logout))
