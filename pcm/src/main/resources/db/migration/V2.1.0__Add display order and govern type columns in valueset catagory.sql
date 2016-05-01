ALTER TABLE value_set_category  ADD COLUMN is_federal BOOLEAN;
ALTER TABLE value_set_category  ADD COLUMN display_order INTEGER UNIQUE;
