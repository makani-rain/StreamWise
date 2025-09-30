\echo === Seed data for StreamWise ===

CREATE EXTENSION IF NOT EXISTS pgcrypto;

\echo -- Seed catalog_db
\connect catalog_db

-- streaming services
INSERT INTO streaming_service (service_id, name)
SELECT gen_random_uuid(), v.name
FROM (VALUES ('Netflix'), ('Prime Video'), ('Disney+')) AS v(name)
WHERE NOT EXISTS (SELECT 1 FROM streaming_service s WHERE s.name = v.name);

-- titles
INSERT INTO title (title_id, title_name, type, release_date, category, creator, language)
SELECT gen_random_uuid(), v.title_name, v.type, v.release_date, v.category, v.creator, v.language
FROM (VALUES
  ('Stranger Things','TV','2016','Sci-Fi,Drama','The Duffer Brothers','English'),
  ('Inception','Movie','2010','Sci-Fi,Thriller','Christopher Nolan','English'),
  ('The Mandalorian','TV','2019','Sci-Fi,Adventure','Jon Favreau','English')
) AS v(title_name, type, release_date, category, creator, language)
WHERE NOT EXISTS (SELECT 1 FROM title t WHERE t.title_name = v.title_name AND t.release_date = v.release_date);

-- packages (per service)
INSERT INTO package (package_id, service_id, name, price, period, ad_supported)
SELECT gen_random_uuid(), s.service_id, p.name, p.price, p.period, p.ad
FROM (VALUES
  ('Netflix','Standard w/ads',6.99,1,TRUE),
  ('Netflix','Standard',15.49,1,FALSE),
  ('Netflix','Premium',22.99,1,FALSE),
  ('Prime Video','Prime w/ads',14.99,1,TRUE),
  ('Prime Video','Prime ad-free',17.99,1,FALSE),
  ('Disney+','Disney Basic',7.99,1,TRUE)
) AS p(svc, name, price, period, ad)
JOIN streaming_service s ON s.name = p.svc
WHERE NOT EXISTS (SELECT 1 FROM package pk WHERE pk.service_id = s.service_id AND pk.name = p.name);

-- streams (service <-> title)
INSERT INTO streams (service_id, title_id, arrival_date, leaving_date)
SELECT s.service_id, t.title_id, NULL, NULL
FROM streaming_service s
JOIN title t ON (t.title_name IN ('Stranger Things','Inception','The Mandalorian'))
WHERE (s.name = 'Netflix' AND t.title_name IN ('Stranger Things','Inception'))
   OR (s.name = 'Prime Video' AND t.title_name IN ('Inception'))
ON CONFLICT (service_id, title_id) DO NOTHING;

\echo -- Seed catalog_db_dev (same sample data)
\connect catalog_db_dev
-- streaming services
INSERT INTO streaming_service (service_id, name)
SELECT gen_random_uuid(), v.name
FROM (VALUES ('Netflix'), ('Prime Video'), ('Disney+')) AS v(name)
WHERE NOT EXISTS (SELECT 1 FROM streaming_service s WHERE s.name = v.name);

-- titles
INSERT INTO title (title_id, title_name, type, release_date, category, creator, language)
SELECT gen_random_uuid(), v.title_name, v.type, v.release_date, v.category, v.creator, v.language
FROM (VALUES
  ('Stranger Things','TV','2016','Sci-Fi,Drama','The Duffer Brothers','English'),
  ('Inception','Movie','2010','Sci-Fi,Thriller','Christopher Nolan','English'),
  ('The Mandalorian','TV','2019','Sci-Fi,Adventure','Jon Favreau','English')
) AS v(title_name, type, release_date, category, creator, language)
WHERE NOT EXISTS (SELECT 1 FROM title t WHERE t.title_name = v.title_name AND t.release_date = v.release_date);

