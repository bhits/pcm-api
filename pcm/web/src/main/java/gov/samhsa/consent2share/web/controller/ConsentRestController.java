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
package gov.samhsa.consent2share.web.controller;

import gov.samhsa.consent2share.service.consent.ConsentService;
import gov.samhsa.consent2share.service.dto.AddConsentFieldsDto;
import gov.samhsa.consent2share.service.dto.ConsentListDto;
import gov.samhsa.consent2share.service.notification.NotificationService;
import gov.samhsa.consent2share.service.patient.PatientService;
import gov.samhsa.consent2share.service.reference.PurposeOfUseCodeService;
import gov.samhsa.consent2share.service.valueset.MedicalSectionService;
import gov.samhsa.consent2share.service.valueset.ValueSetCategoryService;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class ConsentRestController.
 */
@RestController
@RequestMapping("/patients")
public class ConsentRestController extends AbstractController {

	public final static Long SAMPLE_C32_ID = new Long(-1);

	/** The consent service. */
	@Autowired
	private ConsentService consentService;

	/** The patient service. */
	@Autowired
	private PatientService patientService;

	/** The clinical document section type code service. */
	@Autowired
	private MedicalSectionService medicalSectionServiceImpl;

	/** The purpose of use code service. */
	@Autowired
	private PurposeOfUseCodeService purposeOfUseCodeService;

	@Autowired
	NotificationService notificationService;

	/** The value set category service. */
	@Autowired
	private ValueSetCategoryService valueSetCategoryService;

	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The C32_ do c_ code. */
	public final String C32_DOC_CODE = "34133-9";
	
	
	private String  username="albert.smith";
	
	
	
	@RequestMapping(value = "consents/pageNumber/{pageNumber}")
	public List<ConsentListDto> listConsents(@PathVariable("pageNumber") String pageNumber) {

		final List<ConsentListDto> consentListDtos = consentService
				.findAllConsentsDtoByPatient(patientService
						.findIdByUsername(username));

		return consentListDtos;
	}
	
	@RequestMapping(value = "purposeOfUse")
	public List<AddConsentFieldsDto> purposeOfUseLookup() {

		List<AddConsentFieldsDto> purposeOfUseDto = purposeOfUseCodeService
				.findAllPurposeOfUseCodesAddConsentFieldsDto();
		 return purposeOfUseDto;
	}
	
	
	@RequestMapping(value = "medicalSection")
	public List<AddConsentFieldsDto> medicalSectionLookup() {

		List<AddConsentFieldsDto> medicalSectionDtos = medicalSectionServiceImpl
				.findAllMedicalSectionsAddConsentFieldsDto();
		 return medicalSectionDtos;
	}
	
	
	@RequestMapping(value = "sensitivityPolicy")
	public List<AddConsentFieldsDto> sensitivityPolicyLookup() {

		List<AddConsentFieldsDto> sensitivityPolicyDtos =valueSetCategoryService
				.findAllValueSetCategoriesAddConsentFieldsDto();
		 return sensitivityPolicyDtos;
	}
	
	
	
	

	
}
