(ns clj-rest-web-app.controllers.home
  (:require [compojure.core :refer [defroutes GET]]
            [clj-rest-web-app.views.home :as view]))

(defn show
  []
  (view/show))

(defroutes routes
  (GET "/" [] (show)))
