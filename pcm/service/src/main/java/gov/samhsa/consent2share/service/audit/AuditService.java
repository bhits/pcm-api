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
package gov.samhsa.consent2share.service.audit;

import gov.samhsa.consent2share.domain.audit.ModifiedEntityTypeEntity;
import gov.samhsa.consent2share.service.dto.HistoryDto;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

/**
 * The Interface AuditService.
 */
@Secured("ROLE_USER")
public interface AuditService {

	/**
	 * Find all history.
	 *
	 * @param username the username
	 * @return the list
	 */
	List<HistoryDto> findAllHistory(String username);

	/**
	 * Gets the reversed.
	 *
	 * @param original the original
	 * @return the reversed
	 */
	List<HistoryDto> getReversed(List<HistoryDto> original);

	/**
	 * Find history detail.
	 *
	 * @param n the n
	 * @return the history dto
	 */
	HistoryDto findHistoryDetail(Number n);

	/**
	 * Find history details.
	 *
	 * @param revisions the revisions
	 * @return the list
	 */
	List<HistoryDto> findHistoryDetails(List<Number> revisions);

	/**
	 * Make history dtos.
	 *
	 * @return the list
	 */
	List<HistoryDto> makeHistoryDtos();

	/**
	 * Make history dto.
	 *
	 * @return the history dto
	 */
	HistoryDto makeHistoryDto();

	/**
	 * Find legal history by patient.
	 *
	 * @param patientId the patient id
	 * @return the list
	 */
	List<HistoryDto> findLegalHistoryByPatient(long patientId);

	/**
	 * Find legal history details.
	 *
	 * @param revisions the revisions
	 * @return the list
	 */
	List<HistoryDto> findLegalHistoryDetails(List<Number> revisions);

	/**
	 * Find legal history detail.
	 *
	 * @param n the n
	 * @return the history dto
	 */
	HistoryDto findLegalHistoryDetail(Number n);

	/**
	 * Find rev type.
	 *
	 * @param modifiedEntityTypeEntitys the modified entity type entitys
	 * @return the string
	 */
	String findRevType(List<ModifiedEntityTypeEntity> modifiedEntityTypeEntitys);

	/**
	 * Find rev type.
	 *
	 * @param btype the btype
	 * @return the string
	 */
	String findRevType(Byte btype);

	/**
	 * Find rev class name.
	 *
	 * @param modifiedEntityTypeEntitys the modified entity type entitys
	 * @return the string
	 */
	String findRevClassName(
			List<ModifiedEntityTypeEntity> modifiedEntityTypeEntitys);

}
