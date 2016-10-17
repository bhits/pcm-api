/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * <p/>
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
 * <p/>
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
package gov.samhsa.c2s.pcm.service.patient;

import gov.samhsa.c2s.pcm.domain.patient.Patient;
import gov.samhsa.c2s.pcm.infrastructure.dto.PatientDto;
import gov.samhsa.c2s.pcm.service.dto.*;

import java.util.List;
import java.util.Set;

/**
 * The Interface PatientService.
 */
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
     * @param id the id
     * @return the patient profile dto
     */
    PatientProfileDto findPatient(Long id);

    /**
     * Find id by username.
     *
     * @param username the username
     * @return the long
     */
    Long findIdByUsername(String username);

    /**
     * Find username by id.
     *
     * @param id the id
     * @return the string
     */
    String findUsernameById(long id);

    /**
     * Find patient email by username.
     *
     * @param username the username
     * @return the string
     */
    String findPatientEmailByUsername(String username);

    /**
     * Find patient profile by username.
     *
     * @param username the username
     * @return the patient profile dto
     */
    PatientProfileDto findPatientProfileByUsername(String username);

    PatientProfileDto findPatientProfileByPatientId(Long patientId);

    /**
     * Find patient connection by username.
     *
     * @param username the username
     * @return the patient connection dto
     */
    PatientConnectionDto findPatientConnectionByUsername(String username);

    /**
     * Find patient connection by patient.
     *
     * @param patient the patient
     * @return the patient connection dto
     */
    PatientConnectionDto findPatientConnectionByPatient(Patient patient);


    /**
     * Find patient connection by username.
     *
     * @param username the username
     * @return the patient connection dto
     */
    Set<ProviderDto> findProvidersByUsername(String username);

    /**
     * Find patient connection by patient.
     *
     * @param patient the patient
     * @return the patient connection dto
     */
    Set<ProviderDto> findProvidersByPatient(Patient patient);

    Set<ProviderDto> findProvidersByPatientId(Long patientId);

    /**
     * Find patient connection by username.
     *
     * @param id the id
     * @return the patient connection dto
     */
    PatientConnectionDto findPatientConnectionById(long id);

    /**
     * Find patient entries.
     *
     * @param pageNumber the page number
     * @param pageSzie   the page szie
     * @return the list
     */
    List<PatientProfileDto> findPatientEntries(int pageNumber, int pageSzie);

    /**
     * Save patient.
     *
     * @param patientProfileDto the patient profile dto
     * @return the patient profile dto
     */
    PatientProfileDto savePatient(PatientProfileDto patientProfileDto);

    /**
     * Update patient.
     *
     * @param patientDto the patient dto
     */
    void updatePatient(PatientProfileDto patientDto);

    /**
     * Find add consent individual provider dto by username.
     *
     * @param username the username
     * @return the array list
     */
    List<AddConsentIndividualProviderDto> findAddConsentIndividualProviderDtoByUsername(
            String username);

    /**
     * Find add consent organizational provider dto by username.
     *
     * @param username the username
     * @return the array list
     */
    List<AddConsentOrganizationalProviderDto> findAddConsentOrganizationalProviderDtoByUsername(
            String username);

    /**
     * Find add consent individual provider dto by patient id.
     *
     * @param pateintId the pateint id
     * @return the list
     */
    List<AddConsentIndividualProviderDto> findAddConsentIndividualProviderDtoByPatientId(
            long pateintId);

    /**
     * Find add consent organizational provider dto by username.
     *
     * @param pateintId the pateint id
     * @return the array list
     */
    List<AddConsentOrganizationalProviderDto> findAddConsentOrganizationalProviderDtoByPatientId(
            long pateintId);

    /**
     * Checks if is legal rep for current user.
     *
     * @param username   username
     * @param legalRepId the legal rep id
     * @return true, if is legal rep for current user
     */
    boolean isLegalRepForCurrentUser(String username, Long legalRepId);

    /**
     * Find patient by first name and last name.
     *
     * @param tokens the tokens
     * @return the patient profile dto
     */
    public List<PatientAdminDto> findAllPatientByFirstNameAndLastName(
            String[] tokens);

    /**
     * Find recent patient dtos by id.
     *
     * @param ids the ids
     * @return the list
     */
    public List<RecentPatientDto> findRecentPatientDtosById(List<String> ids);

    /**
     * Find by username.
     *
     * @param username the username
     * @return the patient profile dto
     */
    PatientProfileDto findByUsername(String username);

    // FIXME (#24): remove this block when patient creation concept in PCM is finalized
    Long createNewPatientWithOAuth2AuthenticationIfNotExists();

    void updatePatientFromPHR(PatientDto patientDto);
}
