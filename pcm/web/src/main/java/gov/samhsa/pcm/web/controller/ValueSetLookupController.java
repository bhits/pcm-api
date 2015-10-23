package gov.samhsa.pcm.web.controller;

import gov.samhsa.pcm.common.UserContext;
import gov.samhsa.pcm.service.dto.ValueSetLookUpDto;
import gov.samhsa.pcm.service.dto.ValueSetQueryDto;
import gov.samhsa.pcm.service.dto.ValueSetQueryListDto;
import gov.samhsa.pcm.service.valueset.CodeSystemVersionNotFoundException;
import gov.samhsa.pcm.service.valueset.ConceptCodeNotFoundException;
import gov.samhsa.pcm.service.valueset.ValueSetLookupService;
import gov.samhsa.pcm.service.valueset.ValueSetNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sysadmin")
public class ValueSetLookupController {
	protected static final String REQUEST_MAPPING_LIST = "/lookup";
	protected static final String REDIRECT_MAPPING_LIST = "/lookupService";
	protected static final String MULTIPLE_VALUESET_LOOKUP = "/lookupService/multipleValueset";
	protected static final String REST_MAPPING_LIST = "/lookupService/rest";
	protected static final String LOOKUP_GET = "/lookup/get";
	protected static final String MODEL_ATTIRUTE_LOOKUPDTO = "lookupDto";

