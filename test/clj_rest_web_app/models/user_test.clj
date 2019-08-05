(ns clj-rest-web-app.models.user-test
  (:require [clj-rest-web-app.models.user :refer :all]
            [clj-rest-web-app.models.core :refer [db-url]]
            [clojure.java.jdbc :as sql]
            [clojure.test :refer :all]
            [clj-rest-web-app.fixtures :as fixtures]))

(use-fixtures :each fixtures/database-cleaner)

(deftest test-row-count
  (sql/insert! db-url :users {:id 1 :email "test@mail.com" :password "12345"})
  (sql/insert! db-url :users {:id 2 :email "mail@test.com" :password "12345"})
  (testing "without query"
    (is (= (row-count) 2)))
  (testing "with query"
    (is (= (row-count "email LIKE ?" "test@%") 1))))

(deftest test-find-many
  (sql/insert! db-url :users {:id 1 :email "test@mail.com" :password "12345"})
  (sql/insert! db-url :users {:id 2 :email "mail@test.com" :password "12345"})
  (testing "without query"
    (let [users (find-many)]
      (is (= (get users 0) {:id 1 :email "test@mail.com" :password "12345"}))
      (is (= (get users 1) {:id 2 :email "mail@test.com" :password "12345"}))))
  (testing "with query"
    (let [users (find-many "email LIKE ?" "test@%")]
      (is (= (count users) 1))
      (is (= (first users) {:id 1 :email "test@mail.com" :password "12345"})))))

(deftest test-find-one
  (sql/insert! db-url :users {:id 1 :email "test@mail.com" :password "12345"})
  (sql/insert! db-url :users {:id 2 :email "mail@test.com" :password "12345"})
  (testing "without query"
    (let [user (find-one)]
      (is (= user {:id 1 :email "test@mail.com" :password "12345"}))))
  (testing "with query"
    (let [user (find-one "email LIKE ?" "%@test%")]
      (is (= user {:id 2 :email "mail@test.com" :password "12345"})))))

(deftest test-valid-password
  (let [user {:id 1 :email "test@mail.com" :password (encrypt-password "12345")}]
    (testing "returns true for correct password"
      (is (valid-password? user "12345")))
    (testing "returns false for incorrect password"
      (is (not (valid-password? user "123456"))))))
