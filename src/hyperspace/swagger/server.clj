(ns hyperspace.swagger.server
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as s])
  (:use [hyperspace.server.database.datasource :only [datasource]]))

(s/defschema Total {:total Long})

(defapi app
        (swagger-ui)
        (swagger-docs
          :title "Hyperspace API"
          :description "Simple 2D game written in Clojure")

        (swaggered "user"
                   :description "user manipulation"
                   (POST* "/register" []
                          :return String
                          :query-params [login :- String
                                         password :- String]
                          :summary "register user"
                          (ok "Not implemented"))))