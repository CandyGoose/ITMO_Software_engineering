SELECT miracle.name, danger.description AS danger, location.name AS location, comrade.name AS comrade, miracle_seen.seeing_date
FROM miracle
JOIN danger ON miracle.danger = danger.id
JOIN location ON miracle.location = location.id
JOIN miracle_seen ON miracle.id = miracle_seen.miracle
JOIN comrade ON miracle_seen.comrade = comrade.id;