(ns clj-rest-web-app.controllers.home
  (:require [compojure.core :refer [defroutes GET]]
            [clj-rest-web-app.views.home :as view]
            [clj-rest-web-app.models.user :as user]))

(defn show
  [session flash]
  (if-let [user-id (:user-id session)]
    (view/show (user/find-one "id = ?" user-id) flash)
    (view/show nil flash)))

(defroutes routes
  (GET "/" [:as {session :session flash :flash}] (show session flash)))
