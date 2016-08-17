package gov.samhsa.c2s.vss.service;

import gov.samhsa.c2s.vss.service.dto.ValueSetDto;
import gov.samhsa.c2s.vss.service.dto.ValueSetVSCDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * The Interface ValueSetService.
 */
@Transactional(readOnly = true)
public interface ValueSetService {

    /**
     * Creates a new person.
     *
     * @param created The information of the created person.
     * @return The created person.
     * @throws ValueSetCategoryNotFoundException the value set category not found exception
     */
    public ValueSetDto create(ValueSetDto created)
            throws ValueSetCategoryNotFoundException;

    /**
     * Deletes a ValueSet.
     *
     * @param valueSetId the value set id
     * @return The deleted ValueSet.
     * @throws ValueSetNotFoundException if no ValueSet is found with the given id.
     */
    public ValueSetDto delete(Long valueSetId) throws ValueSetNotFoundException;

    /**
     * Finds all ValueSets.
     *
     * @return A list of ValueSets.
     */
    public List<ValueSetDto> findAll();

    /**
     * Find all by page number.
     *
     * @param pageNumber the page number
     * @return the list
     */
    public Map<String, Object> findAll(int pageNumber);

    /**
     * Finds ValueSet by id.
     *
     * @param id The id of the wanted ValueSet.
     * @return The found ValueSet. If no ValueSet is found, this method returns
     * null.
     * @throws ValueSetCategoryNotFoundException the value set category not found exception
     */
    public ValueSetDto findById(Long id)
            throws ValueSetCategoryNotFoundException;

    /**
     * Updates the information of a ValueSet.
     *
     * @param updated The information of the updated ValueSet.
     * @return The updated ValueSet.
     * @throws ValueSetNotFoundException         if no ValueSet is found with given id.
     * @throws ValueSetCategoryNotFoundException the value set category not found exception
     */
    public ValueSetDto update(ValueSetDto updated)
            throws ValueSetNotFoundException, ValueSetCategoryNotFoundException;

    /**
     * Creates a new person.
     *
     * @return The created person.
     * @throws ValueSetCategoryNotFoundException the value set category not found exception
     */
    public ValueSetVSCDto create() throws ValueSetCategoryNotFoundException;

    /**
     * Find all by name.
     *
     * @param searchTerm       the search term
     * @param valueSetCategory the value set category
     * @param pageNumber       the page number
     * @return the map
     */
    public Map<String, Object> findAllByName(String searchTerm,
                                             String valueSetCategory, int pageNumber);

    /**
     * Find by code.
     *
     * @param searchTerm       the search term
     * @param valueSetCategory the value set category
     * @param pageNumber       the page number
     * @return the map
     */
    public Map<String, Object> findAllByCode(String searchTerm,
                                             String valueSetCategory, int pageNumber);

    /**
     * Value set batch upload.
     *
     * @param valueSetDto the value set dto
     * @param file        the bytes
     * @return the value set dto
     * @throws Exception the exception
     */
    public ValueSetDto valueSetBatchUpload(ValueSetDto valueSetDto,
                                           MultipartFile file) throws Exception;

    /**
     * Find all without deletable.
     *
     * @return the list
     */
    public List<ValueSetDto> findAllWithoutDeletable();

}
