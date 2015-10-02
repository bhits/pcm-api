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
package gov.samhsa.consent2share.service.patient;

import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.infrastructure.security.AuthenticationFailedException;
import gov.samhsa.consent2share.service.dto.AddConsentIndividualProviderDto;
import gov.samhsa.consent2share.service.dto.AddConsentOrganizationalProviderDto;
import gov.samhsa.consent2share.service.dto.PatientAdminDto;
import gov.samhsa.consent2share.service.dto.PatientConnectionDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;
import gov.samhsa.consent2share.service.dto.RecentPatientDto;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

/**
 * The Interface PatientService.
 */
@Secured({ "ROLE_USER", "ROLE_ADMIN" })
public interface PatientService {

	/**
	 * Count all patients.
	 *
	 * @return the long
	 */
	long countAllPatients();

	/**
	 * Find patient.
	 *
	 * @param id
	 *            the id
	 * @return the patient profile dto
	 */
	PatientProfileDto findPatient(Long id);

	/**
	 * Find id by username.
	 *
	 * @param username
	 *            the username
	 * @return the long
	 */
	Long findIdByUsername(String username);

	/**
	 * Find username by id.
	 *
	 * @param id
	 *            the id
	 * @return the string
	 */
	String findUsernameById(long id);

	/**
	 * Find patient email by username.
	 *
	 * @param username
	 *            the username
	 * @return the string
	 */
	String findPatientEmailByUsername(String username);

	/**
	 * Find patient profile by username.
	 *
	 * @param username
	 *            the username
	 * @return the patient profile dto
	 */
	PatientProfileDto findPatientProfileByUsername(String username);

	/**
	 * Find patient connection by username.
	 *
	 * @param username
	 *            the username
	 * @return the patient connection dto
	 */
	PatientConnectionDto findPatientConnectionByUsername(String username);

	/**
	 * Find patient connection by patient.
	 *
	 * @param patient
	 *            the patient
	 * @return the patient connection dto
	 */
	PatientConnectionDto findPatientConnectionByPatient(Patient patient);

	/**
	 * Find patient connection by username.
	 *
	 * @param id
	 *            the id
	 * @return the patient connection dto
	 */
	PatientConnectionDto findPatientConnectionById(long id);

	/**
	 * Find patient entries.
	 *
	 * @param pageNumber
	 *            the page number
	 * @param pageSzie
	 *            the page szie
	 * @return the list
	 */
	List<PatientProfileDto> findPatientEntries(int pageNumber, int pageSzie);

	/**
	 * Save patient.
	 *
	 * @param patientProfileDto
	 *            the patient profile dto
	 * @return the patient profile dto
	 */
	PatientProfileDto savePatient(PatientProfileDto patientProfileDto);

	/**
	 * Update patient.
	 *
	 * @param patientDto
	 *            the patient dto
	 * @throws AuthenticationFailedException
	 *             the authentication failed exception
	 */
	void updatePatient(PatientProfileDto patientDto)
			throws AuthenticationFailedException;

	/**
	 * Update eid.
	 *
	 * @param username
	 *            the username
	 */
	public void updateEid(String username);

	/**
	 * Update eid.
	 *
	 * @param id
	 *            the id
	 */
	public void updateEid(long id);

	/**
	 * Find add consent individual provider dto by username.
	 *
	 * @param username
	 *            the username
	 * @return the array list
	 */
	List<AddConsentIndividualProviderDto> findAddConsentIndividualProviderDtoByUsername(
			String username);

	/**
	 * Find add consent organizational provider dto by username.
	 *
	 * @param username
	 *            the username
	 * @return the array list
	 */
	List<AddConsentOrganizationalProviderDto> findAddConsentOrganizationalProviderDtoByUsername(
			String username);

	/**
	 * Find add consent individual provider dto by patient id.
	 *
	 * @param pateintId
	 *            the pateint id
	 * @return the list
	 */
	List<AddConsentIndividualProviderDto> findAddConsentIndividualProviderDtoByPatientId(
			long pateintId);

	/**
	 * Find add consent organizational provider dto by username.
	 *
	 * @param pateintId
	 *            the pateint id
	 * @return the array list
	 */
	List<AddConsentOrganizationalProviderDto> findAddConsentOrganizationalProviderDtoByPatientId(
			long pateintId);

	/**
	 * Checks if is legal rep for current user.
	 *
	 * @param legalRepId
	 *            the legal rep id
	 * @return true, if is legal rep for current user
	 */
	boolean isLegalRepForCurrentUser(Long legalRepId);

	/**
	 * Find patient by first name and last name.
	 *
	 * @param tokens
	 *            the tokens
	 * @return the patient profile dto
	 */
	public List<PatientAdminDto> findAllPatientByFirstNameAndLastName(
			String[] tokens);

	/**
	 * Find recent patient dtos by id.
	 *
	 * @param ids
	 *            the ids
	 * @return the list
	 */
	public List<RecentPatientDto> findRecentPatientDtosById(List<String> ids);

	/**
	 * Find by username.
	 *
	 * @param username
	 *            the username
	 * @return the patient profile dto
	 */
	PatientProfileDto findByUsername(String username);
}
