# numcompress [![Build Status](https://travis-ci.org/tirkarthi/numcompress.svg?branch=master)](https://travis-ci.org/tirkarthi/numcompress)

A clojure port of [numcompress](https://github.com/amit1rrr/numcompress) Python library

Simple way to compress and decompress numerical series.
 - Easily gets you above 80% compression ratio
 - You can specify the precision you need for floating points (up to 10 decimal points)
 - Useful to store or transmit stock prices, monitoring & other time series data in compressed string format

Compression algorithm is based on [google encoded polyline format](https://developers.google.com/maps/documentation/utilities/polylinealgorithm). The python library is motivated by usefulness of [time aware polyline](https://github.com/hypertrack/time-aware-polyline-py) built by [Arjun Attam](https://github.com/arjun27) at [HyperTrack](https://github.com/hypertrack/time-aware-polyline-py). This is a clojure port of the Python library.

## Leiningen

[xtreak/numcompress "0.1.0"]

## Usage

```clojure
numcompress.core> (require [numcompress.core :refer all])
numcompress.core> (let [shuffled     (shuffle (range 2000 2010))
                        compressed   (compress shuffled)
                        decompressed (decompress compressed)]
                    (println "Series       : " shuffled)
                    (println "Compressed   : " compressed)
                    (println "Decompressed : " decompressed))
Series       :  [2001 2005 2004 2009 2000 2007 2003 2006 2008 2002]
Compressed   :  BoecyB_yFn}@owHnqPotL~xFozD_|B~uJ
Decompressed :  [2001.0 2005.0 2004.0 2009.0 2000.0 2007.0 2003.0 2006.0 2008.0 2002.0]
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

Thanks to @amit1rrr for the Python library

## License

Copyright © 2018 Karthikeyan S

Distributed under the MIT License
