SELECT
  m.id AS activity_id,
  r.rev AS revision_id,
  r.revtstmp AS timestamp,
  r.username AS username,
  rtc.display_name AS rec_type,
  m.entity_class_name AS type
FROM
(
  select *
  from modified_entity_type_entity m
  where
    case
      when m.entity_class_name = "gov.samhsa.c2s.pcm.domain.patient.Patient" or m.entity_class_name = "gov.samhsa.c2s.pcm.domain.consent.Consent"
      then m.revision_type <> 1
      else true
    end
) as m
    LEFT JOIN
  revinfo AS r ON r.rev = m.revision_rev
    LEFT JOIN
  revision_type_code rtc ON m.revision_type = rtc.code
    LEFT JOIN
  patient_audit pa ON r.rev = pa.rev