\echo -- Enable pgcrypto for gen_random_uuid()
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Drop tables (reverse dependency order) so the script can be re-run safely
\echo -- Dropping existing tables if any
DROP TABLE IF EXISTS subscribes CASCADE;
DROP TABLE IF EXISTS streams CASCADE;
DROP TABLE IF EXISTS watchlist_item CASCADE;
DROP TABLE IF EXISTS watchlist CASCADE;
DROP TABLE IF EXISTS package CASCADE;
DROP TABLE IF EXISTS title CASCADE;
DROP TABLE IF EXISTS consumer CASCADE;
DROP TABLE IF EXISTS streaming_service CASCADE;

-- Create tables in dependency order
\echo -- Creating table: streaming_service
CREATE TABLE streaming_service (
  service_id UUID default gen_random_uuid() primary key,
  name text UNIQUE NOT NULL
);

\echo -- Creating table: title
CREATE TABLE title (
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
CREATE TABLE package (
  package_id UUID default gen_random_uuid() primary key,
  service_id UUID references streaming_service(service_id) ON DELETE CASCADE,
  name text,
  price decimal(6, 2),
  period int default 1,
  ad_supported boolean default false,
  deprecated boolean default false
);

\echo -- Creating table: consumer
CREATE TABLE consumer (
  consumer_id UUID default gen_random_uuid() primary key,
  name text NOT NULL,
  address text,
  budget decimal(6, 0)
);

\echo -- Creating table: watchlist
CREATE TABLE watchlist (
  watchlist_id UUID default gen_random_uuid() primary key,
  consumer_id UUID references consumer(consumer_id) ON DELETE CASCADE
);

\echo -- Creating table: watchlist_item
CREATE TABLE watchlist_item (
  watchlist_item_id UUID default gen_random_uuid() primary key,
  watchlist_id UUID references watchlist(watchlist_id) ON DELETE CASCADE,
  title_id UUID references title(title_id) ON DELETE CASCADE
);

\echo -- Creating table: streams
CREATE TABLE streams (
  service_id UUID references streaming_service(service_id) ON DELETE CASCADE,
  title_id UUID references title(title_id) ON DELETE CASCADE,
  arrival_date bigint,
  leaving_date bigint,
  primary key (service_id, title_id)
);

\echo -- Creating table: subscribes
CREATE TABLE subscribes (
  consumer_id UUID references consumer(consumer_id) ON DELETE CASCADE,
  package_id UUID references package(package_id) ON DELETE CASCADE,
  renewal_date date,
  primary key (consumer_id, package_id)
);
