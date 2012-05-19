(ns hyperspace.test.geometry
  (:use [hyperspace.geometry]
        [hyperspace.test.utils]
        [midje.sweet]))

(fact "about vector-sum function"
      (vector-sum [2] [2]) => [4]
      (vector-sum [5, 5] [2, 3]) => [7, 8]
      (vector-sum [1, 2, 3] [4, 5, 6]) => [5, 7, 9]
      (vector-sum [0.2, 0.4] [0.1, 0.3]) => (just [(almost= 0.3)
                                                   (almost= 0.7)])
      (vector-sum [12, 62]) => [12, 62]
      (vector-sum [1, 2] [3, 4] [5, 6] [7, 8]) => [16, 20])

(fact "about vector-subtract function"
      (vector-subtract [3] [2]) => [1]
      (vector-subtract [3, 2] [2, 1]) => [1, 1]
      (vector-subtract [4, 5, 6] [6, 5, 4]) => [-2, 0, 2]
      (vector-subtract [0.4 0.2] [0.3 0.2]) => (just [(almost= 0.1)
                                                      (almost= 0.0)])
      (vector-subtract [4, 6]) => [-4, -6]
      (vector-subtract [100, 100] [2, 5] [5, 2] [9, 3]) => [84, 90])

(fact "about multiply-by-scalar function"
      (multiply-by-scalar [2] 10) => [20]
      (multiply-by-scalar [1, 2] 2) => [2, 4]
      (multiply-by-scalar [5, 2, 1] 5) => [25, 10, 5]
      (multiply-by-scalar [0.2, 0.5] 3) => (just [(almost= 0.6),
                                                  (almost= 1.5)]))

(fact "about vector-length function"
      (vector-length [7]) => (almost= 7)
      (vector-length [10, 10]) => (almost= 14.142135623730951)
      (vector-length [34, 123, 431]) => (almost= 449.4952725001677)
      (vector-length [1.23, 10.32]) => (almost= 10.393040940937354))

(fact "about normilize-vector function"
      (vector-length (normilize-vector [7])) => (almost= 1.0)
      (vector-length (normilize-vector [10, 10])) => (almost= 1.0)
      (vector-length (normilize-vector [34, 123, 431])) => (almost= 1.0)
      (vector-length (normilize-vector [1.23, 10.32])) => (almost= 1.0))