(require '[clojure.string :as str])

(defn to-ints [string]
  (let [strings (str/split string #"")
        ints (map #(Integer/parseInt %) strings)]
    ints))

(comment
  (to-ints "12345"))

(def input
  (->> (slurp "day3.txt")
       str/split-lines))

(get "aap" 1)
(comment
  input)

(defn zero-at? [string index]
  (= \0 (get string index)))

(defn one-at? [string index]
  (= \1 (get string index)))

(comment
  (zero-at? "01" 0)
  (one-at? "10" 1))

(defn o2 [input index]
  (if (= 1 (count input))
    (Integer/parseInt (first input) 2)
    (let [zeros (filter #(zero-at? % index) input)
          ones (filter #(one-at? % index) input)]
      (if (> (count zeros) (count ones))
        (recur zeros (inc index))
        (recur ones (inc index))))))

(defn co2 [input index]
  (if (= 1 (count input))
    (Integer/parseInt (first input) 2)
    (let [zeros (filter #(zero-at? % index) input)
          ones (filter #(one-at? % index) input)]
      (if (< (count ones) (count zeros))
        (recur ones (inc index))
        (recur zeros (inc index))))))

(comment
  (o2 input 0)
  (co2 input 0))

(println "Second result: " (* (o2 input 0) (co2 input 0)))
