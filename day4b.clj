(require '[clojure.string :as str]
         '[clojure.set :as cs])

(defn to-int [string]
  (Integer/parseInt string))

(defn to-ints [string]
  (let [strings (str/split string #",")
        ints (map to-int strings)]
    ints))

(defn to-board [ints]
  {:values (into {} (for [row (range 5)
                          col (range 5)]
                      [(ints (+ (* 5 row) col)) [row col]]))
   :rows [[] [] [] [] []]
   :columns [[] [] [] [] []]
   :bingo? false})

(defn to-boards [strings]
  (->> strings
       (map #(re-seq #"\d+" %))
       (map #(mapv to-int %))
       (map to-board)))

(let [[numbers-string & board-strings] (-> (slurp "day4.txt")
                                           (str/split #"\n\n"))]
  (def numbers (to-ints numbers-string))
  (def boards (to-boards board-strings)))

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

(defn score [board used-numbers]
  (let [number (last used-numbers)
        all-numbers (set (keys (:values board)))
        remaining-numbers (cs/difference all-numbers (set used-numbers))]
    (* number (apply + remaining-numbers))))

(defn play-bingo
  ([numbers boards] (play-bingo numbers boards [] []))
  ([numbers boards used-numbers winners]
   (if (empty? boards)
     (score (last winners) used-numbers)
     (let [number (first numbers)
           marked-boards (map #(mark % number) boards)
           used-numbers (conj used-numbers number)
           winners (into winners (filter :bingo? marked-boards))
           boards (remove :bingo? marked-boards)]
       (recur (rest numbers) boards used-numbers winners)))))

(println "Second result: " (play-bingo numbers boards))
