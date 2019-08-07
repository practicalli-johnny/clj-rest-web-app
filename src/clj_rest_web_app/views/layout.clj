(ns clj-rest-web-app.views.layout
  (:require [hiccup.page :as h]))

(defn page
  [title & body]
  (h/html5
    [:head
      [:meta {:charset "utf-8"}]
      [:title title]
      [:link {:rel "stylesheet" :href "/css/styles.css" :type "text/css"}]]
    [:body
      [:div {:id "content"} body]]))
