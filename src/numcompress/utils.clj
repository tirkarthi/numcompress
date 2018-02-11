(ns numcompress.utils)

(defmacro validate-spec
  [spec input]
  `(if-not (spec/valid? ~spec ~input)
     (throw (Exception. (spec/explain-str ~spec ~input)))
     true))
