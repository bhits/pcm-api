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
package gov.samhsa.acs.documentsegmentation.valueset;

import gov.samhsa.acs.documentsegmentation.valueset.dto.CodeAndCodeSystemSetDto;
import gov.samhsa.acs.documentsegmentation.valueset.dto.ValueSetQueryDto;
import gov.samhsa.acs.documentsegmentation.valueset.dto.ValueSetQueryListDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * The Class ValueSetServiceImpl.
 */
public class ValueSetServiceImpl implements ValueSetService {

	/** The Constant PARAM_CODE. */
	public static final String PARAM_CODE = "code";

	/** The Constant PARAM_CODE_SYSTEM. */
	public static final String PARAM_CODE_SYSTEM = "codeSystemOid";
	public static final String PARAM_CODE_AND_CODESYSTEM_PAIR = "code:codeSystemOid";

	/** The endpoint address. */
	private String endpointAddress;

	private String singleCodeRestUrl;
	
	private String multipleCodeRestUrl;

	/** The self signed ssl helper. */
	private SelfSignedSSLHelper selfSignedSSLHelper;

	/**
	 * Instantiates a new value set service impl.
	 */
	public ValueSetServiceImpl() {
		super();
	}

	/**
	 * Instantiates a new value set service impl.
	 * 
	 * @param endpointAddress
	 *            the endpoint address
	 */
	public ValueSetServiceImpl(String endpointAddress) {
		super();
		this.endpointAddress = endpointAddress;
		this.singleCodeRestUrl = null;
		this.multipleCodeRestUrl = null;
		this.selfSignedSSLHelper = null;
	}

	/**
	 * Instantiates a new value set service impl.
	 * 
	 * @param endpointAddress
	 *            the endpoint address
	 * @param selfSignedSSLHelper
	 *            the self signed ssl helper
	 */
	public ValueSetServiceImpl(String endpointAddress,
			SelfSignedSSLHelper selfSignedSSLHelper) {
		super();
		this.endpointAddress = endpointAddress;
		this.singleCodeRestUrl = null;
		this.selfSignedSSLHelper = selfSignedSSLHelper;

		if (selfSignedSSLHelper != null) {
			selfSignedSSLHelper.trustSelfSignedSSL();
		}		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.documentsegmentation.valueset.ValueSetService#
	 * lookupValueSetCategories(java.lang.String, java.lang.String)
	 */
	@Override
	public Set<String> lookupValueSetCategories(String code, String codeSystem) {
		if (singleCodeRestUrl == null) {
			initRestUrl();
		}

		Map<String, String> parameterMap = createParameterMap(code, codeSystem);

		RestTemplate restTemplate = configureRestTemplate();
/*
		if (selfSignedSSLHelper != null) {
			selfSignedSSLHelper.trustSelfSignedSSL();
		}*/

		ValueSetQueryDto resp = restTemplate.getForObject(singleCodeRestUrl,
				ValueSetQueryDto.class, parameterMap);

		return resp.getVsCategoryCodes();
	}
	
	@Override
	public List<Map<String, Object>> lookupValuesetCategoriesOfMultipleCodeAndCodeSystemSet(List<CodeAndCodeSystemSetDto> codeAndCodeSystemSetDtoList){
		List<Map<String, Object>> resp=new ArrayList<Map<String, Object>>();
		String restURL;
		
		StringBuilder builder;
		Iterator<CodeAndCodeSystemSetDto> iterator=codeAndCodeSystemSetDtoList.iterator();
		while(iterator.hasNext()) {
			builder=new StringBuilder(endpointAddress+"/multipleValueset?");
			while (iterator.hasNext()) {
				CodeAndCodeSystemSetDto codeAndCodeSystemSetDto=iterator.next();
				builder.append("code:codeSystemOid="+codeAndCodeSystemSetDto.getConceptCode()+":"+codeAndCodeSystemSetDto.getCodeSystemOid());
				
				if(builder.length()>1500)
					break;
				
				if (iterator.hasNext()) {
					builder.append("&");
				}
			}
			restURL=builder.toString();
			RestTemplate restTemplate = configureRestTemplate();
			resp.addAll(restTemplate.getForObject(restURL,List.class));
		}
		return resp;
	}
	
//	private String createMultipleCodeRestUrl(List<CodeAndCodeSystemSetDto> codeAndCodeSystemSetDtoList) {
//		
//	}

	@Override
	public ValueSetQueryListDto RestfulValueSetCategories(
			ValueSetQueryListDto valueSetQueryListDtos) {
	
		RestTemplate restTemplate = configureRestTemplate();

		if (selfSignedSSLHelper != null) {
			selfSignedSSLHelper.trustSelfSignedSSL();
		}

		ValueSetQueryListDto resp = null;
		try {
			resp = restTemplate.postForObject(endpointAddress + "/rest/",valueSetQueryListDtos,
					ValueSetQueryListDto.class);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resp;
	}
	
	
	
	
	/**
	 * Configures the rest template.
	 * 
	 * @return the rest template
	 */
	RestTemplate configureRestTemplate() {

		RestTemplate restTemplate = new RestTemplate();
		
		List<HttpMessageConverter<?>> messageConverters = restTemplate
				.getMessageConverters();
		
		//Create a list for the message converters
		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>(
				messageConverters);
		
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		
		//Add the Jackson Message converter
		converters.add(jsonConverter);
		
		restTemplate.setMessageConverters(converters);

		return restTemplate;
	}

	/**
	 * Creates the parameter map.
	 * 
	 * @param code
	 *            the code
	 * @param codeSystem
	 *            the code system
	 * @return the map
	 */
	private Map<String, String> createParameterMap(String code,
			String codeSystem) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put(PARAM_CODE, code);
		paramMap.put(PARAM_CODE_SYSTEM, codeSystem);
		return paramMap;
	}
	
	private Map<String, String> createParameterMapForMultipleValueSet(List<CodeAndCodeSystemSetDto> codeAndCodeSystemSetDtoList) {
		Map<String, String> paramMap = new HashMap<String, String>();
		for(CodeAndCodeSystemSetDto codeAndCodeSystemSetDto:codeAndCodeSystemSetDtoList) {
			paramMap.put(PARAM_CODE_AND_CODESYSTEM_PAIR, codeAndCodeSystemSetDto.getConceptCode()+":"+codeAndCodeSystemSetDto.getCodeSystemOid());
		}
		return paramMap;
	}

	/**
	 * Initializes the rest url.
	 */
	private void initRestUrl() {
		StringBuilder builder = new StringBuilder();
		builder.append(endpointAddress);
		builder.append("?");
		addParameter(builder, PARAM_CODE);
		builder.append("&");
		addParameter(builder, PARAM_CODE_SYSTEM);
		singleCodeRestUrl = builder.toString();
	}

	/**
	 * Adds the parameter.
	 * 
	 * @param builder
	 *            the builder
	 * @param param
	 *            the param
	 */
	private void addParameter(StringBuilder builder, String param) {
		builder.append(param);
		builder.append("={");
		builder.append(param);
		builder.append("}");
	}



}
