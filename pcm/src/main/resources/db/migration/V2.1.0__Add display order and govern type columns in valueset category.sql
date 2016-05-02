ALTER TABLE value_set_category  ADD COLUMN is_federal BOOLEAN;
ALTER TABLE value_set_category  ADD COLUMN display_order BIGINT UNSIGNED UNIQUE;
ALTER TABLE value_set_category_aud  ADD COLUMN is_federal BOOLEAN;
ALTER TABLE value_set_category_aud  ADD COLUMN display_order BIGINT UNSIGNED UNIQUE;
