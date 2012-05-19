(ns hyperspace.test.geometry
  (:use [hyperspace.geometry]
        [hyperspace.test.utils]
        [midje.sweet]))

(fact "about vector-sum function"
      ;; One-dimensional integer vectors
      (vector-sum [2] [2])               => [4]
      
      ;; Two-dimensional integer vectors
      (vector-sum [5, 5] [2, 3])         => [7, 8]
      
      ;; Tree-dimenstional integer vectors
      (vector-sum [1, 2, 3] [4, 5, 6])   => [5, 7, 9]

      ;; Floating point vectors
      (vector-sum [0.2, 0.4] [0.1, 0.3]) => (just [(almost= 0.3)
                                                   (almost= 0.7)]))