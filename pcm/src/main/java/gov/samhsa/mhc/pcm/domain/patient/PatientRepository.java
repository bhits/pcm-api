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
package gov.samhsa.mhc.pcm.domain.patient;

import gov.samhsa.mhc.pcm.domain.reference.AdministrativeGenderCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * The Interface PatientRepository.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>,
        JpaSpecificationExecutor<Patient> {

    /**
     * Find by username.
     *
     * @param username
     *            the username
     * @return the patient
     */
    public abstract Patient findByUsername(String username);

    /**
     * Find all by first name likes and last name likes.
     *
     * @param token1
     *            the token1
     * @return the list
     */
    @Query("select p from Patient p where p.firstName like ?1 or p.lastName like ?1")
    public abstract List<Patient> findAllByFirstNameLikesAndLastNameLikes(
            String token1);

    /**
     * Find all by first name likes and last name likes.
     *
     * @param token1
     *            the token1
     * @param token2
     *            the token2
     * @return the list
     */
    @Query("select p from Patient p where (p.firstName like ?1 or p.firstName like ?2) and (p.lastName like ?1 or p.lastName like ?2)")
    public abstract List<Patient> findAllByFirstNameLikesAndLastNameLikes(
            String token1, String token2);

    /**
     * Find all by medical record number.
     *
     * @param medicalRecordNumber
     *            the medical record number
     * @return the list
     */
    public abstract List<Patient> findAllByMedicalRecordNumber(
            String medicalRecordNumber);

    /**
     * Find by enterprise identifier.
     *
     * @param enterpriseIdentifier
     *            the enterprise identifier
     * @return the patient
     */
    public abstract Patient findByEnterpriseIdentifier(
            String enterpriseIdentifier);

    /**
     * Find by first name and last name and birth day and social security number
     * and administrative gender code.
     *
     * @param firstName
     *            the first name
     * @param lastName
     *            the last name
     * @param birthDay
     *            the birth day
     * @param socialSecurityNumber
     *            the social security number
     * @param administrativeGenderCode
     *            the administrative gender code
     * @return the patient
     */
    public abstract Patient findByFirstNameAndLastNameAndBirthDayAndSocialSecurityNumberAndAdministrativeGenderCode(
            String firstName, String lastName, Date birthDay,
            String socialSecurityNumber,
            AdministrativeGenderCode administrativeGenderCode);

    /**
     * Find by first name and last name and birth day and email.
     *
     * @param firstName
     *            the first name
     * @param lastName
     *            the last name
     * @param birthDay
     *            the birth day
     * @param email
     *            the email
     * @return the patient
     */
    public abstract Patient findByFirstNameAndLastNameAndBirthDayAndEmail(
            String firstName, String lastName, Date birthDay, String email);

    default Optional<Patient> findOneAsOptional(Long id) {
        return Optional.ofNullable(findOne(id));
    }
}
