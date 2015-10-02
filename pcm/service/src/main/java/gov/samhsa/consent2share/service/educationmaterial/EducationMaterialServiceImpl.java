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
package gov.samhsa.consent2share.service.educationmaterial;

import gov.samhsa.consent2share.domain.educationmaterial.EducationMaterial;
import gov.samhsa.consent2share.domain.educationmaterial.EducationMaterialRepository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

/**
 * The Class EducationMaterialServiceImpl.
 */
@Transactional
public class EducationMaterialServiceImpl implements EducationMaterialService {

	/** The education material repository. */
	EducationMaterialRepository educationMaterialRepository;

	/**
	 * Instantiates a new education material service impl.
	 *
	 * @param educationMaterialRepository
	 *            the education material repository
	 */
	public EducationMaterialServiceImpl(
			EducationMaterialRepository educationMaterialRepository) {
		super();
		this.educationMaterialRepository = educationMaterialRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.educationmaterial.EducationMaterialService
	 * #countAllEducationMaterials()
	 */
	@Override
	public long countAllEducationMaterials() {
		return educationMaterialRepository.count();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.educationmaterial.EducationMaterialService
	 * #
	 * deleteEducationMaterial(gov.samhsa.consent2share.domain.educationmaterial
	 * .EducationMaterial)
	 */
	@Override
	public void deleteEducationMaterial(EducationMaterial educationMaterial) {
		educationMaterialRepository.delete(educationMaterial);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.educationmaterial.EducationMaterialService
	 * #findEducationMaterial(java.lang.Long)
	 */
	@Override
	public EducationMaterial findEducationMaterial(Long id) {
		return educationMaterialRepository.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.educationmaterial.EducationMaterialService
	 * #findAllEducationMaterials()
	 */
	@Override
	public List<EducationMaterial> findAllEducationMaterials() {
		return educationMaterialRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.educationmaterial.EducationMaterialService
	 * #findEducationMaterialEntries(int, int)
	 */
	@Override
	public List<EducationMaterial> findEducationMaterialEntries(
			int firstResult, int maxResults) {
		return educationMaterialRepository.findAll(
				new org.springframework.data.domain.PageRequest(firstResult
						/ maxResults, maxResults)).getContent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.educationmaterial.EducationMaterialService
	 * #saveEducationMaterial(gov.samhsa.consent2share.domain.educationmaterial.
	 * EducationMaterial)
	 */
	@Override
	public void saveEducationMaterial(EducationMaterial educationMaterial) {
		educationMaterialRepository.save(educationMaterial);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.educationmaterial.EducationMaterialService
	 * #
	 * updateEducationMaterial(gov.samhsa.consent2share.domain.educationmaterial
	 * .EducationMaterial)
	 */
	@Override
	public EducationMaterial updateEducationMaterial(
			EducationMaterial educationMaterial) {
		return educationMaterialRepository.save(educationMaterial);
	}
}
