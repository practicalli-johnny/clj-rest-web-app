(ns clj-rest-web-app.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :as ring]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.flash :refer [wrap-flash]]
            [clj-rest-web-app.controllers.home :as home]
            [clj-rest-web-app.controllers.sessions :as sessions])
  (:gen-class))

(defroutes app-routes
  home/routes
  sessions/routes
  route/not-found "Not Found")

(def application (-> app-routes
                   (wrap-defaults site-defaults)
                   (wrap-flash)))

(defn start [port]
  (ring/run-jetty application {:port port
                               :join? false}))

(defn -main []
  (let [port (Integer. (or (System/getenv "PORT") "8080"))]
    (start port)))
