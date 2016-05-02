UPDATE value_set_category SET is_federal = TRUE WHERE valueset_cat_id = '1';
UPDATE value_set_category SET is_federal = FALSE WHERE valueset_cat_id = '2';
UPDATE value_set_category SET is_federal = FALSE WHERE valueset_cat_id = '3';
UPDATE value_set_category SET is_federal = FALSE WHERE valueset_cat_id = '4';
UPDATE value_set_category SET is_federal = FALSE WHERE valueset_cat_id = '6';
UPDATE value_set_category SET is_federal = TRUE WHERE valueset_cat_id = '9';
UPDATE value_set_category SET is_federal = FALSE WHERE valueset_cat_id = '10';
UPDATE value_set_category SET is_federal = FALSE WHERE valueset_cat_id = '11';

-- Initial Valueset Category Display Order
UPDATE value_set_category SET display_order = '1' WHERE valueset_cat_id = '1';
UPDATE value_set_category SET display_order = '5' WHERE valueset_cat_id = '2';
UPDATE value_set_category SET display_order = '4' WHERE valueset_cat_id = '3';
UPDATE value_set_category SET display_order = '3' WHERE valueset_cat_id = '4';
UPDATE value_set_category SET display_order = '7' WHERE valueset_cat_id = '6';
UPDATE value_set_category SET display_order = '2' WHERE valueset_cat_id = '9';
UPDATE value_set_category SET display_order = '6' WHERE valueset_cat_id = '10';
UPDATE value_set_category SET display_order = '8' WHERE valueset_cat_id = '11';