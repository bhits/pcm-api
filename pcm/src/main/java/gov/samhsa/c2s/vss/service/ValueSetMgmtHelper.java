package gov.samhsa.c2s.vss.service;

import gov.samhsa.c2s.pcm.domain.consent.ConsentRepository;
import gov.samhsa.c2s.pcm.domain.valueset.*;
import gov.samhsa.c2s.vss.service.dto.*;
import gov.samhsa.c2s.pcm.domain.consent.Consent;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * The Class ValueSetMgmtHelper.
 */
public class ValueSetMgmtHelper {

    /**
     * The logger.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * The Constant CONCEPTCODES_CODE_CELL_NUM.
     */
    private static final int CONCEPTCODES_CODE_CELL_NUM = 0;

    /**
     * The Constant CONCEPTCODES_NAME_CELL_NUM.
     */
    private static final int CONCEPTCODES_NAME_CELL_NUM = 1;

    /**
     * The Constant CONCEPTCODES_DESCRIPTION_CELL_NUM.
     */
    private static final int CONCEPTCODES_DESC_CELL_NUM = 2;

    /**
     * The Constant CODE_FIELD.
     */
    private static final String CODE_FIELD = "code";

    /**
     * The Constant NAME_FIELD.
     */
    private static final String NAME_FIELD = "name";

    /**
     * The Constant DESC_FIELD.
     */
    private static final String DESC_FIELD = "description";

    /**
     * The Constant CATEGORY_NAME_FIELD.
     */
    private static final String CATEGORY_NAME_FIELD = "category name";

    /**
     * The Constant VALUE_SET_CODE_CELL_NUM.
     */
    private static final int VALUE_SET_CODE_CELL_NUM = 0;

    /**
     * The Constant VALUE_SET_NAME_CELL_NUM.
     */
    private static final int VALUE_SET_NAME_CELL_NUM = 1;

    /**
     * The Constant VALUE_SET_CATEGORY_NAME_CELL_NUM.
     */
    private static final int VALUE_SET_CATEGORY_NAME_CELL_NUM = 2;

    /**
     * The Constant VALUE_SET_CATEGORY_NAME_CELL_NUM.
     */
    private static final int VALUE_SET_DESC_CELL_NUM = 3;

    /**
     * The concept code list page size.
     */
    private final int conceptCodeListPageSize;

    /**
     * Instantiates a new value set mgmt helper.
     *
     * @param conceptCodeListPageSize the concept code list page size
     */
    public ValueSetMgmtHelper(int conceptCodeListPageSize) {
        super();
        this.conceptCodeListPageSize = conceptCodeListPageSize;
    }

    // Code System

    /**
     * Convert code system entities to dtos.
     *
     * @param codeSystems the code systems
     * @return the list
     */
    public List<CodeSystemDto> convertCodeSystemEntitiesToDtos(
            List<CodeSystem> codeSystems) {
        List<CodeSystemDto> codeSystemsDtos = new ArrayList<CodeSystemDto>();
        for (CodeSystem codeSystem : codeSystems) {
            CodeSystemDto codeSystemsDto = createCodeSystemDtoFromEntity(codeSystem);
            codeSystemsDtos.add(codeSystemsDto);
        }
        return codeSystemsDtos;
    }

    /**
     * Creates the code system dto from entity.
     *
     * @param codeSystem the code system
     * @return the code system dto
     */
    public CodeSystemDto createCodeSystemDtoFromEntity(CodeSystem codeSystem) {
        CodeSystemDto codeSystemDto = new CodeSystemDto();
        codeSystemDto.setCode(codeSystem.getCode());
        codeSystemDto.setCodeSystemOId(codeSystem.getCodeSystemOId());
        codeSystemDto.setDisplayName(codeSystem.getDisplayName());
        codeSystemDto.setName(codeSystem.getName());
        codeSystemDto.setId(codeSystem.getId());
        // is it eligible to delete
        // check if any versions are associated to the code systems
        List<CodeSystemVersion> codeSystemVersions = codeSystem
                .getCodeSystemVersions();
        if (null != codeSystemVersions && codeSystemVersions.size() > 0) {
            codeSystemDto.setDeletable(false);
        }
        return codeSystemDto;
    }

