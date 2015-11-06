package gov.samhsa.pcm.service.valueset;

import gov.samhsa.pcm.domain.valueset.ValueSet;
import gov.samhsa.pcm.service.dto.ConceptCodeDto;
import gov.samhsa.pcm.service.dto.ConceptCodeVSCSDto;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * The Interface ConceptCodeService.
 */
@Transactional(readOnly = true)
public interface ConceptCodeService {

	/**
	 * Creates a new concept code.
	 *
	 * @param created            The information of the created concept code.
	 * @return The created concept code.
	 * @throws ValueSetNotFoundException the value set not found exception
	 * @throws CodeSystemNotFoundException the code system not found exception
	 * @throws DuplicateConceptCodeException the duplicate concept code exception
	 */
	public ConceptCodeDto create(ConceptCodeDto created) throws ValueSetNotFoundException, CodeSystemNotFoundException,
			DuplicateConceptCodeException;

	/**
	 * Deletes a ConceptCode.
	 *
	 * @param conceptCodeId the concept code id
	 * @return The deleted ConceptCode.
	 * @throws ConceptCodeNotFoundException             if no ConceptCode is found with the given id.
	 */
	public ConceptCodeDto delete(Long conceptCodeId) throws ConceptCodeNotFoundException;

	/**
	 * Finds all ConceptCodes.
	 * 
	 * @return A list of ConceptCodes.
	 */
	public List<ConceptCodeDto> findAll();


	/**
	 * Find all.
	 *
	 * @param pageNumber the page number
	 * @return the list
	 */
	public Map<String, Object> findAll(int pageNumber);
	
	/**
	 * Find all by code.
	 *
	 * @param searchTerm the search term
	 * @param codeSystem the code system
	 * @param codeSystemVersion the code system version
	 * @param valueSetName the value set name
	 * @param pageNumber the page number
	 * @return the list
	 */
	public Map<String, Object> findAllByCode(String searchTerm, String codeSystem, String codeSystemVersion, String valueSetName, int pageNumber);

	/**
	 * Find all by name.
	 *
	 * @param searchTerm the search term
	 * @param codeSystem the code system
	 * @param codeSystemVersion the code system version
	 * @param valueSetName the value set name
	 * @param pageNumber the page number
	 * @return the list
	 */
	public Map<String, Object> findAllByName(String searchTerm, String codeSystem, String codeSystemVersion, String valueSetName, int pageNumber);

	/**
	 * Finds ConceptCode by id.
	 *
	 * @param id            The id of the wanted ConceptCode.
	 * @return The found ConceptCode. If no ConceptCode is found, this method
	 *         returns null.
	 * @throws ValueSetNotFoundException the value set not found exception
	 * @throws ConceptCodeNotFoundException the concept code not found exception
	 */
	public ConceptCodeDto findById(Long id) throws ValueSetNotFoundException, ConceptCodeNotFoundException;

	/**
	 * Updates the information of a ConceptCode.
	 *
	 * @param updated            The information of the updated ConceptCode.
	 * @return The updated ConceptCode.
	 * @throws ConceptCodeNotFoundException             if no ConceptCode is found with given id.
	 * @throws ValueSetNotFoundException the value set not found exception
	 */
	public ConceptCodeDto update(ConceptCodeDto updated) throws ConceptCodeNotFoundException, ValueSetNotFoundException;

	/**
	 * Creates the.
	 *
	 * @return the concept code vscs dto
	 * @throws ValueSetNotFoundException the value set not found exception
	 * @throws CodeSystemVersionNotFoundException the code system version not found exception
	 * @throws CodeSystemNotFoundException the code system not found exception
	 */
	public ConceptCodeVSCSDto create() throws ValueSetNotFoundException, CodeSystemVersionNotFoundException,
			CodeSystemNotFoundException;

	/**
	 * Concept code batch upload.
	 *
	 * @param conceptCodeDto the concept code dto
	 * @param file the file
	 * @param codeSystemId the code system id
	 * @param codeSystemVersionId the code system version id
	 * @param valueSetIds the value set ids
	 * @return the concept code dto
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ValueSetNotFoundException the value set not found exception
	 * @throws CodeSystemNotFoundException the code system not found exception
	 */
	public ConceptCodeDto conceptCodeBatchUpload(ConceptCodeDto conceptCodeDto, MultipartFile file,
			String codeSystemId, Long codeSystemVersionId, List<Long> valueSetIds) throws IOException,
			ValueSetNotFoundException, CodeSystemNotFoundException;

	/**
	 * Find value sets by code system.
	 *
	 * @param codeSystem the code system
	 * @param codeSystemVersion the code system version
	 * @return 
	 */
	public List<ValueSet> findValueSetsByCodeSystem(String codeSystem, String codeSystemVersion);


}
