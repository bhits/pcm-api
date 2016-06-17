SELECT
  m.id AS activityId,
  r.rev AS revisionid,
  r.revtstmp AS timestamp,
  r.username AS changedBy,
  pa.first_name AS firstName,
  pa.last_name AS lastName,
  rtc.display_name AS recType,
  m.entity_class_name AS type
FROM
  ( SELECT *
    FROM revinfo
    LIMIT 10 OFFSET 0
  ) AS r
    LEFT JOIN
  modified_entity_type_entity m ON r.rev = m.revision
    LEFT JOIN
  revision_type_code rtc ON m.revision_type = rtc.code
    LEFT JOIN
  patient_audit pa ON r.rev = pa.rev