    /**
     * Convert code system entities to map.
     *
     * @param codeSystems the code systems
     * @return the map
     */
    public Map<Long, String> convertCodeSystemEntitiesToMap(
            List<CodeSystem> codeSystems) {
        Map<Long, String> codeSystemsMap = new HashMap<Long, String>();
        for (CodeSystem codeSystem : codeSystems) {
            codeSystemsMap.put(codeSystem.getId(), codeSystem.getCode());
        }
        return codeSystemsMap;
    }

    // Code System Version

    /**
     * Convert code system version entities to dtos.
     *
     * @param codeSystemVersions the code system versions
     * @return the list
     */
    public List<CodeSystemVersionDto> convertCodeSystemVersionEntitiesToDtos(
            List<CodeSystemVersion> codeSystemVersions) {
        List<CodeSystemVersionDto> codeSystemVersionDtos = new ArrayList<CodeSystemVersionDto>();
        for (CodeSystemVersion codeSystemVersion : codeSystemVersions) {
            CodeSystemVersionDto codeSystemVersionDto = createCodeSystemVersionDtoFromEntity(codeSystemVersion);
            codeSystemVersionDtos.add(codeSystemVersionDto);
        }
        return codeSystemVersionDtos;
    }

    /**
     * Creates the code system version dto from entity.
     *
     * @param codeSystemVersion the code system version
     * @return the code system version dto
     */
    public CodeSystemVersionDto createCodeSystemVersionDtoFromEntity(
            CodeSystemVersion codeSystemVersion) {
        CodeSystemVersionDto codeSystemVersionDto = new CodeSystemVersionDto();
        codeSystemVersionDto.setDescription(codeSystemVersion.getDescription());
        codeSystemVersionDto.setName(codeSystemVersion.getName());
        codeSystemVersionDto.setId(codeSystemVersion.getId());
        codeSystemVersionDto.setCodeSystemId(codeSystemVersion.getCodeSystem()
                .getId());
        codeSystemVersionDto.setCodeSystemName(codeSystemVersion
                .getCodeSystem().getName());
        // is it eligible to delete
        // check if any concept codes associated to this version
        List<ConceptCode> conceptCodes = codeSystemVersion.getConceptCodes();
        if (null != conceptCodes && conceptCodes.size() > 0) {
            codeSystemVersionDto.setDeletable(false);
        }
        return codeSystemVersionDto;
    }

    /**
     * Convert code system version entities to map.
     *
     * @param codeSystemVersions the code system versions
     * @return the map
     */
    public Map<Long, String> convertCodeSystemVersionEntitiesToMap(
            List<CodeSystemVersion> codeSystemVersions) {
        Map<Long, String> codeSystemVersionsMap = new HashMap<Long, String>();
        for (CodeSystemVersion codeSystemVersion : codeSystemVersions) {
            codeSystemVersionsMap.put(codeSystemVersion.getId(),
                    codeSystemVersion.getName());
        }
        return codeSystemVersionsMap;
    }

    // return each code system along with versions

    /**
     * Creates the cs versions form entity.
     *
     * @param cSystems the c systems
     * @return the map
     */
    public Map<Map<Long, String>, Map<Long, String>> createCSVersionsFormEntity(
            List<CodeSystem> cSystems) {
        Map<Map<Long, String>, Map<Long, String>> codeSVersions = new HashMap<Map<Long, String>, Map<Long, String>>();

        for (CodeSystem codeSystem : cSystems) {
            Map<Long, String> csMap = new HashMap<Long, String>();
            List<CodeSystemVersion> versions = codeSystem
                    .getCodeSystemVersions();
            Map<Long, String> versionMap = new HashMap<Long, String>();
            for (CodeSystemVersion csVersion : versions) {
                versionMap.put(csVersion.getId(), csVersion.getName());
            }
            csMap.put(codeSystem.getId(), codeSystem.getCode());
            codeSVersions.put(csMap, versionMap);
        }
        return codeSVersions;

    }

