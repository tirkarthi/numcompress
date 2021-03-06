(ns numcompress.core
  (:require [clojure.spec.alpha :as spec]
            [numcompress.utils :refer [validate-spec]]))


(spec/def ::series (spec/coll-of number?))
(spec/def ::precision (spec/and number? nat-int? #(<= %1 10)))
(spec/def ::text (spec/and string?
                           #(let [precision (- (int (get %1 0)) 63)]
                              (spec/valid? ::precision precision))))


(defn- calculate-diff
  [result last-num num precision]
  (let [pivotal (biginteger 0x20)
        adder   (biginteger 63)
        diff    (-> (- num last-num)
                    (#(biginteger (Math/round (*' %1 (Math/pow 10 precision)))))
                    (#(if (neg? %1)
                        (.not (.shiftLeft %1 1))
                        (.shiftLeft %1 1))))]
    (loop [diff diff
           result result]
      (if (>= diff pivotal)
        (recur (.shiftRight diff 5)
               (str result (char (+' adder
                                     (.or pivotal (.and diff (biginteger 0x1f)))))))
        (str result (char (+' adder diff)))))))


(defn compress
  "Returns a string for the given series of numbers and precision"
  ([series] (compress series 3))
  ([series precision]
   {:pre [(validate-spec ::series series)
          (validate-spec ::precision precision)]}
   (let [result (char (+ 63 precision))
         inputs (partition 2 1 (concat [0] series))]
     (reduce (fn [result [last-num num]]
               (calculate-diff result last-num num precision))
             result inputs))))


(spec/fdef compress
           :args (spec/and (spec/cat :series ::series :precision ::precision))
           :ret string?)


(defn- decompress-number
  [text index]
  (loop [result (biginteger 1)
         shift  0
         index  index
         b      (biginteger (- (int (get text index)) 64))]
    (if (< b 0x1f)
      [[(inc index)
        (let [result (biginteger (+ result (.shiftLeft (biginteger b) shift)))]
          (if (not= 0 (.and result (biginteger 1)))
            (.shiftRight (.not result) 1)
            (.shiftRight result 1)))]]
      (recur (biginteger (+ result (.shiftLeft (biginteger b) shift)))
             (+ shift 5)
             (inc index)
             (-' (int (get text (inc index))) (biginteger 64))))))


(defn decompress
  "Returns a collection of numbers for the given string"
  [text]
  {:pre [(validate-spec ::text text)]}
  (let [index      0
        last-num   0
        precision  (- (int (get text index)) 63)
        length     (count text)
        results    (loop [res (decompress-number text (inc index))
                          last-num last-num
                          result []]
                     (if (>= (ffirst res) length)
                       (conj result (+ last-num (second (first res))))
                       (recur (decompress-number text (ffirst res))
                              (+ last-num (second (first res)))
                              (conj result (+ last-num (second (first res)))))))]
    (mapv (fn [item]
            (if (> precision 0)
              (with-precision precision
                (*' item (Math/pow 10 (- precision))))
              (*' item (int (Math/pow 10 (- precision))))))
          results)))


(spec/fdef decompress
           :args (spec/cat :text ::text)
           :ret ::series)
