(ns hyperspace.swagger.server
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as s]
            [hyperspace.server.database.user :as user]))

(s/defschema User {:login String
                   :password String})

(defapi app
        (swagger-ui)
        (swagger-docs
          :title "Hyperspace API"
          :description "Simple 2D game written in Clojure")

        (swaggered "user"
                   :description "user manipulation"
                   (POST* "/register" []
                          :body [user User]
                          :summary "register user"
                          (do (user/create user)
                              (ok nil)))

                   (POST* "/login" []
                          :return String
                          :body [user User]
                          :summary "log in and get session token"
                          (let [session (user/login user)]
                            (if session
                              (ok session)
                              (forbidden "invalid username or password"))))))