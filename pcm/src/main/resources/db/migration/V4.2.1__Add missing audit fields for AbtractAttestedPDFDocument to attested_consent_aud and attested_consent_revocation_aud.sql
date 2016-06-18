ALTER TABLE attested_consent_aud ADD attested_pdf_consent_mod BIT(1) DEFAULT NULL;
ALTER TABLE attested_consent_aud ADD consent_terms_accepted_mod BIT(1) DEFAULT NULL;
ALTER TABLE attested_consent_aud ADD consent_terms_versions_mod BIT(1) DEFAULT NULL;


ALTER TABLE attested_consent_aud ADD attested_date_time DATETIME DEFAULT NULL;
ALTER TABLE attested_consent_aud ADD attested_date_time_mod BIT(1) DEFAULT NULL;

ALTER TABLE attested_consent_aud ADD attester_by_user VARCHAR(255) DEFAULT NULL;
ALTER TABLE attested_consent_aud ADD attester_by_user_mod BIT(1) DEFAULT NULL;

ALTER TABLE attested_consent_aud ADD attester_email VARCHAR(255) DEFAULT NULL;
ALTER TABLE attested_consent_aud ADD attester_email_mod BIT(1) DEFAULT NULL;

ALTER TABLE attested_consent_aud ADD attester_first_name VARCHAR(255) DEFAULT NULL;
ALTER TABLE attested_consent_aud ADD attester_first_name_mod BIT(1) DEFAULT NULL;

ALTER TABLE attested_consent_aud ADD attester_last_name VARCHAR(255) DEFAULT NULL;
ALTER TABLE attested_consent_aud ADD attester_last_name_mod BIT(1) DEFAULT NULL;

ALTER TABLE attested_consent_aud ADD attester_middle_name VARCHAR(255) DEFAULT NULL;
ALTER TABLE attested_consent_aud ADD attester_middle_name_mod BIT(1) DEFAULT NULL;

ALTER TABLE attested_consent_aud ADD consent_reference_id VARCHAR(255) DEFAULT NULL;
ALTER TABLE attested_consent_aud ADD consent_reference_id_mod BIT(1) DEFAULT NULL;


ALTER TABLE attested_consent_revocation_aud ADD attested_date_time DATETIME DEFAULT NULL;
ALTER TABLE attested_consent_revocation_aud ADD attested_date_time_mod BIT(1) DEFAULT NULL;

ALTER TABLE attested_consent_revocation_aud ADD attester_by_user VARCHAR(255) DEFAULT NULL;
ALTER TABLE attested_consent_revocation_aud ADD attester_by_user_mod BIT(1) DEFAULT NULL;

ALTER TABLE attested_consent_revocation_aud ADD attester_email VARCHAR(255) DEFAULT NULL;
ALTER TABLE attested_consent_revocation_aud ADD attester_email_mod BIT(1) DEFAULT NULL;

ALTER TABLE attested_consent_revocation_aud ADD attester_first_name VARCHAR(255) DEFAULT NULL;
ALTER TABLE attested_consent_revocation_aud ADD attester_first_name_mod BIT(1) DEFAULT NULL;

ALTER TABLE attested_consent_revocation_aud ADD attester_last_name VARCHAR(255) DEFAULT NULL;
ALTER TABLE attested_consent_revocation_aud ADD attester_last_name_mod BIT(1) DEFAULT NULL;

ALTER TABLE attested_consent_revocation_aud ADD attester_middle_name VARCHAR(255) DEFAULT NULL;
ALTER TABLE attested_consent_revocation_aud ADD attester_middle_name_mod BIT(1) DEFAULT NULL;

ALTER TABLE attested_consent_revocation_aud ADD consent_reference_id VARCHAR(255) DEFAULT NULL;
ALTER TABLE attested_consent_revocation_aud ADD consent_reference_id_mod BIT(1) DEFAULT NULL;

ALTER TABLE attested_consent_revocation_aud ADD attested_pdf_consent_revoke_mod BIT(1) DEFAULT NULL;

ALTER TABLE attested_consent_revocation_aud ADD consent_revoke_terms_accepted_mod BIT(1) DEFAULT NULL;

ALTER TABLE attested_consent_revocation_aud ADD consent_revocation_terms_versions_mod BIT(1) DEFAULT NULL;
