-- Changing tinyint type to BIT(1)
ALTER TABLE consent_aud MODIFY consent_reference_id_mod BIT(1);
ALTER TABLE consent_aud MODIFY consent_revokation_type_mod BIT(1);
ALTER TABLE consent_aud MODIFY consent_revoked BIT(1);
ALTER TABLE consent_aud MODIFY description_mod BIT(1);
ALTER TABLE consent_aud MODIFY end_date_mod BIT(1);
ALTER TABLE consent_aud MODIFY legal_representative_mod BIT(1);
ALTER TABLE consent_aud MODIFY name_mod BIT(1);
ALTER TABLE consent_aud MODIFY patient_mod BIT(1);
ALTER TABLE consent_aud MODIFY revocation_date_mod BIT(1);
ALTER TABLE consent_aud MODIFY signed_date_mod BIT(1);
ALTER TABLE consent_aud MODIFY signed_pdf_consent_mod BIT(1);
ALTER TABLE consent_aud MODIFY signed_pdf_consent_revoke_mod BIT(1);
ALTER TABLE consent_aud MODIFY start_date_mod BIT(1);
ALTER TABLE consent_aud MODIFY consent_revoked_mod BIT(1);
ALTER TABLE consent_aud MODIFY description_mod BIT(1);
ALTER TABLE email_token MODIFY is_token_used BIT(1);

ALTER TABLE system_notification MODIFY dismissed BIT(1);
ALTER TABLE system_notification MODIFY dismissed BIT(1) NOT NULL;