    // return each code system along with versions

    /**
     * Creates the cs versions dto form entity.
     *
     * @param cSystems the c systems
     * @return the list
     */
    public List<CSVersionsDto> createCSVersionsDTOFormEntity(
            List<CodeSystem> cSystems) {

        List<CSVersionsDto> csVersionsDtos = new ArrayList<CSVersionsDto>();

        for (CodeSystem codeSystem : cSystems) {
            CSVersionsDto csVersionsDto = new CSVersionsDto();
            List<CodeSystemVersion> versions = codeSystem
                    .getCodeSystemVersions();
            Map<Long, String> versionMap = new HashMap<Long, String>();
            for (CodeSystemVersion csVersion : versions) {
                versionMap.put(csVersion.getId(), csVersion.getName());
            }
            csVersionsDto.setCodeSystemId(codeSystem.getId());
            csVersionsDto.setCodeSystemName(codeSystem.getName());
            csVersionsDto.setVersionsMap(versionMap);
            csVersionsDtos.add(csVersionsDto);
        }
        return csVersionsDtos;

    }

    // Value Set Category

    /**
     * Convert value set category entities to dtos.
     *
     * @param valueSetCategories the value set categories
     * @param consentRepository  the consent repository
     * @return the list
     */
    public List<ValueSetCategoryDto> convertValueSetCategoryEntitiesToDtos(
            List<ValueSetCategory> valueSetCategories,
            ConsentRepository consentRepository) {
        List<ValueSetCategoryDto> valueSetCategoryDtos = new ArrayList<ValueSetCategoryDto>();
        for (ValueSetCategory valueSetCategory : valueSetCategories) {
            ValueSetCategoryDto valueSetCategoryDto = createValuesetCategoryDtoFromEntity(
                    valueSetCategory, consentRepository);
            valueSetCategoryDtos.add(valueSetCategoryDto);
        }
        return valueSetCategoryDtos;
    }

    /**
     * Creates the valueset category dto from entity.
     *
     * @param valueSetCategory  the value set category
     * @param consentRepository the consent repository
     * @return the value set category dto
     */
    public ValueSetCategoryDto createValuesetCategoryDtoFromEntity(
            ValueSetCategory valueSetCategory,
            ConsentRepository consentRepository) {
        ValueSetCategoryDto valueSetCategoryDto = new ValueSetCategoryDto();
        valueSetCategoryDto.setCode(valueSetCategory.getCode());
        valueSetCategoryDto.setDescription(valueSetCategory.getDescription());
        valueSetCategoryDto.setName(valueSetCategory.getName());
        valueSetCategoryDto.setId(valueSetCategory.getId());
        // is it eligible to delete
        // check if any value sets are associated to this category
        List<ValueSet> valueSets = valueSetCategory.getValueSets();
        if (null != valueSets && valueSets.size() > 0) {
            valueSetCategoryDto.setDeletable(false);
        }
        // TODO check if consents are created with not to share these categories
        List<Consent> consentS = consentRepository
                .findAllByDoNotShareSensitivityPolicyCodesValueSetCategory(valueSetCategory);
        if (null != consentS && consentS.size() > 0) {
            valueSetCategoryDto.setDeletable(false);
        }

        return valueSetCategoryDto;
    }

    /**
     * Convert value set category entities to map.
     *
     * @param valueSetCategories the value set categories
     * @return the map
     */
    public Map<Long, String> convertValueSetCategoryEntitiesToMap(
            List<ValueSetCategory> valueSetCategories) {
        Map<Long, String> valueSetCategoriesMap = new HashMap<Long, String>();
        for (ValueSetCategory valueSetCategory : valueSetCategories) {
            valueSetCategoriesMap.put(valueSetCategory.getId(),
                    valueSetCategory.getName());
        }
        return valueSetCategoriesMap;
    }

