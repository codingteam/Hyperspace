(ns hyperspace.test.geometry
  (:use [hyperspace.geometry]
        [hyperspace.test.checkers]
        [midje.sweet]))

(facts "about vector-sum function"
       (vector-sum [5, 5] [2, 3]) => [7, 8]
       (vector-sum [0.2, 0.4] [0.1, 0.3]) => (just [(almost= 0.3)
                                                    (almost= 0.7)])
       (vector-sum [12, 62]) => [12, 62])

(facts "about vector-subtract function"
       (vector-subtract [3, 2] [2, 1]) => [1, 1]
       (vector-subtract [0.4 0.2] [0.3 0.2]) => (just [(almost= 0.1)
                                                       (almost= 0.0)])
       (vector-subtract [4, 6]) => [-4, -6])

(facts "about multiply-by-scalar function"
       (multiply-by-scalar [1, 2] 2) => [2, 4]
       (multiply-by-scalar [0.2, 0.5] 3) => (just [(almost= 0.6),
                                                   (almost= 1.5)]))

;; (facts "about polar->cartesian function"
;;        )

;; (facts "about cartesia->polar function"
;;        )

(facts "about vector-length function"
       (vector-length [10, 10]) => (almost= 14.142135623730951)
       (vector-length [1.23, 10.32]) => (almost= 10.393040940937354))

(facts "about normilize-vector function"
       (cartesian->polar (normilize-vector [10, 10]))
       =>
       (just [(almost= (-> [10, 10] cartesian->polar first))
              (almost= 1.0)])

       (cartesian->polar (normilize-vector [1.23, 10.32]))
       =>
       (just [(almost= (-> [1.23, 10.32] cartesian->polar first))
              (almost= 1.0)]))

(facts "about distance function"
       (distance [0, 0] [10, 10]) => (almost= (vector-length [10, 10]))
       (distance [34, 12] [-24, 42]) => (almost= 65.29931086925804))

;; (facts "about circle-X-circle? function"
;;        (circle-X-circle? {:position [0, 0],     :radius 10}
;;                          {:position [100, 100], :radius 10})
;;        => false

;;        (circle-X-circle? {:position [34, 32], :radius 20}
;;                          {:position []})
;;        => true
;;        )

;; (facts "about circle-X-any-circle? function"
;;        )

;; (facts "about circle-X-rectangle? fucntion"
;;        )