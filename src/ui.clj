(import '(javax.swing JFrame JLabel))

(defn start-ui
  "Starts UI."
  [world]
  (let [text (str world)
        label (JLabel. text)
        frame (JFrame. "Hyperspace")]
    (doto frame
      (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
      (.add label)
      (.pack)
      (.setVisible true))))