    // Value Set

    /**
     * Convert value set entities to dtos.
     *
     * @param valueSets the value sets
     * @return the list
     */
    public List<ValueSetDto> convertValueSetEntitiesToDtos(
            List<ValueSet> valueSets) {
        List<ValueSetDto> valueSetDtos = new ArrayList<ValueSetDto>();
        for (ValueSet valueSet : valueSets) {
            ValueSetDto valueSetDto = createValuesetDtoFromEntity(valueSet);
            valueSetDtos.add(valueSetDto);
        }
        return valueSetDtos;
    }

    /**
     * Convert value set entities to dtos without deletable.
     *
     * @param valueSets the value sets
     * @return the list
     */
    public List<ValueSetDto> convertValueSetEntitiesToDtosWithoutDeletable(
            List<ValueSet> valueSets) {
        List<ValueSetDto> valueSetDtos = new ArrayList<ValueSetDto>();

        for (ValueSet valueSet : valueSets) {
            ValueSetDto valueSetDto = createValuesetDtoFromEntityWithoutDeletable(valueSet);
            valueSetDtos.add(valueSetDto);
        }
        return valueSetDtos;
    }

    /**
     * Creates the valueset dto from entity.
     *
     * @param valueSet the value set
     * @return the value set dto
     */
    public ValueSetDto createValuesetDtoFromEntity(ValueSet valueSet) {
        ValueSetDto valueSetDto = new ValueSetDto();
        valueSetDto.setCode(valueSet.getCode());
        valueSetDto.setDescription(valueSet.getDescription());
        valueSetDto.setName(valueSet.getName());
        valueSetDto.setId(valueSet.getId());
        valueSetDto.setValueSetCategoryId(valueSet.getValueSetCategory()
                .getId());
        valueSetDto
                .setValueSetCatName(valueSet.getValueSetCategory().getName());
        valueSetDto
                .setValueSetCatCode(valueSet.getValueSetCategory().getCode());
        // is it eligible to delete
        // check if any concept codes associated to this version
        List<ConceptCodeValueSet> conceptCodeValueSets = valueSet
                .getConceptCodes();
        if (null != conceptCodeValueSets && conceptCodeValueSets.size() > 0) {
            valueSetDto.setDeletable(false);
        }
        return valueSetDto;
    }

    /**
     * Creates the valueset dto from entity.
     *
     * @param valueSet the value set
     * @return the value set dto
     */
    public ValueSetDto createValuesetDtoFromEntityWithoutDeletable(
            ValueSet valueSet) {
        ValueSetDto valueSetDto = new ValueSetDto();
        valueSetDto.setCode(valueSet.getCode());
        valueSetDto.setDescription(valueSet.getDescription());
        valueSetDto.setName(valueSet.getName());
        valueSetDto.setId(valueSet.getId());
        valueSetDto.setValueSetCategoryId(valueSet.getValueSetCategory()
                .getId());
        valueSetDto
                .setValueSetCatName(valueSet.getValueSetCategory().getName());
        valueSetDto
                .setValueSetCatCode(valueSet.getValueSetCategory().getCode());
        return valueSetDto;
    }

    /**
     * Convert value set entities to map.
     *
     * @param valueSets the value sets
     * @return the map
     */
    public Map<Long, String> convertValueSetEntitiesToMap(
            List<ValueSet> valueSets) {
        Map<Long, String> valueSetsMap = new HashMap<Long, String>();
        for (ValueSet valueSet : valueSets) {
            valueSetsMap.put(valueSet.getId(), valueSet.getCode());
        }
        return valueSetsMap;
    }

    // Concept Code

