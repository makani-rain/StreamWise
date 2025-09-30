\echo === Creating databases ===

-- Create databases if they don't exist (psql doesn't support CREATE DATABASE IF NOT EXISTS, so drop/recreate to ensure idempotency during development)
DROP DATABASE IF EXISTS catalog_db;
CREATE DATABASE catalog_db;

DROP DATABASE IF EXISTS consumer_db;
CREATE DATABASE consumer_db;

DROP DATABASE IF EXISTS catalog_db_dev;
CREATE DATABASE catalog_db_dev;

DROP DATABASE IF EXISTS consumer_db_dev;
CREATE DATABASE consumer_db_dev;

\echo === Creating schemas in each database ===

-- Create catalog schema in catalog_db
\connect catalog_db
\i '/docker-entrypoint-initdb.d/subscripts/catalog_schema.sql'

-- Create catalog schema in catalog_db_dev
\connect catalog_db_dev
\i '/docker-entrypoint-initdb.d/subscripts/catalog_schema.sql'

-- Create consumer schema in consumer_db
\connect consumer_db
\i '/docker-entrypoint-initdb.d/subscripts/consumer_schema.sql'

-- Create consumer schema in consumer_db_dev
\connect consumer_db_dev
\i '/docker-entrypoint-initdb.d/subscripts/consumer_schema.sql'
