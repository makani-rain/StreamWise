\echo -- Consumer schema (consumer, watchlist, watchlist_item, subscribes)
CREATE EXTENSION IF NOT EXISTS pgcrypto;
\echo -- Catalog schema (streams) referencing shared catalog objects
\i /docker-entrypoint-initdb.d/subscripts/shared_catalog_objects.sql

\echo -- Creating table: consumer
CREATE TABLE IF NOT EXISTS consumer (
  consumer_id UUID default gen_random_uuid() primary key,
  name text NOT NULL,
  address text,
  budget decimal(6, 0)
);

\echo -- Creating table: watchlist
CREATE TABLE IF NOT EXISTS watchlist (
  watchlist_id UUID default gen_random_uuid() primary key,
  consumer_id UUID references consumer(consumer_id) ON DELETE CASCADE
);

\echo -- Creating table: watchlist_item
CREATE TABLE IF NOT EXISTS watchlist_item (
  watchlist_item_id UUID default gen_random_uuid() primary key,
  watchlist_id UUID references watchlist(watchlist_id) ON DELETE CASCADE,
  title_id UUID references title(title_id) ON DELETE CASCADE
);

\echo -- Creating table: subscribes
CREATE TABLE IF NOT EXISTS subscribes (
  consumer_id UUID references consumer(consumer_id) ON DELETE CASCADE,
  package_id UUID references package(package_id) ON DELETE CASCADE,
  renewal_date date,
  primary key (consumer_id, package_id)
);
