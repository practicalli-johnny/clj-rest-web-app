(ns clj-rest-web-app.controllers.sessions-test
  (:require [clj-rest-web-app.controllers.sessions :refer :all]
            [clj-rest-web-app.models.core :refer [db-url]]
            [clj-rest-web-app.models.user :as user]
            [clojure.java.jdbc :as sql]
            [clojure.test :refer :all]
            [clj-rest-web-app.fixtures :as fixtures]
            [ring.mock.request :as mock]))

(use-fixtures :each fixtures/database-cleaner)

(deftest test-login-form-should-render
  (let [request (assoc (mock/request :get "/login") :flash "Test Flash")]
    (is (clojure.string/includes? (:body (routes request)) "<p style=\"color: red;\">Test Flash</p>"))))

(deftest test-login
  (sql/insert! db-url :users {:id 1 :email "test@test.com" :password (user/encrypt-password "12345")})
  (testing "should be successful with correct credentials"
    (let [request (assoc (mock/request :post "/login") :params {:email "test@test.com" :password "12345"})]
      (is (= (routes request) successful-login))))
  (testing "should be unsuccessful with wrong credentials"
    (let [request (assoc (mock/request :post "/login") :params {:email "test@test.com" :password "123456"})]
      (is (= (routes request) unsuccessful-login))))
  (testing "should be unsuccessful without any credentials"
    (is (= (routes (mock/request :post "/login")) unsuccessful-login))))
