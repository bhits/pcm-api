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
package gov.samhsa.mhc.pcm.service.reference;

import gov.samhsa.mhc.pcm.domain.reference.PurposeOfUseCode;
import gov.samhsa.mhc.pcm.domain.reference.PurposeOfUseCodeRepository;
import gov.samhsa.mhc.vss.service.dto.AddConsentFieldsDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class PurposeOfUseCodeServiceImpl.
 */
@Transactional
public class PurposeOfUseCodeServiceImpl implements PurposeOfUseCodeService {

    /** The purpose of use code repository. */
    PurposeOfUseCodeRepository purposeOfUseCodeRepository;

    /**
     * Instantiates a new purpose of use code service impl.
     *
     * @param purposeOfUseCodeRepository
     *            the purpose of use code repository
     */
    public PurposeOfUseCodeServiceImpl(
            PurposeOfUseCodeRepository purposeOfUseCodeRepository) {
        super();
        this.purposeOfUseCodeRepository = purposeOfUseCodeRepository;
    }

    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.consent2share.service.reference.PurposeOfUseCodeService#
     * findAllPurposeOfUseCodesAddConsentFieldsDto()
     */
    @Override
    public List<AddConsentFieldsDto> findAllPurposeOfUseCodesAddConsentFieldsDto() {
        List<AddConsentFieldsDto> purposeOfUseDto = new ArrayList<AddConsentFieldsDto>();
        List<PurposeOfUseCode> purposeOfUse = purposeOfUseCodeRepository
                .findAll();
        for (PurposeOfUseCode purposeOfUseCode : purposeOfUse) {
            AddConsentFieldsDto purposeOfUseDtoItem = new AddConsentFieldsDto();
            purposeOfUseDtoItem.setCode(purposeOfUseCode.getCode());
            purposeOfUseDtoItem.setDisplayName(purposeOfUseCode
                    .getDisplayName());
            purposeOfUseDto.add(purposeOfUseDtoItem);
        }
        return purposeOfUseDto;
    }
}
