(ns clj-rest-web-app.models.user-test
  (:require [clj-rest-web-app.models.user :refer :all]
            [clj-rest-web-app.models.core :refer [db-url]]
            [clojure.java.jdbc :as sql]
            [clojure.test :refer :all]
            [clj-rest-web-app.fixtures :as fixtures]))

(use-fixtures :each fixtures/database-cleaner)

(deftest row-count-test
  (testing "Row count without query"
    (sql/insert! db-url :users {:id 1 :email "test@mail.com" :password "12345"})
    (is (= (row-count) 1))))
