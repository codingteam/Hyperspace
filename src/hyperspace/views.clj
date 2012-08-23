(ns hyperspace.views
  (:require
    [hiccup
      [page :refer [html5]]
      [element :refer [javascript-tag]]
      [page :refer [include-js]]]))

(defn- include-clojurescript [path]
  (list
    (javascript-tag "var CLOSURE_NO_DEPS = true;")
    (include-js path)))

(defn index-page []
  (html5
    [:head
      [:title "Hyperspace"]
      (include-clojurescript "/js/main.js")]
    [:body
      [:h1 "Hyperspace"]
      [:canvas {:id "canvas"
                :width "800"
                :height "600"}]]))
