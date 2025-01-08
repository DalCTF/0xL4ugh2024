# Manifesto

## Analysis

This problem provides us with the source code for a website about the benefits of the Clojure programming language. As expected, the website is written in Clojure as well.

## Solution

The first thing we will notice when entering the website is that it has a login form. Since we have access to the source code, it is unlikely that this is a matter of brute-forcing. We can double-check that on the main function of the [`core.clj`](src/manifesto/core.clj) file, where a new `admin` user is added with a `random-uuid` as their password.

### Getting access

In the code we will find the definition for all the routes that the website accepts under the `[(defn routes ...)]` line. One of these is the route that matches the index (`/`) uri. This is where we will find the snippet

```clojure
;; index route
(re-matches #"/" uri)
(-> (r/response
    (render-file "index.html"
                {:prefer (or (query-params "prefer") (session "prefer") "light")
                :username (session "username")
                :url uri}))
    (assoc :session (merge {"prefer" "light"} session query-params)))
```

The first couple of lines specify that the template of the file [`index.html`](resources/templates/index.html) will be rendered using the `:prefer`, `:username`, and `:url` parameters. The next line, however, sets the value of the session map as the result of the merge between `{"prefer" "light"}`, `session`, and `query-params`. This means that whenever we request the index of this website, the session map will be the combination of the existing session parameter **and** all of the query parameters. This means that we can set any value of the session by simply passing it as a query parameter.

If we request the page as `localhost:80?username=admin`, we will see that we are not successfully logged in as the `admin` user.

Some lines later, we will find the following snippet:

```clojure
;; display user gists, protected for now
(re-matches #"/gists" uri)
(cond (not= (session "username") "admin")
    (json-response {:error "You do not have enough privileges"})
```

We can see that this snippet tests to see if the current `username` is the session is the `admin` before responding to any requests to the `gists` uri. Given that we have logged in as the admin using our query parameter, we can now navigate to this page.

### Injecting Code

The `gists/` page allows you to submit snippets of code that will be saved on the website. Of course, they have to be in Clojure to be accepted. Looking at the code, we will find the following snippet that handles `POST` requests to this uri:

```clojure
(= request-method :post)
(let [{:strs [gist]} form-params]
    ;; clojure has excellent error handling capabilities
    (try
        (insert-gist (session "username") (read-string gist))
        (r/redirect "/gists")
        (catch Exception e (json-response {:error e}))))
```

Here we will notice that the method `read-string` is being called with our input before storing it on the database. According to the [documentation](https://clojuredocs.org/clojure.core/read-string):

```text
Reads one object from the string s. Optionally include reader options, as specified in read.
Note that read-string can execute code (controlled by *read-eval*), and as such should be used only with trusted sources.
```

This description (and many examples on the page) shows that the `read-string` method is not just parsing the string, but may also interpret it in some cases. More specifically, if the string contains **reader macros**, they will be interpreted during the method call. Here's a simple example:

```clojure
=> (read-string "#=(+ 1 1)")
2
```

This behaviour can be seen if we submit `#=(+ 1 1)` as our input on the `gists/` page. We can now leverage this to look for the flag. While the code itself makes no mention of the flag, the [`Dockerfile`](./Dockerfile) shows us that it is included as an environment vairable for the system. We can call `environ.core/env` with the name of our variable to retrieve it from the environment. Therefore, something like this should work:

```clojure
#=(environ.core/env :flag)
```

Sending that as our input will reload the page and give us the flag:

```
flag{ISZmNep2qk1pW20n0aWm2PuTOUQviBql}
```