package gov.samhsa.c2s.vss.service;

import gov.samhsa.c2s.pcm.domain.valueset.*;
import gov.samhsa.c2s.vss.service.dto.ConceptCodeDto;
import gov.samhsa.c2s.vss.service.dto.ConceptCodeVSCSDto;
import org.apache.poi.POIXMLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class ConceptCodeServiceImpl.
 */
public class ConceptCodeServiceImpl implements ConceptCodeService {

    /**
     * The concept code page size.
     */
    public final int CONCEPT_CODE_PAGE_SIZE;
    /**
     * The logger.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * The code system repository.
     */
    CodeSystemRepository codeSystemRepository;
    /**
     * The code system version repository.
     */
    CodeSystemVersionRepository codeSystemVersionRepository;
    /**
     * The concept code value set repository.
     */
    ConceptCodeValueSetRepository conceptCodeValueSetRepository;
    /**
     * The value set mgmt helper.
     */
    ValueSetMgmtHelper valueSetMgmtHelper;
    /**
     * The concept code repository.
     */
    private ConceptCodeRepository conceptCodeRepository;
    /**
     * The value set repository.
     */
    private ValueSetRepository valueSetRepository;

    /**
     * Instantiates a new concept code service impl.
     *
     * @param conceptCodePageSize           the concept code page size
     * @param conceptCodeRepository         the concept code repository
     * @param valueSetRepository            the value set repository
     * @param codeSystemRepository          the code system repository
     * @param codeSystemVersionRepository   the code system version repository
     * @param conceptCodeValueSetRepository the concept code value set repository
     * @param valueSetMgmtHelper            the value set mgmt helper
     */
    public ConceptCodeServiceImpl(int conceptCodePageSize,
                                  ConceptCodeRepository conceptCodeRepository,
                                  ValueSetRepository valueSetRepository,
                                  CodeSystemRepository codeSystemRepository,
                                  CodeSystemVersionRepository codeSystemVersionRepository,
                                  ConceptCodeValueSetRepository conceptCodeValueSetRepository,
                                  ValueSetMgmtHelper valueSetMgmtHelper) {
        super();
        CONCEPT_CODE_PAGE_SIZE = conceptCodePageSize;
        this.conceptCodeRepository = conceptCodeRepository;
        this.valueSetRepository = valueSetRepository;
        this.codeSystemRepository = codeSystemRepository;
        this.codeSystemVersionRepository = codeSystemVersionRepository;
        this.conceptCodeValueSetRepository = conceptCodeValueSetRepository;
        this.valueSetMgmtHelper = valueSetMgmtHelper;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.valueset.ConceptCodeService#delete(java
     * .lang.Long)
     */
    @Override
    @Transactional(rollbackFor = ConceptCodeNotFoundException.class)
    public ConceptCodeDto delete(Long conceptCodeId)
            throws ConceptCodeNotFoundException {
        logger.debug("Deleting ConceptCode with id: " + conceptCodeId);
        ConceptCode deleted = conceptCodeRepository.findOne(conceptCodeId);
        if (deleted == null) {
            logger.debug("No ConceptCode found with an id: " + conceptCodeId);
            throw new ConceptCodeNotFoundException();
        }
        conceptCodeRepository.delete(deleted);
        return valueSetMgmtHelper.createConceptCodeDtoFromEntity(deleted);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.valueset.ConceptCodeService#findById
     * (java.lang.Long)
     */
    @Override
    public ConceptCodeDto findById(Long id) throws ValueSetNotFoundException,
            ConceptCodeNotFoundException {
        logger.debug("Finding a ConceptCode with id: " + id);

        ConceptCode conceptCode = conceptCodeRepository.findOne(id);
        if (null == conceptCode) {
            throw new ConceptCodeNotFoundException(
                    "Selected Code is not Available");
        }
        // get all value sets in the system
        ConceptCodeDto conceptCodeDto = valueSetMgmtHelper
                .createConceptCodeDtoFromEntity(conceptCode);
        conceptCodeDto.setValueSetMap(getAllValueSetsInMap());

        // get all selected value sets that are associated with this concept
        // code
        conceptCodeDto
                .setValueSetIds(getAllValueSetIdsForConceptCode(conceptCode
                        .getId()));

        return conceptCodeDto;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.valueset.ConceptCodeService#update(gov
     * .samhsa.consent2share.service.dto.ConceptCodeDto)
     */
    @Override
    @Transactional(rollbackFor = ConceptCodeNotFoundException.class)
    public ConceptCodeDto update(ConceptCodeDto updated)
            throws ConceptCodeNotFoundException, ValueSetNotFoundException {
        logger.debug("Updating ConceptCode with information" + updated);

        ConceptCode conceptCode = conceptCodeRepository
                .findOne(updated.getId());
        if (conceptCode == null) {
            logger.debug("No ConceptCode found with an id: " + updated.getId());
            throw new ConceptCodeNotFoundException();
        }

        conceptCode.update(updated.getCode(), updated.getName(),
                updated.getUserName());

        List<Long> selVsIds = updated.getValueSetIds();
        // Atleast on valueset need to be associated with a concept code
        if (null == selVsIds || selVsIds.size() <= 0) {
            throw new ValueSetNotFoundException(
                    "Need to Associate atleast one valueset to the code: "
                            + updated.getCode());
        }

        // 3.Get the value sets associated to the concept code
        List<ConceptCodeValueSet> cValueSets = conceptCodeValueSetRepository
                .findAllByPkConceptCodeId(conceptCode.getId());
        if (cValueSets == null) {
            throw new ValueSetNotFoundException(
                    "No Valusets associated to the given code" + conceptCode);
        }

        List<ConceptCodeValueSet> selCodeValueSets = new ArrayList<ConceptCodeValueSet>();
        List<Long> selIds = new ArrayList<Long>();

        for (ConceptCodeValueSet aCodeValueSet : cValueSets) {

            for (Long selId : selVsIds) {
                if (aCodeValueSet.getValueSet().getId().equals(selId)) {
                    selCodeValueSets.add(aCodeValueSet);
                    selIds.add(selId);
                    break;
                }
            }

        }
        // remove the selected value sets
        cValueSets.removeAll(selCodeValueSets);
        selVsIds.removeAll(selIds);

        // delete the (existing that are not selected )association
        if (null != cValueSets && cValueSets.size() > 0)
            conceptCodeValueSetRepository.delete(cValueSets);

        // add the new associations
        List<ConceptCodeValueSet> cVSets = new ArrayList<ConceptCodeValueSet>();
        for (Long id : selVsIds) {
            ValueSet vSet = valueSetRepository.findOne(id);
            if (null == vSet) {
                throw new ValueSetNotFoundException(
                        "No Valueset Found for the Selected Name");
            }
            ConceptCodeValueSet cValueSet = ConceptCodeValueSet.getBuilder(
                    conceptCode, vSet).build();
            cVSets.add(cValueSet);
        }

        if (null != cVSets && cVSets.size() > 0)
            cVSets = conceptCodeValueSetRepository.save(cVSets);

        // set the selected ones back to concept code
        conceptCode.setValueSets(selCodeValueSets);

        return valueSetMgmtHelper.createConceptCodeDtoFromEntity(conceptCode);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.valueset.ConceptCodeService#findAll()
     */
    @Override
    public List<ConceptCodeDto> findAll() {
        logger.debug("Finding all conceptCodes");

        List<ConceptCode> conceptCodes = conceptCodeRepository.findAll();

        return valueSetMgmtHelper
                .convertConceptCodeEntitiesToDtos(conceptCodes);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.valueset.ConceptCodeService#findAll()
     */
    @Override
    public Map<String, Object> findAll(int pageNumber) {

        logger.debug("Finding all conceptCodes with paging");
        Sort sort = new Sort(new Order(Direction.ASC, "code"));
        PageRequest pageRequest = new PageRequest(pageNumber,
                CONCEPT_CODE_PAGE_SIZE, sort);

        Page<ConceptCode> pagedConceptCodes = conceptCodeRepository
                .findAll(pageRequest);
        logger.debug("Total Concept Codes: "
                + pagedConceptCodes.getTotalElements());
        logger.debug("Total Pages: " + pagedConceptCodes.getTotalPages());

        Map<String, Object> pageResultsMap = new HashMap<String, Object>();
        pageResultsMap.put("conceptCodes", valueSetMgmtHelper
                .convertConceptCodeEntitiesToDtos(pagedConceptCodes
                        .getContent()));
        pageResultsMap.put("totalNumberOfConceptCodes",
                pagedConceptCodes.getTotalElements());
        pageResultsMap.put("totalPages", pagedConceptCodes.getTotalPages());
        pageResultsMap.put("itemsPerPage", pagedConceptCodes.getSize());
        pageResultsMap.put("currentPage", pagedConceptCodes.getNumber());
        pageResultsMap.put("numberOfElements",
                pagedConceptCodes.getNumberOfElements());

        return pageResultsMap;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.valueset.ConceptCodeService#findAllByCode
     * (java.lang.String)
     */
    @Override
    public Map<String, Object> findAllByCode(String searchTerm,
                                             String codeSystem, String codeSystemVersion, String valueSetName,
                                             int pageNumber) {
        Sort sort = new Sort(new Order(Direction.ASC, "code"));
        PageRequest pageRequest = new PageRequest(pageNumber,
                CONCEPT_CODE_PAGE_SIZE, sort);

        Page<ConceptCode> pagedConceptCodes = conceptCodeRepository
                .findAllByCodeLike("%" + searchTerm + "%", "%" + codeSystem
                        + "%", "%" + codeSystemVersion + "%", "%"
                        + valueSetName + "%", pageRequest);
        logger.debug("Total Concept Codes: "
                + pagedConceptCodes.getTotalElements());
        logger.debug("Total Pages: " + pagedConceptCodes.getTotalPages());

        Map<String, Object> pageResultsMap = new HashMap<String, Object>();
        pageResultsMap.put("conceptCodes", valueSetMgmtHelper
                .convertConceptCodeEntitiesToDtos(pagedConceptCodes
                        .getContent()));
        pageResultsMap.put("totalNumberOfConceptCodes",
                pagedConceptCodes.getTotalElements());
        pageResultsMap.put("totalPages", pagedConceptCodes.getTotalPages());
        pageResultsMap.put("itemsPerPage", pagedConceptCodes.getSize());
        pageResultsMap.put("currentPage", pagedConceptCodes.getNumber());
        pageResultsMap.put("numberOfElements",
                pagedConceptCodes.getNumberOfElements());

        return pageResultsMap;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.valueset.ConceptCodeService#findAllByName
     * (java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * int)
     */
    @Override
    public Map<String, Object> findAllByName(String searchTerm,
                                             String codeSystem, String codeSystemVersion, String valueSetName,
                                             int pageNumber) {
        Sort sort = new Sort(new Order(Direction.ASC, "name"));
        PageRequest pageRequest = new PageRequest(pageNumber,
                CONCEPT_CODE_PAGE_SIZE, sort);

        if (StringUtils.isEmpty(valueSetName))
            valueSetName = "%";

        Page<ConceptCode> pagedConceptCodes = conceptCodeRepository
                .findAllByName("%" + searchTerm + "%", "%" + codeSystem + "%",
                        "%" + codeSystemVersion + "%", valueSetName,
                        pageRequest);
        logger.debug("Total Concept Codes: "
                + pagedConceptCodes.getTotalElements());
        logger.debug("Total Pages: " + pagedConceptCodes.getTotalPages());

        Map<String, Object> pageResultsMap = new HashMap<String, Object>();
        pageResultsMap.put("conceptCodes", valueSetMgmtHelper
                .convertConceptCodeEntitiesToDtos(pagedConceptCodes
                        .getContent()));
        pageResultsMap.put("totalNumberOfConceptCodes",
                pagedConceptCodes.getTotalElements());
        pageResultsMap.put("totalPages", pagedConceptCodes.getTotalPages());
        pageResultsMap.put("itemsPerPage", pagedConceptCodes.getSize());
        pageResultsMap.put("currentPage", pagedConceptCodes.getNumber());
        pageResultsMap.put("numberOfElements",
                pagedConceptCodes.getNumberOfElements());

        return pageResultsMap;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.valueset.ConceptCodeService#create()
     */
    @Override
    public ConceptCodeVSCSDto create() throws ValueSetNotFoundException,
            CodeSystemVersionNotFoundException, CodeSystemNotFoundException {
        ConceptCodeVSCSDto conceptCodeVSCSDto = new ConceptCodeVSCSDto();

        conceptCodeVSCSDto.setValueSetsMap(getAllValueSetsInMap());

        // Get all code system versions
        List<CodeSystem> codeSystems = codeSystemRepository.findAll();

        if (codeSystems == null || codeSystems.size() == 0) {
            logger.debug("No codeSystems found in the system");
            throw new CodeSystemNotFoundException();

        }
        conceptCodeVSCSDto.setCsVersions(valueSetMgmtHelper
                .createCSVersionsDTOFormEntity(codeSystems));

        return conceptCodeVSCSDto;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.valueset.ConceptCodeService#create(gov
     * .samhsa.consent2share.service.dto.ConceptCodeDto)
     */
    @Override
    @Transactional
    public ConceptCodeDto create(ConceptCodeDto created)
            throws ValueSetNotFoundException, CodeSystemNotFoundException,
            DuplicateConceptCodeException {

        ConceptCodeDto conceptCodeDto = new ConceptCodeDto();

        String description = (created.getDescription() != null) ? created
                .getDescription() : "";

        // STEP:1 :- Get CodeSystemVersion Object from DB
        Long codeSystemVersionId = created.getCodeSystemVersionId();

        // find code system version
        CodeSystemVersion selectedCsv = codeSystemVersionRepository
                .findOne(codeSystemVersionId);

        if (selectedCsv == null) {
            logger.debug("No Code System version found with an id: "
                    + codeSystemVersionId);
            throw new CodeSystemNotFoundException();
        }

        List<Long> selVsIds = created.getValueSetIds();

        if (null == selVsIds || selVsIds.size() <= 0) {
            throw new ValueSetNotFoundException(
                    "Need to Associate atleast one valueset to the code: "
                            + created.getCode());
        }

        boolean isNewVS = false;
        int conceptCodesInserted = 0;

        // Loop through for each selected value set
        for (Long valueSetId : selVsIds) {

            // STEP:2 :-Get valueset Object from DB
            ValueSet selectedVs = valueSetRepository.findOne(valueSetId);
            if (selectedVs == null) {
                logger.debug("No valueSet found with an id: " + valueSetId);
                throw new ValueSetNotFoundException();
            }

            // STEP:3 :- Code and its association with value set save

            // Check if conceptcode already exists for the code system version
            logger.debug("Creating a new ConceptCode with information: "
                    + created);
            ConceptCode conceptCode = conceptCodeRepository
                    .findByCodeAndCodeSystemVersionId(created.getCode(),
                            selectedCsv.getId());
            ConceptCodeValueSet conceptCodeValueSet = ConceptCodeValueSet
                    .getBuilder(conceptCode, selectedVs).build();

            if (null != conceptCode) {
                // check if association between code and valueset exists
                conceptCodeValueSet = conceptCodeValueSetRepository
                        .findOne(conceptCodeValueSet.getPk());

                if (null == conceptCodeValueSet) {
                    // Scenario-1 : Code Exists and Code with VS association
                    // does not exists
                    // build and save association
                    conceptCodeValueSet = ConceptCodeValueSet.getBuilder(
                            conceptCode, selectedVs).build();
                    // save association
                    conceptCodeValueSet = conceptCodeValueSetRepository
                            .save(conceptCodeValueSet);
                    isNewVS = true;
                    conceptCodesInserted++;
                } else {
                    // Scenario-2 : Code Exists and Code with VS association
                    // exists
                    logger.debug("Code and Code with VS association exists: "
                            + created);
                }

            } else {
                // Scenario-3 : Code and Code with VS association does not
                // exists

                // build conceptcode
                conceptCode = ConceptCode
                        .getBuilder(created.getCode(), created.getName(),
                                created.getUserName()).description(description)
                        .build();

                // set code system version
                conceptCode.setCodeSystemVersion(selectedCsv);

                // Create ConceptCode_Valueset object
                conceptCodeValueSet = ConceptCodeValueSet.getBuilder(
                        conceptCode, selectedVs).build();
                List<ConceptCodeValueSet> vs = new ArrayList<ConceptCodeValueSet>();
                vs.add(conceptCodeValueSet);

                // map association
                conceptCode.setValueSets(vs);
                isNewVS = true;

                // Save conceptcode
                conceptCode = conceptCodeRepository.save(conceptCode);
                conceptCodesInserted++;
            }

            conceptCodeDto = valueSetMgmtHelper
                    .createConceptCodeDtoFromEntity(conceptCode);
        }
        conceptCodeDto.setConceptCodesInserted(conceptCodesInserted);

        if (!isNewVS) {
            throw new DuplicateConceptCodeException();
        }
        return conceptCodeDto;

    }

    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.consent2share.service.valueset.ConceptCodeService#
     * conceptCodeBatchUpload
     * (gov.samhsa.consent2share.service.dto.ConceptCodeDto,
     * org.springframework.web.multipart.MultipartFile, java.lang.String,
     * java.lang.String, java.lang.Long)
     */
    @Override
    @Transactional(rollbackFor = DataIntegrityViolationException.class)
    public ConceptCodeDto conceptCodeBatchUpload(ConceptCodeDto conceptCodeDto,
                                                 MultipartFile file, String codeSystemId, Long codeSystemVersionId,
                                                 List<Long> valueSetIds) throws ValueSetNotFoundException,
            CodeSystemNotFoundException {

        String userName = conceptCodeDto.getUserName();

        try {
            List<ConceptCodeDto> listOfConceptCodeDtos = valueSetMgmtHelper
                    .readConceptCodesFromFile(file, codeSystemId,
                            codeSystemVersionId, valueSetIds, userName);

            int rowsUpdated = 0;
            // for batch upload
            List<Integer> listOfDuplicateCodes = new ArrayList<Integer>();

            // iterate rows with value set fields
            for (int i = 0; i < listOfConceptCodeDtos.size(); i++) {

                try {
                    conceptCodeDto = create(listOfConceptCodeDtos.get(i));
                    rowsUpdated = rowsUpdated
                            + conceptCodeDto.getConceptCodesInserted();
                } catch (DuplicateConceptCodeException e) {
                    // adjusting for header row
                    listOfDuplicateCodes.add(i + 2);
                }
            }
            conceptCodeDto.setConceptCodesInserted(rowsUpdated);
            conceptCodeDto.setListOfDuplicatesCodes(listOfDuplicateCodes);

        } catch (ValueSetNotFoundException ex) {
            logger.debug("Missing required field while doing batch upload: "
                    + ex.getMessage());
            conceptCodeDto.setError(true);
            conceptCodeDto.setErrorMessage(ex.getMessage());
            throw ex;
        } catch (InvalidCSVException ex) {
            conceptCodeDto.setError(true);
            conceptCodeDto.setErrorMessage(ex.getMessage());
            throw ex;
        } catch (POIXMLException ex) {
            logger.debug("Incorrect file format: " + ex.getMessage());
            conceptCodeDto.setError(true);
            conceptCodeDto
                    .setErrorMessage("Incorrect file format. File should be a correct .xslx file");
        } catch (IOException ex) {
            logger.debug("Unable to open file: " + ex.getMessage());
            conceptCodeDto.setError(true);
            conceptCodeDto.setErrorMessage("Unable to open file");
        } catch (Exception ex) {
            logger.debug("Exception thrown: " + ex.getMessage());
            conceptCodeDto.setError(true);
            conceptCodeDto
                    .setErrorMessage("An error occurred. Please check with administrator: "
                            + ex.getMessage());
        }
        return conceptCodeDto;
    }

    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.consent2share.service.valueset.ConceptCodeService#
     * findValueSetsByCodeSystem(java.lang.String, java.lang.String)
     */
    @Override
    public List<ValueSet> findValueSetsByCodeSystem(String codeSystem,
                                                    String codeSystemVersion) {
        return conceptCodeRepository.findValueSetNamesFilterByCodeSystem(
                codeSystem, codeSystemVersion);
    }

    /**
     * This setter method should be used only by unit tests.
     *
     * @param conceptCodeRepository the new concept code repository
     */
    protected void setConceptCodeRepository(
            ConceptCodeRepository conceptCodeRepository) {
        this.conceptCodeRepository = conceptCodeRepository;
    }

    /**
     * Gets the all value sets in map.
     *
     * @return the all value sets in map
     * @throws ValueSetNotFoundException the value set not found exception
     */
    private Map<Long, String> getAllValueSetsInMap()
            throws ValueSetNotFoundException {
        // Get all valuesets
        List<ValueSet> valueSets = valueSetRepository.findAll();

        if (valueSets == null || valueSets.size() == 0) {
            logger.debug("No Valuesets found in the system");
            throw new ValueSetNotFoundException(
                    "No Value Sets Found in the system");

        }
        return valueSetMgmtHelper.convertValueSetEntitiesToMap(valueSets);
    }

    /**
     * Gets the all value set ids for concept code.
     *
     * @param conceptCodeId the concept code id
     * @return the all value set ids for concept code
     */
    private List<Long> getAllValueSetIdsForConceptCode(Long conceptCodeId) {
        List<ConceptCodeValueSet> cValueSets = conceptCodeValueSetRepository
                .findAllByPkConceptCodeId(conceptCodeId);
        List<Long> selValueSetIds = new ArrayList<Long>();

        for (ConceptCodeValueSet cValueSet : cValueSets) {
            selValueSetIds.add(cValueSet.getValueSet().getId());
        }
        return selValueSetIds;
    }
}
