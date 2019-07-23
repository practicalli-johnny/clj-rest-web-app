(ns clj-rest-web-app.fixtures
  (:require [clojure.java.jdbc :as sql]
            [clj-rest-web-app.models.core :refer [db-url]]))

(defn clean-db
  []
  (sql/execute! db-url ["DELETE FROM users"]))

(defn database-cleaner
  [f]
  (f)
  (clean-db))
