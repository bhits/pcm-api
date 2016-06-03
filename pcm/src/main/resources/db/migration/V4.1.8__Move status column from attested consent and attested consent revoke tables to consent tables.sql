ALTER TABLE pcm.attested_consent DROP COLUMN status ;
ALTER TABLE pcm.attested_consent_revocation DROP COLUMN status;
ALTER TABLE pcm.consent ADD status VARCHAR(255) NOT NULL ;
ALTER TABLE pcm.consent_aud ADD status VARCHAR(255) NOT NULL ;
ALTER TABLE pcm.consent_aud ADD status_mod BIT(1) NULL ;