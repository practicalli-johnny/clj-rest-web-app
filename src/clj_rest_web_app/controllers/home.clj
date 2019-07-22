(ns clj-rest-web-app.controllers.home
  (:require [compojure.core :refer [defroutes GET]]
            [clj-rest-web-app.views.home :as view]))

(defn show
  [flash]
  (view/show flash))

(defroutes routes
  (GET "/" [:as {flash :flash}] (show flash)))
