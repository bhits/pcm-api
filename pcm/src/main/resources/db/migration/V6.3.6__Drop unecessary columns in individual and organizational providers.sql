
-- Delete unnecessary columns from pcm.individual_provider
ALTER TABLE pcm.individual_provider drop column name_prefix;
ALTER TABLE pcm.individual_provider drop column name_suffix;
ALTER TABLE pcm.individual_provider drop column credential;
ALTER TABLE pcm.individual_provider drop column provider_taxonomy_code;
ALTER TABLE pcm.individual_provider drop column provider_taxonomy_description;


-- Delete unnecessary columns from pcm.organizational_provider
ALTER TABLE pcm.organizational_provider drop column provider_taxonomy_code;
ALTER TABLE pcm.organizational_provider drop column provider_taxonomy_description;
ALTER TABLE pcm.organizational_provider drop column other_org_name;
ALTER TABLE pcm.organizational_provider drop column authorized_official_last_name;
ALTER TABLE pcm.organizational_provider drop column authorized_official_first_name;
ALTER TABLE pcm.organizational_provider drop column authorized_official_title;
ALTER TABLE pcm.organizational_provider drop column authorized_official_name_prefix;
ALTER TABLE pcm.organizational_provider drop column authorized_official_telephone_number;