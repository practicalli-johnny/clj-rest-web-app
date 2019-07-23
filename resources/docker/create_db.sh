#!/bin/bash
set -e

POSTGRES="psql --username ${POSTGRES_USER}"

echo "Creating database: ${SERVER_DB}"

$POSTGRES <<EOSQL
CREATE DATABASE ${SERVER_DB} OWNER ${POSTGRES_USER};
EOSQL

echo "Creating database: ${REPL_DB}"

$POSTGRES <<EOSQL
CREATE DATABASE ${REPL_DB} OWNER ${POSTGRES_USER};
EOSQL

