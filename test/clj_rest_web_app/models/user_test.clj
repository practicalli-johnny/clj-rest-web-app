(ns clj-rest-web-app.models.user-test
  (:require [clj-rest-web-app.models.user :refer :all]
            [clj-rest-web-app.models.core :refer [db-url]]
            [clojure.java.jdbc :as sql]
            [clojure.test :refer :all]
            [clj-rest-web-app.fixtures :as fixtures]))

(use-fixtures :each fixtures/database-cleaner)

(deftest row-count-wo-query-test
  (sql/insert! db-url :users {:id 1 :email "test@mail.com" :password "12345"})
  (is (= (row-count) 1)))

(deftest row-count-with-query-test
  (sql/insert! db-url :users {:id 1 :email "test@mail.com" :password "12345"})
  (sql/insert! db-url :users {:id 2 :email "mail@test.com" :password "12345"})
  (is (= (row-count "email LIKE ?" "test@%") 1)))

(deftest find-many-wo-query-test
  (sql/insert! db-url :users {:id 1 :email "test@mail.com" :password "12345"})
  (sql/insert! db-url :users {:id 2 :email "mail@test.com" :password "12345"})
  (let [users (find-many)]
    (is (= (get users 0) {:id 1 :email "test@mail.com" :password "12345"}))
    (is (= (get users 1) {:id 2 :email "mail@test.com" :password "12345"}))))

(deftest find-many-with-query-test
  (sql/insert! db-url :users {:id 1 :email "test@mail.com" :password "12345"})
  (sql/insert! db-url :users {:id 2 :email "mail@test.com" :password "12345"})
  (let [users (find-many "email LIKE ?" "test@%")]
    (is (= (count users) 1))
    (is (= (first users) {:id 1 :email "test@mail.com" :password "12345"}))))

(deftest find-one-wo-query-test
  (sql/insert! db-url :users {:id 1 :email "test@mail.com" :password "12345"})
  (sql/insert! db-url :users {:id 2 :email "mail@test.com" :password "12345"})
  (let [user (find-one)]
    (is (= user {:id 1 :email "test@mail.com" :password "12345"}))))

(deftest find-one-wo-query-test
  (sql/insert! db-url :users {:id 1 :email "test@mail.com" :password "12345"})
  (sql/insert! db-url :users {:id 2 :email "mail@test.com" :password "12345"})
  (let [user (find-one "email LIKE ?" "%@test%")]
    (is (= user {:id 2 :email "mail@test.com" :password "12345"}))))
