ALTER TABLE code_system DROP KEY code_system_id;
DROP INDEX code_system_oid ON code_system;
ALTER TABLE code_system ADD CONSTRAINT UK_7j0saojr9joix0quj9a65492x UNIQUE (code_system_oid);


ALTER TABLE code_system_version DROP KEY code_system_version_id;

ALTER TABLE value_set_category ADD CONSTRAINT UK_ei6kilxu5rvqu6h6tvsqgqtyh UNIQUE (code);
DROP INDEX code ON value_set_category;

ALTER TABLE value_set ADD CONSTRAINT UK_qqdh21ud433ry6ybr0mwnu6c2 UNIQUE (code);
DROP INDEX code ON value_set;

ALTER TABLE concept_code ADD CONSTRAINT UK_3qk6t75290ixvreggu1u1k9jw UNIQUE (code, fk_code_system_version_id);
ALTER TABLE concept_code DROP KEY concept_code_id;
DROP INDEX code ON concept_code;

