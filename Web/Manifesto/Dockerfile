FROM clojure

WORKDIR /app

COPY project.clj .
RUN lein deps

COPY src/ src/
COPY resources/ resources/

ENV CLOJURE_PORT 80
ENV FLAG '0xL4ugh{this_is_a_fake_flag}'

EXPOSE 80

ENTRYPOINT ["lein", "run"]
