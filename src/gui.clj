(import '(javax.swing JFrame JLabel))

(defn start-gui
  "Starts GUI."
  []
  (let [label (JLabel. "Hyperspace")
        frame (JFrame. "Hyperspace")]
    (doto frame
      (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
      (.add label)
      (.pack)
      (.setVisible true))))
