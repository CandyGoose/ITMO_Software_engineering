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
