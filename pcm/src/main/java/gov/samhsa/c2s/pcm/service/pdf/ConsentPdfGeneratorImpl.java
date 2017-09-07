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
public class ConsentPdfGeneratorImpl implements ConsentPdfGenerator {

    private final PdfBoxService pdfBoxService;

    @Autowired
    public ConsentPdfGeneratorImpl(PdfBoxService pdfBoxService) {
        this.pdfBoxService = pdfBoxService;
    }

    @Override
    public byte[] generateConsentPdf(Consent consent, Patient patient, boolean isSigned, Date attestedOn, String terms) {
        return new byte[0];
    }
}
