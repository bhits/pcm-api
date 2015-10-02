package gov.samhsa.acs.polrep.client;

import gov.samhsa.acs.polrep.client.dto.PolicyContainerDto;
import gov.samhsa.acs.polrep.client.dto.PolicyContentContainerDto;
import gov.samhsa.acs.polrep.client.dto.PolicyContentDto;
import gov.samhsa.acs.polrep.client.dto.PolicyDto;
import gov.samhsa.acs.polrep.client.dto.PolicyMetadataContainerDto;
import gov.samhsa.acs.polrep.client.dto.PolicyMetadataDto;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

public class PolRepRestClient {
	public static final String DEFAULT_ENCODING = "UTF-8";
	public static final String DEFAULT_WILDCARD = "*";

	private static final String PARAM_NAME_WILDCARD = "wildcard";
	private static final String PARAM_NAME_FORCE = "force";
	private static final String PARAM_NAME_POLICY_SET_ID = "policySetId";
	private static final String PARAM_NAME_POLICY_COMBINING_ALG_ID = "policyCombiningAlgId";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final String scheme;
	private final String host;
	private final int port;
	private final String context;
	private final String version;

	private HttpHeaders httpHeaders;
	private RestTemplate restTemplate;

	public PolRepRestClient(String scheme, String host, int port,
			String context, String version) {
		super();
		this.scheme = scheme;
		this.host = host;
		this.port = port;
		this.context = context;
		this.version = version;
		init();
	}

	public PolicyMetadataContainerDto addPolicies(
			PolicyContentContainerDto addPolicyRequestContainerDto,
			boolean force) {
		final String apiPath = Paths.POLICIES.getPath();
		final List<NameValuePair> parameters = NVPBuilder.withParam(
				PARAM_NAME_FORCE, Boolean.toString(force)).build();

		return post(apiPath, parameters,
				httpEntity(addPolicyRequestContainerDto),
				PolicyMetadataContainerDto.class);
	}

	public void deletePolicy(String policyId) {
		final String apiPath = Paths.POLICIES_WITH_PARAM.getPath(policyId);
		delete(apiPath);
	}

	public PolicyContainerDto getPolicies(String policyId, String wildcard) {
		final String apiPath = Paths.POLICIES_WITH_PARAM.getPath(policyId);
		final List<NameValuePair> parameters = wildcard != null ? NVPBuilder
				.withParam(PARAM_NAME_WILDCARD, wildcard).build() : null;

		return get(apiPath, parameters, PolicyContainerDto.class);
	}

	public PolicyDto getPoliciesCombinedAsPolicySet(String policyId,
			String wildcard, String policySetId,
			PolicyCombiningAlgIds policyCombiningAlgId) {
		final String apiPath = Paths.POLICIES_COMBINED_WITH_PARAM
				.getPath(policyId);
		final NVPBuilder nvpBuilder = NVPBuilder.withParam(
				PARAM_NAME_POLICY_SET_ID, policySetId).and(
				PARAM_NAME_POLICY_COMBINING_ALG_ID,
				policyCombiningAlgId.getUrn());
		if (wildcard != null) {
			nvpBuilder.and(PARAM_NAME_WILDCARD, wildcard);
		}
		final List<NameValuePair> parameters = nvpBuilder.build();

		return get(apiPath, parameters, PolicyDto.class);
	}

