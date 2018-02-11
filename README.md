# numcompress

A clojure port of [numcompress](https://github.com/amit1rrr/numcompress) python library

## Usage

```clojure
(require [numcompress.core :refer all])

(compress [12345] 0) ; "?qbW"
(decompress "?qbW") ; [12345]
```

## Benchmarks

```clojure
numcompress.core> (use 'criterium.core)
numcompress.core> (let [compressed (compress (range 1000 2100))] (quick-bench (decompress compressed)))
Evaluation count : 210 in 6 samples of 35 calls.
             Execution time mean : 3.004693 ms
    Execution time std-deviation : 243.400916 µs
   Execution time lower quantile : 2.793014 ms ( 2.5%)
   Execution time upper quantile : 3.380952 ms (97.5%)
                   Overhead used : 2.608691 ns
numcompress.core> (let [input (range 1000 2100)] (quick-bench (compress input)))
Evaluation count : 36 in 6 samples of 6 calls.
             Execution time mean : 20.067066 ms
    Execution time std-deviation : 2.513239 ms
   Execution time lower quantile : 17.578674 ms ( 2.5%)
   Execution time upper quantile : 22.842125 ms (97.5%)
                   Overhead used : 2.608691 ns
```

## Thanks

Thanks to @amit1rrr for the python library

## License

Copyright © 2018 Karthikeyan S

Distributed under the MIT License
