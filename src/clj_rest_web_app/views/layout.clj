(ns clj-rest-web-app.views.layout
  (:require [hiccup.page :as h]))

(defn page
  [title & body]
  (h/html5
    [:head
      [:meta {:charset "utf-8"}]
      [:title title]]
    [:body
      [:div {:id "content"} body]]))
