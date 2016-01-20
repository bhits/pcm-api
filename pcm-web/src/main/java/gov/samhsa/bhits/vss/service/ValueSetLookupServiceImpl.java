package gov.samhsa.bhits.vss.service;

import gov.samhsa.bhits.pcm.domain.valueset.*;
import gov.samhsa.bhits.vss.service.dto.ValueSetLookUpDto;
import gov.samhsa.bhits.vss.service.dto.ValueSetQueryDto;
import gov.samhsa.bhits.vss.service.dto.ValueSetQueryListDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The Class ValueSetLookupServiceImpl.
 */
public class ValueSetLookupServiceImpl implements ValueSetLookupService {

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
     * Instantiates a new value set lookup service impl.
     *
     * @param conceptCodeRepository         the concept code repository
     * @param valueSetRepository            the value set repository
     * @param codeSystemRepository          the code system repository
     * @param codeSystemVersionRepository   the code system version repository
     * @param conceptCodeValueSetRepository the concept code value set repository
     * @param valueSetMgmtHelper            the value set mgmt helper
     */
    public ValueSetLookupServiceImpl(
            ConceptCodeRepository conceptCodeRepository,
            ValueSetRepository valueSetRepository,
            CodeSystemRepository codeSystemRepository,
            CodeSystemVersionRepository codeSystemVersionRepository,
            ConceptCodeValueSetRepository conceptCodeValueSetRepository,
            ValueSetMgmtHelper valueSetMgmtHelper) {
        super();
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
     * @see gov.samhsa.consent2share.service.valueset.ValueSetLookupService#
     * lookupValueSetCategories(java.lang.String, java.lang.String)
     */
    @Override
    public ValueSetLookUpDto lookupValueSetCategories(String code,
                                                      String codeSystemOid) throws CodeSystemVersionNotFoundException,
            ConceptCodeNotFoundException, ValueSetNotFoundException {

        ValueSetLookUpDto valueSetLookUpDto = new ValueSetLookUpDto();
        valueSetLookUpDto.setCodeSystemOid(codeSystemOid);
        valueSetLookUpDto.setConceptCode(code);
        valueSetLookUpDto.setVsCategoryCodes(valueSetCategoriesInSet(code,
                codeSystemOid));

        return valueSetLookUpDto;
    }

    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.consent2share.service.valueset.ValueSetLookupService#
     * restfulValueSetCategories(java.lang.String, java.lang.String)
     */
    @Override
    public ValueSetQueryDto restfulValueSetCategories(String code,
                                                      String codeSystemOid) throws CodeSystemVersionNotFoundException,
            ConceptCodeNotFoundException, ValueSetNotFoundException {

        ValueSetQueryDto valueSetQueryDto = new ValueSetQueryDto();
        valueSetQueryDto.setCodeSystemOid(codeSystemOid);
        valueSetQueryDto.setConceptCode(code);
        valueSetQueryDto.setVsCategoryCodes(valueSetCategoriesInSet(code,
                codeSystemOid));

        return valueSetQueryDto;
    }

    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.consent2share.service.valueset.ValueSetLookupService#
     * restfulValueSetCategories
     * (gov.samhsa.consent2share.service.dto.ValueSetQueryListDto)
     */
    @Override
    public ValueSetQueryListDto restfulValueSetCategories(
            ValueSetQueryListDto valueSetQueryListDtos)
            throws CodeSystemVersionNotFoundException,
            ConceptCodeNotFoundException, ValueSetNotFoundException {
        Set<ValueSetQueryDto> valueSetQueryDtos = valueSetQueryListDtos
                .getValueSetQueryDtos();

        for (ValueSetQueryDto valueSetQueryDto : valueSetQueryDtos) {
            valueSetQueryDto.setVsCategoryCodes(valueSetCategoriesInSet(
                    valueSetQueryDto.getConceptCode(),
                    valueSetQueryDto.getCodeSystemOid()));
            logger.debug("ValueSetQueryDto : " + valueSetQueryDto);
        }

        return valueSetQueryListDtos;
    }

    /**
     * Value set categories in set.
     *
     * @param code          the code
     * @param codeSystemOid the code system oid
     * @return the sets the
     * @throws CodeSystemVersionNotFoundException the code system version not found exception
     * @throws ConceptCodeNotFoundException       the concept code not found exception
     * @throws ValueSetNotFoundException          the value set not found exception
     */
    private Set<String> valueSetCategoriesInSet(String code,
                                                String codeSystemOid) throws CodeSystemVersionNotFoundException,
            ConceptCodeNotFoundException, ValueSetNotFoundException {

        Set<String> vsCategories = new HashSet<String>();

        // validate the inputs
        if (null == code || code.length() <= 0) {
            throw new ConceptCodeNotFoundException();
        }

        // 1.Get latest version of Code System version for the given code system
        // oid
        List<CodeSystemVersion> codeSystemVersions = codeSystemVersionRepository
                .findAllByCodeSystemCodeSystemOIdOrderByIdDesc(codeSystemOid);
        if (codeSystemVersions == null || codeSystemVersions.size() <= 0) {
            logger.debug("No CodeSystem found with the oid: " + codeSystemOid);
            throw new CodeSystemVersionNotFoundException(
                    "No Code System Versions found for the given codesystem oid"
                            + codeSystemOid);
        }
        // Get the latest version
        CodeSystemVersion codeSystemVersion = codeSystemVersions.get(0);

        // 2.Get the concept code for the given code and the latest code system
        // version
        ConceptCode conceptCode = conceptCodeRepository
                .findByCodeAndCodeSystemVersionId(code.trim(),
                        codeSystemVersion.getId());
        if (conceptCode == null) {
            throw new ConceptCodeNotFoundException(
                    "No Concept Code found for the given Code System  oid: "
                            + codeSystemOid + " And its latest version name: "
                            + codeSystemVersion.getName());
        }

        // 3.Get the value sets associated to the concept code
        List<ConceptCodeValueSet> cValueSets = conceptCodeValueSetRepository
                .findAllByPkConceptCodeId(conceptCode.getId());
        if (cValueSets == null) {
            throw new ValueSetNotFoundException(
                    "No Valusets associated to the given codes" + conceptCode);
        }
        for (ConceptCodeValueSet codeValueSet : cValueSets) {
            // get the category code for the associated valuesets
            vsCategories.add(codeValueSet.getValueSet().getValueSetCategory()
                    .getCode());
        }

        return vsCategories;
    }
}
