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
package gov.samhsa.mhc.pcm.service.clinicaldata;

import gov.samhsa.mhc.pcm.domain.clinicaldata.ClinicalDocument;
import gov.samhsa.mhc.pcm.domain.patient.Patient;
import gov.samhsa.mhc.pcm.service.dto.CCDDto;
import gov.samhsa.mhc.pcm.service.dto.ClinicalDocumentDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * The Interface ClinicalDocumentService.
 */
public interface ClinicalDocumentService {

    /**
     * Count all clinical documents.
     *
     * @return the long
     */
    public abstract long countAllClinicalDocuments();


    /**
     * Delete clinical document.
     *
     * @param documentId the clinical document id
     */
    public abstract void deleteClinicalDocument(Long documentId);


    /**
     * Find clinical document.
     *
     * @param id the id
     * @return the clinical document
     */
    public abstract ClinicalDocument findClinicalDocument(String username, Long id);


    /**
     * Find all clinical documents.
     *
     * @return the list
     */
    public abstract List<ClinicalDocument> findAllClinicalDocuments();


    /**
     * Find clinical document entries.
     *
     * @param firstResult the first result
     * @param maxResults  the max results
     * @return the list
     */
    public abstract List<ClinicalDocument> findClinicalDocumentEntries(int firstResult, int maxResults);


    /**
     * Save clinical document.
     *
     * @param clinicalDocumentDto the clinical document dto
     * @return
     */
    public abstract void saveClinicalDocument(ClinicalDocumentDto clinicalDocumentDto);


    /**
     * Update clinical document.
     *
     * @param clinicalDocument the clinical document
     * @return the clinical document
     */
    public abstract ClinicalDocument updateClinicalDocument(ClinicalDocument clinicalDocument);


    /**
     * Find clinical document dto.
     *
     * @param documentId the document id
     * @return the clinical document dto
     */
    public abstract ClinicalDocumentDto findClinicalDocumentDto(String usrename, long documentId);


    /**
     * Find by patient.
     *
     * @param patient the patient
     * @return the list
     */
    public abstract List<ClinicalDocument> findByPatient(Patient patient);


    /**
     * Find by patient Id
     *
     * @param patientId
     * @return
     */
    public abstract List<ClinicalDocument> findByPatientId(long patientId);

    /**
     * Find dto by patient dto.
     *
     * @param patientId the patient id
     * @return the list
     */
    public abstract List<ClinicalDocumentDto> findClinicalDocumentDtoByPatientId(Long patientId);


    /**
     * Checks if is document belongs to this user.
     *
     * @param clinicalDocumentDto the clinical document dto
     * @return true, if is document belongs to this user
     */
    public abstract boolean isDocumentBelongsToThisUser(String username, ClinicalDocumentDto clinicalDocumentDto);

    /**
     * Finds the CCD DTo for the given Id..
     *
     * @param documentId the clinical document id
     * @return CCDDto, The CCD document DTO.
     */
    public abstract CCDDto findCCDDto(String username, long documentId);

    boolean isDocumentOversized(MultipartFile file);

    boolean isDocumentExtensionPermitted(MultipartFile file);

    void validate(MultipartFile file) throws IOException;
}
