(ns clj-rest-web-app.lein-tasks.migrations
  (:require [ragtime.jdbc :as jdbc]
            [ragtime.repl :as repl]
            [environ.core :refer [env]]))

(defn load-config []
  {:datastore  (jdbc/sql-database (env :database-url))
   :migrations (jdbc/load-resources "migrations")})

(defn migrate []
  (repl/migrate (load-config)))

(defn rollback []
  (repl/rollback (load-config)))
