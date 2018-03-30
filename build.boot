(set-env!
 :source-paths #{"src/clj" "src/cljs"}
 :resource-paths #{"html"}

 :dependencies '[
                 [org.clojure/clojure "1.8.0"]         ;; add CLJ
                 [org.clojure/clojurescript "1.9.473"] ;; add CLJS
                 [adzerk/boot-cljs "1.7.228-2"]        ;; CLJS compiler
                 [pandeiro/boot-http "0.7.6"]          ;; web server
                 [adzerk/boot-reload "0.5.1"]          ;; live reload
                 [adzerk/boot-cljs-repl "0.3.3"]       ;; CLJS bREPL
                 [com.cemerick/piggieback "0.2.1"]     ;; needed by bREPL
                 [weasel "0.7.0"]                      ;; needed by bREPL
                 [org.clojure/tools.nrepl "0.2.12"]    ;; needed by bREPL
                 [org.clojars.magomimmo/domina "2.0.0-SNAPSHOT"] ;; DOM manip
                 [org.clojars.magomimmo/shoreleave-remote-ring "0.3.3"]
                 [org.clojars.magomimmo/shoreleave-remote "0.3.1"]
                 [hiccups "0.3.0"]
                 [javax.servlet/javax.servlet-api "3.1.0"]     ;; for dev only
                 [compojure "1.5.2"]                   ;; routing lib
                 ])

(require '[adzerk.boot-cljs :refer [cljs]]
         '[pandeiro.boot-http :refer [serve]]
         '[adzerk.boot-reload :refer [reload]]
         '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]])

;;; add dev task
(deftask dev
  "Launch immediate feedback dev environment"
  []
  (comp
   (serve :handler 'modern-cljs.remotes/app            ;; ring handler
          :resource-root "target"                      ;; root classpath
          :dir "target"
          :reload true)                                ;; reload ns
   (watch)
   (reload)
   (cljs-repl) ;; before cljs
   (cljs)
   (target :dir #{"target"})))
