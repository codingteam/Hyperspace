(ns hyperspace.webgl)

(def vertex-shader "
attribute vec2 pos;

void main()
{
  gl_Position = vec4(pos, 0, 1);
}
")

(def fragment-shader "
precision mediump float;

void main()
{
  gl_FragColor = vec4(0, 0.8, 0, 1);
}
")

(defn create-shader
  [gl text type]
  (let [shader (.createShader gl type)]
    (.shaderSource gl shader text)
    (.compileShader gl shader)
    shader))

(defn create-program
  [gl vstr fstr]
  (let [program (.createProgram gl)
        vshader (create-shader gl vstr (.-VERTEX_SHADER gl))
        fshader (create-shader gl fstr (.-FRAGMENT_SHADER gl))]
    (.attachShader gl program vshader)
    (.attachShader gl program fshader)
    (.linkProgram gl program)
    program))

(defn get-context
  [canvas]
  (.getContext canvas "experimental-webgl"))

(defn init
  [context]
  (let [buffer (.createBuffer context)
        program (create-program context vertex-shader fragment-shader)]
    (.bindBuffer context (.-ARRAY_BUFFER context) buffer)
    (.useProgram context program)
    (set! (.-vertexPosAttrib program) (.getAttribLocation context program "pos"))
    (.enableVertexAttribArray context (.-vertexPosAttrib program))
    (.vertexAttribPointer context (.-vertexPosAttrib program)
                                  2
                                  (.-FLOAT context)
                                  false
                                  0
                                  0)))

(defn clear
  [context]
  (.clear context (.-COLOR_BUFFER_BIT context)))

(defn ellipse
  [context [x, y] radius segments]
;; Let's call triangle as 'circle'.
  (let [coords (js/Array. x                  (- x radius)
                          (- x (/ radius 2)) (- y (/ radius 2))
                          (+ x (/ radius 2)) (- y (/ radius 2)))]
    (.bufferData context (.-ARRAY_BUFFER context)
                         (js/Float32Array. coords)
                         (.-STATIC_DRAW context))
    (.drawArrays context (.-TRIANGLES context) 0 3)))
