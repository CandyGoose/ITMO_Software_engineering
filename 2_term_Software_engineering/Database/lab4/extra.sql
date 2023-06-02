-- Создать таблицу на год, используя for, с месяцами, номерами недель и днями в неделе, а также с типом занятия, где 1-3 день - лекции, 4-6 - практики, 7 - выходной
CREATE TABLE schedule (
    month INT,
    week INT,
    day INT,
    type INT
);

CREATE TABLE activity (
    type_num INT,
    type_name VARCHAR(50)
);

INSERT INTO activity (type_num, type_name)
VALUES (10, 'Лекция'), (20, 'Практика'), (30, 'Выходной');

DO $$
DECLARE
    curr_date DATE := '2023-01-01';
    max_date DATE := '2023-12-31';
    month_num INT;
    week_num INT = 0;
    day_num INT;
    activity_type INT;
BEGIN
    FOR i IN 1..365 LOOP
        month_num := EXTRACT(MONTH FROM curr_date);
        day_num := i % 7;

        IF day_num = 0 THEN
            activity_type := 30;
        ELSIF day_num < 4 THEN
            IF day_num = 1 THEN
                week_num := week_num + 1;
            end if;
            activity_type := 10;
        ELSE
            activity_type := 20;
        END IF;

        INSERT INTO schedule (month, week, day, type)
        VALUES (month_num, week_num, EXTRACT(DOW FROM curr_date) + 1, activity_type);

        curr_date := curr_date + INTERVAL '1 DAY';
        EXIT WHEN curr_date > max_date;
    END LOOP;
END $$;

-- Создать представление, которое будет находить таблицу с наибольшим количество атрибутов и выводить их
CREATE VIEW max_attributes_table_view AS
SELECT table_name, COUNT(column_name) AS attribute_count, STRING_AGG(column_name, ', ') AS attribute_names
FROM information_schema.columns
WHERE table_schema = 'pg_catalog'
GROUP BY table_name
ORDER BY attribute_count DESC
LIMIT 1;

SELECT * FROM max_attributes_table_view;

