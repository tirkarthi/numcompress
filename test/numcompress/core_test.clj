(ns numcompress.core-test
  (:require [clojure.test :refer :all]
            [numcompress.core :refer :all]))

(deftest compress-test
  (testing "Compression"
    (is (= "?qbW" (compress {:series [12345] :precision 0})))
    (is (= "Io{pcifu|_Fopmdsp}ak@fzhxqgxf}@" (compress {:series [12365.54524354, 14789.54699, 11367.67845123] :precision 10})))))

(deftest decompression-test
  (testing "Decompressoion"
    (is (= [12345] (decompress {:text "?qbW"})))
    (is (= [12365.54524354, 14789.54699, 11367.67845123] (decompress {:text "Io{pcifu|_Fopmdsp}ak@fzhxqgxf}@"})))))
