ALTER TABLE attested_consent ADD attester_ip_address VARCHAR(255) DEFAULT NULL;

ALTER TABLE attested_consent_aud ADD attester_ip_address VARCHAR(255) DEFAULT NULL;
ALTER TABLE attested_consent_aud ADD attester_ip_address_mod BIT(1) DEFAULT NULL;


ALTER TABLE attested_consent ADD patient_guid VARCHAR(255) DEFAULT NULL;

ALTER TABLE attested_consent_aud ADD patient_guid VARCHAR(255) DEFAULT NULL;
ALTER TABLE attested_consent_aud ADD patient_guid_mod BIT(1) DEFAULT NULL;




ALTER TABLE attested_consent_revocation ADD attester_ip_address VARCHAR(255) DEFAULT NULL;

ALTER TABLE attested_consent_revocation_aud ADD attester_ip_address VARCHAR(255) DEFAULT NULL;
ALTER TABLE attested_consent_revocation_aud ADD attester_ip_address_mod BIT(1) DEFAULT NULL;


ALTER TABLE attested_consent_revocation ADD patient_guid VARCHAR(255) DEFAULT NULL;

ALTER TABLE attested_consent_revocation_aud ADD patient_guid VARCHAR(255) DEFAULT NULL;
ALTER TABLE attested_consent_revocation_aud ADD patient_guid_mod BIT(1) DEFAULT NULL;
