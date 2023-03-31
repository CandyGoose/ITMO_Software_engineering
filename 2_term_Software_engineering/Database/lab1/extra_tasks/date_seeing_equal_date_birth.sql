UPDATE comrade SET birth_date = '2001-01-01' WHERE id = 7;

SELECT DISTINCT comrade.name
FROM comrade
JOIN miracle_seen ON comrade.id = miracle_seen.comrade
JOIN miracle ON miracle_seen.miracle = miracle.id
WHERE DATE_PART('month', comrade.birth_date) = 1
AND comrade.birth_date = ANY(SELECT seeing_date FROM miracle_seen);

/*
Задание 4:
1) обновить дату рождения человека по его id
2) вывести людей, у которых дата рождения совпадает с датой просмотра какого-нибудь чуда, дата должна быть в январе
*/