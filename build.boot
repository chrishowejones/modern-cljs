(set-env!
 :source-paths #{"src/cljs"}
 :resource-paths #{"html"}
 :dependencies '[[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.473"]
                 [adzerk/boot-cljs "1.7.228-2"]
                 [pandeiro/boot-http "0.7.6" :exclusions [org.clojure/clojure]]
                 [org.clojure/tools.nrepl "0.2.12" :exclusions [org.clojure/clojure]]
                 [adzerk/boot-reload "0.5.1" :exclusions [org.clojure/clojure]]
                 [adzerk/boot-cljs-repl "0.3.3"]
                 [com.cemerick/piggieback "0.2.1" :scope "test"]
                 [weasel "0.7.0" :scope "test" :exclusions [org.clojure/clojurescript]]
                 ])

(require '[adzerk.boot-cljs :refer [cljs]]
         '[pandeiro.boot-http :refer [serve]]
         '[adzerk.boot-reload :refer [reload]]
         '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
         )

(deftask dev
  "Launch Immediate Feedback Development Environment"
  []
  (comp
   (serve :dir "target")
   (watch)
   (reload)
   (cljs-repl) ;; before cljs task
   (cljs)
   (target :dir #{"target"})))
