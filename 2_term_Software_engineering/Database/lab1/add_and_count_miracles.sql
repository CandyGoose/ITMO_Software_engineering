INSERT INTO comrade (name, birth_date, death_date, destiny, complaint)
VALUES ('Генри', '1990-01-01', null, 4, 4);

SELECT comrade.name, COUNT(miracle_seen.miracle) AS num_of_miracles_seen
FROM comrade
LEFT JOIN miracle_seen ON comrade.id = miracle_seen.comrade
GROUP BY comrade.name;
