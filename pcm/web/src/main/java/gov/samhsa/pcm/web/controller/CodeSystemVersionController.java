package gov.samhsa.pcm.web.controller;

import gov.samhsa.pcm.common.AuthenticatedUser;
import gov.samhsa.pcm.common.UserContext;
import gov.samhsa.pcm.service.dto.CodeSystemVersionCSDto;
import gov.samhsa.pcm.service.dto.CodeSystemVersionDto;
import gov.samhsa.pcm.service.valueset.CodeSystemNotFoundException;
import gov.samhsa.pcm.service.valueset.CodeSystemVersionNotFoundException;
import gov.samhsa.pcm.service.valueset.CodeSystemVersionService;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
@SessionAttributes("codeSystemVersion")
@RequestMapping("/sysadmin")
public class CodeSystemVersionController extends AbstractNodeController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CodeSystemVersionController.class);

	protected static final String ERROR_MESSAGE_KEY_DELETED_CODESYSTEMVERSION_WAS_NOT_FOUND = "Deleted codeSystemVersion was not found.";
	protected static final String ERROR_MESSAGE_KEY_EDITED_CODESYSTEMVERSION_WAS_NOT_FOUND = "Edited codeSystemVersion was not found.";
	protected static final String ERROR_MESSAGE_CODESYSTEMVERSIONS_NOT_FOUND = "Code Systems not Found. To create and manage Code System Versions, you need to add Code Systems.";

	protected static final String MODEL_ATTIRUTE_CODESYSTEMVERSIONDTO = "codeSystemVersionDto";
	protected static final String MODEL_ATTIRUTE_CODESYSTEMVERSIONCSDTO = "codeSystemVersionCSDto";
	protected static final String MODEL_ATTRIBUTE_CODESYSTEMVERSIONDTOS = "codeSystemVersionDtos";

	protected static final String CODESYSTEMVERSION_ADD_FORM_VIEW = "views/sysadmin/codeSystemVersionAdd";
	protected static final String CODESYSTEMVERSION_EDIT_FORM_VIEW = "views/sysadmin/codeSystemVersionEdit";
	protected static final String CODESYSTEMVERSION_LIST_VIEW = "views/sysadmin/codeSystemVersionList";

	protected static final String REQUEST_MAPPING_LIST = "/codeSystemVersionList";
	protected static final String REDIRECT_MAPPING_LIST = "../../codeSystemVersionList";
	protected static final String REDIRECT_ID_MAPPING_LIST = "../../codeSystemVersionList";

	@Resource
	private CodeSystemVersionService codeSystemVersionService;

	/** The user context. */
	@Autowired
	UserContext userContext;

	/**
	 * Processes create codeSystemVersion requests.
	 * 
	 * @param model
	 * @return The name of the create codeSystemVersion form view.
	 */
	@RequestMapping(value = REQUEST_MAPPING_LIST, method = RequestMethod.GET)
	public String showCreateCodeSystemVersionForm(Model model,
			HttpServletRequest request) {

		LOGGER.debug("Rendering Value Set  list page");

		List<CodeSystemVersionDto> codeSystemVersions = codeSystemVersionService
				.findAll();

		model.addAttribute(MODEL_ATTRIBUTE_CODESYSTEMVERSIONDTOS,
				codeSystemVersions);
		return CODESYSTEMVERSION_LIST_VIEW;
	}

	/**
	 * Processes the submissions of create codeSystemVersion form.
	 * 
	 * @param created
	 *            The information of the created codeSystemVersions.
	 * @param bindingResult
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "codeSystemVersionAdd.html", method = RequestMethod.GET)
	public String codeSystemVersionAddForm(Model model,
			HttpServletRequest request) {
		LOGGER.debug("Rendering Value Set  Add Form ");
		String returnPage = CODESYSTEMVERSION_ADD_FORM_VIEW;
		CodeSystemVersionCSDto codeSystemVersionCSDto = new CodeSystemVersionCSDto();
		CodeSystemVersionDto codeSystemVersionDto = new CodeSystemVersionDto();

		try {
			codeSystemVersionCSDto = codeSystemVersionService.create();
			codeSystemVersionDto = (CodeSystemVersionDto) model.asMap().get(
					MODEL_ATTIRUTE_CODESYSTEMVERSIONDTO);
			if (codeSystemVersionDto != null)
				codeSystemVersionCSDto
						.setCodeSystemVersionDto(codeSystemVersionDto);

		} catch (CodeSystemNotFoundException e) {
			LOGGER.debug("No value sets found in the system");
			codeSystemVersionDto.setError(true);
			codeSystemVersionDto
					.setErrorMessage(ERROR_MESSAGE_CODESYSTEMVERSIONS_NOT_FOUND);
			returnPage = CODESYSTEMVERSION_LIST_VIEW;
		}

		model.addAttribute(MODEL_ATTIRUTE_CODESYSTEMVERSIONDTO,
				(codeSystemVersionDto == null) ? new CodeSystemVersionDto()
						: codeSystemVersionDto);
		model.addAttribute(MODEL_ATTIRUTE_CODESYSTEMVERSIONCSDTO,
				codeSystemVersionCSDto);
		return returnPage;
	}

	/**
	 * Processes the submissions of create codeSystemVersion form.
	 * 
	 * @param created
	 *            The information of the created codeSystemVersions.
	 * @param bindingResult
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/codeSystemVersion/create", method = RequestMethod.POST)
	public String submitCreateCodeSystemVersionForm(
			@ModelAttribute(MODEL_ATTIRUTE_CODESYSTEMVERSIONDTO) CodeSystemVersionDto created,
			RedirectAttributes redirectAttribute, Model model) {
		LOGGER.debug("Create codeSystemVersion form was submitted with information: "
				+ created);

		AuthenticatedUser currentUser = userContext.getCurrentUser();
		String notification = "";
		CodeSystemVersionDto codeSystemVersionDto;
		String path = REDIRECT_MAPPING_LIST;
		try {
			created.setUserName(currentUser.getUsername());
			codeSystemVersionDto = codeSystemVersionService.create(created);
			created.setError(false);
			created.setSuccessMessage("Code System Version   with name:"
					+ codeSystemVersionDto.getName() + " for Code System: "
					+ codeSystemVersionDto.getCodeSystemName()
					+ " is Added Successfully");
		} catch (DataIntegrityViolationException ex) {
			LOGGER.info(ex.getLocalizedMessage());
			Throwable t = ex.getCause();
			String message = null;
			if (t != null) {
				message = "Cause: " + t.getMessage();
			}
			created.setError(true);
			created.setErrorMessage("Value Set  is not Added " + message);
			path = "../codeSystemVersionAdd.html";
		} catch (CodeSystemNotFoundException e) {
			LOGGER.debug("No value set category found with id: "
					+ created.getCodeSystemId());
			created.setError(true);
			created.setErrorMessage(ERROR_MESSAGE_CODESYSTEMVERSIONS_NOT_FOUND);
			path = "../codeSystemVersionAdd.html";
		}
		model.addAttribute(MODEL_ATTIRUTE_CODESYSTEMVERSIONDTO, created);
		redirectAttribute.addFlashAttribute(
				MODEL_ATTIRUTE_CODESYSTEMVERSIONDTO, created);
		model.addAttribute("notification", notification);

		return createRedirectViewPath(path);
	}

	/**
	 * Processes delete codeSystemVersion requests.
	 * 
	 * @param id
	 *            The id of the deleted codeSystemVersion.
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/codeSystemVersion/delete/{id}", method = RequestMethod.POST)
	public String delete(@PathVariable("id") Long id,
			RedirectAttributes redirectAttribute) {
		LOGGER.debug("Deleting codeSystemVersion with id: " + id);
		CodeSystemVersionDto deleted = new CodeSystemVersionDto();
		try {
			deleted = codeSystemVersionService.delete(id);
			deleted.setError(false);
			deleted.setSuccessMessage(" CodeSystemVersion with Code: "
					+ deleted.getCode() + " and Name: " + deleted.getName()
					+ " is deleted Successfully. ");
		} catch (CodeSystemVersionNotFoundException e) {
			LOGGER.debug("No codeSystemVersion found with id: " + id);
			deleted.setError(true);
			deleted.setErrorMessage(ERROR_MESSAGE_KEY_DELETED_CODESYSTEMVERSION_WAS_NOT_FOUND);
		}
		redirectAttribute.addFlashAttribute(
				MODEL_ATTIRUTE_CODESYSTEMVERSIONDTO, deleted);
		return createRedirectViewPath(REDIRECT_ID_MAPPING_LIST);
	}

	/**
	 * Processes edit codeSystemVersion requests.
	 * 
	 * @param id
	 *            The id of the edited codeSystemVersion.
	 * @param model
	 * @param attributes
	 * @return The name of the edit codeSystemVersion form view.
	 */
	@RequestMapping(value = "/codeSystemVersion/edit/{id}", method = RequestMethod.GET)
	public String showEditCodeSystemVersionForm(@PathVariable("id") Long id,
			Model model, RedirectAttributes attributes) {
		LOGGER.debug("Rendering edit codeSystemVersion form for codeSystemVersion with id: "
				+ id);
		CodeSystemVersionDto codeSystemVersionDto = new CodeSystemVersionDto();
		try {
			codeSystemVersionDto = codeSystemVersionService.findById(id);
			if (codeSystemVersionDto == null) {
				LOGGER.debug("No codeSystemVersion found with id: " + id);
				codeSystemVersionDto = new CodeSystemVersionDto();
				codeSystemVersionDto.setError(true);
				codeSystemVersionDto
						.setErrorMessage(ERROR_MESSAGE_KEY_EDITED_CODESYSTEMVERSION_WAS_NOT_FOUND);
				return createRedirectViewPath(REDIRECT_ID_MAPPING_LIST);
			}

		} catch (CodeSystemNotFoundException e) {
			LOGGER.debug(e.getMessage());
			codeSystemVersionDto.setError(true);
			codeSystemVersionDto.setErrorMessage(e.getMessage());
		}

		model.addAttribute(MODEL_ATTIRUTE_CODESYSTEMVERSIONDTO,
				codeSystemVersionDto);

		return CODESYSTEMVERSION_EDIT_FORM_VIEW;
	}

	/**
	 * Processes the submissions of edit codeSystemVersion form.
	 * 
	 * @param updated
	 *            The information of the edited codeSystemVersion.
	 * @param bindingResult
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/codeSystemVersion/edit/{id}", method = RequestMethod.POST)
	public String submitEditCodeSystemVersionForm(
			@ModelAttribute(MODEL_ATTIRUTE_CODESYSTEMVERSIONDTO) CodeSystemVersionDto updated,
			@PathVariable("id") Long id) {
		LOGGER.debug("Edit codeSystemVersion form was submitted with information: "
				+ updated + id);

		try {
			AuthenticatedUser currentUser = userContext.getCurrentUser();
			updated.setUserName(currentUser.getUsername());
			updated.setId(id);
			updated = codeSystemVersionService.update(updated);
			updated.setError(false);
			updated.setSuccessMessage("Value Set   with Code: "
					+ updated.getCode() + " and Name: " + updated.getName()
					+ " is Edited Successfully");

		} catch (CodeSystemVersionNotFoundException e) {
			LOGGER.debug("No codeSystemVersion was found with id: "
					+ updated.getId());
			updated.setError(true);
			updated.setErrorMessage("Edited Value Set is not found");
		} catch (CodeSystemNotFoundException e) {
			LOGGER.debug(e.getMessage());
			updated.setError(true);
			updated.setErrorMessage(e.getMessage());
		}

		return createRedirectViewPath(REDIRECT_ID_MAPPING_LIST);
	}

	/**
	 * This setter method should only be used by unit tests
	 * 
	 * @param codeSystemVersionService
	 */
	protected void setCodeSystemVersionService(
			CodeSystemVersionService codeSystemVersionService) {
		this.codeSystemVersionService = codeSystemVersionService;
	}
}
