ALTER TABLE attested_consent MODIFY COLUMN attester_middle_name VARCHAR(255) DEFAULT NULL;
ALTER TABLE attested_consent_revocation MODIFY COLUMN attester_middle_name VARCHAR(255) DEFAULT NULL;
