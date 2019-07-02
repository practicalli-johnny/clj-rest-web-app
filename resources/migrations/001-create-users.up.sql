CREATE TABLE users (
  id       BIGSERIAL,
  email    TEXT NOT NULL UNIQUE,
  password TEXT NOT NULL
) ;