-- packages (per service)
INSERT INTO package (package_id, service_id, name, price, period, ad_supported)
SELECT gen_random_uuid(), s.service_id, p.name, p.price, p.period, p.ad
FROM (VALUES
  ('Netflix','Standard w/ads',6.99,1,TRUE),
  ('Netflix','Standard',15.49,1,FALSE),
  ('Netflix','Premium',22.99,1,FALSE),
  ('Prime Video','Prime w/ads',14.99,1,TRUE),
  ('Prime Video','Prime ad-free',17.99,1,FALSE),
  ('Disney+','Disney Basic',7.99,1,TRUE)
) AS p(svc, name, price, period, ad)
JOIN streaming_service s ON s.name = p.svc
WHERE NOT EXISTS (SELECT 1 FROM package pk WHERE pk.service_id = s.service_id AND pk.name = p.name);

-- streams (service <-> title)
INSERT INTO streams (service_id, title_id, arrival_date, leaving_date)
SELECT s.service_id, t.title_id, NULL, NULL
FROM streaming_service s
JOIN title t ON (t.title_name IN ('Stranger Things','Inception','The Mandalorian'))
WHERE (s.name = 'Netflix' AND t.title_name IN ('Stranger Things','Inception'))
   OR (s.name = 'Prime Video' AND t.title_name IN ('Inception'))
ON CONFLICT (service_id, title_id) DO NOTHING;

\echo -- Seed consumer_db
\connect consumer_db

-- consumers
INSERT INTO consumer (consumer_id, name, address, budget)
SELECT gen_random_uuid(), v.name, v.addr, v.budget
FROM (VALUES ('Alice','123 Maple St',100), ('Bob','456 Oak Ave',50)) AS v(name, addr, budget)
WHERE NOT EXISTS (SELECT 1 FROM consumer c WHERE c.name = v.name AND c.address = v.addr);

-- create a watchlist per consumer
INSERT INTO watchlist (watchlist_id, consumer_id)
SELECT gen_random_uuid(), c.consumer_id
FROM consumer c
WHERE NOT EXISTS (SELECT 1 FROM watchlist w WHERE w.consumer_id = c.consumer_id);

-- copy a few titles into consumer DB (so watchlists can reference them)
INSERT INTO title (title_id, title_name, type, release_date, category, creator, language)
SELECT gen_random_uuid(), v.title_name, v.type, v.release_date, v.category, v.creator, v.language
FROM (VALUES
  ('Stranger Things','TV','2016','Sci-Fi,Drama','The Duffer Brothers','English'),
  ('Inception','Movie','2010','Sci-Fi,Thriller','Christopher Nolan','English')
) AS v(title_name, type, release_date, category, creator, language)
WHERE NOT EXISTS (SELECT 1 FROM title t WHERE t.title_name = v.title_name AND t.release_date = v.release_date);

-- add two watchlist items
INSERT INTO watchlist_item (watchlist_item_id, watchlist_id, title_id)
SELECT gen_random_uuid(), w.watchlist_id, t.title_id
FROM watchlist w
JOIN consumer c ON c.consumer_id = w.consumer_id
JOIN title t ON t.title_name = 'Stranger Things'
WHERE c.name = 'Alice'
  AND NOT EXISTS (SELECT 1 FROM watchlist_item wi WHERE wi.watchlist_id = w.watchlist_id AND wi.title_id = t.title_id);

INSERT INTO watchlist_item (watchlist_item_id, watchlist_id, title_id)
SELECT gen_random_uuid(), w.watchlist_id, t.title_id
FROM watchlist w
JOIN consumer c ON c.consumer_id = w.consumer_id
JOIN title t ON t.title_name = 'Inception'
WHERE c.name = 'Bob'
  AND NOT EXISTS (SELECT 1 FROM watchlist_item wi WHERE wi.watchlist_id = w.watchlist_id AND wi.title_id = t.title_id);

-- add subscribes: create sample packages in consumer DB to reference
INSERT INTO package (package_id, service_id, name, price, period, ad_supported)
SELECT gen_random_uuid(), NULL, 'Consumer Sample Package', 9.99, 1, TRUE
WHERE NOT EXISTS (SELECT 1 FROM package p WHERE p.name = 'Consumer Sample Package');

