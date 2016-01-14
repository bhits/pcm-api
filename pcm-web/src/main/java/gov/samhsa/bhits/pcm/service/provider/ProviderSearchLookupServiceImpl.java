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
package gov.samhsa.bhits.pcm.service.provider;


import gov.samhsa.bhits.pcm.service.dto.LookupDto;
import gov.samhsa.bhits.pcm.service.reference.StateCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * The Class ProviderSearchLookupServiceImpl.
 */
@Transactional
public class ProviderSearchLookupServiceImpl implements
        ProviderSearchLookupService {

    /**
     * The logger.
     */
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * The provider search url.
     */
    String providerSearchURL;

    /**
     * The state code service.
     */
    protected StateCodeService stateCodeService;

    /**
     * Instantiates a new provider search lookup service impl.
     *
     * @param providerSearchURL the provider search url
     * @param stateCodeService  the state code service
     */
    public ProviderSearchLookupServiceImpl(String providerSearchURL,
                                           StateCodeService stateCodeService) {
        super();
        this.providerSearchURL = providerSearchURL;
        this.stateCodeService = stateCodeService;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.provider.ProviderSearchLookupService
     * #getProviderSearchURL()
     */
    @Override
    public String getProviderSearchURL() {
        return providerSearchURL;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.provider.ProviderSearchLookupService
     * #setProviderSearchURL(java.lang.String)
     */
    @Override
    public void setProviderSearchURL(String providerSearchURL) {
        this.providerSearchURL = providerSearchURL;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.provider.ProviderSearchLookupService
     * #providerSearch(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, int)
     */
    @Override
    public String providerSearch(String usstate, String city, String zipcode,
                                 String gender, String specialty, String phone, String firstname,
                                 String lastname, String facilityName, int pageNumber) {
        // check the rule
        String query = generateProviderSearchURL(usstate, city, zipcode,
                gender, specialty, phone, firstname, lastname, facilityName,
                pageNumber).replace(" ", "%20");

        StringBuilder output = new StringBuilder();

        output.append(callProviderSearch(query));

        return output.toString().replace("]}{\"providers\":[", "");
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.provider.ProviderSearchLookupService
     * #providerSearch(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, int)
     */
    @Override
    public String providerSearch(String usstate, String city, String zipcode,
                                 String gender, String specialty, String phone, String firstname,
                                 String lastnameOrFacilityName, int pageNumber) {
        // check the rule
        String query = generateProviderSearchURL(usstate, city, zipcode,
                gender, specialty, phone, firstname, lastnameOrFacilityName,
                pageNumber).replace(" ", "%20");

        StringBuilder output = new StringBuilder();

        output.append(callProviderSearch(query));

        return output.toString().replace("]}{\"providers\":[", "");
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.provider.ProviderSearchLookupService
     * #providerSearchByNpi(java.lang.String)
     */
    @Override
    public String providerSearchByNpi(String npi) {
        StringBuffer query = new StringBuffer(getProviderSearchURL());
        query.append("/pageNumber/0/").append(npi);
        return callProviderSearch(query.toString());
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.provider.ProviderSearchLookupService
     * #callProviderSearch(java.lang.String)
     */
    @Override
    public String callProviderSearch(String query) {
        StringBuilder output = new StringBuilder();
        try {
            URL queryURL;
            queryURL = new URL(query);
            logger.info("Start calling " + queryURL + "...");
            URLConnection urlConnection = queryURL.openConnection();
            logger.info("Success.");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                output.append(inputLine);
            in.close();
        } catch (Exception e) {
            logger.warn("Error while calling provider search...");
            logger.warn("The exception is", e);
        }
        return output.toString();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.provider.ProviderSearchLookupService
     * #generateProviderSearchURL(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    public String generateProviderSearchURL(String usstate, String city,
                                            String zipcode, String gender, String specialty, String phone,
                                            String firstname, String lastname, String facilityName,
                                            int pageNumber) {
        StringBuffer query = new StringBuffer(getProviderSearchURL());

        query.append("/pageNumber/").append(pageNumber);

        if (usstate != null)
            query.append("/usstate/").append(usstate);
        if (city != null)
            query.append("/city/").append(city);
        if (zipcode != null)
            query.append("/zipcode/").append(zipcode);
        if (gender != null)
            query.append("/gender/").append(gender);
        if (specialty != null)
            query.append("/specialty/").append(specialty);
        if (phone != null)
            query.append("/phone/").append(phone);
        if (firstname != null)
            query.append("/firstname/").append(firstname);
        if (lastname != null)
            query.append("/lastname/").append(lastname);
        if (facilityName != null)
            query.append("/facilityname/").append(facilityName);

        return query.toString();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.provider.ProviderSearchLookupService
     * #generateProviderSearchURL(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, int)
     */
    @Override
    public String generateProviderSearchURL(String usstate, String city,
                                            String zipcode, String gender, String specialty, String phone,
                                            String firstname, String lastnameOrFacilityName, int pageNumber) {
        StringBuffer query = new StringBuffer(getProviderSearchURL());

        query.append("/pageNumber/").append(pageNumber);

        if (usstate != null)
            query.append("/usstate/").append(usstate);
        if (city != null)
            query.append("/city/").append(city);
        if (zipcode != null)
            query.append("/zipcode/").append(zipcode);
        if (gender != null)
            query.append("/gender/").append(gender);
        if (specialty != null)
            query.append("/specialty/").append(specialty);
        if (phone != null)
            query.append("/phone/").append(phone);
        if (firstname != null)
            query.append("/firstname/").append(firstname);
        if (lastnameOrFacilityName != null)
            query.append("/lastnameOrFacilityName/").append(
                    lastnameOrFacilityName);
        return query.toString();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.provider.ProviderSearchLookupService
     * #isValidatedSearch(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    public boolean isValidatedSearch(String usstate, String city,
                                     String zipcode, String gender, String specialty, String phone,
                                     String firstname, String lastname, String facilityName) {
        boolean validateCall = true;
        if (usstate == null && city == null && zipcode == null && phone == null
                && specialty == null && firstname == null && lastname == null
                && gender == null)
            validateCall = false;
        if (usstate != null && zipcode != null)
            validateCall = false;
        if (usstate != null) {
            if (usstate.matches("[a-zA-Z][a-zA-Z]") == true) {

                // Get a list of all valid US states from database via
                // stateCodeService service
                List<LookupDto> stateCodes = stateCodeService
                        .findAllStateCodes();
                boolean isValidStateCode = false;

                // Loop through list of valid state codes and check if usstate
                // value
                // matches a valid state
                for (LookupDto stateCode : stateCodes) {
                    if (usstate.compareToIgnoreCase(stateCode.getCode()) == 0) {
                        isValidStateCode = true;
                        break;
                    }
                }

                // If no state in the list matched the input value for usstate,
                // then
                // set validateCall to false
                if (isValidStateCode != true) {
                    validateCall = false;
                }
            } else
                validateCall = false;
        }

        if (city != null && city.length() < 3)
            validateCall = false;
        if (zipcode != null && zipcode.length() < 5)
            validateCall = false;
        if (specialty != null && specialty.length() < 3)
            validateCall = false;
        if (phone != null && phone.length() != 10)
            validateCall = false;
        if (firstname != null && firstname.length() < 2)
            validateCall = false;
        if (lastname != null && lastname.length() < 2)
            validateCall = false;
        if (city != null && usstate == null)
            validateCall = false;

        return validateCall;
    }
}
