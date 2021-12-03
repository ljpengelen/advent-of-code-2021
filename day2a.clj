(require '[clojure.string :as str])

(def input
  (->> (slurp "day2.txt")
       str/split-lines
       (map (fn [string] (str/split string #" ")))
       (map (fn [[command amount]] [(keyword command) (Integer/parseInt amount)]))))

input

(def initial-state
  {:horizontal 0
   :depth 0})

(defn execute [state command]
  (let [{:keys [horizontal depth]} state
        [direction amount] command]
    (case direction
      :forward {:horizontal (+ horizontal amount)
                :depth depth}
      :up {:horizontal horizontal
           :depth (- depth amount)}
      :down {:horizontal horizontal
             :depth (+ depth amount)})))

(execute initial-state [:forward 10])
(execute initial-state [:up 10])
(execute initial-state [:down 10])

(def final-state
  (reduce execute initial-state input))

(println "First result: " (* (:horizontal final-state) (:depth final-state)))
