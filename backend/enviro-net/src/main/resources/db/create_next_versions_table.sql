DROP TABLE IF EXISTS projects.next_versions;

CREATE TABLE IF NOT EXISTS projects.next_versions (
    document_id bigint,
    project_id bigint,
    next_version bigint
);
