(require '[clojure.string :as str])

(def input
  (->> (slurp "day1.txt")
      str/split-lines
      (map (fn [string] (Integer/parseInt string)))))

(defn count-increases [depths]
  (->> depths
       (partition 2 1 input)
       (map (fn [[l r]] (< l r)))
       (filter true?)
       count))

(def first-result
  (count-increases input))

first-result

(def second-result
  (->> (partition 3 1 input)
       (map (fn [triple] (apply + triple)))
       count-increases))

second-result