    /**
     * Creates the concept code dto from entity.
     *
     * @param conceptCode the concept code
     * @return the concept code dto
     */
    public ConceptCodeDto createConceptCodeDtoFromEntity(ConceptCode conceptCode) {
        ConceptCodeDto conceptCodeDto = new ConceptCodeDto();
        conceptCodeDto.setCode(conceptCode.getCode());
        conceptCodeDto.setName(conceptCode.getName());
        conceptCodeDto.setDescription(conceptCode.getDescription());
        conceptCodeDto.setId(conceptCode.getId());

        // Setting Value Set Name and category name
        List<ConceptCodeValueSet> valueSets = conceptCode.getValueSets();
        Map<Long, String> valueSetMap = new HashMap<Long, String>();
        for (ConceptCodeValueSet conceptCodeValueSet : valueSets) {
            // Get Value Sets
            ValueSet valueSet = conceptCodeValueSet.getValueSet();
            valueSetMap.put(valueSet.getId(), valueSet.getName());
        }
        conceptCodeDto.setValueSetMap(valueSetMap);

        // Setting code system name and version
        conceptCodeDto.setCodeSystemName(conceptCode.getCodeSystemVersion()
                .getCodeSystem().getName());

        // setting code system version name
        conceptCodeDto.setCodeSystemVersionName(conceptCode
                .getCodeSystemVersion().getName());

        return conceptCodeDto;
    }

    /**
     * Convert concept code entities to dtos.
     *
     * @param conceptCodes the concept codes
     * @return the list
     */
    public List<ConceptCodeDto> convertConceptCodeEntitiesToDtos(
            List<ConceptCode> conceptCodes) {
        List<ConceptCodeDto> conceptCodeDtos = new ArrayList<ConceptCodeDto>();
        for (ConceptCode conceptCode : conceptCodes) {
            ConceptCodeDto conceptCodeDto = createConceptCodeDtoFromEntity(conceptCode);
            conceptCodeDtos.add(conceptCodeDto);
        }
        return conceptCodeDtos;
    }

    // Value Set Lookup

    /**
     * Convert set to dto.
     *
     * @param catCodes the cat codes
     * @return the value set look up dto
     */
    public ValueSetLookUpDto convertSetToDto(Set<String> catCodes) {
        ValueSetLookUpDto lookUpDto = new ValueSetLookUpDto();
        return lookUpDto;

    }

