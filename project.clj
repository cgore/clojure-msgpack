(defproject cgore/clojure-msgpack "1.2.0"
  :description "A lightweight Clojure implementation of the MessagePack spec."
  :url "https://github.com/cgore/clojure-msgpack"
  :license {:name "The MIT License (MIT)"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/test.check "0.7.0"]]
  :global-vars {*warn-on-reflection* true}
  :scm {:name "git"
        :url "https://github.com/cgore/clojure-msgpack"})
