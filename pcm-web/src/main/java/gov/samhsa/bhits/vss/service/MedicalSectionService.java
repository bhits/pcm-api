package gov.samhsa.bhits.vss.service;

import gov.samhsa.bhits.vss.service.dto.AddConsentFieldsDto;
import gov.samhsa.bhits.vss.service.dto.MedicalSectionDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface MedicalSectionService {

    /**
     * Creates a new person.
     *
     * @param created The information of the created person.
     * @return The created person.
     */
    public MedicalSectionDto create(MedicalSectionDto created);

    /**
     * Deletes a MedicalSection.
     *
     * @param medicalSectionId The id of the deleted MedicalSection.
     * @return The deleted MedicalSection.
     * @throws MedicalSectionNotFoundException if no MedicalSection is found with the given id.
     */
    public MedicalSectionDto delete(Long medicalSectionId) throws MedicalSectionNotFoundException;

    /**
     * Finds all MedicalSections.
     *
     * @return A list of MedicalSections.
     */
    public List<MedicalSectionDto> findAll();

    /**
     * Finds MedicalSection by id.
     *
     * @param id The id of the wanted MedicalSection.
     * @return The found MedicalSection. If no MedicalSection is found, this method returns null.
     */
    public MedicalSectionDto findById(Long id);

    /**
     * Updates the information of a MedicalSection.
     *
     * @param updated The information of the updated MedicalSection.
     * @return The updated MedicalSection.
     * @throws MedicalSectionNotFoundException if no MedicalSection is found with given id.
     */
    public MedicalSectionDto update(MedicalSectionDto updated) throws MedicalSectionNotFoundException;

    /**
     * Find all value set categories add consent fields dto.
     *
     * @return the list
     */
    public List<AddConsentFieldsDto> findAllMedicalSectionsAddConsentFieldsDto();
}
