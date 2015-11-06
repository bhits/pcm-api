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
package gov.samhsa.pcm.service.reference;

import gov.samhsa.pcm.domain.reference.ClinicalDocumentSectionTypeCode;
import gov.samhsa.pcm.domain.reference.ClinicalDocumentSectionTypeCodeRepository;
import gov.samhsa.pcm.service.dto.AddConsentFieldsDto;
import gov.samhsa.pcm.service.dto.LookupDto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class ClinicalDocumentSectionTypeCodeServiceImpl.
 */
@Transactional
public class ClinicalDocumentSectionTypeCodeServiceImpl implements
		ClinicalDocumentSectionTypeCodeService {

	/** The clinical document section type code repository. */
	ClinicalDocumentSectionTypeCodeRepository clinicalDocumentSectionTypeCodeRepository;

	/** The model mapper. */
	ModelMapper modelMapper;

	/**
	 * Instantiates a new clinical document section type code service impl.
	 *
	 * @param clinicalDocumentSectionTypeCodeRepository
	 *            the clinical document section type code repository
	 * @param modelMapper
	 *            the model mapper
	 */
	public ClinicalDocumentSectionTypeCodeServiceImpl(
			ClinicalDocumentSectionTypeCodeRepository clinicalDocumentSectionTypeCodeRepository,
			ModelMapper modelMapper) {
		super();
		this.clinicalDocumentSectionTypeCodeRepository = clinicalDocumentSectionTypeCodeRepository;
		this.modelMapper = modelMapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.reference.
	 * ClinicalDocumentSectionTypeCodeService
	 * #findAllClinicalDocumentSectionTypeCodes()
	 */
	@Override
	public List<LookupDto> findAllClinicalDocumentSectionTypeCodes() {

		List<LookupDto> lookups = new ArrayList<LookupDto>();

		List<ClinicalDocumentSectionTypeCode> clinicalDocumentSectionTypeCodeList = clinicalDocumentSectionTypeCodeRepository
				.findAll();

		for (ClinicalDocumentSectionTypeCode entity : clinicalDocumentSectionTypeCodeList) {
			lookups.add(modelMapper.map(entity, LookupDto.class));
		}
		return lookups;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.reference.
	 * ClinicalDocumentSectionTypeCodeService
	 * #findAllClinicalDocumentSectionTypeCodesAddConsentFieldsDto()
	 */
	@Override
	public List<AddConsentFieldsDto> findAllClinicalDocumentSectionTypeCodesAddConsentFieldsDto() {
		List<AddConsentFieldsDto> clinicalDocumentSectionTypeDto = new ArrayList<AddConsentFieldsDto>();
		List<ClinicalDocumentSectionTypeCode> clinicalDocumentSectionTypeCodeList = clinicalDocumentSectionTypeCodeRepository
				.findAll();
		for (ClinicalDocumentSectionTypeCode clinicalDocumentSectionTypeCode : clinicalDocumentSectionTypeCodeList) {
			AddConsentFieldsDto clinicalDocumentSectionTypeItem = new AddConsentFieldsDto();
			clinicalDocumentSectionTypeItem
					.setCode(clinicalDocumentSectionTypeCode.getCode());
			clinicalDocumentSectionTypeItem
					.setDisplayName(clinicalDocumentSectionTypeCode
							.getDisplayName());
			clinicalDocumentSectionTypeDto.add(clinicalDocumentSectionTypeItem);
		}
		return clinicalDocumentSectionTypeDto;
	}
}
