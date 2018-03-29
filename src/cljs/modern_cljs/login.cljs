(ns modern-cljs.login
  (:require [domina.core :refer [append! by-class by-id destroy! value]]
            [domina.events :refer [listen!]]
            [hiccups.runtime])
  (:require-macros [hiccups.core :refer [html]]))

;; define the function to be attached to form submission event
(defn validate-form
  []
  (if (and (> (count (value (by-id "email"))) 0)
           (> (count (value (by-id "password"))) 0))
    true
    (do (js/alert "Please, complete the form!")
        false)))

;; define the function to attach validate-form to onsubmit event of the form
(defn ^:export init []
  ;;verify that js/document exists and that it has a getElementById property
  (when (and js/document
           (.-getElementById js/document))
    ;; get loginForm by element id and set its onsubmit property to
    ;; our validate-form function
    (listen! (by-id "submit") :click validate-form)
    (listen! (by-id "submit") :mouseover
             (fn []
               (append! (by-id "loginForm")
                        (html [:div.help "Click to submit"]))))
    (listen! (by-id "submit") :mouseout
             (fn []
               (destroy! (by-class "help"))))))
