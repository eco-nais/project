CREATE OR REPLACE FUNCTION projects.get_next_version(p_project_id bigint, p_document_id bigint)
    RETURNS bigint AS $$
DECLARE
    next_version bigint;
BEGIN
    SELECT COALESCE(MAX(version), -1) + 1
    INTO next_version
    FROM projects.document_versions
    WHERE project_id = p_project_id AND document_id = p_document_id;

    RETURN next_version;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION projects.update_next_vers()
    RETURNS TRIGGER AS $$
DECLARE
    next_vers bigint;
BEGIN
    SELECT nv.next_version INTO next_vers
    FROM projects.next_versions nv
    WHERE nv.project_id = NEW.project_id AND nv.document_id = NEW.document_id;

    UPDATE projects.next_versions
    SET next_version = next_vers + 1
    WHERE project_id = NEW.project_id AND document_id = NEW.document_id;

    RETURN NEW;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE EXCEPTION 'Next version not found for project_id % and document_id %', NEW.project_id, NEW.document_id;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER doc_vers_ar_ins_trigger
    AFTER INSERT ON projects.document_versions
    FOR EACH ROW
EXECUTE FUNCTION projects.update_next_vers();


CREATE OR REPLACE FUNCTION projects.set_next_vers()
    RETURNS TRIGGER AS $$
DECLARE
    next_version bigint;
BEGIN
    BEGIN
        SELECT nv.next_version INTO next_version
        FROM projects.next_versions nv
        WHERE nv.project_id = NEW.project_id AND nv.document_id = NEW.document_id;

        IF NOT FOUND THEN
            next_version := 1;
        END IF;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            next_version := 1;
    END;

    NEW.version := next_version;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER doc_vers_br_ins_trigger
    BEFORE INSERT ON projects.document_versions
    FOR EACH ROW
EXECUTE FUNCTION projects.set_next_vers();

CREATE OR REPLACE FUNCTION projects.prevent_vers_change()
    RETURNS TRIGGER AS $$
BEGIN
    RAISE EXCEPTION 'Updates or deletes of document versions are not allowed';
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER doc_vers_prevent_trigger
    BEFORE UPDATE OR DELETE ON projects.document_versions
    FOR EACH ROW
EXECUTE FUNCTION projects.prevent_vers_change();