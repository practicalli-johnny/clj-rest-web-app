(defproject clj-rest-web-app "0.1.0-SNAPSHOT"
  :description "This application provides an example of how web apps can be written in clojure"
  :url "https://github.com/bakku/clj-rest-web-app"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/java.jdbc "0.7.9"]
                 [org.postgresql/postgresql "42.2.6"]
                 [com.taoensso/timbre "4.10.0"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [ring/ring-defaults "0.1.2"]
                 [ring/ring-mock "0.4.0"]
                 [compojure "1.6.1"]
                 [hiccup "1.0.5"]
                 [environ "1.1.0"]
                 [ragtime "0.8.0"]
                 [crypto-password "0.2.1"]]
  :plugins [[lein-environ "1.1.0"]]
  :aliases {"migrate"  ["run" "-m" "clj-rest-web-app.lein-tasks.migrations/migrate"]
            "rollback" ["run" "-m" "clj-rest-web-app.lein-tasks.migrations/rollback"]}
  :main ^:skip-aot clj-rest-web-app.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
