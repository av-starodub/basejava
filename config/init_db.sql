-- auto-generated definition
CREATE TABLE resume
(
    uuid      CHAR(36) NOT NULL PRIMARY KEY,
    full_name TEXT     NOT NULL
);

ALTER TABLE resume
    OWNER TO postgres;

-- auto-generated definition
CREATE TABLE contact
(
    id          SERIAL PRIMARY KEY,
    resume_uuid char(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
    type        TEXT     NOT NULL,
    value       TEXT     NOT NULL
);
CREATE UNIQUE INDEX contact_uuid_type_index ON contact (resume_uuid, type);

ALTER TABLE resume
    OWNER TO postgres;
