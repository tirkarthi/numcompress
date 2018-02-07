# Introduction to numcompress

```clojure
(require [numcompress.core :refer all])

(compress {:series [12345] :precision 0}) ; "?qbW"
(decompress {:text "?qbW"}) ; [12345]
```