	public PolicyDto getPolicy(String policyId) {
		return getPolicies(policyId, null).getPolicies().get(0);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getPolicyCombiningAlgIds() {
		final String apiPath = Paths.POLICY_COMBINING_ALG_IDS.getPath();
		return get(apiPath, null, Map.class);
	}

	public PolicyMetadataDto updatePolicy(
			PolicyContentDto updatePolicyRequestDto, String policyId) {
		final String apiPath = Paths.POLICIES_WITH_PARAM.getPath(policyId);
		return put(apiPath, httpEntity(updatePolicyRequestDto),
				PolicyMetadataDto.class);
	}

	private URI buildURI(final String apiPath) {
		return buildURI(apiPath, null);
	}

	private URI buildURI(final String apiPath,
			final List<NameValuePair> parameters) {
		URI url;
		final String path = getPath(apiPath);
		final URIBuilder builder = newURIBuilder();
		builder.setPath(path);
		if (parameters != null && parameters.size() > 0) {
			builder.addParameters(parameters);
		}
		try {
			url = builder.build();
		} catch (final URISyntaxException e) {
			throw new RuntimeException(e);
		}
		return url;
	}

	private void delete(String apiPath) {
		final URI url = buildURI(apiPath);
		try {
			restTemplate.exchange(url, HttpMethod.DELETE, httpEntity(),
					Object.class);
		} catch (final HttpStatusCodeException e) {
			logger.error(e.getMessage(), e);
			logger.error(e.getResponseBodyAsString());
			throw e;
		}
	}

	private <R> R get(String apiPath, List<NameValuePair> parameters,
			Class<R> responseType) {
		final URI url = buildURI(apiPath, parameters);
		try {
			return restTemplate.exchange(url, HttpMethod.GET, httpEntity(),
					responseType).getBody();
		} catch (final HttpStatusCodeException e) {
			logger.error(e.getMessage(), e);
			logger.error(e.getResponseBodyAsString());
			throw e;
		}
	}

	private String getPath(String apiPath) {
		final StringBuilder pathBuilder = new StringBuilder();
		if (!this.context.startsWith("/")) {
			pathBuilder.append("/");
		}
		pathBuilder.append(this.context);
		pathBuilder.append("/rest/");
		pathBuilder.append(this.version);
		pathBuilder.append(apiPath);
		return pathBuilder.toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private HttpEntity httpEntity() {
		Assert.notNull(this.httpHeaders, "HttpHeaders is not initialized!");
		return new HttpEntity(this.httpHeaders);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T> HttpEntity<T> httpEntity(T request) {
		Assert.notNull(this.httpHeaders, "HttpHeaders is not initialized!");
		return new HttpEntity(request, this.httpHeaders);
	}

	private void init() {
		Assert.hasText(this.scheme, "Scheme is not passed!");
		Assert.hasText(this.host, "Host is not passed!");
		Assert.isTrue(this.port > 0, "Port is not passed!");
		Assert.hasText(this.context, "Context is not passed!");
		Assert.hasText(this.version, "Version is not passed!");

		initRestTemplate();
		initHttpHeaders();
	}

	private void initHttpHeaders() {
		this.httpHeaders = new HttpHeaders();
		this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		this.httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		this.httpHeaders.setAcceptCharset(Arrays.asList(Charset
				.forName(DEFAULT_ENCODING)));
	}

	private void initRestTemplate() {
		this.restTemplate = new RestTemplate();
		final List<HttpMessageConverter<?>> messageConverters = restTemplate
				.getMessageConverters();
		// Create a list for the message converters
		final List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>(
				messageConverters);
		final MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		// Add the Jackson Message converter
		converters.add(jsonConverter);
		this.restTemplate.setMessageConverters(converters);
	}

	private URIBuilder newURIBuilder() {
		final URIBuilder builder = new URIBuilder();
		builder.setScheme(this.scheme);
		builder.setHost(this.host);
		builder.setPort(this.port);
		return builder;
	}

	private <R> R post(String apiPath, List<NameValuePair> parameters,
			HttpEntity<Object> request, Class<R> responseType) {
		final URI url = buildURI(apiPath, parameters);
		try {
			return restTemplate.postForObject(url, request, responseType);
		} catch (final HttpStatusCodeException e) {
			logger.error(e.getMessage(), e);
			logger.error(e.getResponseBodyAsString());
			throw e;
		}
	}

	private <R> R put(String apiPath, HttpEntity<Object> request,
			Class<R> responseType) {
		final URI url = buildURI(apiPath);
		try {
			return restTemplate.exchange(url, HttpMethod.PUT, request,
					responseType).getBody();
		} catch (final HttpStatusCodeException e) {
			logger.error(e.getMessage(), e);
			logger.error(e.getResponseBodyAsString());
			throw e;
		}
	}

}
