CREATE TABLE consent_terms_versions (
    id BIGINT(20) AUTO_INCREMENT NOT NULL,
    consent_terms_text TEXT NOT NULL,
    added_date_time DATETIME NOT NULL,
    version_disabled BIT(1) DEFAULT 0,
    CONSTRAINT PK_CONSENT_TERMS_VERSIONS PRIMARY KEY (id)
);