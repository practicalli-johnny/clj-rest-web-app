# Clojure REST Web App

![Build status](https://travis-ci.org/bakku/clj-rest-web-app.svg?branch=master)

This project shows how to create a more or less REST-y web application using clojure.

## Development

The easiest way to work on the application is by using docker.

```
docker-compose up
```

starts a webserver on port 8080 with code reloading enabled and connected to the PSQL server database.

```
docker-compose exec app lein repl
```

starts a REPL to which you can connect e.g. with `vim-fireplace`. I mostly have both
commands running at the same time, so I can run tests and evaluate code through
`vim-fireplace` and take a look at the web views using the running server in docker.

The lein repl will have his own database to not delete data of the running web server.

## Production

When building the docker image a fresh uberjar is created at: `/usr/bin/app-standalone.jar`.
This can easily be started by using docker on production.
