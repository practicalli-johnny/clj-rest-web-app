(ns clj-rest-web-app.controllers.users-test
  (:require [clojure.test :refer :all]
            [clojure.java.jdbc :as sql]
            [ring.mock.request :as mock]
            [clj-rest-web-app.controllers.users :refer :all]
            [clj-rest-web-app.models.core :refer [db-url]]
            [clj-rest-web-app.fixtures :as fixtures]
            [clj-rest-web-app.models.user :as user]))

(use-fixtures :each fixtures/database-cleaner)

(deftest test-signup-form-should-render
  (is (-> (mock/request :get "/users/signup")
          (routes)
          (:body)
          (clojure.string/includes? "<h1>Sign Up</h1>"))))

(deftest test-signup
  (testing "should be successful with correct inputs"
    (let [request (assoc (mock/request :post "/users") :params {:email "test@mail.com" :password "12345"})]
      (is (routes request)
          {:status 302
           :headers {"Location" "/"}
           :body ""
           :flash "Successfully signed up"})
      (let [record (first (sql/query db-url ["SELECT * FROM users LIMIT 1"]))]
        (is (:email record) "test@mail.com")
        (is (:password record) (user/encrypt-password "12345")))))
  (testing "should not be successful for same email"
    (is (-> (mock/request :post "/users")
            (assoc :params {:email "test@mail.com" :password "12345"})
            (routes)
            (:body)
            (clojure.string/includes? "<p class=\"alert\">Email is already taken</p>"))))
  (testing "should not be successful with empty inputs"
    (is (-> (mock/request :post "/users")
            (assoc :params {:email "test@mail.com" :password ""})
            (routes)
            (:body)
            (clojure.string/includes? "<p class=\"alert\">User could not be created</p>")))))
