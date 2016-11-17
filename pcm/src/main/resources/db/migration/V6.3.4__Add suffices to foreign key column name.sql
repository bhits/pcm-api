
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
ALTER TABLE `patient_individual_providers_aud` CHANGE COLUMN `individual_providers` `individual_providers_id` bigint(20);

ALTER TABLE `patient_individual_providers` CHANGE COLUMN `patient` `patient_id` bigint(20);
ALTER TABLE `patient_individual_providers` CHANGE COLUMN `individual_providers` `individual_providers_id` bigint(20);

ALTER TABLE `patient_legal_representative_association_aud` CHANGE COLUMN `legal_representative_type_code` `legal_representative_type_code_id` bigint(20);
ALTER TABLE `patient_legal_representative_association_aud` CHANGE COLUMN `legal_representative` `legal_representative_id` bigint(20);
ALTER TABLE `patient_legal_representative_association_aud` CHANGE COLUMN `patient` `patient_id` bigint(20);

ALTER TABLE `patient_organizational_providers_aud` CHANGE COLUMN `patient` `patient_id` bigint(20);
ALTER TABLE `patient_organizational_providers_aud` CHANGE COLUMN `organizational_providers` `organizational_providers_id` bigint(20);

ALTER TABLE `patient_organizational_providers` CHANGE COLUMN `patient` `patient_id` bigint(20);
ALTER TABLE `patient_organizational_providers` CHANGE COLUMN `organizational_providers` `organizational_providers_id` bigint(20);

ALTER TABLE `patient_patient_legal_representative_associations_aud` CHANGE COLUMN `patient` `patient_id` bigint(20);
ALTER TABLE `patient_patient_legal_representative_associations_aud` CHANGE COLUMN `patient_legal_representative_associations` `patient_legal_representative_associations_id` bigint(20);

ALTER TABLE `patient_patient_legal_representative_associations` CHANGE COLUMN `patient` `patient_id` bigint(20);
ALTER TABLE `patient_patient_legal_representative_associations` CHANGE COLUMN `patient_legal_representative_associations` `patient_legal_representative_associations_id` bigint(20);

ALTER TABLE `patient_legal_representative_association` CHANGE COLUMN `legal_representative_type_code` `legal_representative_type_code_id` bigint(20);
ALTER TABLE `patient_legal_representative_association` CHANGE COLUMN `patient` `patient_id` bigint(20);
ALTER TABLE `patient_legal_representative_association` CHANGE COLUMN `legal_representative` `legal_representative_id` bigint(20);

ALTER TABLE `problem` CHANGE COLUMN `patient` `patient_id` bigint(20);
ALTER TABLE `problem` CHANGE COLUMN `problem_status_code` `problem_status_code_id` bigint(20);

ALTER TABLE `procedure_observation` CHANGE COLUMN `patient` `patient_id` bigint(20);
ALTER TABLE `procedure_observation` CHANGE COLUMN `procedure_status_code` `procedure_status_code_id` bigint(20);
ALTER TABLE `procedure_observation` CHANGE COLUMN `target_site_code` `target_site_code_id` bigint(20);

ALTER TABLE `procedure_observation_procedure_performer` CHANGE COLUMN `procedure_observation` `procedure_observation_id` bigint(20);
ALTER TABLE `procedure_observation_procedure_performer` CHANGE COLUMN `procedure_performer` `procedure_performer_id` bigint(20);

ALTER TABLE `result_observation` CHANGE COLUMN `result_status_code` `result_status_code_id` bigint(20);
ALTER TABLE `result_observation` CHANGE COLUMN `patient` `patient_id` bigint(20);
ALTER TABLE `result_observation` CHANGE COLUMN `result_interpretation_code` `result_interpretation_code_id` bigint(20);
ALTER TABLE `result_observation` CHANGE COLUMN `unit_of_measure_code` `unit_of_measure_code_id` bigint(20);

ALTER TABLE `social_history` CHANGE COLUMN `patient` `patient_id` bigint(20);
ALTER TABLE `social_history` CHANGE COLUMN `social_history_status_code` `social_history_status_code_id` bigint(20);
ALTER TABLE `social_history` CHANGE COLUMN `social_history_type_code` `social_history_type_code_id` bigint(20);

ALTER TABLE `staff` CHANGE COLUMN `administrative_gender_code` `administrative_gender_code_id` bigint(20);

ALTER TABLE `staff_aud` CHANGE COLUMN `administrative_gender_code` `administrative_gender_code_id` bigint(20);

ALTER TABLE `staff_individual_provider` CHANGE COLUMN `individual_provider` `individual_provider_id` bigint(20);

ALTER TABLE `value_set_category_aud` CHANGE COLUMN `valueset_cat_id` `id` bigint(20);

ALTER TABLE `value_set` CHANGE COLUMN `value_set_category_id` `fk_valueset_cat_id` bigint(20);













