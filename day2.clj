(require '[clojure.string :as str])

(def input
  (->> (slurp "day2.txt")
      str/split-lines
       (map (fn [string] (str/split string #" ")))
       (map (fn [[command amount]] [(keyword command) (Integer/parseInt amount)]))))

input

(defn execute [state command]
  state)

(reduce execute  {:horizontal 0
                  :depth 0} input)
