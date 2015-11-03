/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
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
package gov.samhsa.consent2share.service.consent;

import gov.samhsa.consent.ConsentGenException;
import gov.samhsa.consent2share.domain.consent.Consent;
import gov.samhsa.consent2share.domain.consent.SignedPDFConsent;
import gov.samhsa.consent2share.domain.consent.SignedPDFConsentRevocation;
import gov.samhsa.consent2share.service.dto.AbstractPdfDto;
import gov.samhsa.consent2share.service.dto.ConsentDto;
import gov.samhsa.consent2share.service.dto.ConsentListDto;
import gov.samhsa.consent2share.service.dto.ConsentPdfDto;
import gov.samhsa.consent2share.service.dto.ConsentRevokationPdfDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.access.annotation.Secured;

/**
 * The Interface ConsentService.
 */
public interface ConsentService {

	/**
	 * Count all consents.
	 *
	 * @return the long
	 */
	long countAllConsents();

	/**
	 * Delete consent.
	 *
	 * @param consent
	 *            the consent
	 */
	void deleteConsent(Consent consent);

	/**
	 * Delete consent.
	 *
	 * @param consentId
	 *            the consent id
	 * @return the boolean true on success, false on failure
	 */
	boolean deleteConsent(Long consentId);

	/**
	 * Find consent.
	 *
	 * @param id
	 *            the id
	 * @return the consent
	 */
	Consent findConsent(Long id);

	/**
	 * Find all consents.
	 *
	 * @return the list
	 */
	List<Consent> findAllConsents();

	/**
	 * Find all consents dto by patient.
	 *
	 * @param patientId
	 *            the patient id
	 * @return the list
	 */
	List<ConsentListDto> findAllConsentsDtoByPatient(Long patientId);

	/**
	 * Find consent entries.
	 *
	 * @param firstResult
	 *            the first result
	 * @param maxResults
	 *            the max results
	 * @return the list
	 */
	List<Consent> findConsentEntries(int firstResult, int maxResults);

	/**
	 * Find consentPdfDto.
	 *
	 * @param consentId
	 *            the consent id
	 * @return the consent pdf dto
	 */
	ConsentPdfDto findConsentPdfDto(Long consentId);

	/**
	 * Find consentPdfDto.
	 *
	 * @param consentId
	 *            the consent id
	 * @return the consent pdf dto
	 */
	AbstractPdfDto findConsentContentDto(Long consentId);

	/**
	 * Make consentListDto Array.
	 *
	 * @return the array list
	 */
	ArrayList<ConsentListDto> makeConsentListDtos();

	/**
	 * Make consent revokation pdf dto.
	 *
	 * @return the consent revokation pdf dto
	 */
	ConsentRevokationPdfDto makeConsentRevokationPdfDto();

	/**
	 * Save consent.
	 *
	 * @param consent
	 *            the consent
	 */
	void saveConsent(Consent consent);

	/**
	 * Save consent.
	 *
	 * @param consentDto
	 *            the consent dto
	 * @param patientId
	 *            the patient id
	 * @return the object
	 * @throws ConsentGenException
	 *             the consent gen exception
	 */
	Object saveConsent(ConsentDto consentDto, long patientId)
			throws ConsentGenException;

	/**
	 * Update consent.
	 *
	 * @param consent
	 *            the consent
	 * @return the consent
	 */
	Consent updateConsent(Consent consent);

	/**
	 * Checks if this consent belong to this user.
	 *
	 * @param consentId
	 *            the consent id
	 * @param patient
	 *            the patient
	 * @return true, if is consent belong to this user
	 */
	boolean isConsentBelongToThisUser(Long consentId, Long patient);

	/**
	 * Find consent by id.
	 *
	 * @param consentId
	 *            the consent id
	 * @return the consent dto
	 */
	ConsentDto findConsentById(Long consentId);

	/**
	 * Sign consent.
	 *
	 * @param consentPdfDto
	 *            the consent pdf dto
	 * @return true, if successful
	 */
	boolean signConsent(ConsentPdfDto consentPdfDto);

	/**
	 * Validate consent date.
	 *
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return true, if successful
	 */
	boolean validateConsentDate(Date startDate, Date endDate);

	/**
	 * Make SignedPdfConsent.
	 *
	 * @return the signed pdf consent
	 */
	SignedPDFConsent makeSignedPdfConsent();

	/**
	 * Make ConsentPdfDto.
	 *
	 * @return the consent pdf dto
	 */
	ConsentPdfDto makeConsentPdfDto();

	/**
	 * Make signed pdf consent revocation.
	 *
	 * @return the signed pdf consent revocation
	 */
	SignedPDFConsentRevocation makeSignedPDFConsentRevocation();

	/**
	 * Make consent.
	 *
	 * @return the consent
	 */
	Consent makeConsent();

	/**
	 * Make consent dto.
	 *
	 * @return the consent dto
	 */
	ConsentDto makeConsentDto();

	/**
	 * Are there duplicates in two sets.
	 *
	 * @param set1
	 *            the set1
	 * @param set2
	 *            the set2
	 * @return true, if successful
	 */
	@SuppressWarnings("rawtypes")
	boolean areThereDuplicatesInTwoSets(Set set1, Set set2);

	/**
	 * Revoke consent.
	 *
	 * @param consentRevokationPdfDto
	 *            the consent revokation pdf dto
	 */
	void signConsentRevokation(ConsentRevokationPdfDto consentRevokationPdfDto);

	/**
	 * Find consent revokation pdf dto.
	 *
	 * @param consentId
	 *            the consent id
	 * @return the consent revokation pdf dto
	 */
	ConsentRevokationPdfDto findConsentRevokationPdfDto(Long consentId);

	/**
	 * Adds the unsigned consent revokation pdf.
	 *
	 * @param consentId
	 *            the consent id
	 * @param revokationType
	 *            the revokation type
	 */
	void addUnsignedConsentRevokationPdf(Long consentId, String revokationType);

	/**
	 * Find all consents dto by user name.
	 *
	 * @param userName
	 *            the user name
	 * @return the list
	 */
	List<ConsentListDto> findAllConsentsDtoByUserName(String userName);

	/**
	 * Return tagged c32. Entry tags that have been removed in segmented c32
	 * were tagged and returned
	 *
	 * @param originalC32
	 *            the original c32
	 * @param consentId
	 *            the consent id
	 * @param purposeOfUse
	 *            the purpose of use
	 * @return the tagged c32
	 * @throws ConsentGenException
	 *             the consent gen exception
	 */
	public String getTaggedC32(String originalC32, Long consentId,
			String purposeOfUse) throws ConsentGenException;

	/**
	 * Creates the embedded widget.
	 *
	 * @param consentPdfDto
	 *            the consent pdf dto
	 * @return the string
	 */
	String createConsentEmbeddedWidget(ConsentPdfDto consentPdfDto);

	/**
	 * Creates the revocation embedded widget.
	 *
	 * @param consentRevokationPdfDto
	 *            the consent revokation pdf dto
	 * @return the string
	 */
	String createRevocationEmbeddedWidget(
			ConsentRevokationPdfDto consentRevokationPdfDto);

	/**
	 * Gets the consent signed stage.
	 *
	 * @param consentId
	 *            the consent id
	 * @return the consent signed stage
	 */
	String getConsentSignedStage(Long consentId);

	/**
	 * Gets the xacml ccd.
	 *
	 * @param consentId
	 *            the consent id
	 * @return the xacml ccd
	 */
	byte[] getXacmlCcd(Long consentId);

	 Map<String, Object> findAllConsentsDtoByPatientAndPage(Long patientId, String pageNumber);
}
