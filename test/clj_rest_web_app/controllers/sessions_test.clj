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
    (is (-> (routes request)
            (:body)
            (clojure.string/includes? "<p class=\"alert\">Test Flash</p>")))))

(deftest test-successul-login
  (testing "should set session"
    (is (= (successful-login {:id 5})
           {:status 302
            :headers {"Location" "/"}
            :body ""
            :flash "Successfully logged in"
            :session {:user-id 5}}))))

(deftest test-login
  (sql/insert! db-url :users {:id 1 :email "test@test.com" :password (user/encrypt-password "12345")})
  (testing "should be successful with correct credentials"
    (let [request (assoc (mock/request :post "/login") :params {:email "test@test.com" :password "12345"})]
      (is (= (routes request) (successful-login {:id 1})))))
  (testing "should be unsuccessful with wrong credentials"
    (let [request (assoc (mock/request :post "/login") :params {:email "test@test.com" :password "123456"})]
      (is (= (routes request) unsuccessful-login))))
  (testing "should be unsuccessful without any credentials"
    (is (= (routes (mock/request :post "/login")) unsuccessful-login))))

(deftest test-logout
  (is (= (routes (mock/request :post "/logout"))
         {:status 302
          :headers {"Location" "/"}
          :body ""
          :flash "Successfully logged out"
          :session nil})))
