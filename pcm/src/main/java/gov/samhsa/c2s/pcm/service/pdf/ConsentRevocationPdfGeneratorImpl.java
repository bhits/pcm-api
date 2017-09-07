package gov.samhsa.c2s.pcm.service.pdf;

import gov.samhsa.c2s.pcm.domain.consent.Consent;
import gov.samhsa.c2s.pcm.domain.patient.Patient;
import gov.samhsa.c2s.pcm.infrastructure.pdfbox.PdfBoxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class ConsentRevocationPdfGeneratorImpl implements ConsentRevocationPdfGenerator {

    private final PdfBoxService pdfBoxService;

    @Autowired
    public ConsentRevocationPdfGeneratorImpl(PdfBoxService pdfBoxService) {
        this.pdfBoxService = pdfBoxService;
    }

    @Override
    public byte[] generateConsentRevocationPdf(Consent consent, Patient patient, Date revokedOnDateTime, String consentRevocationTerm) {
        return new byte[0];
    }
}
