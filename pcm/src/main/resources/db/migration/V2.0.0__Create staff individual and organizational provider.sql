CREATE TABLE staff_individual_provider (id BIGINT AUTO_INCREMENT NOT NULL, individual_provider BIGINT NULL, CONSTRAINT PK_STAFF_INDIVIDUAL_PROVIDER PRIMARY KEY (id));
ALTER TABLE staff_individual_provider ADD CONSTRAINT FK_ioned9nvm4j3uds9rwmck698v FOREIGN KEY (individual_provider) REFERENCES individual_provider (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

CREATE TABLE staff_organizational_provider (id BIGINT AUTO_INCREMENT NOT NULL, individual_provider BIGINT NULL, CONSTRAINT PK_STAFF_ORGANIZATIONAL_PROVIDER PRIMARY KEY (id));
ALTER TABLE staff_organizational_provider ADD CONSTRAINT FK_g1j2ny84tiakvt9vj9oxpf12r FOREIGN KEY (individual_provider) REFERENCES organizational_provider (id) ON UPDATE NO ACTION ON DELETE NO ACTION;
