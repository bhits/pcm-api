package gov.samhsa.pcm.web.controller;

import gov.samhsa.pcm.common.AuthenticatedUser;
import gov.samhsa.pcm.common.UserContext;
import gov.samhsa.pcm.service.dto.CodeSystemDto;
import gov.samhsa.pcm.service.valueset.CodeSystemNotFoundException;
import gov.samhsa.pcm.service.valueset.CodeSystemService;

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
@SessionAttributes("codeSystem")
@RequestMapping("/sysadmin")
public class CodeSystemController extends AbstractNodeController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CodeSystemController.class);

	protected static final String ERROR_MESSAGE_KEY_DELETED_CODESYSTEM_WAS_NOT_FOUND = "Deleted codeSystem was not found.";
	protected static final String ERROR_MESSAGE_KEY_EDITED_CODESYSTEM_WAS_NOT_FOUND = "Edited codeSystem was not found.";

	protected static final String MODEL_ATTIRUTE_CODESYSTEMDTO = "codeSystemDto";
	protected static final String MODEL_ATTRIBUTE_CODESYSTEMDTOS = "codeSystemDtos";

	protected static final String CODESYSTEM_ADD_FORM_VIEW = "views/sysadmin/codeSystemAdd";
	protected static final String CODESYSTEM_EDIT_FORM_VIEW = "views/sysadmin/codeSystemEdit";
	protected static final String CODESYSTEM_LIST_VIEW = "views/sysadmin/home";

	protected static final String REQUEST_MAPPING_LIST = "/home";
	protected static final String REDIRECT_MAPPING_LIST = "../home";
	protected static final String REDIRECT_ID_MAPPING_LIST = "../../home";

	@Resource
	private CodeSystemService codeSystemService;

	/** The user context. */
	@Autowired
	UserContext userContext;

	/**
	 * Processes create codeSystem requests.
	 * 
	 * @param model
	 * @return The name of the create codeSystem form view.
	 */
	@RequestMapping(value = REQUEST_MAPPING_LIST, method = RequestMethod.GET)
	public String showCreateCodeSystemForm(Model model,
			HttpServletRequest request) {

		LOGGER.debug("Rendering Code System list page");

		List<CodeSystemDto> codeSystems = codeSystemService.findAll();

		model.addAttribute(MODEL_ATTRIBUTE_CODESYSTEMDTOS, codeSystems);
		return CODESYSTEM_LIST_VIEW;
	}

	/**
	 * Processes the submissions of create codeSystem form.
	 * 
	 * @param created
	 *            The information of the created codeSystems.
	 * @param bindingResult
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "codeSystemAdd.html", method = RequestMethod.GET)
	public String codeSystemAddForm(Model model, HttpServletRequest request) {
		LOGGER.debug("Rendering Code System Add Form ");
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		model.addAttribute("currentUser", currentUser);
		CodeSystemDto codeSystemDto = (CodeSystemDto) model.asMap().get(
				MODEL_ATTIRUTE_CODESYSTEMDTO);
		model.addAttribute(MODEL_ATTIRUTE_CODESYSTEMDTO,
				(codeSystemDto == null) ? new CodeSystemDto() : codeSystemDto);
		return CODESYSTEM_ADD_FORM_VIEW;
	}

	/**
	 * Processes the submissions of create codeSystem form.
	 * 
	 * @param created
	 *            The information of the created codeSystems.
	 * @param bindingResult
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/codeSystem/create", method = RequestMethod.POST)
	public String submitCreateCodeSystemForm(
			@Valid @ModelAttribute(MODEL_ATTIRUTE_CODESYSTEMDTO) CodeSystemDto created,
			RedirectAttributes redirectAttribute, Model model) {
		LOGGER.debug("Create codeSystem form was submitted with information: "
				+ created);

		AuthenticatedUser currentUser = userContext.getCurrentUser();
		String notification = "";
		CodeSystemDto codeSystemDto;
		String path = REDIRECT_MAPPING_LIST;
		try {
			created.setUserName(currentUser.getUsername());
			codeSystemDto = codeSystemService.create(created);
			created.setError(false);
			created.setSuccessMessage("Code System  with code:"
					+ codeSystemDto.getCode() + " and oid: "
					+ codeSystemDto.getCodeSystemOId()
					+ " is Added Successfully");
		} catch (DataIntegrityViolationException ex) {
			LOGGER.info(ex.getLocalizedMessage());
			Throwable t = ex.getCause();
			String message = null;
			if (t != null) {
				message = "Cause: " + t.getMessage();
			}
			created.setError(true);
			created.setErrorMessage("Code System is not Added " + message);
			path = "../codeSystemAdd.html";
		}
		model.addAttribute(MODEL_ATTIRUTE_CODESYSTEMDTO, created);
		redirectAttribute.addFlashAttribute(MODEL_ATTIRUTE_CODESYSTEMDTO,
				created);
		model.addAttribute("notification", notification);

		return createRedirectViewPath(path);
	}

	/**
	 * Processes delete codeSystem requests.
	 * 
	 * @param id
	 *            The id of the deleted codeSystem.
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/codeSystem/delete/{id}", method = RequestMethod.POST)
	public String delete(@PathVariable("id") Long id,
			RedirectAttributes redirectAttribute) {
		LOGGER.debug("Deleting codeSystem with id: " + id);
		CodeSystemDto deleted = new CodeSystemDto();
		try {
			deleted = codeSystemService.delete(id);
			deleted.setError(false);
			deleted.setSuccessMessage(" CodeSystem with Code: "
					+ deleted.getCode() + " and OID: "
					+ deleted.getCodeSystemOId() + " is deleted Successfully. ");
		} catch (CodeSystemNotFoundException e) {
			LOGGER.debug("No codeSystem found with id: " + id);
			deleted.setError(true);
			deleted.setErrorMessage(ERROR_MESSAGE_KEY_DELETED_CODESYSTEM_WAS_NOT_FOUND);
		}
		redirectAttribute.addFlashAttribute(MODEL_ATTIRUTE_CODESYSTEMDTO,
				deleted);
		return createRedirectViewPath(REDIRECT_ID_MAPPING_LIST);
	}

	/**
	 * Processes edit codeSystem requests.
	 * 
	 * @param id
	 *            The id of the edited codeSystem.
	 * @param model
	 * @param attributes
	 * @return The name of the edit codeSystem form view.
	 */
	@RequestMapping(value = "/codeSystem/edit/{id}", method = RequestMethod.GET)
	public String showEditCodeSystemForm(@PathVariable("id") Long id,
			Model model, RedirectAttributes attributes) {
		LOGGER.debug("Rendering edit codeSystem form for codeSystem with id: "
				+ id);
		CodeSystemDto codeSystemDto = codeSystemService.findById(id);
		if (codeSystemDto == null) {
			LOGGER.debug("No codeSystem found with id: " + id);
			codeSystemDto = new CodeSystemDto();
			codeSystemDto.setError(true);
			codeSystemDto
					.setErrorMessage(ERROR_MESSAGE_KEY_EDITED_CODESYSTEM_WAS_NOT_FOUND);
			return createRedirectViewPath(REDIRECT_ID_MAPPING_LIST);
		}

		model.addAttribute(MODEL_ATTIRUTE_CODESYSTEMDTO, codeSystemDto);

		return CODESYSTEM_EDIT_FORM_VIEW;
	}

	/**
	 * Processes the submissions of edit codeSystem form.
	 * 
	 * @param updated
	 *            The information of the edited codeSystem.
	 * @param bindingResult
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/codeSystem/edit/{id}", method = RequestMethod.POST)
	public String submitEditCodeSystemForm(
			@ModelAttribute(MODEL_ATTIRUTE_CODESYSTEMDTO) CodeSystemDto updated,
			@PathVariable("id") Long id, RedirectAttributes redirectAttribute) {
		LOGGER.debug("Edit codeSystem form was submitted with information: "
				+ updated + id);

		try {
			AuthenticatedUser currentUser = userContext.getCurrentUser();
			updated.setUserName(currentUser.getUsername());
			updated.setId(id);
			updated = codeSystemService.update(updated);
			updated.setError(false);
			updated.setSuccessMessage("Code System  with Code: "
					+ updated.getCode() + " and OID: "
					+ updated.getCodeSystemOId() + " is Edited Successfully");

		} catch (CodeSystemNotFoundException e) {
			LOGGER.debug("No codeSystem was found with id: " + updated.getId());
			updated.setError(true);
			updated.setErrorMessage("Edited Code System is not found");
		}
		redirectAttribute.addFlashAttribute(MODEL_ATTIRUTE_CODESYSTEMDTO,
				updated);
		return createRedirectViewPath(REDIRECT_ID_MAPPING_LIST);
	}

	/**
	 * This setter method should only be used by unit tests
	 * 
	 * @param codeSystemService
	 */
	protected void setCodeSystemService(CodeSystemService codeSystemService) {
		this.codeSystemService = codeSystemService;
	}
}
