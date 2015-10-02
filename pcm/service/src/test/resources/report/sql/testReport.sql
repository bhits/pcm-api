SELECT 
    pa.last_name AS lastname,
    pa.first_name AS firstname,
    pa.medical_record_number,
    agc.code,
    r.revtstmp,
    s.last_name AS adminLastname,
    s.first_name AS adminFirstname,
    s.employeeid,
    IFNULL(sc.signedConsents, 0) AS signedConsents,
    IFNULL(sc.unsignedConsents, 0) AS unsignedConsents
FROM
    patient pa
        LEFT JOIN
    patient_audit p ON pa.id = p.id AND p.revtype = 0
        LEFT JOIN
    revinfo r ON p.rev = r.rev
        LEFT JOIN
    staff s ON r.username = s.username
        LEFT JOIN
    administrative_gender_code agc ON agc.id = pa.administrative_gender_code
        LEFT JOIN
    (SELECT 
        patient,
            sum(CASE
                WHEN signed_date IS NULL THEN 0
                ELSE 1
            END) AS signedConsents,
            sum(CASE
                WHEN signed_date IS NOT NULL THEN 0
                ELSE 1
            END) AS unsignedConsents
    FROM
        consent
    GROUP BY patient) sc ON sc.patient = pa.id;