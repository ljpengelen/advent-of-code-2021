(require '[clojure.string :as str])

(defn to-ints [string]
  (let [strings (str/split string #"")
        ints (map #(Integer/parseInt %) strings)]
    ints))

(comment
  (to-ints "12345"))

(def input
  (->> (slurp "day3.txt")
       str/split-lines
       (map to-ints)))

(comment
  input)

(defn add [s1 s2]
  (map + s1 s2))

(comment
  (add [1 2 3] [4 5 6])
  (reduce add input))

(def result
  (let [sum (reduce add input)
        threshold (/ (count input) 2)
        most-common-bits (map #(if (> % threshold) "1" "0") sum)
        bit-string (apply str most-common-bits)]
    (Integer/parseInt bit-string 2)))

(comment
  result)

(println "First result: " result)
