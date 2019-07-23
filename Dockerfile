FROM clojure:openjdk-8-lein

WORKDIR /usr/src/app

COPY project.clj /usr/src/app/

RUN lein deps

COPY . /usr/src/app

RUN mkdir -p build && mv "$(lein uberjar | sed -n 's/^Created \(.*standalone\.jar\)/\1/p')" build/app-standalone.jar

CMD ["java", "-jar", "build/app-standalone.jar"]
