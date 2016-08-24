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
package gov.samhsa.c2s.pcm.service.consent;

import gov.samhsa.c2s.pcm.domain.consent.ConsentTermsVersions;
import gov.samhsa.c2s.pcm.domain.consent.ConsentTermsVersionsRepository;
import gov.samhsa.c2s.pcm.service.exception.ConsentTermsVersionNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The Class ConsentServiceImpl.
 */
@Transactional
@Service
public class ConsentTermsVersionsServiceImpl implements ConsentTermsVersionsService {

    @Autowired
    private ConsentTermsVersionsRepository consentTermsVersionsRepository;

    public ConsentTermsVersions getEnabledConsentTermsVersion(){
        List<ConsentTermsVersions> consentRevocationTermsVersionsList = consentTermsVersionsRepository.findAllByVersionDisabledOrderByAddedDateTimeDesc(false);
        if(consentRevocationTermsVersionsList.size() > 0){
            return consentRevocationTermsVersionsList.get(0);
        }else {
            throw new ConsentTermsVersionNotFoundException("No active ConsentTermsVersions record found in database");
        }
    };

}
