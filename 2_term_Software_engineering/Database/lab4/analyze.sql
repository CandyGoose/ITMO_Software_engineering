EXPLAIN ANALYZE SELECT "Н_ЛЮДИ"."ИМЯ", "Н_ЛЮДИ"."ФАМИЛИЯ", "Н_ВЕДОМОСТИ"."ИД"
FROM "Н_ЛЮДИ"
RIGHT JOIN "Н_ВЕДОМОСТИ" ON "Н_ЛЮДИ"."ИД" = "Н_ВЕДОМОСТИ"."ЧЛВК_ИД"
WHERE "Н_ЛЮДИ"."ФАМИЛИЯ" < 'Иванов'
AND "Н_ВЕДОМОСТИ"."ИД" IN (1250981, 1250972);

EXPLAIN ANALYZE SELECT "Н_ЛЮДИ"."ОТЧЕСТВО", "Н_ВЕДОМОСТИ"."ДАТА", "Н_СЕССИЯ"."ИД"
FROM "Н_ЛЮДИ"
RIGHT JOIN "Н_ВЕДОМОСТИ" ON "Н_ЛЮДИ"."ИД" = "Н_ВЕДОМОСТИ"."ЧЛВК_ИД"
RIGHT JOIN "Н_СЕССИЯ" ON "Н_ВЕДОМОСТИ"."СЭС_ИД" = "Н_СЕССИЯ"."СЭС_ИД"
WHERE "Н_ЛЮДИ"."ИД" < 100012
AND "Н_ВЕДОМОСТИ"."ИД" = 1457443