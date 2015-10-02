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
package gov.samhsa.consent2share.service.audit.pg;

import gov.samhsa.consent2share.domain.audit.ModifiedEntityTypeEntityRepository;
import gov.samhsa.consent2share.domain.audit.RevisionInfoEntityRepository;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientLegalRepresentativeAssociationRepository;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.domain.staff.StaffRepository;
import gov.samhsa.consent2share.service.dto.HistoryDto;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class AuditServiceImpl.
 */
@Transactional
public class AuditServiceImpl extends
		gov.samhsa.consent2share.service.audit.AuditServiceImpl {

	/**
	 * Instantiates a new audit service impl.
	 *
	 * @param entityManagerFactory
	 *            the entity manager factory
	 * @param patientRepository
	 *            the patient repository
	 * @param patientRevisionEntityRepository
	 *            the patient revision entity repository
	 * @param modifiedEntityTypeEntityRepository
	 *            the modified entity type entity repository
	 * @param patientLegalRepresentativeAssociationRepository
	 *            the patient legal representative association repository
	 * @param staffRepository
	 *            the staff repository
	 */
	public AuditServiceImpl(
			EntityManagerFactory entityManagerFactory,
			PatientRepository patientRepository,
			RevisionInfoEntityRepository patientRevisionEntityRepository,
			ModifiedEntityTypeEntityRepository modifiedEntityTypeEntityRepository,
			PatientLegalRepresentativeAssociationRepository patientLegalRepresentativeAssociationRepository,
			StaffRepository staffRepository) {
		super(entityManagerFactory, patientRepository,
				patientRevisionEntityRepository,
				modifiedEntityTypeEntityRepository,
				patientLegalRepresentativeAssociationRepository,
				staffRepository);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.audit.AuditServiceImpl#findAllHistory
	 * (java.lang.String)
	 */
	@Override
	public List<HistoryDto> findAllHistory(String username) {

		Patient patientA = patientRepository.findByUsername(username);
		Long patientId = patientA.getId();

		EntityManager em = entityManagerFactory.createEntityManager();
		AuditReader reader = AuditReaderFactory.get(em);
		List<Number> revisions = reader.getRevisions(Patient.class, patientId);
		List<HistoryDto> historyList = findHistoryDetails(revisions);
		List<HistoryDto> consentRevokationHistoryList = findConsentRevokationHistoryByPatient(
				reader, patientId);
		historyList.addAll(consentRevokationHistoryList);

		List<HistoryDto> consentSignPDFHistoryList = findConsentSignPDFHistoryByPatient(
				reader, patientId);
		historyList.addAll(consentSignPDFHistoryList);
		Collections.sort(historyList);

		List<HistoryDto> historyListReverse = getReversed(historyList);
		em.close();
		return historyListReverse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.audit.AuditServiceImpl#
	 * findLegalHistoryByPatient(long)
	 */
	@Override
	public List<HistoryDto> findLegalHistoryByPatient(long patientId) {
		throw new UnsupportedOperationException(
				"This operation is not supported in PGC project. Please refer to the baseline implementation for details if required in the future.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.audit.AuditServiceImpl#
	 * findLegalHistoryDetails(java.util.List)
	 */
	@Override
	public List<HistoryDto> findLegalHistoryDetails(List<Number> revisions) {
		throw new UnsupportedOperationException(
				"This operation is not supported in PGC project. Please refer to the baseline implementation for details if required in the future.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.audit.AuditServiceImpl#
	 * findLegalHistoryDetail(java.lang.Number)
	 */
	@Override
	public HistoryDto findLegalHistoryDetail(Number n) {
		throw new UnsupportedOperationException(
				"This operation is not supported in PGC project. Please refer to the baseline implementation for details if required in the future.");
	}
}
