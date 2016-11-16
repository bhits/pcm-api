SET FOREIGN_KEY_CHECKS=0;

ALTER TABLE `consent_do_not_share_sensitivity_policy_code`
DROP FOREIGN KEY `FKC819CBFA1765E89`;

ALTER TABLE `value_set`
DROP FOREIGN KEY `FKD2615C94ED451F98`;

ALTER TABLE `value_set_category` CHANGE COLUMN `valueset_cat_id` `id` bigint(20);

ALTER TABLE `consent_do_not_share_sensitivity_policy_code`
CHANGE COLUMN `value_set_category` `value_set_category_id` bigint(20);

ALTER TABLE `consent_do_not_share_sensitivity_policy_code`
ADD CONSTRAINT `FKC819CBFA1765E89` FOREIGN KEY (`value_set_category_id`) REFERENCES `value_set_category` (`id`);

-- value_set - value_set_category

ALTER TABLE `value_set`
CHANGE COLUMN `fk_valueset_cat_id` `value_set_category_id` bigint(20);


ALTER TABLE `value_set`
ADD CONSTRAINT `FKD2615C94ED451F98` FOREIGN KEY (`value_set_category_id`) REFERENCES `value_set_category` (`id`);

-- Renaming foreign key name in modified_entity_type_entity

ALTER TABLE `modified_entity_type_entity`
DROP FOREIGN KEY `FK9D17762BE1C7106`;

ALTER TABLE `modified_entity_type_entity` CHANGE COLUMN `revision` `revision_rev` bigint(20);

ALTER TABLE `modified_entity_type_entity`
ADD CONSTRAINT `FK9D17762BE1C7106` FOREIGN KEY (`revision_rev`) REFERENCES `revinfo` (`rev`);


SET FOREIGN_KEY_CHECKS=1;