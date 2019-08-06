(ns clj-rest-web-app.models.core
  (:require [clojure.java.jdbc :as sql]
            [environ.core :refer [env]]))

(def db-url (env :database-url))

(defn extract-query-args
  [query]
  (if (empty? query)
      []
      (into [] (rest query))))

(defn extract-query
  [query]
  (if (empty? query)
      ""
      (str " WHERE " (first query))))

(defn select-query
  [fields table query]
  (into []
        (flatten [(str "SELECT "
                       fields
                       " FROM "
                       table
                       (extract-query query))
                 (extract-query-args query)])))

(defn find-many
  [table query]
  (->> (select-query "*" table query)
       (sql/query db-url)
       (into [])))

(defn find-one
  [table query]
  (let [[select-stmt & select-args] (select-query "*" table query)]
    (->> [(str select-stmt " LIMIT 1") select-args]
         flatten
         (remove nil?)
         (into [])
         (sql/query db-url)
         first)))

(defn row-count
  [table query]
  (->> (select-query "COUNT(*)" table query)
       (sql/query db-url)
       first
       :count))

(defn insert-one
  [table record]
  (sql/insert! db-url (keyword table) record))