    /**
     * Read concept codes from file.
     *
     * @param file                the file
     * @param codeSystemId        the code system id
     * @param codeSystemVersionId the code system version id
     * @param valueSetIds         the value set ids
     * @param userName            the user name
     * @return the list
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public List<ConceptCodeDto> readConceptCodesFromFile(MultipartFile file,
                                                         String codeSystemId, Long codeSystemVersionId,
                                                         List<Long> valueSetIds, String userName) throws IOException {

        validateInputs(codeSystemId, codeSystemVersionId, valueSetIds);

        List<ConceptCodeDto> listOfConceptCodesDtos = new ArrayList<ConceptCodeDto>();
        ConceptCodeDto conceptCodeDto;

        // Get the workbook instance for XLS file
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

        // Get first sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);

        // Iterate through each rows from first sheet
        Iterator<Row> rowIterator = sheet.iterator();

        Row row = null;

        // reading header row
        if (rowIterator.hasNext()) {
            Row headerRow = rowIterator.next();

            if (headerRow != null) {
                if (headerRow.getCell(CONCEPTCODES_CODE_CELL_NUM,
                        Row.RETURN_BLANK_AS_NULL) == null
                        || headerRow.getCell(CONCEPTCODES_NAME_CELL_NUM,
                        Row.RETURN_BLANK_AS_NULL) == null
                        || headerRow.getCell(CONCEPTCODES_DESC_CELL_NUM,
                        Row.RETURN_BLANK_AS_NULL) == null) {
                    throw new InvalidCSVException(
                            "Header row values in file should be in the following format: Code, Name, Description");

                } else if (!getCellValueAsString(
                        headerRow.getCell(CONCEPTCODES_CODE_CELL_NUM,
                                Row.RETURN_BLANK_AS_NULL)).equalsIgnoreCase(
                        CODE_FIELD)
                        || !getCellValueAsString(
                        headerRow.getCell(CONCEPTCODES_NAME_CELL_NUM,
                                Row.RETURN_BLANK_AS_NULL))
                        .equalsIgnoreCase(NAME_FIELD)
                        || !getCellValueAsString(
                        headerRow.getCell(CONCEPTCODES_DESC_CELL_NUM,
                                Row.RETURN_BLANK_AS_NULL))
                        .equalsIgnoreCase(DESC_FIELD)) {
                    throw new InvalidCSVException(
                            "Header row values in excel file should be in the following format: Code, Name, Description");
                }
            }
        }

        // iterate rows with value set fields
        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            conceptCodeDto = new ConceptCodeDto();

            if (row != null) {

                Cell codeCell = row.getCell(CONCEPTCODES_CODE_CELL_NUM,
                        Row.RETURN_BLANK_AS_NULL);
                Cell nameCell = row.getCell(CONCEPTCODES_NAME_CELL_NUM,
                        Row.RETURN_BLANK_AS_NULL);
                Cell descriptionCell = row.getCell(CONCEPTCODES_DESC_CELL_NUM,
                        Row.RETURN_BLANK_AS_NULL);

                // ignore empty row and throw error on missing fields
                if (codeCell == null || nameCell == null) {

                    if (codeCell != null || nameCell != null) {
                        throw new InvalidCSVException(
                                "Cannot add value set. Required field(s) empty for row: "
                                        + (row.getRowNum() + 1));
                    }
                } else {
                    conceptCodeDto.setCodeSystemName(codeSystemId);
                    conceptCodeDto.setCodeSystemVersionId(codeSystemVersionId);
                    conceptCodeDto.setValueSetIds(valueSetIds);
                    conceptCodeDto.setUserName(userName);
                    conceptCodeDto.setCode(getCellValueAsString(codeCell));
                    conceptCodeDto.setName(getCellValueAsString(nameCell));
                    conceptCodeDto
                            .setDescription(getCellValueAsString(descriptionCell));

                    listOfConceptCodesDtos.add(conceptCodeDto);
                }
            }
        }

        return listOfConceptCodesDtos;
    }

    /**
     * Read value sets from file.
     *
     * @param file     the file
     * @param userName the user name
     * @return the list
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public List<ValueSetDto> readValueSetsFromFile(MultipartFile file,
                                                   String userName) throws IOException {
        List<ValueSetDto> listOfvalueSets = new ArrayList<ValueSetDto>();

        // Get the workbook instance for XLS file
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

        // Get first sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);

        // Iterate through each rows from first sheet
        Iterator<Row> rowIterator = sheet.iterator();

        Row row = null;

        if (rowIterator.hasNext()) {
            Row headerRow = rowIterator.next();

            if (headerRow != null) {
                if (headerRow.getCell(VALUE_SET_CODE_CELL_NUM,
                        Row.RETURN_BLANK_AS_NULL) == null
                        || headerRow.getCell(VALUE_SET_NAME_CELL_NUM,
                        Row.RETURN_BLANK_AS_NULL) == null
                        || headerRow.getCell(VALUE_SET_CATEGORY_NAME_CELL_NUM,
                        Row.RETURN_BLANK_AS_NULL) == null
                        || headerRow.getCell(VALUE_SET_DESC_CELL_NUM,
                        Row.RETURN_BLANK_AS_NULL) == null) {
                    throw new InvalidCSVException(
                            "Header row values in file should be in the following format: Code, Name, Category Name, Description");

                } else if (!getCellValueAsString(
                        headerRow.getCell(VALUE_SET_CODE_CELL_NUM,
                                Row.RETURN_BLANK_AS_NULL)).equalsIgnoreCase(
                        CODE_FIELD)
                        || !getCellValueAsString(
                        headerRow.getCell(VALUE_SET_NAME_CELL_NUM,
                                Row.RETURN_BLANK_AS_NULL))
                        .equalsIgnoreCase(NAME_FIELD)
                        || !getCellValueAsString(
                        headerRow.getCell(
                                VALUE_SET_CATEGORY_NAME_CELL_NUM,
                                Row.RETURN_BLANK_AS_NULL))
                        .equalsIgnoreCase(CATEGORY_NAME_FIELD)
                        || !getCellValueAsString(
                        headerRow.getCell(VALUE_SET_DESC_CELL_NUM,
                                Row.RETURN_BLANK_AS_NULL))
                        .equalsIgnoreCase(DESC_FIELD)) {
                    throw new InvalidCSVException(
                            "Header row values in excel file should be in the following format: Code, Name, Category Name, Description");
                }
            }
        }

        // iterate rows with value set fields
        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            ValueSetDto valueSetDto = new ValueSetDto();

            if (row != null) {

                Cell codeCell = row.getCell(VALUE_SET_CODE_CELL_NUM,
                        Row.RETURN_BLANK_AS_NULL);
                Cell nameCell = row.getCell(VALUE_SET_NAME_CELL_NUM,
                        Row.RETURN_BLANK_AS_NULL);
                Cell categoryNameCell = row.getCell(
                        VALUE_SET_CATEGORY_NAME_CELL_NUM,
                        Row.RETURN_BLANK_AS_NULL);
                Cell descriptionCell = row.getCell(VALUE_SET_DESC_CELL_NUM,
                        Row.RETURN_BLANK_AS_NULL);

                // ignore empty row and throw error on missing fields
                if (codeCell == null || nameCell == null
                        || categoryNameCell == null) {

                    if (codeCell != null || nameCell != null
                            || categoryNameCell != null) {
                        throw new InvalidCSVException(
                                "Required field(s) empty for row: "
                                        + (row.getRowNum() + 1));
                    }
                } else {
                    valueSetDto.setUserName(userName);
                    valueSetDto.setCode(getCellValueAsString(codeCell));
                    valueSetDto.setName(getCellValueAsString(nameCell));
                    valueSetDto
                            .setDescription(getCellValueAsString(descriptionCell));
                    valueSetDto
                            .setValueSetCatName(getCellValueAsString(categoryNameCell));

                    listOfvalueSets.add(valueSetDto);

                }
            }
        }

        return listOfvalueSets;
    }

    /**
     * Gets the cell value as string.
     *
     * @param cell the cell
     * @return the cell value as string
     */
    public String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return null;
        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            logger.info("TEST: " + cell.toString());
            return cell.toString();
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            DataFormatter formatter = new DataFormatter();
            String formattedValue = formatter.formatCellValue(cell);
            return formattedValue;
        } else {
            throw new InvalidCSVException(
                    "Value stored in cell is invalid! Valid types are Numbers or Strings.");
        }
    }

    /**
     * Gets the concept code list page size.
     *
     * @return the concept code list page size
     */
    public int getConceptCodeListPageSize() {
        logger.debug("conceptCodeListPageSize: " + conceptCodeListPageSize);

        return conceptCodeListPageSize;
    }


    /**
     * Validate inputs.
     *
     * @param codeSystemId        the code system id
     * @param codeSystemVersionId the code system version id
     * @param valueSetIds         the value set ids
     */
    private static void validateInputs(String codeSystemId,
                                       Long codeSystemVersionId, List<Long> valueSetIds) {

        if (StringUtils.isBlank(codeSystemId) || codeSystemVersionId == null
                || valueSetIds == null || valueSetIds.isEmpty()) {
            throw new InvalidCSVException(
                    "Code System, Code System Version and Value Set Names need to be selected for Batch Upload");
        }

    }
}