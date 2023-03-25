SELECT string_agg(name, ', ') AS comrades, destiny.description AS destiny
FROM comrade
JOIN destiny ON comrade.destiny = destiny.id
GROUP BY destiny.description
HAVING COUNT(comrade.id) > 1;

SELECT comrade.name, COUNT(miracle_seen.miracle) AS miracle_count
FROM comrade
JOIN miracle_seen ON comrade.id = miracle_seen.comrade
GROUP BY comrade.id
HAVING COUNT(miracle_seen.miracle) > 2;
