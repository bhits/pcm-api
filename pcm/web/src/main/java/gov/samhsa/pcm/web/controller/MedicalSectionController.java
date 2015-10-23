package gov.samhsa.pcm.web.controller;

import gov.samhsa.pcm.common.AuthenticatedUser;
import gov.samhsa.pcm.common.UserContext;
import gov.samhsa.pcm.service.dto.MedicalSectionDto;
import gov.samhsa.pcm.service.valueset.MedicalSectionNotFoundException;
import gov.samhsa.pcm.service.valueset.MedicalSectionService;

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
@SessionAttributes("medicalSection")
@RequestMapping("/sysadmin")
public class MedicalSectionController extends AbstractNodeController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ValueSetController.class);

	protected static final String ERROR_MESSAGE_KEY_DELETED_MEDICALSECTION_WAS_NOT_FOUND = "Deleted medicalSection was not found.";
	protected static final String ERROR_MESSAGE_KEY_EDITED_MEDICALSECTION_WAS_NOT_FOUND = "Edited medicalSection was not found.";

	protected static final String MODEL_ATTIRUTE_MEDICALSECTIONDTO = "medicalSectionDto";
	protected static final String MODEL_ATTRIBUTE_MEDICALSECTIONDTOS = "medicalSectionDtos";

	protected static final String MEDICALSECTION_ADD_FORM_VIEW = "views/sysadmin/medicalSectionAdd";
	protected static final String MEDICALSECTION_EDIT_FORM_VIEW = "views/sysadmin/medicalSectionEdit";
	protected static final String MEDICALSECTION_LIST_VIEW = "views/sysadmin/medicalSectionList";

	protected static final String REQUEST_MAPPING_LIST = "/medicalSectionList";
	protected static final String REDIRECT_MAPPING_LIST = "../medicalSectionList";
	protected static final String REDIRECT_ID_MAPPING_LIST = "../../medicalSectionList";

	@Resource
	private MedicalSectionService medicalSectionService;

	/** The user context. */
	@Autowired
	UserContext userContext;

	/**
	 * Processes create medicalSection requests.
	 * 
	 * @param model
	 * @return The name of the create medicalSection form view.
	 */
	@RequestMapping(value = REQUEST_MAPPING_LIST, method = RequestMethod.GET)
	public String showCreateValueSetForm(Model model, HttpServletRequest request) {

		LOGGER.debug("Rendering Medical Section  list page");

		List<MedicalSectionDto> medicalSections = medicalSectionService
				.findAll();

		model.addAttribute(MODEL_ATTRIBUTE_MEDICALSECTIONDTOS, medicalSections);
		return MEDICALSECTION_LIST_VIEW;
	}

	/**
	 * Processes the submissions of create medicalSection form.
	 * 
	 * @param created
	 *            The information of the created medicalSections.
	 * @param bindingResult
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "medicalSectionAdd.html", method = RequestMethod.GET)
	public String medicalSectionAddForm(Model model, HttpServletRequest request) {
		LOGGER.debug("Rendering Medical Section  Add Form ");
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		model.addAttribute("currentUser", currentUser);
		MedicalSectionDto medicalSectionDto = (MedicalSectionDto) model.asMap()
				.get(MODEL_ATTIRUTE_MEDICALSECTIONDTO);
		model.addAttribute(MODEL_ATTIRUTE_MEDICALSECTIONDTO,
				(medicalSectionDto == null) ? new MedicalSectionDto()
						: medicalSectionDto);
		return MEDICALSECTION_ADD_FORM_VIEW;
	}

	/**
	 * Processes the submissions of create medicalSection form.
	 * 
	 * @param created
	 *            The information of the created medicalSections.
	 * @param bindingResult
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/medicalSection/create", method = RequestMethod.POST)
	public String submitCreateValueSetForm(
			@Valid @ModelAttribute(MODEL_ATTIRUTE_MEDICALSECTIONDTO) MedicalSectionDto created,
			RedirectAttributes redirectAttribute, Model model) {
		LOGGER.debug("Create medicalSection form was submitted with information: "
				+ created);

		AuthenticatedUser currentUser = userContext.getCurrentUser();
		String notification = "";
		MedicalSectionDto medicalSectionDto;
		String path = REDIRECT_MAPPING_LIST;
		try {
			created.setUserName(currentUser.getUsername());
			medicalSectionDto = medicalSectionService.create(created);
			created.setError(false);
			created.setSuccessMessage("Medical Section  with code:"
					+ medicalSectionDto.getCode() + " and Name: "
					+ medicalSectionDto.getName() + " is Added Successfully");
		} catch (DataIntegrityViolationException ex) {
			LOGGER.info(ex.getLocalizedMessage());
			Throwable t = ex.getCause();
			String message = null;
			if (t != null) {
				message = "Cause: " + t.getMessage();
			}
			created.setError(true);
			created.setErrorMessage("Medical Section is not Added " + message);
			path = "../medicalSectionAdd.html";
		}
		model.addAttribute(MODEL_ATTIRUTE_MEDICALSECTIONDTO, created);
		redirectAttribute.addFlashAttribute(MODEL_ATTIRUTE_MEDICALSECTIONDTO,
				created);
		model.addAttribute("notification", notification);

		return createRedirectViewPath(path);
	}

	/**
	 * Processes delete medicalSection requests.
	 * 
	 * @param id
	 *            The id of the deleted medicalSection.
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/medicalSection/delete/{id}", method = RequestMethod.POST)
	public String delete(@PathVariable("id") Long id,
			RedirectAttributes redirectAttribute) {
		LOGGER.debug("Deleting medicalSection with id: " + id);
		MedicalSectionDto deleted = new MedicalSectionDto();
		try {
			deleted = medicalSectionService.delete(id);
			deleted.setError(false);
			deleted.setSuccessMessage(" MedicalSection with Code: "
					+ deleted.getCode() + " and Name: " + deleted.getName()
					+ " is deleted Successfully. ");
		} catch (MedicalSectionNotFoundException e) {
			LOGGER.debug("No medicalSection found with id: " + id);
			deleted.setError(true);
			deleted.setErrorMessage(ERROR_MESSAGE_KEY_DELETED_MEDICALSECTION_WAS_NOT_FOUND);
		}
		redirectAttribute.addFlashAttribute(MODEL_ATTIRUTE_MEDICALSECTIONDTO,
				deleted);
		return createRedirectViewPath(REDIRECT_ID_MAPPING_LIST);
	}

	/**
	 * Processes edit medicalSection requests.
	 * 
	 * @param id
	 *            The id of the edited medicalSection.
	 * @param model
	 * @param attributes
	 * @return The name of the edit medicalSection form view.
	 */
	@RequestMapping(value = "/medicalSection/edit/{id}", method = RequestMethod.GET)
	public String showEditMedicalSectionForm(@PathVariable("id") Long id,
			Model model, RedirectAttributes attributes) {
		LOGGER.debug("Rendering edit medicalSection form for medicalSection with id: "
				+ id);
		MedicalSectionDto medicalSectionDto = medicalSectionService
				.findById(id);
		if (medicalSectionDto == null) {
			LOGGER.debug("No medicalSection found with id: " + id);
			medicalSectionDto = new MedicalSectionDto();
			medicalSectionDto.setError(true);
			medicalSectionDto
					.setErrorMessage(ERROR_MESSAGE_KEY_EDITED_MEDICALSECTION_WAS_NOT_FOUND);
			return createRedirectViewPath(REDIRECT_ID_MAPPING_LIST);
		}

		model.addAttribute(MODEL_ATTIRUTE_MEDICALSECTIONDTO, medicalSectionDto);

		return MEDICALSECTION_EDIT_FORM_VIEW;
	}

	/**
	 * Processes the submissions of edit medicalSection form.
	 * 
	 * @param updated
	 *            The information of the edited medicalSection.
	 * @param bindingResult
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/medicalSection/edit/{id}", method = RequestMethod.POST)
	public String submitEditMedicalSectionForm(
			@ModelAttribute(MODEL_ATTIRUTE_MEDICALSECTIONDTO) MedicalSectionDto updated,
			@PathVariable("id") Long id) {
		LOGGER.debug("Edit medicalSection form was submitted with information: "
				+ updated + id);

		try {
			AuthenticatedUser currentUser = userContext.getCurrentUser();
			updated.setUserName(currentUser.getUsername());
			updated.setId(id);
			updated = medicalSectionService.update(updated);
			updated.setError(false);
			updated.setSuccessMessage("Medical Section  with Code: "
					+ updated.getCode() + " and Name: " + updated.getName()
					+ " is Edited Successfully");

		} catch (MedicalSectionNotFoundException e) {
			LOGGER.debug("No medicalSection was found with id: "
					+ updated.getId());
			updated.setError(true);
			updated.setErrorMessage("Edited Medical Section  is not found");
		}

		return createRedirectViewPath(REDIRECT_ID_MAPPING_LIST);
	}

	/**
	 * This setter method should only be used by unit tests
	 * 
	 * @param medicalSectionService
	 */
	protected void setMedicalSectionService(
			MedicalSectionService medicalSectionService) {
		this.medicalSectionService = medicalSectionService;
	}
}
