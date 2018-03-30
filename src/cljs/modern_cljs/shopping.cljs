(ns modern-cljs.shopping
  (:require [domina.core :refer [append! by-class by-id destroy! value set-value!]]
            [domina.events :refer [listen!]]
            [hiccups.runtime]
            [shoreleave.remotes.http-rpc :refer [remote-callback]]
            [cljs.reader :refer [read-string]])
  (:require-macros [hiccups.core :refer [html]]
                   [shoreleave.remotes.macros :as macros]))

(defn calculate
  "Calculate the total shopping order."
  []
  (let [quantity (read-string (value (by-id "quantity")))
        price (read-string (value (by-id "price")))
        tax (read-string (value (by-id "tax")))
        discount (read-string (value (by-id "discount")))]
    (remote-callback :calculate
                     [quantity price tax discount]
                     #(set-value! (by-id "total") (.toFixed % 2)))))

(defn ^:export init
  []
  (when (and js/document
           (.-getElementById js/document))
    (listen! (by-id "calc") :click calculate)
    (listen! (by-id "calc") :mouseover
             (fn []
               (append! (by-id "shoppingForm")
                        (html [:div.help "Click to calculate"]))))
    (listen! (by-id "calc") :mouseout
             (fn []
               (destroy! (by-class "help"))))))
