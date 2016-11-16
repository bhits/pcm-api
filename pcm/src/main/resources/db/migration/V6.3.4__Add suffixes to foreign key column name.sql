
ALTER TABLE `allergy` CHANGE COLUMN `adverse_event_type_code` `adverse_event_type_code_id` bigint(20);
ALTER TABLE `allergy` CHANGE COLUMN `allergy_reaction` `allergy_reaction_id` bigint(20);
ALTER TABLE `allergy` CHANGE COLUMN `allergy_severity_code` `allergy_severity_code_id` bigint(20);
ALTER TABLE `allergy` CHANGE COLUMN `allergy_status_code` `allergy_status_code_id` bigint(20);
ALTER TABLE `allergy` CHANGE COLUMN `patient` `patient_id` bigint(20);

ALTER TABLE `attested_consent` CHANGE COLUMN `consent_terms_versions` `consent_terms_versions_id` bigint(20);
ALTER TABLE `attested_consent_aud` CHANGE COLUMN `consent_terms_versions` `consent_terms_versions_id` bigint(20);

ALTER TABLE `attested_consent_revocation` CHANGE COLUMN `consent_revocation_terms_versions` `consent_revocation_terms_versions_id` bigint(20);
ALTER TABLE `attested_consent_revocation_aud` CHANGE COLUMN `consent_revocation_terms_versions` `consent_revocation_terms_versions_id` bigint(20);

ALTER TABLE `clinical_document_audit` CHANGE COLUMN `clinical_document_type_code` `clinical_document_type_code_id` bigint(20);
ALTER TABLE `clinical_document_audit` CHANGE COLUMN `patient` `patient_id` bigint(20);


ALTER TABLE `clinical_document` CHANGE COLUMN `clinical_document_type_code` `clinical_document_type_code_id` bigint(20);
ALTER TABLE `clinical_document` CHANGE COLUMN `patient` `patient_id` bigint(20);

ALTER TABLE `consent` CHANGE COLUMN `attested_consent` `attested_consent_id` bigint(20);
ALTER TABLE `consent` CHANGE COLUMN `attested_consent_revocation` `attested_consent_revocation_id` bigint(20);
ALTER TABLE `consent` CHANGE COLUMN `legal_representative` `legal_representative_id` bigint(20);
ALTER TABLE `consent` CHANGE COLUMN `patient` `patient_id` bigint(20);

ALTER TABLE `consent_aud` CHANGE COLUMN `attested_consent` `attested_consent_id` bigint(20);
ALTER TABLE `consent_aud` CHANGE COLUMN `attested_consent_revocation` `attested_consent_revocation_id` bigint(20);
ALTER TABLE `consent_aud` CHANGE COLUMN `legal_representative` `legal_representative_id` bigint(20);
ALTER TABLE `consent_aud` CHANGE COLUMN `patient` `patient_id` bigint(20);

ALTER TABLE `consent_do_not_share_clinical_concept_codes` CHANGE COLUMN `do_not_share_clinical_concept_codes` `do_not_share_clinical_concept_codes_id` bigint(20);

ALTER TABLE `consent_do_not_share_clinical_document_type_code` CHANGE COLUMN `clinical_document_type_code` `clinical_document_type_code_id` bigint(20);

ALTER TABLE `consent_individual_provider_disclosure_is_made_to` CHANGE COLUMN `individual_provider` `individual_provider_id` bigint(20);

ALTER TABLE `consent_individual_provider_permitted_to_disclose` CHANGE COLUMN `individual_provider` `individual_provider_id` bigint(20);

ALTER TABLE `consent_organizational_provider_disclosure_is_made_to` CHANGE COLUMN `organizational_provider` `organizational_provider_id` bigint(20);

ALTER TABLE `consent_organizational_provider_permitted_to_disclose` CHANGE COLUMN `organizational_provider` `organizational_provider_id` bigint(20);

ALTER TABLE `consent_share_for_purpose_of_use_code` CHANGE COLUMN `purpose_of_use_code` `purpose_of_use_code_id` bigint(20);

ALTER TABLE `medication` CHANGE COLUMN `body_site_code` `body_site_code_id` bigint(20);
ALTER TABLE `medication` CHANGE COLUMN `unit_of_measure_code` `unit_of_measure_code_id` bigint(20);
ALTER TABLE `medication` CHANGE COLUMN `medication_status_code` `medication_status_code_id` bigint(20);
ALTER TABLE `medication` CHANGE COLUMN `patient` `patient_id` bigint(20);
ALTER TABLE `medication` CHANGE COLUMN `product_form_code` `product_form_code_id` bigint(20);
ALTER TABLE `medication` CHANGE COLUMN `route_code` `route_code_id` bigint(20);

-- Patient

ALTER TABLE `patient` CHANGE COLUMN `address_use_code` `address_use_code_id` bigint(20);
ALTER TABLE `patient` CHANGE COLUMN `country_code` `country_code_id` bigint(20);
ALTER TABLE `patient` CHANGE COLUMN `state_code` `state_code_id` bigint(20);
ALTER TABLE `patient` CHANGE COLUMN `administrative_gender_code` `administrative_gender_code_id` bigint(20);
ALTER TABLE `patient` CHANGE COLUMN `ethnic_group_code` `ethnic_group_code_id` bigint(20);
ALTER TABLE `patient` CHANGE COLUMN `language_code` `language_code_id` bigint(20);
ALTER TABLE `patient` CHANGE COLUMN `marital_status_code` `marital_status_code_id` bigint(20);
ALTER TABLE `patient` CHANGE COLUMN `race_code` `race_code_id` bigint(20);
ALTER TABLE `patient` CHANGE COLUMN `telecom_use_code` `telecom_use_code_id` bigint(20);
ALTER TABLE `patient` CHANGE COLUMN `religious_affiliation_code` `religious_affiliation_code_id` bigint(20);

ALTER TABLE `patient_audit` CHANGE COLUMN `address_use_code` `address_use_code_id` bigint(20);
ALTER TABLE `patient_audit` CHANGE COLUMN `country_code` `country_code_id` bigint(20);
ALTER TABLE `patient_audit` CHANGE COLUMN `state_code` `state_code_id` bigint(20);
ALTER TABLE `patient_audit` CHANGE COLUMN `administrative_gender_code` `administrative_gender_code_id` bigint(20);
ALTER TABLE `patient_audit` CHANGE COLUMN `ethnic_group_code` `ethnic_group_code_id` bigint(20);
ALTER TABLE `patient_audit` CHANGE COLUMN `language_code` `language_code_id` bigint(20);
ALTER TABLE `patient_audit` CHANGE COLUMN `marital_status_code` `marital_status_code_id` bigint(20);
ALTER TABLE `patient_audit` CHANGE COLUMN `race_code` `race_code_id` bigint(20);
ALTER TABLE `patient_audit` CHANGE COLUMN `telecom_use_code` `telecom_use_code_id` bigint(20);
ALTER TABLE `patient_audit` CHANGE COLUMN `religious_affiliation_code` `religious_affiliation_code_id` bigint(20);

ALTER TABLE `patient_individual_providers_aud` CHANGE COLUMN `patient` `patient_id` bigint(20);








