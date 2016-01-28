-- Add unique constraint to consent
ALTER TABLE consent ADD CONSTRAINT consent_consent_reference_id_idx UNIQUE (consent_reference_id);
ALTER TABLE consent MODIFY consent_reference_id VARCHAR(255) NOT NULL;
ALTER TABLE consent MODIFY consent_revoked BIT(1);

-- Add unique constraint to patient
ALTER TABLE patient ADD CONSTRAINT patient_medical_record_number_idx UNIQUE (medical_record_number);
ALTER TABLE patient ADD CONSTRAINT patient_username_idx UNIQUE (username);
CREATE INDEX patient_enterprise_identifier_idx ON patient(enterprise_identifier);
ALTER TABLE patient MODIFY medical_record_number VARCHAR(30) NOT NULL;

DROP INDEX individual_providers ON patient_individual_providers;
ALTER TABLE patient_individual_providers ADD CONSTRAINT UK_cnxbjkdwq5amd0m5x7kpq2opq UNIQUE (individual_providers);

DROP INDEX code ON medical_section;
ALTER TABLE medical_section ADD CONSTRAINT UK_e6gfje2mokdtfn8f7930gf4gw UNIQUE (code);

DROP INDEX patient_legal_representative_associations ON patient_patient_legal_representative_associations;
ALTER TABLE patient_patient_legal_representative_associations ADD CONSTRAINT UK_c9krgj0yrbcq2b17hghkqv8iv UNIQUE (patient_legal_representative_associations);
