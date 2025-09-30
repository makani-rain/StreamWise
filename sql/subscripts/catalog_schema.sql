\echo -- Catalog schema (streams) referencing shared catalog objects
\i /docker-entrypoint-initdb.d/subscripts/shared_catalog_objects.sql

\echo -- Creating table: streams
CREATE TABLE IF NOT EXISTS streams (
  service_id UUID references streaming_service(service_id) ON DELETE CASCADE,
  title_id UUID references title(title_id) ON DELETE CASCADE,
  arrival_date bigint,
  leaving_date bigint,
  primary key (service_id, title_id)
);
