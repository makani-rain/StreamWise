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

-- Create users (if they exist, alter password to ensure idempotent setup)
DO $$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'catalog_user') THEN
       CREATE ROLE catalog_user LOGIN PASSWORD 'catalog_pass';
   ELSE
       ALTER ROLE catalog_user WITH PASSWORD 'catalog_pass';
   END IF;
   IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'consumer_user') THEN
       CREATE ROLE consumer_user LOGIN PASSWORD 'consumer_pass';
   ELSE
       ALTER ROLE consumer_user WITH PASSWORD 'consumer_pass';
   END IF;
END
$$;

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE catalog_db TO catalog_user;
GRANT ALL PRIVILEGES ON DATABASE catalog_db_dev TO catalog_user;
GRANT ALL PRIVILEGES ON DATABASE consumer_db TO consumer_user;
GRANT ALL PRIVILEGES ON DATABASE consumer_db_dev TO consumer_user;

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
