(ns numcompress.core-test
  (:require [clojure.test :refer :all]
            [numcompress.core :refer :all]))

(deftest compress-test
  (testing "Compression"
    (is (= "?qbW" (compress [12345] 0)))
    (is (= "BoinpV" (compress [12345])))
    (is (= "Io{pcifu|_Fopmdsp}ak@fzhxqgxf}@" (compress [12365.54524354, 14789.54699, 11367.67845123] 10)))))

(deftest decompression-test
  (testing "Decompressoion"
    (is (= [12345] (decompress "?qbW")))
    (is (= [12345.0] (decompress "BoinpV")))
    (is (= [12365.54524354, 14789.54699, 11367.67845123] (decompress "Io{pcifu|_Fopmdsp}ak@fzhxqgxf}@")))))
