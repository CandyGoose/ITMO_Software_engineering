SELECT * FROM miracle;

-- Добавление нового чуда в таблицу miracle:
INSERT INTO miracle (name, danger, location) VALUES ('Планета', 2, 2);

-- Обновление чуда в таблице miracle:
UPDATE miracle
SET name = 'Гора Пат', danger = 1, location = 3
WHERE id = 1;

-- Удаление чуда по ид:
DELETE FROM miracle WHERE id = 3;

SELECT * FROM miracle_log;