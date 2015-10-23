package gov.samhsa.pcm.web.controller;

import gov.samhsa.pcm.common.AuthenticatedUser;
import gov.samhsa.pcm.common.UserContext;
import gov.samhsa.pcm.service.dto.ValueSetCategoryDto;
import gov.samhsa.pcm.service.valueset.ValueSetCategoryNotFoundException;
import gov.samhsa.pcm.service.valueset.ValueSetCategoryService;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("valueSetCategory")
@RequestMapping("/sysadmin")
public class ValueSetCategoryController extends AbstractNodeController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ValueSetController.class);

	protected static final String ERROR_MESSAGE_KEY_DELETED_VALUESETCATEGORY_WAS_NOT_FOUND = "Deleted valueSetCategory was not found.";
	protected static final String ERROR_MESSAGE_KEY_EDITED_VALUESETCATEGORY_WAS_NOT_FOUND = "Edited valueSetCategory was not found.";

	protected static final String MODEL_ATTIRUTE_VALUESETCATEGORYDTO = "valueSetCategoryDto";
	protected static final String MODEL_ATTRIBUTE_VALUESETCATEGORYDTOS = "valueSetCategoryDtos";

	protected static final String VALUESETCATEGORY_ADD_FORM_VIEW = "views/sysadmin/valueSetCategoryAdd";
	protected static final String VALUESETCATEGORY_EDIT_FORM_VIEW = "views/sysadmin/valueSetCategoryEdit";
	protected static final String VALUESETCATEGORY_LIST_VIEW = "views/sysadmin/valueSetCategoryList";

	protected static final String REQUEST_MAPPING_LIST = "/valueSetCategoryList";
	protected static final String REDIRECT_MAPPING_LIST = "../valueSetCategoryList";
	protected static final String REDIRECT_ID_MAPPING_LIST = "../../valueSetCategoryList";

	@Resource
	private ValueSetCategoryService valueSetCategoryService;

	/** The user context. */
	@Autowired
	UserContext userContext;

	/**
	 * Processes create valueSetCategory requests.
	 * 
	 * @param model
	 * @return The name of the create valueSetCategory form view.
	 */
	@RequestMapping(value = REQUEST_MAPPING_LIST, method = RequestMethod.GET)
	public String showCreateValueSetForm(Model model, HttpServletRequest request) {

		LOGGER.debug("Rendering Value Set Category list page");

		List<ValueSetCategoryDto> valueSetCategories = valueSetCategoryService
				.findAll();

		model.addAttribute(MODEL_ATTRIBUTE_VALUESETCATEGORYDTOS,
				valueSetCategories);
		return VALUESETCATEGORY_LIST_VIEW;
	}

	/**
	 * Processes the submissions of create valueSetCategory form.
	 * 
	 * @param created
	 *            The information of the created valueSetCategories.
	 * @param bindingResult
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "valueSetCategoryAdd.html", method = RequestMethod.GET)
	public String valueSetCategoryAddForm(Model model,
			HttpServletRequest request) {
		LOGGER.debug("Rendering Value Set Category Add Form ");
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		model.addAttribute("currentUser", currentUser);
		ValueSetCategoryDto valueSetCategoryDto = (ValueSetCategoryDto) model
				.asMap().get(MODEL_ATTIRUTE_VALUESETCATEGORYDTO);
		model.addAttribute(MODEL_ATTIRUTE_VALUESETCATEGORYDTO,
				(valueSetCategoryDto == null) ? new ValueSetCategoryDto()
						: valueSetCategoryDto);
		return VALUESETCATEGORY_ADD_FORM_VIEW;
	}

	/**
	 * Processes the submissions of create valueSetCategory form.
	 * 
	 * @param created
	 *            The information of the created valueSetCategories.
	 * @param bindingResult
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/valueSetCategory/create", method = RequestMethod.POST)
	public String submitCreateValueSetForm(
			@Valid @ModelAttribute(MODEL_ATTIRUTE_VALUESETCATEGORYDTO) ValueSetCategoryDto created,
			RedirectAttributes redirectAttribute, Model model) {
		LOGGER.debug("Create valueSetCategory form was submitted with information: "
				+ created);

		AuthenticatedUser currentUser = userContext.getCurrentUser();
		String notification = "";
		ValueSetCategoryDto valueSetCategoryDto;
		String path = REDIRECT_MAPPING_LIST;
		try {
			created.setUserName(currentUser.getUsername());
			valueSetCategoryDto = valueSetCategoryService.create(created);
			created.setError(false);
			created.setSuccessMessage("Value Set Category  with code:"
					+ valueSetCategoryDto.getCode() + " and Name: "
					+ valueSetCategoryDto.getName() + " is Added Successfully");
		} catch (DataIntegrityViolationException ex) {
			LOGGER.info(ex.getLocalizedMessage());
			Throwable t = ex.getCause();
			String message = null;
			if (t != null) {
				message = "Cause: " + t.getMessage();
			}
			created.setError(true);
			created.setErrorMessage("Value Set Category is not Added "
					+ message);
			path = "../valueSetCategoryAdd.html";
		}
		model.addAttribute(MODEL_ATTIRUTE_VALUESETCATEGORYDTO, created);
		redirectAttribute.addFlashAttribute(MODEL_ATTIRUTE_VALUESETCATEGORYDTO,
				created);
		model.addAttribute("notification", notification);

		return createRedirectViewPath(path);
	}

	/**
	 * Processes delete valueSetCategory requests.
	 * 
	 * @param id
	 *            The id of the deleted valueSetCategory.
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/valueSetCategory/delete/{id}", method = RequestMethod.POST)
	public String delete(@PathVariable("id") Long id,
			RedirectAttributes redirectAttribute) {
		LOGGER.debug("Deleting valueSetCategory with id: " + id);
		ValueSetCategoryDto deleted = new ValueSetCategoryDto();
		try {
			deleted = valueSetCategoryService.delete(id);
			deleted.setError(false);
			deleted.setSuccessMessage(" ValueSetCategory with Code: "
					+ deleted.getCode() + " and Name: " + deleted.getName()
					+ " is deleted Successfully. ");
		} catch (ValueSetCategoryNotFoundException e) {
			LOGGER.debug("No valueSetCategory found with id: " + id);
			deleted.setError(true);
			deleted.setErrorMessage(ERROR_MESSAGE_KEY_DELETED_VALUESETCATEGORY_WAS_NOT_FOUND);
		}
		redirectAttribute.addFlashAttribute(MODEL_ATTIRUTE_VALUESETCATEGORYDTO,
				deleted);
		return createRedirectViewPath(REDIRECT_ID_MAPPING_LIST);
	}

	/**
	 * Processes edit valueSetCategory requests.
	 * 
	 * @param id
	 *            The id of the edited valueSetCategory.
	 * @param model
	 * @param attributes
	 * @return The name of the edit valueSetCategory form view.
	 */
	@RequestMapping(value = "/valueSetCategory/edit/{id}", method = RequestMethod.GET)
	public String showEditValueSetCategoryForm(@PathVariable("id") Long id,
			Model model, RedirectAttributes attributes) {
		LOGGER.debug("Rendering edit valueSetCategory form for valueSetCategory with id: "
				+ id);
		ValueSetCategoryDto valueSetCategoryDto = valueSetCategoryService
				.findById(id);
		if (valueSetCategoryDto == null) {
			LOGGER.debug("No valueSetCategory found with id: " + id);
			valueSetCategoryDto = new ValueSetCategoryDto();
			valueSetCategoryDto.setError(true);
			valueSetCategoryDto
					.setErrorMessage(ERROR_MESSAGE_KEY_EDITED_VALUESETCATEGORY_WAS_NOT_FOUND);
			return createRedirectViewPath(REDIRECT_ID_MAPPING_LIST);
		}

		model.addAttribute(MODEL_ATTIRUTE_VALUESETCATEGORYDTO,
				valueSetCategoryDto);

		return VALUESETCATEGORY_EDIT_FORM_VIEW;
	}

	/**
	 * Processes the submissions of edit valueSetCategory form.
	 * 
	 * @param updated
	 *            The information of the edited valueSetCategory.
	 * @param bindingResult
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/valueSetCategory/edit/{id}", method = RequestMethod.POST)
	public String submitEditValueSetCategoryForm(
			@ModelAttribute(MODEL_ATTIRUTE_VALUESETCATEGORYDTO) ValueSetCategoryDto updated,
			@PathVariable("id") Long id) {
		LOGGER.debug("Edit valueSetCategory form was submitted with information: "
				+ updated + id);

		try {
			AuthenticatedUser currentUser = userContext.getCurrentUser();
			updated.setUserName(currentUser.getUsername());
			updated.setId(id);
			updated = valueSetCategoryService.update(updated);
			updated.setError(false);
			updated.setSuccessMessage("Value Set Category  with Code: "
					+ updated.getCode() + " and Name: " + updated.getName()
					+ " is Edited Successfully");

		} catch (ValueSetCategoryNotFoundException e) {
			LOGGER.debug("No valueSetCategory was found with id: "
					+ updated.getId());
			updated.setError(true);
			updated.setErrorMessage("Edited Value Set Category is not found");
		}

		return createRedirectViewPath(REDIRECT_ID_MAPPING_LIST);
	}

	/**
	 * This setter method should only be used by unit tests
	 * 
	 * @param valueSetCategoryService
	 */
	protected void setValueSetCategoryService(
			ValueSetCategoryService valueSetCategoryService) {
		this.valueSetCategoryService = valueSetCategoryService;
	}
}
