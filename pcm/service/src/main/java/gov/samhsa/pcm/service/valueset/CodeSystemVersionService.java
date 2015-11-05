package gov.samhsa.pcm.service.valueset;

import gov.samhsa.pcm.service.dto.CodeSystemVersionCSDto;
import gov.samhsa.pcm.service.dto.CodeSystemVersionDto;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

/**
 * Declares methods used to obtain and modify CodeSystemVersion information.
 */
@Transactional(readOnly = true)
public interface CodeSystemVersionService {

	/**
	 * Creates a new person.
	 *
	 * @param created
	 *            The information of the created codeSystemVersion.
	 * @return The created person.
	 * @throws CodeSystemNotFoundException
	 *             the code system not found exception
	 */
	public CodeSystemVersionDto create(CodeSystemVersionDto created)
			throws CodeSystemNotFoundException;

	/**
	 * Deletes a CodeSystemVersion.
	 *
	 * @param valueSetId
	 *            the value set id
	 * @return The deleted CodeSystemVersion.
	 * @throws CodeSystemVersionNotFoundException
	 *             if no CodeSystemVersion is found with the given id.
	 */
	public CodeSystemVersionDto delete(Long valueSetId)
			throws CodeSystemVersionNotFoundException;

	/**
	 * Finds all CodeSystemVersions.
	 * 
	 * @return A list of CodeSystemVersions.
	 */
	public List<CodeSystemVersionDto> findAll();

	/**
	 * Finds CodeSystemVersion by id.
	 * 
	 * @param id
	 *            The id of the wanted CodeSystemVersion.
	 * @return The found CodeSystemVersion. If no CodeSystemVersion is found,
	 *         this method returns null.
	 * @throws CodeSystemNotFoundException
	 */
	public CodeSystemVersionDto findById(Long id)
			throws CodeSystemNotFoundException;

	/**
	 * Updates the information of a CodeSystemVersion.
	 * 
	 * @param updated
	 *            The information of the updated CodeSystemVersion.
	 * @return The updated CodeSystemVersion.
	 * @throws CodeSystemVersionNotFoundException
	 *             if no CodeSystemVersion is found with given id.
	 * @throws CodeSystemNotFoundException
	 */
	public CodeSystemVersionDto update(CodeSystemVersionDto updated)
			throws CodeSystemVersionNotFoundException,
			CodeSystemNotFoundException;

	/**
	 * Creates a new person.
	 *
	 * @return The created person.
	 * @throws CodeSystemNotFoundException
	 *             the code system not found exception
	 */
	public CodeSystemVersionCSDto create() throws CodeSystemNotFoundException;

	/**
	 * Group by code system.
	 *
	 * @param codeSystemVersionDtos
	 *            the code system version dtos
	 * @return the list
	 */
	public List<CodeSystemVersionDto> groupByCodeSystem(
			List<CodeSystemVersionDto> codeSystemVersionDtos);
}
