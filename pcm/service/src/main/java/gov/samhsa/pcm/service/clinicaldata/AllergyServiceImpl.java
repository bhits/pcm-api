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
package gov.samhsa.pcm.service.clinicaldata;

import gov.samhsa.pcm.domain.clinicaldata.Allergy;
import gov.samhsa.pcm.domain.clinicaldata.AllergyRepository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

/**
 * The Class AllergyServiceImpl.
 */
@Transactional
public class AllergyServiceImpl implements AllergyService {

	/** The allergy repository. */
	AllergyRepository allergyRepository;

	/**
	 * Instantiates a new allergy service impl.
	 *
	 * @param allergyRepository
	 *            the allergy repository
	 */
	public AllergyServiceImpl(AllergyRepository allergyRepository) {
		super();
		this.allergyRepository = allergyRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.clinicaldata.AllergyService#countAllAllergys
	 * ()
	 */
	@Override
	public long countAllAllergys() {
		return allergyRepository.count();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.clinicaldata.AllergyService#deleteAllergy
	 * (gov.samhsa.consent2share.domain.clinicaldata.Allergy)
	 */
	@Override
	public void deleteAllergy(Allergy allergy) {
		allergyRepository.delete(allergy);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.clinicaldata.AllergyService#findAllergy
	 * (java.lang.Long)
	 */
	@Override
	public Allergy findAllergy(Long id) {
		return allergyRepository.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.clinicaldata.AllergyService#findAllAllergys
	 * ()
	 */
	@Override
	public List<Allergy> findAllAllergys() {
		return allergyRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.clinicaldata.AllergyService#
	 * findAllergyEntries(int, int)
	 */
	@Override
	public List<Allergy> findAllergyEntries(int firstResult, int maxResults) {
		return allergyRepository.findAll(
				new org.springframework.data.domain.PageRequest(firstResult
						/ maxResults, maxResults)).getContent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.clinicaldata.AllergyService#saveAllergy
	 * (gov.samhsa.consent2share.domain.clinicaldata.Allergy)
	 */
	@Override
	public void saveAllergy(Allergy allergy) {
		allergyRepository.save(allergy);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.clinicaldata.AllergyService#updateAllergy
	 * (gov.samhsa.consent2share.domain.clinicaldata.Allergy)
	 */
	@Override
	public Allergy updateAllergy(Allergy allergy) {
		return allergyRepository.save(allergy);
	}
}
