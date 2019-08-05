(ns clj-rest-web-app.controllers.home-test
  (:require [clj-rest-web-app.controllers.home :refer :all]
            [clj-rest-web-app.models.core :refer [db-url]]
            [clojure.java.jdbc :as sql]
            [clojure.test :refer :all]
            [clj-rest-web-app.fixtures :as fixtures]
            [ring.mock.request :as mock]))

(use-fixtures :each fixtures/database-cleaner)

(deftest test-show-should-render
  (sql/insert! db-url :users {:id 1 :email "test@mail.com" :password "12345"})
  (is (clojure.string/includes? (routes (mock/request :get "/")) "Users in database: 1")))
