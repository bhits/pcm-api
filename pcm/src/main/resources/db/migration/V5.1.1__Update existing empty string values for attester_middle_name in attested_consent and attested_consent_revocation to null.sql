update attested_consent set attester_middle_name=null where attester_middle_name='';
update attested_consent_revocation set attester_middle_name=null where attester_middle_name='';

update attested_consent_aud set attester_middle_name=null, attester_middle_name_mod=0 where attester_middle_name='';
update attested_consent_revocation_aud set attester_middle_name=null, attester_middle_name_mod=0 where attester_middle_name='';
