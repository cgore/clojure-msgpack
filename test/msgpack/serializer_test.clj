(ns msgpack.serializer-test
  (:require [clojure.test :refer :all]
            [msgpack.utils :refer :all]
            [msgpack.serializer :refer :all]))

(deftest nil-test
  (testing "nil"
    (is (= (unsigned-bytes [0xc0]) (serialize nil)))))

(deftest boolean-test
  (testing "booleans"
    (is (= (unsigned-bytes [0xc2]) (serialize false)))
    (is (= (unsigned-bytes [0xc3]) (serialize true)))))

(deftest int-test
  (testing "positive fixnum"
    (is (= (unsigned-bytes [0x00]) (serialize 0)))
    (is (= (unsigned-bytes [0x10]) (serialize 0x10)))
    (is (= (unsigned-bytes [0x7f]) (serialize 0x7f))))
  (testing "negative fixnum"
    (is (= (unsigned-bytes [0xff]) (serialize -1)))
    (is (= (unsigned-bytes [0xf0]) (serialize -16)))
    (is (= (unsigned-bytes [0xe0]) (serialize -32))))
  (testing "uint 8"
    (is (= (unsigned-bytes [0xcc 0x80]) (serialize 0x80)))
    (is (= (unsigned-bytes [0xcc 0xf0]) (serialize 0xf0)))
    (is (= (unsigned-bytes [0xcc 0xff]) (serialize 0xff))))
  (testing "uint 16"
    (is (= (unsigned-bytes [0xcd 0x01 0x00]) (serialize 0x100)))
    (is (= (unsigned-bytes [0xcd 0x20 0x00]) (serialize 0x2000)))
    (is (= (unsigned-bytes [0xcd 0xff 0xff]) (serialize 0xffff))))
  (testing "uint 32"
    (is (= (unsigned-bytes [0xce 0x00 0x01 0x00 0x00]) (serialize 0x10000)))
    (is (= (unsigned-bytes [0xce 0x00 0x20 0x00 0x00]) (serialize 0x200000)))
    (is (= (unsigned-bytes [0xce 0xff 0xff 0xff 0xff]) (serialize 0xffffffff))))
  (testing "uint 64"
    (is (= (unsigned-bytes [0xcf 0x00 0x00 0x00 0x01 0x00 0x00 0x00 0x00]) (serialize 0x100000000)))
    (is (= (unsigned-bytes [0xcf 0x00 0x00 0x20 0x00 0x00 0x00 0x00 0x00]) (serialize 0x200000000000)))
    (is (= (unsigned-bytes [0xcf 0xff 0xff 0xff 0xff 0xff 0xff 0xff 0xff]) (serialize 0xffffffffffffffff))))
  (testing "int 8"
    (is (= (unsigned-bytes [0xd0 0xdf]) (serialize -33)))
    (is (= (unsigned-bytes [0xd0 0x9c]) (serialize -100)))
    (is (= (unsigned-bytes [0xd0 0x80]) (serialize -128))))
  (testing "int 16"
    (is (= (unsigned-bytes [0xd1 0xff 0x7f]) (serialize -129)))
    (is (= (unsigned-bytes [0xd1 0xf8 0x30]) (serialize -2000)))
    (is (= (unsigned-bytes [0xd1 0x80 0x00]) (serialize -32768))))
  (testing "int 32"
    (is (= (unsigned-bytes [0xd2 0xff 0xff 0x7f 0xff]) (serialize -32769)))
    (is (= (unsigned-bytes [0xd2 0xc4 0x65 0x36 0x00]) (serialize -1000000000)))
    (is (= (unsigned-bytes [0xd2 0x80 0x00 0x00 0x00]) (serialize -2147483648))))
  (testing "int 64"
    (is (= (unsigned-bytes [0xd3 0xff 0xff 0xff 0xff 0x7f 0xff 0xff 0xff]) (serialize -2147483649)))
    (is (= (unsigned-bytes [0xd3 0xf2 0x1f 0x49 0x4c 0x58 0x9b 0xff 0xfe]) (serialize -1000000000000000002)))
    (is (= (unsigned-bytes [0xd3 0x80 0x00 0x00 0x00 0x00 0x00 0x00 0x00]) (serialize -9223372036854775808)))))

(deftest float-test
  (testing "float 64"
    (is (= (unsigned-bytes [0xcb 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00]) (serialize 0.0)))
    (is (= (unsigned-bytes [0xcb 0x40 0x04 0x00 0x00 0x00 0x00 0x00 0x00]) (serialize 2.5)))
    (is (= (unsigned-bytes [0xcb 0x47 0x33 0x42 0x61 0x72 0xc7 0x4d 0x82]) (serialize (Math/pow 10 35))))))
