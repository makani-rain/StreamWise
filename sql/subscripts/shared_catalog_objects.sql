\echo -- Shared catalog objects (minimal) used by both catalog and consumer DBs
CREATE EXTENSION IF NOT EXISTS pgcrypto;

\echo -- Creating table: streaming_service
CREATE TABLE IF NOT EXISTS streaming_service (
  service_id UUID default gen_random_uuid() primary key,
  name text UNIQUE NOT NULL
);

\echo -- Creating table: title
CREATE TABLE IF NOT EXISTS title (
  title_id UUID default gen_random_uuid() primary key,
  title_name varchar(128) NOT NULL,
  type varchar(32),
  rating varchar(32),
  release_date varchar(32),
  category text,
  creator text,
  language varchar(64),
  CONSTRAINT unique_title_release UNIQUE(title_name, type, rating, creator, release_date, language)
);

\echo -- Creating table: package
CREATE TABLE IF NOT EXISTS package (
  package_id UUID default gen_random_uuid() primary key,
  service_id UUID references streaming_service(service_id) ON DELETE CASCADE,
  name text,
  price decimal(6, 2),
  period int default 1,
  ad_supported boolean default false,
  deprecated boolean default false
);
