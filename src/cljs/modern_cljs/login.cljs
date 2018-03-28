(ns modern-cljs.login)

;; define the function to be attached to form submission event
(defn validate-form
  []
  (let [email (.getElementById js/document "email")
        password (.getElementById js/document "password")]
    (if (and (> (count (.-value email)) 0)
             (> (count (.-value password)) 0))
      true
      (do (js/alert "Please, complete the form!")
          false))))

;; define the function to attach validate-form to onsubmit event of the form
(defn init []
  ;;verify that js/document exists and that it has a getElementById property
  (if (and js/document
           (.-getElementById js/document))
    ;; get loginForm by element id and set its onsubmit property to
    ;; our validate-form function
    (let [login-form (.getElementById js/document "loginForm")]
      (.log js/console "loginForm=" login-form)
      (set! (.-onsubmit login-form) validate-form))))

;; initialise the HTML page in an unobtrusive way
(set! (.-onload js/window) init)
