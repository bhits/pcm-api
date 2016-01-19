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
package gov.samhsa.bhits.pcm.service.esignaturepolling;

import echosign.api.clientv20.dto20.DocumentHistoryEvent;
import echosign.api.clientv20.dto20.DocumentInfo;
import gov.samhsa.bhits.pcm.domain.consent.AbstractSignedPDFDocument;
import gov.samhsa.bhits.pcm.domain.consent.Consent;
import gov.samhsa.bhits.pcm.domain.consent.ConsentRepository;
import gov.samhsa.bhits.pcm.infrastructure.EchoSignSignatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * The Class EchoSignPollingService.
 */
@Transactional
public class EchoSignPollingService implements EsignaturePollingService {

    /**
     * The logger.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * The Signed_ staus.
     */
    private final String Signed_Staus = "SIGNED";

    /**
     * The consent repository.
     */
    private ConsentRepository consentRepository;

    /**
     * The signature service.
     */
    private EchoSignSignatureService signatureService;


    /**
     * Instantiates a new echo sign polling service.
     *
     * @param consentRepository the consent repository
     * @param signatureService  the signature service
     */
    public EchoSignPollingService(ConsentRepository consentRepository,
                                  EchoSignSignatureService signatureService) {
        this.consentRepository = consentRepository;
        this.signatureService = signatureService;
    }

    /**
     * Gets the latest sign status.
     *
     * @param latestDocumentInfo the latest document info
     * @return the latest sign status
     */
    protected String getLatestSignStatus(DocumentInfo latestDocumentInfo) {
        String latestSignStatus = latestDocumentInfo.getStatus().toString();
        return latestSignStatus;
    }

    /**
     * Get pdf document.
     *
     * @param consent the consent
     * @return the abstract signed pdf document
     */
    private AbstractSignedPDFDocument getPdfDocument(Consent consent) {
        if (isRevokedConsent(consent))
            return consent.getSignedPdfConsentRevoke();
        return consent.getSignedPdfConsent();
    }

    /**
     * Checks if is revoked consent.
     *
     * @param consent the consent
     * @return true, if is revoked consent
     */
    private boolean isRevokedConsent(Consent consent) {
        return consent.getSignedPdfConsent().getDocumentSignedStatus()
                .equals("SIGNED");
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.esignaturepolling.EsignaturePollingService#poll
     * ()
     */
    @Override
    @Scheduled(fixedDelayString = "${bhits.pcm.config.echosign.esignaturePollingServicePollTaskFixedDelayInMilliseconds}")
    public void poll() {
        logger.debug("Starts polling at {}...", new Date().toString());

        try {
            Set<Consent> consentSet = new HashSet<Consent>();
            consentSet
                    .addAll(consentRepository
                            .findBySignedPdfConsentDocumentSignedStatusNot(Signed_Staus));
            consentSet
                    .addAll(consentRepository
                            .findBySignedPdfConsentRevokeDocumentSignedStatusNot(Signed_Staus));

            for (Consent consent : consentSet) {
                AbstractSignedPDFDocument document = getPdfDocument(consent);
                String parentDocumentKey = document.getDocumentId();
                String childDocumentKey = signatureService
                        .getChildDocumentKey(parentDocumentKey);

                logger.debug(
                        "Starts calling adobe echosign web service to get document info at {}...",
                        new Date().toString());
                DocumentInfo latestDocumentInfo;
                if (childDocumentKey == null)
                    latestDocumentInfo = signatureService
                            .getDocumentInfo(parentDocumentKey);
                else
                    latestDocumentInfo = signatureService
                            .getDocumentInfo(childDocumentKey);

                logger.debug(
                        "Ended calling adobe echosign web service to get document info at {}.",
                        new Date().toString());

                String latestSignStatus = getLatestSignStatus(latestDocumentInfo);
                boolean isSigned = latestSignStatus.equals(Signed_Staus);
                if (isSigned) {
                    for (DocumentHistoryEvent event : latestDocumentInfo
                            .getEvents().getDocumentHistoryEvent()) {
                        if (event.getDescription().startsWith(
                                "Document esigned by")) {
                            Date signedDate = event.getDate()
                                    .toGregorianCalendar().getTime();
                            if (consent.getSignedPdfConsentRevoke() == null)
                                consent.setSignedDate(signedDate);
                            else
                                consent.setRevocationDate(signedDate);
                        }
                    }

                    logger.debug(
                            "Starts calling adobe echosign web service to get signed document at {}...",
                            new Date().toString());
                    byte[] latestData = null;
                    if (childDocumentKey == null)
                        latestData = signatureService
                                .getLatestDocument(parentDocumentKey);
                    else
                        latestData = signatureService
                                .getLatestDocument(childDocumentKey);

                    logger.debug(
                            "Ended calling adobe echosign web service to get signed document at {}.",
                            new Date().toString());

                    document.setContent(latestData, consent.getId());
                    document.setDocumentSignedStatus(latestSignStatus);

                    consentRepository.save(consent);
                }
            }

        } catch (Exception e) {
            String errorMessage = "Error occurred in EchoSignPollingService.";
            logger.error(errorMessage, e);
            throw new EchoSignPollingServiceException(errorMessage, e);
        }

        logger.debug("Ended polling at {}.", new Date().toString());
    }
}
