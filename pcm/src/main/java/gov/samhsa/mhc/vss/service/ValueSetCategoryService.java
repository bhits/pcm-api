package gov.samhsa.mhc.vss.service;

import gov.samhsa.mhc.vss.service.dto.AddConsentFieldsDto;
import gov.samhsa.mhc.vss.service.dto.ValueSetCategoryDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ValueSetCategoryService {

    /**
     * Creates a new person.
     *
     * @param created The information of the created person.
     * @return The created person.
     */
    public ValueSetCategoryDto create(ValueSetCategoryDto created);

    /**
     * Deletes a ValueSetCategory.
     *
     * @param valueSetCategoryId The id of the deleted ValueSetCategory.
     * @return The deleted ValueSetCategory.
     * @throws ValueSetCategoryNotFoundException if no ValueSetCategory is found with the given id.
     */
    public ValueSetCategoryDto delete(Long valueSetCategoryId) throws ValueSetCategoryNotFoundException;

    /**
     * Finds all ValueSetCategories.
     *
     * @return A list of ValueSetCategories.
     */
    public List<ValueSetCategoryDto> findAll();

    /**
     * Finds ValueSetCategory by id.
     *
     * @param id The id of the wanted ValueSetCategory.
     * @return The found ValueSetCategory. If no ValueSetCategory is found, this method returns null.
     */
    public ValueSetCategoryDto findById(Long id);

    /**
     * Updates the information of a ValueSetCategory.
     *
     * @param updated The information of the updated ValueSetCategory.
     * @return The updated ValueSetCategory.
     * @throws ValueSetCategoryNotFoundException if no ValueSetCategory is found with given id.
     */
    public ValueSetCategoryDto update(ValueSetCategoryDto updated) throws ValueSetCategoryNotFoundException;

    /**
     * Find all value set categories add consent fields dto.
     *
     * @return the list
     */
    public List<AddConsentFieldsDto> findAllValueSetCategoriesAddConsentFieldsDto();
}
