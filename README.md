# numcompress

A clojure port of [numcompress](https://github.com/amit1rrr/numcompress) python library

## Usage

```clojure
(require [numcompress.core :refer all])

(compress {:series [12345] :precision 0}) ; "?qbW"
(decompress {:text "?qbW"}) ; [12345]
```

## Thanks

Thanks to @amit1rrr for the python library

## License

Copyright © 2018 Karthikeyan S

Distributed under the MIT License
