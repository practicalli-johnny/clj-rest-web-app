(ns clj-rest-web-app.models.user
  (:require [clj-rest-web-app.models.core :as db]
            [crypto.password.bcrypt :as bcrypt]))

(defn encrypt-password
  [password]
  (bcrypt/encrypt password))

(defn valid-password?
  [user password]
  (bcrypt/check password (:password user)))

(defn row-count
  [& query]
  (db/row-count "users" query))

(defn find-many
  [& query]
  (db/find-many "users" query))

(defn find-one
  [& query]
  (db/find-one "users" query))

(defn insert-one
  [user]
  (try
    (first (db/insert-one "users" (->> (:password user)
                                       (encrypt-password)
                                       (assoc user :password))))
    (catch org.postgresql.util.PSQLException e
      (if (clojure.string/includes? (.getMessage e) "users_email_key")
          false
          (throw e)))))
