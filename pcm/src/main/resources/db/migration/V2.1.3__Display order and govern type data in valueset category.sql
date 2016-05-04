UPDATE value_set_category SET is_federal = TRUE WHERE code = 'ETH';
UPDATE value_set_category SET is_federal = FALSE WHERE code = 'GDIS';
UPDATE value_set_category SET is_federal = FALSE WHERE code = 'HIV';
UPDATE value_set_category SET is_federal = FALSE WHERE code = 'PSY';
UPDATE value_set_category SET is_federal = FALSE WHERE code = 'SEX';
UPDATE value_set_category SET is_federal = TRUE WHERE code = 'ALC';
UPDATE value_set_category SET is_federal = FALSE WHERE code = 'COM';
UPDATE value_set_category SET is_federal = FALSE WHERE code = 'ADD';

-- Initial Valueset Category Display Order
UPDATE value_set_category SET display_order = '1' WHERE code = 'ETH';
UPDATE value_set_category SET display_order = '5' WHERE code = 'GDIS';
UPDATE value_set_category SET display_order = '4' WHERE code = 'HIV';
UPDATE value_set_category SET display_order = '3' WHERE code = 'PSY';
UPDATE value_set_category SET display_order = '7' WHERE code = 'SEX';
UPDATE value_set_category SET display_order = '2' WHERE code = 'ALC';
UPDATE value_set_category SET display_order = '6' WHERE code = 'COM';
UPDATE value_set_category SET display_order = '8' WHERE code = 'ADD';