	protected static final String VALUESET_LOOKUP_FORM_VIEW = "views/sysadmin/lookup";
	protected static final String ERROR_MESSAGE_VALUSETS_NOT_FOUND = "Value Sets not Found. To create and manage concept codes, you need to add Value Sets.";
	protected static final String ERROR_MESSAGE_CODESYSTEMVERSIONS_NOT_FOUND = "Code System Versions not Found.";
	protected static final String ERROR_MESSAGE_CODESYSTEMS_NOT_FOUND = "Code System Versions not Found. To create and manage concept codes, you need to add Code System Versions.";
	protected static final String ERROR_MESSAGE_KEY_SELECTED_CODESYSTEM_WAS_NOT_FOUND = "Selected code system was not found.";
	protected static final String ERROR_MESSAGE_KEY_DUPLICATE_CODE_FOUND = "ConceptCode Already Exists";
	protected static final String ERROR_MESSAGE_KEY_SELECTED_VALUESET_WAS_NOT_FOUND = "Selected valueSet was not found.";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ValueSetLookupController.class);

	@Resource
	private ValueSetLookupService lookupService;

	@Autowired
	UserContext userContext;

	@RequestMapping(value = REQUEST_MAPPING_LIST, method = RequestMethod.GET)
	public String showLookupForm(Model model, HttpServletRequest request) {

		LOGGER.debug("Rendering Lookup Service page");

		model.addAttribute(VALUESET_LOOKUP_FORM_VIEW, new ValueSetLookUpDto());
		return VALUESET_LOOKUP_FORM_VIEW;
	}

	/**
	 * Processes create conceptCode requests.
	 * 
	 * @param model
	 * @return The name of the create conceptCode form view.
	 */
	@RequestMapping(value = LOOKUP_GET, method = RequestMethod.POST)
	public String lookupService(
			@ModelAttribute(MODEL_ATTIRUTE_LOOKUPDTO) ValueSetLookUpDto lookupDto,
			Model model) {
		try {
			lookupDto = lookupService.lookupValueSetCategories(lookupDto
					.getConceptCode().trim(), lookupDto.getCodeSystemOid()
					.trim());
		} catch (CodeSystemVersionNotFoundException e) {
			LOGGER.debug(e.getMessage());
			lookupDto.setError(true);
			lookupDto.setErrorMessage(e.getMessage());
		} catch (ConceptCodeNotFoundException e) {
			LOGGER.debug(e.getMessage());
			lookupDto.setError(true);
			lookupDto.setErrorMessage(e.getMessage());
		} catch (ValueSetNotFoundException e) {
			LOGGER.debug(e.getMessage());
			lookupDto.setError(true);
			lookupDto.setErrorMessage(e.getMessage());
		}
		model.addAttribute(MODEL_ATTIRUTE_LOOKUPDTO, lookupDto);

		return VALUESET_LOOKUP_FORM_VIEW;
	}

	/**
	 * Processes create conceptCode requests.
	 * 
	 * @param model
	 * @return The name of the create conceptCode form view.
	 */
	@RequestMapping(value = REDIRECT_MAPPING_LIST, method = RequestMethod.GET)
	public @ResponseBody ValueSetQueryDto lookupValuesetCategories(
			@RequestParam(value = "code", required = false, defaultValue = "296.8") String code,
			@RequestParam(value = "codeSystemOid", required = false, defaultValue = "2.16.840.1.113883.6.90") String codeSystemOid,
			Model model) {

		LOGGER.debug("Rendering Concept Code list page");
		ValueSetQueryDto valueSetQueryDto = new ValueSetQueryDto();
		try {
			valueSetQueryDto.setCodeSystemOid(codeSystemOid);
			valueSetQueryDto.setConceptCode(code);
			valueSetQueryDto = lookupService.RestfulValueSetCategories(
					code.trim(), codeSystemOid.trim());
		} catch (CodeSystemVersionNotFoundException e) {
			LOGGER.debug(e.getMessage());
		} catch (ConceptCodeNotFoundException e) {
			LOGGER.debug(e.getMessage());
		} catch (ValueSetNotFoundException e) {
			LOGGER.debug(e.getMessage());
		}

		return valueSetQueryDto;
	}

	@RequestMapping(value = MULTIPLE_VALUESET_LOOKUP, method = RequestMethod.GET)
	public @ResponseBody List<ValueSetQueryDto> lookupValuesetCategoriesOfMultipleCodesAndCodeSystemSet(
			@RequestParam(value = "code:codeSystemOid", required = false, defaultValue = "296.8:2.16.840.1.113883.6.90") List<String> codeAndCodeSystemPairList,
			Model model) {
		List<ValueSetQueryDto> valueSetQueryDtoList = new ArrayList<ValueSetQueryDto>();
		for (String codeAndCodeSystemPair : codeAndCodeSystemPairList) {
			List<String> spilitedCodeAndCodeSystem = new ArrayList<String>(
					Arrays.asList(codeAndCodeSystemPair.split(":")));
			if (spilitedCodeAndCodeSystem.size() != 2)
				continue;
			String code = spilitedCodeAndCodeSystem.get(0);
			String codeSystemOid = spilitedCodeAndCodeSystem.get(1);
			LOGGER.debug("Rendering Concept Code list page");
			ValueSetQueryDto valueSetQueryDto = new ValueSetQueryDto();
			try {
				valueSetQueryDto.setCodeSystemOid(codeSystemOid);
				valueSetQueryDto.setConceptCode(code);
				valueSetQueryDto = lookupService.RestfulValueSetCategories(
						code.trim(), codeSystemOid.trim());
			} catch (CodeSystemVersionNotFoundException e) {
				LOGGER.debug(e.getMessage());
			} catch (ConceptCodeNotFoundException e) {
				LOGGER.debug(e.getMessage());
			} catch (ValueSetNotFoundException e) {
				LOGGER.debug(e.getMessage());
			}
			valueSetQueryDtoList.add(valueSetQueryDto);
		}

		return valueSetQueryDtoList;
	}

	/**
	 * Processes create conceptCode requests.
	 * 
	 * @param model
	 * @return The name of the create conceptCode form view.
	 */
	@RequestMapping(value = REST_MAPPING_LIST, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ValueSetQueryListDto lookupValuesetCategoriesList(
			@RequestBody ValueSetQueryListDto valueSetQueryListDtos) {

		LOGGER.debug("Rendering Concept Code list page");
		// Set<ValueSetQueryDto> valueSetQueryDtos =
		// valueSetQueryListDtos.getValueSetQueryDtos();
		try {
			valueSetQueryListDtos = lookupService
					.RestfulValueSetCategories(valueSetQueryListDtos);
		} catch (CodeSystemVersionNotFoundException e) {
			LOGGER.debug(e.getMessage());
		} catch (ConceptCodeNotFoundException e) {
			LOGGER.debug(e.getMessage());
		} catch (ValueSetNotFoundException e) {
			LOGGER.debug(e.getMessage());
		}

		return valueSetQueryListDtos;
	}

}
