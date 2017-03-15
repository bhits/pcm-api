package gov.samhsa.c2s.pcm.service.fhir;

import gov.samhsa.c2s.pcm.infrastructure.dto.PatientDto;
import org.hl7.fhir.dstu3.model.Consent;

public interface FhirConsentService {
    public Consent createFhirConsent(gov.samhsa.c2s.pcm.domain.consent.Consent consent, PatientDto patientDto);
    public void publishFhirConsentToHie(Consent fhirConsent);
    public void publishFhirConsentToHie(gov.samhsa.c2s.pcm.domain.consent.Consent consent, PatientDto patientDto);

}
