package gov.samhsa.mhc.vss.service;

import gov.samhsa.mhc.pcm.domain.valueset.CodeSystem;
import gov.samhsa.mhc.pcm.domain.valueset.CodeSystemRepository;
import gov.samhsa.mhc.pcm.domain.valueset.CodeSystemVersion;
import gov.samhsa.mhc.pcm.domain.valueset.CodeSystemVersionRepository;
import gov.samhsa.mhc.vss.service.dto.CodeSystemVersionCSDto;
import gov.samhsa.mhc.vss.service.dto.CodeSystemVersionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The Class CodeSystemVersionServiceImpl.
 */
public class CodeSystemVersionServiceImpl implements CodeSystemVersionService {

    /**
     * The logger.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * The code system version mgmt helper.
     */
    ValueSetMgmtHelper codeSystemVersionMgmtHelper;
    /**
     * The code system version repository.
     */
    private CodeSystemVersionRepository codeSystemVersionRepository;
    /**
     * The code system repository.
     */
    private CodeSystemRepository codeSystemRepository;

    /**
     * Instantiates a new code system version service impl.
     *
     * @param codeSystemVersionRepository the code system version repository
     * @param codeSystemRepository        the code system repository
     * @param codeSystemVersionMgmtHelper the code system version mgmt helper
     */
    public CodeSystemVersionServiceImpl(
            CodeSystemVersionRepository codeSystemVersionRepository,
            CodeSystemRepository codeSystemRepository,
            ValueSetMgmtHelper codeSystemVersionMgmtHelper) {
        super();
        this.codeSystemVersionRepository = codeSystemVersionRepository;
        this.codeSystemRepository = codeSystemRepository;
        this.codeSystemVersionMgmtHelper = codeSystemVersionMgmtHelper;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.valueset.CodeSystemVersionService#create
     * (gov.samhsa.consent2share.service.dto.CodeSystemVersionDto)
     */
    @Override
    @Transactional
    public CodeSystemVersionDto create(CodeSystemVersionDto created)
            throws CodeSystemNotFoundException {
        logger.debug("Creating a new CodeSystemVersion with information: "
                + created);
        String description = (created.getDescription() != null) ? created
                .getDescription() : "";
        CodeSystemVersion codeSystemVersion = CodeSystemVersion
                .getBuilder(created.getCode(), created.getName(),
                        created.getUserName()).description(description).build();
        // Step:1 Save the codesystemversion in the codesystemversion table.
        codeSystemVersion = codeSystemVersionRepository.save(codeSystemVersion);

        // get the codesystemv entity from chosen codesystem id
        Long codeSystemId = created.getCodeSystemId();

        CodeSystem selected = codeSystemRepository.findOne(codeSystemId);
        if (selected == null) {
            logger.debug("No CodeSystem found with an id: " + codeSystemId);
            throw new CodeSystemNotFoundException();
        }

        // refere codesystemversion category id to the codesystemversion entity
        // object
        codeSystemVersion.setCodeSystem(selected);
        CodeSystemVersionDto codeSystemVersionDto = codeSystemVersionMgmtHelper
                .createCodeSystemVersionDtoFromEntity(codeSystemVersion);
        codeSystemVersionDto.setCodeSystemName(selected.getName());

        return codeSystemVersionDto;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.valueset.CodeSystemVersionService#delete
     * (java.lang.Long)
     */
    @Override
    @Transactional(rollbackFor = CodeSystemVersionNotFoundException.class)
    public CodeSystemVersionDto delete(Long codeSystemVersionId)
            throws CodeSystemVersionNotFoundException {
        logger.debug("Deleting CodeSystemVersion with id: "
                + codeSystemVersionId);
        CodeSystemVersion deleted = codeSystemVersionRepository
                .findOne(codeSystemVersionId);
        if (deleted == null) {
            logger.debug("No CodeSystemVersion found with an id: "
                    + codeSystemVersionId);
            throw new CodeSystemVersionNotFoundException();
        }
        codeSystemVersionRepository.delete(deleted);
        return codeSystemVersionMgmtHelper
                .createCodeSystemVersionDtoFromEntity(deleted);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.valueset.CodeSystemVersionService#findAll
     * ()
     */
    @Override
    public List<CodeSystemVersionDto> findAll() {
        logger.debug("Finding all codeSystemVersions");
        List<CodeSystemVersion> codeSystemVersions = codeSystemVersionRepository
                .findAll();
        return groupByCodeSystem(codeSystemVersionMgmtHelper
                .convertCodeSystemVersionEntitiesToDtos(codeSystemVersions));
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.valueset.CodeSystemVersionService#findById
     * (java.lang.Long)
     */
    @Override
    public CodeSystemVersionDto findById(Long id)
            throws CodeSystemNotFoundException {
        logger.debug("Finding a CodeSystemVersion with id: " + id);
        CodeSystemVersion codeSystemVersion = codeSystemVersionRepository
                .findOne(id);
        CodeSystemVersionDto codeSystemVersionDto = codeSystemVersionMgmtHelper
                .createCodeSystemVersionDtoFromEntity(codeSystemVersion);
        // Get all code systems
        List<CodeSystem> codeSystems = codeSystemRepository.findAll();

        if (codeSystems == null || codeSystems.size() == 0) {
            logger.debug("No Code Systems found in the system");
            throw new CodeSystemNotFoundException(
                    "No Code Systems found in the system");

        }
        codeSystemVersionDto.setCodeSystemMap(codeSystemVersionMgmtHelper
                .convertCodeSystemEntitiesToMap(codeSystems));

        return codeSystemVersionDto;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.valueset.CodeSystemVersionService#update
     * (gov.samhsa.consent2share.service.dto.CodeSystemVersionDto)
     */
    @Override
    @Transactional(rollbackFor = CodeSystemVersionNotFoundException.class)
    public CodeSystemVersionDto update(CodeSystemVersionDto updated)
            throws CodeSystemVersionNotFoundException,
            CodeSystemNotFoundException {
        logger.debug("Updating CodeSystemVersion with information" + updated);

        CodeSystemVersion codeSystemVersion = codeSystemVersionRepository
                .findOne(updated.getId());
        if (codeSystemVersion == null) {
            logger.debug("No CodeSystemVersion found with an id: "
                    + updated.getId());
            throw new CodeSystemVersionNotFoundException();
        }

        codeSystemVersion.update(updated.getCode(), updated.getName(),
                updated.getDescription(), updated.getUserName());

        // code system association change
        Long selCodeSystemId = updated.getCodeSystemId();
        Long origCodeSystemId = codeSystemVersion.getCodeSystem().getId();

        if ((null != selCodeSystemId && null != origCodeSystemId)
                && selCodeSystemId.equals(origCodeSystemId)) {
            logger.debug("category association already exists");
        } else {
            CodeSystem codeSystem = codeSystemRepository
                    .findOne(selCodeSystemId);
            if (codeSystem == null) {
                logger.debug("No Code System found for the selected id"
                        + selCodeSystemId);
                throw new CodeSystemNotFoundException(
                        "No Code System found for the selected id"
                                + selCodeSystemId);

            }
            // save the association
            codeSystemVersion.setCodeSystem(codeSystem);
        }
        return codeSystemVersionMgmtHelper
                .createCodeSystemVersionDtoFromEntity(codeSystemVersion);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.valueset.CodeSystemVersionService#create
     * ()
     */
    @Override
    public CodeSystemVersionCSDto create() throws CodeSystemNotFoundException {
        CodeSystemVersionCSDto codeSystemVersionVSCDto = new CodeSystemVersionCSDto();
        // Get all valuesets
        List<CodeSystem> codeSystems = codeSystemRepository.findAll();

        if (codeSystems == null || codeSystems.size() == 0) {
            logger.debug("No Code Systems found in the system");
            throw new CodeSystemNotFoundException();

        }
        codeSystemVersionVSCDto.setCodeSystemDtoMap(codeSystemVersionMgmtHelper
                .convertCodeSystemEntitiesToMap(codeSystems));
        return codeSystemVersionVSCDto;
    }

    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.consent2share.service.valueset.CodeSystemVersionService#
     * groupByCodeSystem(java.util.List)
     */
    @Override
    public List<CodeSystemVersionDto> groupByCodeSystem(
            List<CodeSystemVersionDto> codeSystemVersionDtos) {
        Collections.sort(codeSystemVersionDtos,
                new Comparator<CodeSystemVersionDto>() {
                    @Override
                    public int compare(CodeSystemVersionDto cd1,
                                       CodeSystemVersionDto cd2) {
                        return cd1.getCodeSystemId().compareTo(
                                cd2.getCodeSystemId());

                    }
                });

        return codeSystemVersionDtos;
    }

    /**
     * This setter method should be used only by unit tests.
     *
     * @param codeSystemVersionRepository the new code system version repository
     */
    protected void setCodeSystemVersionRepository(
            CodeSystemVersionRepository codeSystemVersionRepository) {
        this.codeSystemVersionRepository = codeSystemVersionRepository;
    }
}