-- link Alice to the sample package
INSERT INTO subscribes (consumer_id, package_id, renewal_date)
SELECT c.consumer_id, p.package_id, CURRENT_DATE + INTERVAL '30 days'
FROM consumer c, package p
WHERE c.name = 'Alice' AND p.name = 'Consumer Sample Package'
  AND NOT EXISTS (SELECT 1 FROM subscribes s WHERE s.consumer_id = c.consumer_id AND s.package_id = p.package_id);

\echo -- Seed consumer_db_dev (same sample data)
\connect consumer_db_dev
-- consumers
INSERT INTO consumer (consumer_id, name, address, budget)
SELECT gen_random_uuid(), v.name, v.addr, v.budget
FROM (VALUES ('Alice','123 Maple St',100), ('Bob','456 Oak Ave',50)) AS v(name, addr, budget)
WHERE NOT EXISTS (SELECT 1 FROM consumer c WHERE c.name = v.name AND c.address = v.addr);

-- create a watchlist per consumer
INSERT INTO watchlist (watchlist_id, consumer_id)
SELECT gen_random_uuid(), c.consumer_id
FROM consumer c
WHERE NOT EXISTS (SELECT 1 FROM watchlist w WHERE w.consumer_id = c.consumer_id);

-- copy a few titles into consumer DB (so watchlists can reference them)
INSERT INTO title (title_id, title_name, type, release_date, category, creator, language)
SELECT gen_random_uuid(), v.title_name, v.type, v.release_date, v.category, v.creator, v.language
FROM (VALUES
  ('Stranger Things','TV','2016','Sci-Fi,Drama','The Duffer Brothers','English'),
  ('Inception','Movie','2010','Sci-Fi,Thriller','Christopher Nolan','English')
) AS v(title_name, type, release_date, category, creator, language)
WHERE NOT EXISTS (SELECT 1 FROM title t WHERE t.title_name = v.title_name AND t.release_date = v.release_date);

-- add two watchlist items
INSERT INTO watchlist_item (watchlist_item_id, watchlist_id, title_id)
SELECT gen_random_uuid(), w.watchlist_id, t.title_id
FROM watchlist w
JOIN consumer c ON c.consumer_id = w.consumer_id
JOIN title t ON t.title_name = 'Stranger Things'
WHERE c.name = 'Alice'
  AND NOT EXISTS (SELECT 1 FROM watchlist_item wi WHERE wi.watchlist_id = w.watchlist_id AND wi.title_id = t.title_id);

INSERT INTO watchlist_item (watchlist_item_id, watchlist_id, title_id)
SELECT gen_random_uuid(), w.watchlist_id, t.title_id
FROM watchlist w
JOIN consumer c ON c.consumer_id = w.consumer_id
JOIN title t ON t.title_name = 'Inception'
WHERE c.name = 'Bob'
  AND NOT EXISTS (SELECT 1 FROM watchlist_item wi WHERE wi.watchlist_id = w.watchlist_id AND wi.title_id = t.title_id);

-- add subscribes: create sample packages in consumer DB to reference (dev)
INSERT INTO package (package_id, service_id, name, price, period, ad_supported)
SELECT gen_random_uuid(), NULL, 'Consumer Sample Package', 9.99, 1, TRUE
WHERE NOT EXISTS (SELECT 1 FROM package p WHERE p.name = 'Consumer Sample Package');

-- link Alice to the sample package (dev)
INSERT INTO subscribes (consumer_id, package_id, renewal_date)
SELECT c.consumer_id, p.package_id, CURRENT_DATE + INTERVAL '30 days'
FROM consumer c, package p
WHERE c.name = 'Alice' AND p.name = 'Consumer Sample Package'
  AND NOT EXISTS (SELECT 1 FROM subscribes s WHERE s.consumer_id = c.consumer_id AND s.package_id = p.package_id);

\echo === Seed complete ===
\echo Populating consumer with canned data
INSERT INTO consumer (name, address, budget)
  VALUES ('Alice', '123 Main St.', '100'),
    ('Bob', '124 Main St.', '150'),
    ('Carl', '125 Main St.', '90');