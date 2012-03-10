(import '(org.lwjgl.opengl Display DisplayMode))

(defn start-ui
  "Starts UI."
  [world]
  (Display/setDisplayMode (DisplayMode. 800 600))
  (Display/create)
  (while (not (Display/isCloseRequested))
    (Display/update)))
