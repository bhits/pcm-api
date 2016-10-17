/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of the <organization> nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.c2s.pcm.infrastructure;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import gov.samhsa.c2s.pcm.domain.patient.Patient;
import gov.samhsa.c2s.pcm.domain.consent.Consent;
import gov.samhsa.c2s.pcm.infrastructure.exception.ConsentPdfGenerationException;
import gov.samhsa.c2s.pcm.service.consent.ConsentRevocationTermsVersionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.ByteArrayOutputStream;
import java.util.Date;

/**
 * The Class ConsentRevokationPdfGeneratorImpl.
 */
@Service
public class ConsentRevokationPdfGeneratorImpl extends
        AbstractConsentRevokationPdfGenerator {

    /**
     * The logger.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ITextPdfService iTextPdfService;

    @Autowired
    private ConsentRevocationTermsVersionsService consentRevocationTermsVersionsService;

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.infrastructure.ConsentRevokationPdfGenerator
     * #generateConsentRevokationPdf
     * (gov.samhsa.consent2share.domain.consent.Consent)
     */
    @Override
    public byte[] generateConsentRevokationPdf(Consent consent, Patient patient, Date attestedOnDateTime) {
        Assert.notNull(consent, "Consent is required.");

        Document document = new Document();

        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, pdfOutputStream);

            document.open();

            // Title
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
            document.add(iTextPdfService.createParagraphWithContent("Revocation of Consent to Participate in Health Information Exchange", titleFont));

            // Blank line
            document.add(Chunk.NEWLINE);

            // Consent Created Date
            document.add(iTextPdfService.createParagraphWithContent("Created On: " + iTextPdfService.formatDate(consent.getCreatedDateTime()), null));

            //consent Reference Number
            document.add(iTextPdfService.createConsentReferenceNumberTable(consent));

            document.add(new Paragraph(" "));

            //Patient Name and date of birth
            document.add(iTextPdfService.createPatientNameAndDOBTable(patient.getFirstName(), patient.getLastName(), patient.getBirthDay()));

            document.add(new Paragraph(" "));

            document.add(new Paragraph(consentRevocationTermsVersionsService.findDtoByLatestEnabledVersion().getConsentRevokeTermsText()));

            document.add(new Paragraph(" "));

            //Signing details
            document.add(iTextPdfService.createSigningDetailsTable(patient.getFirstName(), patient.getLastName(), consent.getPatient().getEmail(), true, attestedOnDateTime));

            document.close();

        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            throw new ConsentPdfGenerationException("Exception when trying to generate pdf", e);
        }

        byte[] pdfBytes = pdfOutputStream.toByteArray();

        return pdfBytes;
    }

    @Override
    public byte[] generateUnattestedConsentRevokationPdf(Consent consent, Patient patient) {
        Assert.notNull(consent, "Consent is required.");

        Document document = new Document();

        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, pdfOutputStream);

            document.open();

            // Title
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
            document.add(iTextPdfService.createParagraphWithContent("Revocation of Consent to Participate in Health Information Exchange", titleFont));

            // Blank line
            document.add(Chunk.NEWLINE);

            // Consent Created Date
            document.add(iTextPdfService.createParagraphWithContent("Created On: " + iTextPdfService.formatDate(consent.getCreatedDateTime()), null));

            //consent Reference Number
            document.add(iTextPdfService.createConsentReferenceNumberTable(consent));

            document.add(new Paragraph(" "));

            //Patient Name and date of birth
            document.add(iTextPdfService.createPatientNameAndDOBTable(patient.getFirstName(), patient.getLastName(), patient.getBirthDay()));

            document.add(new Paragraph(" "));

            document.add(new Paragraph(consentRevocationTermsVersionsService.findDtoByLatestEnabledVersion().getConsentRevokeTermsText()));

            document.close();

        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            throw new ConsentPdfGenerationException("Exception when trying to generate pdf", e);
        }

        byte[] pdfBytes = pdfOutputStream.toByteArray();

        return pdfBytes;
    }
}
