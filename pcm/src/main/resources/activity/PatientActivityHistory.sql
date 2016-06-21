SELECT
  m.id AS activity_id,
  r.rev AS revision_id,
  r.revtstmp AS timestamp,
  r.username AS username,
  rtc.display_name AS rec_type,
  m.entity_class_name AS type
FROM
  revinfo AS r
    LEFT JOIN
  modified_entity_type_entity m ON r.rev = m.revision
    LEFT JOIN
  revision_type_code rtc ON m.revision_type = rtc.code
    LEFT JOIN
  patient_audit pa ON r.rev = pa.rev