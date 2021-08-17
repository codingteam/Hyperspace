(ns hyperspace.test.library.geometry
  (:use [clojure.test]
        [hyperspace.library.geometry]
        [hyperspace.test.checkers]))

(deftest vector-sum-tests
  (is (= (vector-sum [5, 5] [2, 3]) [7, 8]))
  (is-almost= (vector-sum [0.2, 0.4] [0.1, 0.3]) [0.3 0.7])
  (is (= (vector-sum [12, 62]) [12, 62])))

(deftest vector-subtract-tests
  (is (= (vector-subtract [3, 2] [2, 1]) [1, 1]))
  (is-almost= (vector-subtract [0.4 0.2] [0.3 0.2]) [0.1 0.0])
  (is (= (vector-subtract [4, 6]) [-4, -6])))

(deftest multiply-by-scalar-tests
  (is (= (multiply-by-scalar [1, 2] 2) [2, 4]))
  (is-almost= (multiply-by-scalar [0.2, 0.5] 3) [0.6 1.5]))

;; (deftest polar->cartesian-tests
;;        )

;; (deftest cartesian->polar-tests
;;        )

(deftest vector-length-tests
  (is-almost= (vector-length [10, 10]) 14.142135623730951)
  (is-almost= (vector-length [1.23, 10.32]) 10.393040940937354))

(deftest normalize-vector-tests
  (is-almost= (cartesian->polar (normalize-vector [10, 10]))
              [(-> [10, 10] cartesian->polar first) 1.0])
  (is-almost= (cartesian->polar (normalize-vector [1.23, 10.32]))
              [(-> [1.23, 10.32] cartesian->polar first) 1.0]))

(deftest distance-tests
  (is-almost= (distance [0, 0] [10, 10]) (vector-length [10, 10]))
  (is-almost= (distance [34, 12] [-24, 42]) 65.29931086925804))

(deftest heading-tests
  (is-almost= (heading [0 0] [1 1]) (/ Math/PI 4))
  (is-almost= (heading [0 0] [1 0]) 0))

;; (deftest circle-X-circle?-tests
;;        (is (not (circle-X-circle? {:position [0, 0],     :radius 10}
;;                                   {:position [100, 100], :radius 10})))
;;
;;        (is (circle-X-circle? {:position [34, 32], :radius 20}
;;                              {:position []}))
;;        )

;; (deftest circle-X-any-circle?-tests
;;        )

;; (deftest circle-X-rectangle?-tests
;;        )
