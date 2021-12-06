(require '[clojure.string :as str]
         '[clojure.set :as cs])

(defn to-int [string]
  (Integer/parseInt string))

(defn to-ints [string]
  (let [strings (str/split string #",")
        ints (map to-int strings)]
    ints))

(comment
  (to-ints "1,2,3,4,5"))

(defn to-board [ints]
  {:values (into {} (for [row (range 5)
                          col (range 5)]
                      [(ints (+ (* 5 row) col)) [row col]]))
   :rows [[] [] [] [] []]
   :columns [[] [] [] [] []]
   :bingo? false})

(range 5)
(comment
  (to-board [22 13 17 11 0 8 2 23 4 24 21 9 14 16 7 6 10 3 18 5 1 12 20 15 19]))

(defn to-boards [strings]
  (->> strings
       (map #(re-seq #"\d+" %))
       (map #(mapv to-int %))
       (map to-board)))

(let [[numbers-string & board-strings] (-> (slurp "day4.txt")
                                           (str/split #"\n\n"))]
  (def numbers (to-ints numbers-string))
  (def boards (to-boards board-strings)))

(comment
  numbers
  boards)

(defn mark [board number]
  (let [{:keys [values rows columns]} board]
    (if-let [[row-index column-index] (values number)]
      (let [row (rows row-index)
            column (columns column-index)
            bingo? (or (= 4 (count row)) (= 4 (count column)))]
        {:values values
         :rows (update rows row-index conj number)
         :columns (update columns column-index conj number)
         :bingo? bingo?})
      board)))

(mark {:values {0 [4 1], 24 [3 3], 4 [4 2], 13 [1 1], 22 [4 0], 17 [2 1], 2 [3 2], 11 [3 1], 14 [4 3], 10 [4 4]}
       :rows [[] [] [] [] []]
       :columns [[] [] [] [] []]
       :bingo? false}
      24)

(defn score [board number used-numbers]
  (let [all-numbers (set (keys (:values board)))
        remaining-numbers (cs/difference all-numbers (set used-numbers))]
    (* number (apply + remaining-numbers))))

(defn play-bingo
  ([numbers boards] (play-bingo numbers boards []))
  ([numbers boards used-numbers]
   (let [number (first numbers)
         marked-boards (map #(mark % number) boards)
         used-numbers (conj used-numbers number)]
     (if-let [winner (first (filter :bingo? marked-boards))]
       (score winner number used-numbers)
       (recur (rest numbers) marked-boards used-numbers)))))

(println "First result: " (play-bingo numbers boards))
