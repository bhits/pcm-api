SELECT 
    pa.last_name AS patient_last_name,
    pa.first_name AS patient_first_name,
    pa.medical_record_number,
    pa.username AS patient_user_name,
    pa.birth_day,
    agc.code,
    r.revtstmp,
    act.active_account_date_time,
	cit.consent_initial_date_time,
    s.last_name AS admin_last_name,
    s.first_name AS admin_first_name,
    s.employeeid AS employee_id,
    IFNULL(sc.num_of_signed_consents, 0) AS num_of_signed_consents,
    IFNULL(sc.num_of_unsigned_consents, 0) AS num_of_unsigned_consents,
    IFNULL(sc.num_of_revoked_consents, 0) AS num_of_revoked_consents,
    IFNULL(sc.num_of_effective_consents, 0) AS num_of_effective_consents,
	IFNULL(sc.num_of_expired_consents, 0) AS num_of_expired_consents
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
	/*
	 * Purpose: Do the aggregation in subqueries, to get multiple number of consents
	 */
    (SELECT 
        patient,
            sum(CASE
                WHEN signed_date IS NULL THEN 0
                ELSE 1
            END) AS num_of_signed_consents,
            sum(CASE
                WHEN signed_date IS NOT NULL THEN 0
                ELSE 1
            END) AS num_of_unsigned_consents,
    /*
	 * Scenario: Do the aggregation for the number of effective consents
	 * 		   1. Signed consent
	 * 		   2. Non revoked consent
	 * 		   3. Non expired consent
	 */   
            sum(CASE
                WHEN
                    signed_date IS NOT NULL
                        AND revocation_date IS NULL
                        AND NOW() BETWEEN start_date AND end_date
                THEN
                    1
                ELSE 0
            END) AS num_of_effective_consents,
    /*
	 * Scenario: Do the aggregation for the number of revoked consents
	 * 		   1. Revoked consent 
	 * 		   2. Non expired consent
	 */  
            sum(CASE
                WHEN 
                	revocation_date IS NULL
                	OR revocation_date > end_date
                THEN 0
                ELSE 1
            END) AS num_of_revoked_consents,
    /*
	 * Scenario: Do the aggregation for the number of expired consents
	 * 		   1. Signed consent
	 * 		   2. Non revoked consent
	 * 		   3. End date of consent has passed
	 * 		   Plus,
	 * 		   1. Signed consent
	 * 		   2. Revoked consent but expired
	 * 		   3. End date of consent has passed			
	 */    
            sum(CASE
                WHEN 
                	(signed_date IS NOT NULL
                	 AND revocation_date IS NULL
                        AND end_date < NOW())
                 OR (signed_date IS NOT NULL
                	 AND revocation_date > end_date
                        AND end_date < NOW())
                THEN 1
                ELSE 0
            END) AS num_of_expired_consents
    FROM
        consent
    GROUP BY patient) sc ON sc.patient = pa.id
        LEFT JOIN
    /*
	 * Purpose: To get the time to activate the patient's account
	 */          
    (SELECT 
        p.id as pid, p.rev, min(r.revtstmp) as active_account_date_time
    FROM
        patient_audit p
    join revinfo r
    where
        p.username is not null and r.rev = p.rev
    group by p.id) act ON pid = pa.id
        LEFT JOIN
    /*
	 * Purpose: To get the time to initial the patient's consent
	 */          
    (SELECT 
        co.patient as cop, min(co.signed_date) as consent_initial_date_time
    FROM
        consent co
    join patient_audit pa
    where
        co.signed_date is not null
            and co.patient = pa.id
    group by pa.id) cit ON cop = pa.id
where
    r.revtstmp BETWEEN ? AND ?
