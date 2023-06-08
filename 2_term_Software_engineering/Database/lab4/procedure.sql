-- Создать процедуру, куда передается имя таблицы и эта таблица создается с полями id и name, создать процедуру, куда передается имя таблицы, id и name и создается запись, а также создать функцию, куда передается имя таблицы и выводятся все записи
CREATE OR REPLACE PROCEDURE create_table_with_name_e(table_name VARCHAR)
    LANGUAGE plpgsql
AS
$$
BEGIN
    BEGIN
        EXECUTE 'CREATE TABLE ' || quote_ident(table_name) || ' (id SERIAL PRIMARY KEY, name TEXT)';
    EXCEPTION
        WHEN duplicate_table THEN
            RAISE NOTICE 'Таблица уже существует';
        WHEN others THEN
            RAISE NOTICE 'Произошла ошибка при выполнении запроса';
    END;
END;
$$;


CREATE OR REPLACE PROCEDURE insert_data_into_table_e(table_name VARCHAR, id INT, name TEXT)
    LANGUAGE plpgsql
AS
$$
BEGIN
    BEGIN
        EXECUTE 'INSERT INTO ' || quote_ident(table_name) || ' (id, name) VALUES ($1, $2)'
        USING id, name;
    EXCEPTION
        WHEN others THEN
            RAISE NOTICE 'Произошла ошибка при вставке данных';
    END;
END;
$$;

CREATE OR REPLACE FUNCTION select_all_from_table_e(table_name VARCHAR)
    RETURNS TABLE
        (id INT, name TEXT)
AS
$$
BEGIN
    BEGIN
        RETURN QUERY EXECUTE 'SELECT * FROM ' || quote_ident(table_name);
    EXCEPTION
        WHEN undefined_table THEN
            RAISE NOTICE 'Таблица не найдена';
        WHEN others THEN
            RAISE NOTICE 'Произошла ошибка при выполнении запроса';
    END;
END;
$$ LANGUAGE plpgsql;

CALL create_table_with_name_e('таблица');
CALL insert_data_into_table_e('таблица', 1, 'значение1');
SELECT * FROM select_all_from_table_e('таблица');
