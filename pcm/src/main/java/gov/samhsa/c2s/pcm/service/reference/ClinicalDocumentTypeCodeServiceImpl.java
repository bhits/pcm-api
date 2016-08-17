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
package gov.samhsa.c2s.pcm.service.reference;

import gov.samhsa.c2s.vss.service.dto.AddConsentFieldsDto;
import gov.samhsa.c2s.pcm.domain.reference.ClinicalDocumentTypeCode;
import gov.samhsa.c2s.pcm.domain.reference.ClinicalDocumentTypeCodeRepository;
import gov.samhsa.c2s.pcm.service.dto.LookupDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class ClinicalDocumentTypeCodeServiceImpl.
 */
@Transactional
@Service
public class ClinicalDocumentTypeCodeServiceImpl implements
        ClinicalDocumentTypeCodeService {

    /** The clinical document type code repository. */
    @Autowired
    private ClinicalDocumentTypeCodeRepository clinicalDocumentTypeCodeRepository;

    /** The model mapper. */
    @Autowired
    private ModelMapper modelMapper;

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.reference.ClinicalDocumentTypeCodeService
     * #findAllClinicalDocumentTypeCodes()
     */
    @Override
    public List<LookupDto> findAllClinicalDocumentTypeCodes() {
        List<LookupDto> lookups = new ArrayList<LookupDto>();

        List<ClinicalDocumentTypeCode> clinicalDocumentTypeCodeList = clinicalDocumentTypeCodeRepository
                .findAll();

        for (ClinicalDocumentTypeCode entity : clinicalDocumentTypeCodeList) {
            lookups.add(modelMapper.map(entity, LookupDto.class));
        }
        return lookups;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.reference.ClinicalDocumentTypeCodeService
     * #findAllClinicalDocumentTypeCodesAddConsentFieldsDto()
     */
    @Override
    public List<AddConsentFieldsDto> findAllClinicalDocumentTypeCodesAddConsentFieldsDto() {
        List<AddConsentFieldsDto> clinicalDocumentTypeDto = new ArrayList<AddConsentFieldsDto>();
        List<ClinicalDocumentTypeCode> clinicalDocumentTypeCodeList = clinicalDocumentTypeCodeRepository
                .findAll();
        for (ClinicalDocumentTypeCode clinicalDocumentTypeCode : clinicalDocumentTypeCodeList) {
            AddConsentFieldsDto clinicalDocumentTypeItem = new AddConsentFieldsDto();
            clinicalDocumentTypeItem
                    .setCode(clinicalDocumentTypeCode.getCode());
            clinicalDocumentTypeItem.setDisplayName(clinicalDocumentTypeCode
                    .getDisplayName());
            clinicalDocumentTypeDto.add(clinicalDocumentTypeItem);
        }
        return clinicalDocumentTypeDto;
    }
}
