/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 *   
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *       * Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *       * Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *       * Neither the name of the <organization> nor the
 *         names of its contributors may be used to endorse or promote products
 *         derived from this software without specific prior written permission.
 *   
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *   DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 *   DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.c2s.pcm.domain.valueset;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConceptCodeRepository extends
		JpaRepository<ConceptCode, Long>, JpaSpecificationExecutor<ConceptCode> {

	@Query("select DISTINCT c from ConceptCode c, CodeSystemVersion csv, CodeSystem cs, ConceptCodeValueSet ccvs, ValueSet vs where c.name like ?1 AND "
			+ "c.codeSystemVersion.id = csv.id AND csv.codeSystem.id = cs.id AND cs.name like ?2 AND csv.name like ?3 AND "
			+ "(c.id = ccvs.pk.conceptCode AND ccvs.pk.valueSet = vs.id AND vs.name like ?4)")
	public Page<ConceptCode> findAllByName(String searchTerm,
			String codeSystem, String codeSystemVersion, String valueSetName,
			Pageable pageable);

	@Query("select DISTINCT c from ConceptCode c, CodeSystemVersion csv, CodeSystem cs, ConceptCodeValueSet ccvs, ValueSet vs where c.code like ?1 AND "
			+ "c.codeSystemVersion.id = csv.id AND csv.codeSystem.id = cs.id AND cs.name like ?2 AND csv.name like ?3 AND "
			+ "(c.id = ccvs.pk.conceptCode AND ccvs.pk.valueSet = vs.id AND vs.code like ?4)")
	public Page<ConceptCode> findAllByCodeLike(String searchTerm,
			String codeSystem, String codeSystemVersion, String valueSetName,
			Pageable pageable);

	@Query("select p from ConceptCode p where p.codeSystemVersion like ?1")
	public List<ConceptCode> findAllByCodeSystemVersion(Long codeSystemVersionId);

	ConceptCode findByCodeAndCodeSystemVersionId(String code, Long id);

	@Query("SELECT c.name, c.code, v.name FROM ConceptCode c, ConceptCodeValueSet cv, ValueSet v where c.id = cv.pk.conceptCode AND cv.pk.valueSet = v.id AND c.code LIKE ?1")
	public Page<ConceptCode> findAllByFilter(String code, Pageable pageable);

	public ConceptCode findByCode(String code);

	@Query("SELECT v.name FROM ConceptCode c, ConceptCodeValueSet cv, ValueSet v where c.id = cv.pk.conceptCode "
			+ "AND cv.pk.valueSet = v.id AND c.code = ?1")
	public List<String> findValueSetsForConceptCodes(String code);

	@Query("SELECT v.name, vsc.name, v.id FROM ConceptCode c, ConceptCodeValueSet cv, ValueSet v, ValueSetCategory vsc where c.id = cv.pk.conceptCode "
			+ "AND cv.pk.valueSet = v.id AND c.code = ?1 AND v.valueSetCategory = vsc.id")
	public List<Object[]> findValueSetsAndCategoriesForConceptCodes(String code);

	@Query("select p.code from ConceptCode p, CodeSystemVersion csv, CodeSystem cs where p.codeSystemVersion.id = csv.id AND "
			+ "csv.codeSystem.id = cs.id AND cs.code = ?1")
	public List<String> findByCodeSystem(String code);

	@Query("select DISTINCT vs from ConceptCode c, CodeSystemVersion csv, CodeSystem cs, ConceptCodeValueSet ccvs, ValueSet vs where "
			+ "c.codeSystemVersion.id = csv.id AND csv.codeSystem.id = cs.id AND cs.name like ?1 AND csv.name like ?2 AND "
			+ "(c.id = ccvs.pk.conceptCode AND ccvs.pk.valueSet = vs.id)")
	public List<ValueSet> findValueSetNamesFilterByCodeSystem(
			String codeSystem, String codeSystemVersion);

}
