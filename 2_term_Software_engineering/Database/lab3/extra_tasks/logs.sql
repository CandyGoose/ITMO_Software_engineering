CREATE OR REPLACE FUNCTION miracle_log_insert_trigger()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO miracle_log (miracle_id, log_time, log_type, new_name, new_danger_id, new_location_id)
    VALUES (NEW.id, now(), 'INSERT', NEW.name, NEW.danger, NEW.location);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER miracle_insert_trigger
AFTER INSERT ON miracle
FOR EACH ROW
EXECUTE FUNCTION miracle_log_insert_trigger();

CREATE OR REPLACE FUNCTION miracle_log_update_trigger()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO miracle_log (miracle_id, log_time, log_type, old_name, new_name, old_danger_id, new_danger_id, old_location_id, new_location_id)
    VALUES (NEW.id, now(), 'UPDATE', OLD.name, NEW.name, OLD.danger, NEW.danger, OLD.location, NEW.location);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER miracle_update_trigger
AFTER UPDATE ON miracle
FOR EACH ROW
EXECUTE FUNCTION miracle_log_update_trigger();

CREATE OR REPLACE FUNCTION miracle_log_delete_trigger()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO miracle_log (miracle_id, log_time, log_type, old_name, old_danger_id, old_location_id)
    VALUES (OLD.id, now(), 'DELETE', OLD.name, OLD.danger, OLD.location);
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER miracle_delete_trigger
AFTER DELETE ON miracle
FOR EACH ROW
EXECUTE FUNCTION miracle_log_delete_trigger();
