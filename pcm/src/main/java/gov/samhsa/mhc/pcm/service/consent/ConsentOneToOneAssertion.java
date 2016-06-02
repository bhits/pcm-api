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
package gov.samhsa.mhc.pcm.service.consent;

import gov.samhsa.mhc.pcm.service.dto.ConsentDto;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * The Class ConsentOneToOneAssertion.
 */
@Component
class ConsentOneToOneAssertion implements ConsentAssertion {

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.mhc.pcm.service.consent.ConsentAssertion#assertConsentDto
     * (gov.samhsa.mhc.pcm.service.dto.ConsentDto)
     */
    @Override
    public void assertConsentDto(ConsentDto consentDto)
            throws IllegalArgumentException {
        int countProvidersDisclosureIsMadeTo = 0;
        int countProvidersPermittedToDisclose = 0;
        countProvidersDisclosureIsMadeTo += getSize(consentDto, ConsentDto::getOrganizationalProvidersDisclosureIsMadeTo);
        countProvidersDisclosureIsMadeTo += getSize(consentDto, ConsentDto::getOrganizationalProvidersDisclosureIsMadeToNpi);
        countProvidersDisclosureIsMadeTo += getSize(consentDto, ConsentDto::getProvidersDisclosureIsMadeTo);
        countProvidersDisclosureIsMadeTo += getSize(consentDto, ConsentDto::getProvidersDisclosureIsMadeToNpi);
        countProvidersPermittedToDisclose += getSize(consentDto, ConsentDto::getOrganizationalProvidersPermittedToDisclose);
        countProvidersPermittedToDisclose += getSize(consentDto, ConsentDto::getOrganizationalProvidersPermittedToDiscloseNpi);
        countProvidersPermittedToDisclose += getSize(consentDto, ConsentDto::getProvidersPermittedToDisclose);
        countProvidersPermittedToDisclose += getSize(consentDto, ConsentDto::getProvidersPermittedToDiscloseNpi);

        Assert.isTrue(
                countProvidersDisclosureIsMadeTo == 1,
                "'countProvidersDisclosureIsMadeTo' must be 1 in order to be a one-to-one policy.");
        Assert.isTrue(
                countProvidersPermittedToDisclose == 1,
                "'countProvidersPermittedToDisclose' must be 1 in order to be a one-to-one policy.");
    }

    private int getSize(ConsentDto consentDto, Function<ConsentDto, Set> mapTo) {
        return Optional.of(consentDto)
                .map(mapTo)
                .map(Set::size)
                .orElse(0);
    }
}
