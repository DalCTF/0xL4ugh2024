(defproject manifesto "0.1.0-SNAPSHOT"
  :description "manifesto: a great manifesto on a great language"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [ring/ring-core "1.13.0"]
                 [ring/ring-jetty-adapter "1.8.2"]
                 [selmer "1.12.61"]
                 [cheshire "5.13.0"]
                 [environ/environ "1.2.0"]]
  :repl-options {:init-ns manifesto.core}
  :main manifesto.core)
