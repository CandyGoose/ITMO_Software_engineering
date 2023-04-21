/*  Функция, которая возвращает имена товарищей, видевших чудо в заданной локации.
    Функция принимает один параметр - название локации. */
CREATE OR REPLACE FUNCTION miracle_seen_comrades(location_name TEXT)
RETURNS TEXT[] AS $$
DECLARE
    location_id INTEGER;
    comrades TEXT[];
BEGIN
    SELECT id INTO location_id FROM location WHERE name = location_name;
    SELECT array_agg(comrade.name) INTO comrades FROM comrade
    JOIN miracle_seen ON comrade.id = miracle_seen.comrade
    JOIN miracle ON miracle_seen.miracle = miracle.id
    WHERE miracle.location = location_id;
    RETURN comrades;
END;
$$ LANGUAGE plpgsql;

SELECT miracle_seen_comrades('Космос'); -- Возвращает {"Чарли","Джек","Томас"}
