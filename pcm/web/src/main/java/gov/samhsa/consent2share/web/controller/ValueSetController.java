package gov.samhsa.consent2share.web.controller;

import gov.samhsa.consent2share.common.AuthenticatedUser;
import gov.samhsa.consent2share.common.UserContext;
import gov.samhsa.consent2share.service.dto.ValueSetCategoryDto;
import gov.samhsa.consent2share.service.dto.ValueSetDto;
import gov.samhsa.consent2share.service.dto.ValueSetVSCDto;
import gov.samhsa.consent2share.service.valueset.InvalidCSVException;
import gov.samhsa.consent2share.service.valueset.ValueSetCategoryNotFoundException;
import gov.samhsa.consent2share.service.valueset.ValueSetCategoryService;
import gov.samhsa.consent2share.service.valueset.ValueSetNotFoundException;
import gov.samhsa.consent2share.service.valueset.ValueSetService;
import gov.samhsa.consent2share.web.AjaxException;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * The Class ValueSetController.
 */
@Controller
@SessionAttributes("valueSet")
@RequestMapping("/sysadmin")
public class ValueSetController extends AbstractNodeController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ValueSetController.class);

	/** The Constant ERROR_MESSAGE_KEY_DELETED_VALUESET_WAS_NOT_FOUND. */
	protected static final String ERROR_MESSAGE_KEY_DELETED_VALUESET_WAS_NOT_FOUND = "Deleted valueSet was not found.";

	/** The Constant ERROR_MESSAGE_KEY_EDITED_VALUESET_WAS_NOT_FOUND. */
	protected static final String ERROR_MESSAGE_KEY_EDITED_VALUESET_WAS_NOT_FOUND = "Edited valueSet was not found.";

	/** The Constant ERROR_MESSAGE_VALUSETCATEGORIES_NOT_FOUND. */
	protected static final String ERROR_MESSAGE_VALUSETCATEGORIES_NOT_FOUND = "Value Set Categories not Found. To create and manage Value Sets, you need to add Value Set Categories.";

	protected static final String ERROR_MESSAGE_DUPLICATE_VALUESET = "Cannot insert duplicate value sets.";

	/** The Constant MODEL_ATTIRUTE_VALUESETDTO. */

	protected static final String MODEL_ATTIRUTE_VALUESETDTO = "valueSetDto";

	/** The Constant MODEL_ATTIRUTE_VALUESETVCSDTO. */
	protected static final String MODEL_ATTIRUTE_VALUESETVCSDTO = "valueSetVCSDto";

	/** The Constant MODEL_ATTRIBUTE_VALUESETDTOS. */
	protected static final String MODEL_ATTRIBUTE_VALUESETDTOS = "valueSetDtos";

	/** The Constant MODEL_ATTRIBUTE_VALUESETCATEGORYDTOS. */
	protected static final String MODEL_ATTRIBUTE_VALUESETCATEGORYDTOS = "valueSetCategoryDtos";

	/** The Constant VALUESET_ADD_FORM_VIEW. */
	protected static final String VALUESET_ADD_FORM_VIEW = "views/sysadmin/valueSetAdd";

	/** The Constant VALUESET_EDIT_FORM_VIEW. */
	protected static final String VALUESET_EDIT_FORM_VIEW = "views/sysadmin/valueSetEdit";

	/** The Constant VALUESET_LIST_VIEW. */
	protected static final String VALUESET_LIST_VIEW = "views/sysadmin/valueSetList";

	/** The Constant REQUEST_MAPPING_LIST. */
	protected static final String REQUEST_MAPPING_LIST = "/valueSetList";

	/** The Constant REDIRECT_MAPPING_LIST. */
	protected static final String REDIRECT_MAPPING_LIST = "../valueSetList";

	/** The Constant REDIRECT_ID_MAPPING_LIST. */
	protected static final String REDIRECT_ID_MAPPING_LIST = "../../valueSetList";

	/** The value set service. */
	@Resource
	private ValueSetService valueSetService;

	/** The value set category service. */
	@Resource
	private ValueSetCategoryService valueSetCategoryService;

	/** The user context. */
	@Autowired
	UserContext userContext;

	/**
	 * Processes create valueSet requests.
	 *
	 * @param model
	 *            the model
	 * @param request
	 *            the request
	 * @return The name of the create valueSet form view.
	 */
	@RequestMapping(value = REQUEST_MAPPING_LIST, method = RequestMethod.GET)
	public String showCreateValueSetForm(
			Model model,
			@RequestParam(value = "panelState", required = false, defaultValue = "") String panelState) {

		LOGGER.debug("Rendering Value Set  list page");

		Map<String, Object> valueSetsPagedMap = valueSetService.findAll(0);
		List<ValueSetCategoryDto> valueSetCategories = valueSetCategoryService
				.findAll();

		if (panelState.equals("resetoptions")) {
			model.addAttribute("panelState", "resetoptions");
		} else if (panelState.equals("addnew")) {
			model.addAttribute("panelState", "addnew");
		}

		model.addAttribute(MODEL_ATTRIBUTE_VALUESETCATEGORYDTOS,
				valueSetCategories);
		model.addAttribute("valueSetsPagedMap", valueSetsPagedMap);
		return VALUESET_LIST_VIEW;
	}

	@RequestMapping("/valueSet/ajaxGetPagedValueSets/pageNumber/{pageNumber}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Map<String, Object> getAllConceptCodes(
			@PathVariable("pageNumber") String pageNumber) {
		Map<String, Object> valueSetsMap = null;

		valueSetsMap = valueSetService.findAll(Integer.parseInt(pageNumber));

		return valueSetsMap;
	}

	/**
	 * Processes the submissions of create valueSet form.
	 *
	 * @param model
	 *            the model
	 * @param request
	 *            the request
	 * @return the string
	 */
	@RequestMapping(value = "valueSetAdd.html", method = RequestMethod.GET)
	public String valueSetAddForm(Model model, HttpServletRequest request) {
		LOGGER.debug("Rendering Value Set  Add Form ");
		String returnPage = VALUESET_ADD_FORM_VIEW;
		ValueSetVSCDto valueSetVSCDto = new ValueSetVSCDto();
		ValueSetDto valueSetDto = new ValueSetDto();

		try {
			valueSetVSCDto = valueSetService.create();
			valueSetDto = (ValueSetDto) model.asMap().get(
					MODEL_ATTIRUTE_VALUESETDTO);
			if (valueSetDto != null)
				valueSetVSCDto.setValueSetDto(valueSetDto);

		} catch (ValueSetCategoryNotFoundException e) {
			LOGGER.debug("No value sets found in the system");
			valueSetDto.setError(true);
			valueSetDto
					.setErrorMessage(ERROR_MESSAGE_VALUSETCATEGORIES_NOT_FOUND);
			returnPage = VALUESET_LIST_VIEW;
		}

		model.addAttribute(MODEL_ATTIRUTE_VALUESETDTO,
				(valueSetDto == null) ? new ValueSetDto() : valueSetDto);
		model.addAttribute(MODEL_ATTIRUTE_VALUESETVCSDTO, valueSetVSCDto);
		return returnPage;
	}

	/**
	 * Processes the submissions of create valueSet form.
	 *
	 * @param created
	 *            The information of the created valueSets.
	 * @param redirectAttribute
	 *            the redirect attribute
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "/valueSet/create", method = RequestMethod.POST)
	public String submitCreateValueSetForm(
			@Valid @ModelAttribute(MODEL_ATTIRUTE_VALUESETDTO) ValueSetDto created,
			RedirectAttributes redirectAttribute, Model model) {
		LOGGER.debug("Create valueSet form was submitted with information: "
				+ created);

		AuthenticatedUser currentUser = userContext.getCurrentUser();
		String notification = "";
		ValueSetDto valueSetDto;
		String path = "../valueSetAdd.html";
		try {
			created.setUserName(currentUser.getUsername());
			valueSetDto = valueSetService.create(created);
			created.setError(false);
			created.setSuccessMessage("Value Set   with code:"
					+ valueSetDto.getCode() + " and Name: "
					+ valueSetDto.getName() + " is Added Successfully");
			path = REDIRECT_MAPPING_LIST;
		} catch (DataIntegrityViolationException ex) {
			LOGGER.info(ex.getLocalizedMessage());
			Throwable t = ex.getCause();
			String message = null;
			if (t != null) {
				message = "Cause: " + t.getMessage();
			}
			created.setError(true);
			created.setErrorMessage("Value Set  is not Added " + message);
		} catch (ValueSetCategoryNotFoundException e) {
			LOGGER.debug("No value set category found with id: "
					+ created.getValueSetCategoryId());
			created.setError(true);
			created.setErrorMessage(ERROR_MESSAGE_VALUSETCATEGORIES_NOT_FOUND);
		}
		model.addAttribute(MODEL_ATTIRUTE_VALUESETDTO, created);
		redirectAttribute
				.addFlashAttribute(MODEL_ATTIRUTE_VALUESETDTO, created);
		model.addAttribute("notification", notification);

		String panelState = "?panelState=addnew";

		return createRedirectViewPath(path) + panelState;
	}

	/**
	 * Processes delete valueSet requests.
	 *
	 * @param id
	 *            The id of the deleted valueSet.
	 * @param redirectAttribute
	 *            the redirect attribute
	 * @return the string
	 */
	@RequestMapping(value = "/valueSet/delete/{id}", method = RequestMethod.DELETE)
	public @ResponseBody String delete(@PathVariable("id") Long id,
			RedirectAttributes redirectAttribute) {
		LOGGER.debug("Deleting valueSet with id: " + id);
		ValueSetDto deleted = new ValueSetDto();
		try {
			deleted = valueSetService.delete(id);
			deleted.setError(false);
			deleted.setSuccessMessage("ValueSet with Code: "
					+ deleted.getCode() + " and Name: " + deleted.getName()
					+ " is deleted Successfully.");
		} catch (ValueSetNotFoundException e) {
			LOGGER.debug(e.getMessage());
			deleted.setError(true);
			deleted.setErrorMessage(e.getMessage());
			throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage());

		}

		redirectAttribute
				.addFlashAttribute(MODEL_ATTIRUTE_VALUESETDTO, deleted);

		if (deleted.isError == false) {
			return deleted.getSuccessMessage();
		} else {
			throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR,
					deleted.getErrorMessage());
		}
	}

	/**
	 * Processes edit valueSet requests.
	 *
	 * @param id
	 *            The id of the edited valueSet.
	 * @param model
	 *            the model
	 * @param attributes
	 *            the attributes
	 * @return The name of the edit valueSet form view.
	 */
	@RequestMapping(value = "/valueSet/edit/{id}", method = RequestMethod.GET)
	public String showEditValueSetForm(@PathVariable("id") Long id,
			Model model, RedirectAttributes attributes) {
		LOGGER.debug("Rendering edit valueSet form for valueSet with id: " + id);
		ValueSetDto valueSetDto = new ValueSetDto();
		try {
			valueSetDto = valueSetService.findById(id);
			if (valueSetDto == null) {
				LOGGER.debug("No valueSet found with id: " + id);
				valueSetDto = new ValueSetDto();
				valueSetDto.setError(true);
				valueSetDto
						.setErrorMessage(ERROR_MESSAGE_KEY_EDITED_VALUESET_WAS_NOT_FOUND);
				return createRedirectViewPath(REDIRECT_ID_MAPPING_LIST);
			}
		} catch (ValueSetCategoryNotFoundException e) {
			LOGGER.debug(e.getMessage());
			valueSetDto.setError(true);
			valueSetDto.setErrorMessage(e.getMessage());
		}

		model.addAttribute(MODEL_ATTIRUTE_VALUESETDTO, valueSetDto);

		return VALUESET_EDIT_FORM_VIEW;
	}

	/**
	 * Processes the submissions of edit valueSet form.
	 *
	 * @param updated
	 *            The information of the edited valueSet.
	 * @param id
	 *            the id
	 * @return the string
	 */
	@RequestMapping(value = "/valueSet/edit/{id}", method = RequestMethod.POST)
	public String submitEditValueSetForm(
			@ModelAttribute(MODEL_ATTIRUTE_VALUESETDTO) ValueSetDto updated,
			RedirectAttributes attributes, @PathVariable("id") Long id) {
		LOGGER.debug("Edit valueSet form was submitted with information: "
				+ updated + id);

		try {
			AuthenticatedUser currentUser = userContext.getCurrentUser();
			updated.setUserName(currentUser.getUsername());
			updated.setId(id);
			updated = valueSetService.update(updated);
			updated.setError(false);
			updated.setSuccessMessage("Value Set   with Code: "
					+ updated.getCode() + " and Name: " + updated.getName()
					+ " is Edited Successfully");

		} catch (ValueSetNotFoundException e) {
			LOGGER.debug("No valueSet was found with id: " + updated.getId());
			updated.setError(true);
			updated.setErrorMessage("Edited Value Set is not found");
		} catch (ValueSetCategoryNotFoundException e) {
			LOGGER.debug(e.getMessage());
			updated.setError(true);
			updated.setErrorMessage(e.getMessage());
		}

		attributes.addFlashAttribute(MODEL_ATTIRUTE_VALUESETDTO, updated);

		return createRedirectViewPath(REDIRECT_ID_MAPPING_LIST);
	}

	/**
	 * Batch upload.
	 *
	 * @param request
	 *            the request
	 * @param file
	 *            the file
	 * @param redirectAttribute
	 *            the redirect attribute
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = "/valueSet/batchUpload", method = RequestMethod.POST)
	public String batchUpload(HttpServletRequest request,
			@RequestParam("batch_file") MultipartFile file,
			RedirectAttributes redirectAttribute) throws Exception {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		ValueSetDto valueSetDto = new ValueSetDto();
		valueSetDto.setUserName(currentUser.getUsername());

		try {

			valueSetDto = valueSetService
					.valueSetBatchUpload(valueSetDto, file);
			valueSetDto.setSuccessMessage("Added "
					+ valueSetDto.getRowsUpdated() + " Value Sets");

		} catch (ValueSetNotFoundException ex) {
			LOGGER.debug("Invalid value set category found while doing batch upload: "
					+ ex.getMessage());
			valueSetDto.setError(true);
		} catch (InvalidCSVException ex) {
			LOGGER.debug("Invalid csv format found while doing batch upload: "
					+ ex.getMessage());
			valueSetDto.setError(true);
		} catch (DataIntegrityViolationException ex) {
			LOGGER.debug("Duplicate value set while doing batch upload: "
					+ ex.getMessage());
			valueSetDto.setError(true);
		}
		redirectAttribute.addFlashAttribute(MODEL_ATTIRUTE_VALUESETDTO,
				valueSetDto);
		return "redirect:../valueSetList.html";
	}

	/**
	 * This setter method should only be used by unit tests.
	 *
	 * @param valueSetService
	 *            the new value set service
	 */
	protected void setValueSetService(ValueSetService valueSetService) {
		this.valueSetService = valueSetService;
	}

	/**
	 * Search value set.
	 *
	 * @param searchCategory
	 *            the search category
	 * @param searchTerm
	 *            the search term
	 * @return the list
	 */
	@RequestMapping("/valueSet/ajaxSearchValueSet/pageNumber/{pageNumber}/searchCategory/{searchCategory}/searchTerm/{searchTerm}/valueSetCategory/{valueSetCategory}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Map<String, Object> searchValueSet(
			@PathVariable("searchCategory") String searchCategory,
			@PathVariable("searchTerm") String searchTerm,
			@PathVariable("valueSetCategory") String valueSetCategory,
			@PathVariable("pageNumber") String pageNumber) {

		valueSetCategory = validateEmptyFilterParams(valueSetCategory);
		searchTerm = validateEmptyFilterParams(searchTerm);

		Map<String, Object> valueSetsPagedMap = null;

		try {
			if (searchCategory.equals("code"))
				valueSetsPagedMap = valueSetService.findAllByCode(searchTerm,
						valueSetCategory, Integer.parseInt(pageNumber));
			else if (searchCategory.equals("name"))
				valueSetsPagedMap = valueSetService.findAllByName(searchTerm,
						valueSetCategory, Integer.parseInt(pageNumber));
		} catch (IllegalArgumentException e) {
			valueSetsPagedMap = null;
			throw new AjaxException(
					HttpStatus.BAD_REQUEST,
					"Unable to perform search because the request parameters contained invalid data.");
		}

		return valueSetsPagedMap;
	}

	/**
	 * Validate empty filter params.
	 *
	 * @param param
	 *            the param
	 * @return the string
	 */
	private String validateEmptyFilterParams(String param) {
		return param.replace("empty", "");
	}

}
