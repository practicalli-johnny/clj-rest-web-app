(ns clj-rest-web-app.core
  (:require [compojure.core :refer :all]
            [ring.adapter.jetty :as ring]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [clj-rest-web-app.controllers.home :as home])
  (:gen-class))

(defroutes app-routes
  home/routes)

(def application (wrap-defaults app-routes site-defaults))

(defn start [port]
  (ring/run-jetty application {:port port
                               :join? false}))

(defn -main []
  (let [port (Integer. (or (System/getenv "PORT") "8080"))]
    (start port)))
