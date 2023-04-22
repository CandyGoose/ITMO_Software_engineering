CREATE TABLE miracle_log (
    id BIGSERIAL PRIMARY KEY,
    miracle_id BIGINT REFERENCES miracle(id) NOT NULL,
    log_time TIMESTAMP NOT NULL,
    log_type TEXT NOT NULL,
    old_name TEXT,
    new_name TEXT,
    old_danger_id BIGINT,
    new_danger_id BIGINT,
    old_location_id BIGINT,
    new_location_id BIGINT
);
