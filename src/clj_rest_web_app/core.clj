(ns clj-rest-web-app.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :as ring]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.session :refer [wrap-session]]
            [ring.middleware.session.cookie :refer [cookie-store]]
            [ring.middleware.flash :refer [wrap-flash]]
            [environ.core :refer [env]]
            [clj-rest-web-app.middleware.logger :refer [wrap-logger]]
            [clj-rest-web-app.controllers.home :as home]
            [clj-rest-web-app.controllers.sessions :as sessions]
            [clj-rest-web-app.controllers.users :as users])
  (:gen-class))

(defroutes app-routes
  home/routes
  sessions/routes
  users/routes
  (route/resources "/")
  (route/not-found "<h1>Not Found</h1>"))

(def session-defaults (-> site-defaults
                          (:session)
                          (assoc :store (cookie-store {:key (env :application-secret)}))))

(def app-defaults (-> site-defaults
                      (assoc :session session-defaults)))

(def application (-> app-routes
                     (wrap-defaults app-defaults)
                     (wrap-logger)))

(defn start
  [port]
  (ring/run-jetty application {:port port
                               :join? false}))

(defn -main
  []
  (let [port (Integer. (or (env :port) "8080"))]
    (start port)))
