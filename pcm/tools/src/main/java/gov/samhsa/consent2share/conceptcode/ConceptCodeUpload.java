package gov.samhsa.consent2share.conceptcode;

import gov.samhsa.consent2share.domain.valueset.CodeSystemVersion;
import gov.samhsa.consent2share.domain.valueset.CodeSystemVersionRepository;
import gov.samhsa.consent2share.domain.valueset.ConceptCode;
import gov.samhsa.consent2share.domain.valueset.ConceptCodeRepository;
import gov.samhsa.consent2share.domain.valueset.ConceptCodeValueSet;
import gov.samhsa.consent2share.domain.valueset.ConceptCodeValueSetRepository;
import gov.samhsa.consent2share.domain.valueset.ValueSet;
import gov.samhsa.consent2share.domain.valueset.ValueSetRepository;
import gov.samhsa.consent2share.service.dto.ConceptCodeDto;
import gov.samhsa.consent2share.service.valueset.CodeSystemNotFoundException;
import gov.samhsa.consent2share.service.valueset.DuplicateConceptCodeException;
import gov.samhsa.consent2share.service.valueset.InvalidCSVException;
import gov.samhsa.consent2share.service.valueset.ValueSetMgmtHelper;
import gov.samhsa.consent2share.service.valueset.ValueSetNotFoundException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public class ConceptCodeUpload {
	public static final String CC_ICD9_FILE_LOCATION = "C:\\java\\test-files\\cd_diagnosis_icd9_code_SMART Production.xlsx";
	public static final String CC_RxNORM_FILE_LOCATION = "C:\\java\\test-files\\SMART medications with RxCodes V2.1.xlsx";

	public static final String CC_ICD9_UPLOAD_FILE_LOCATION = "C:\\java\\test-files\\cd_diagnosis_ic9_code_SMART Production - Stats v2 2 BTB Revised - ICD9 Description Added.xlsx";
	public static final String CC_RxNorm_UPLOAD_FILE_LOCATION = "C:\\java\\test-files\\SMART medications with RxCodes V2.2.xlsx";

	public static final int CONCEPTCODES_ICD9_CODE_CELL_NUM = 1;
	public static final int CONCEPTCODES_ICD9_NAME_CELL_NUM = 2;
	public static final int CONCEPTCODES_ICD9_DESCRIPTION_CELL_NUM = 2;
	public static final int CONCEPTCODES_ICD9_EXISTS_IN_C2S_CELL_NUM = 14;
	public static final int CONCEPTCODES_ICD9_VALUESET_CELL_NUM = 15;

	public static final int CONCEPTCODES_RxNORM_CODE_CELL_NUM = 3;
	public static final int CONCEPTCODES_RxNORM_VALUESET_CELL_NUM = 5;
	public static final int CONCEPTCODES_RxNORM_EXISTS_IN_C2S_CELL_NUM = 7;

	public static final int CONCEPTCODES_ICD9_UPLOAD_CODE_CELL_NUM = 1;
	public static final int CONCEPTCODES_ICD9_UPLOAD_EXISTS_IN_C2S_CELL_NUM = 14;
	public static final int CONCEPTCODES_ICD9_UPLOAD_DESCRIPTION_CELL_NUM = 2;
	public static final int CONCEPTCODES_ICD9_UPLOAD_ALT_DESCRIPTION_CELL_NUM = 15;
	public static final int CONCEPTCODES_ICD9_UPLOAD_VALUESET_CELL_NUM = 16;

	public static final int CONCEPTCODES_RxNORM_UPLOAD_CODE_CELL_NUM = 3;
	public static final int CONCEPTCODES_RxNORM_UPLOAD_DESCRIPTION_CELL_NUM = 1;
	public static final int CONCEPTCODES_RxNORM_UPLOAD_VALUESET_CELL_NUM = 5;

	public static final String ICD9_CODE_SYSTEM_NAME = "ICD-9-Clinical Modification";
	public static final Long ICD9_CODE_SYSTEM_VERSION_ID = 18L;

	public static final String RxNORM_CODE_SYSTEM_NAME = "RxNorm";
	public static final Long RxNORM_CODE_SYSTEM_VERSION_ID = 22L;

	private static ValueSetMgmtHelper valueSetMgmtHelper = new ValueSetMgmtHelper(
			20);

	private static ConceptCodeRepository conceptCodeRepository;
	private static CodeSystemVersionRepository codeSystemVersionRepository;
	private static ValueSetRepository valueSetRepository;
	private static ConceptCodeValueSetRepository conceptCodeValueSetRepository;

	private static File cc_icd9_file;
	private static File cc_rxnorm_file;

	public static void main(String[] args) throws FileNotFoundException,
			IOException, ValueSetNotFoundException,
			CodeSystemNotFoundException, DuplicateConceptCodeException {

		AbstractApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "util/spring-config.xml" });
		conceptCodeRepository = context.getBean(ConceptCodeRepository.class);
		codeSystemVersionRepository = context
				.getBean(CodeSystemVersionRepository.class);
		valueSetRepository = context.getBean(ValueSetRepository.class);
		conceptCodeValueSetRepository = context
				.getBean(ConceptCodeValueSetRepository.class);

		File cc_icd9_file = new File(CC_ICD9_FILE_LOCATION);
		File cc_rxnorm_file = new File(CC_RxNORM_FILE_LOCATION);

		// findExistingCodesInFileForICD9(cc_icd9_file);

		// findExistingCodesInFileForRxNorm(cc_rxnorm_file);

		// deleteICD9CodesNotRequiredInC2S(cc_icd9_file);

		// deleteAllICD9InC2S();
		// batchUploadICD9ConceptCodesFromFile(CC_ICD9_UPLOAD_FILE_LOCATION);
		//
		deleteAllRxNormInC2S();
		batchUploadRxNormConceptCodesFromFile(CC_RxNorm_UPLOAD_FILE_LOCATION);

		context.close();
	}

	@Transactional
	private static void findExistingCodesInFileForICD9(File cc_file)
			throws FileNotFoundException, IOException {
		boolean foundCode = false, anyDuplicateCodes = false;
		int numFound = 0;
		FileInputStream fileInputStream = new FileInputStream(cc_file);

		XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
		XSSFSheet sheet = workbook.getSheetAt(1);
		Iterator<Row> rowIterator = sheet.iterator();
		Row row = null;

		// reading and ignoring header row
		if (rowIterator.hasNext())
			rowIterator.next();

		while (rowIterator.hasNext()) {
			row = rowIterator.next();

			if (row != null) {

				Cell codeCell = row.getCell(CONCEPTCODES_ICD9_CODE_CELL_NUM,
						Row.RETURN_BLANK_AS_NULL);

				// ignore empty row and throw error on missing fields
				if (codeCell == null)
					throw new InvalidCSVException(
							"Concept code cannot be empty on row: "
									+ (row.getRowNum() + 1));
				else {
					ConceptCode code = conceptCodeRepository
							.findByCode(getCellValueAsString(codeCell));

					if (code != null) {
						foundCode = true;
						numFound++;
						row.getCell(CONCEPTCODES_ICD9_EXISTS_IN_C2S_CELL_NUM,
								Row.CREATE_NULL_AS_BLANK).setCellValue("Yes");

						List<String> valueSetNames = conceptCodeRepository
								.findValueSetsForConceptCodes(getCellValueAsString(codeCell));

						String valuesetValue = new String();
						for (int i = 0; i < valueSetNames.size(); i++) {
							valuesetValue = valuesetValue.concat(valueSetNames
									.get(i));
							if (i + 1 != valueSetNames.size())
								valuesetValue = valuesetValue.concat(", "
										+ valueSetNames.get(i));

						}
						row.getCell(CONCEPTCODES_ICD9_VALUESET_CELL_NUM,
								Row.CREATE_NULL_AS_BLANK).setCellValue(
								valuesetValue);

					} else {
						row.getCell(CONCEPTCODES_ICD9_EXISTS_IN_C2S_CELL_NUM,
								Row.CREATE_NULL_AS_BLANK).setCellValue("No");

					}

				}
			}
		}

		fileInputStream.close();

		FileOutputStream outFile = new FileOutputStream(cc_file);
		workbook.write(outFile);
		outFile.close();

		System.out.println("Found code: " + foundCode);
		System.out.println("Number Duplicates: " + numFound);
		System.out.println("Duplicate codes: " + anyDuplicateCodes);

	}

	@Transactional
	private static void findExistingCodesInFileForRxNorm(File cc_file)
			throws FileNotFoundException, IOException {
		boolean foundCode = false, anyDuplicateCodes = false;
		int numFound = 0;
		FileInputStream fileInputStream = new FileInputStream(cc_file);

		XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		Row row = null;
		int rowIgnored = 0;
		int multipleValueSets = 0;

		// reading and ignoring header row
		if (rowIterator.hasNext())
			rowIterator.next();

		while (rowIterator.hasNext()) {
			row = rowIterator.next();

			if (row != null) {

				Cell codeCell = row.getCell(CONCEPTCODES_RxNORM_CODE_CELL_NUM,
						Row.RETURN_BLANK_AS_NULL);

				// ignore empty row and throw error on missing fields
				if (codeCell == null) {
					rowIgnored++;
					// throw new
					// InvalidCSVException("Concept code cannot be empty on row: "
					// + (row.getRowNum() + 1));
				} else {
					List<ConceptCode> codes = null; // conceptCodeRepository.findByCode(getCellValueAsString(codeCell));

					if (codes.size() > 1) {
						System.out.println("WHYYYY");
					}

					if (codes.size() > 0) {
						foundCode = true;
						numFound++;
						int index = 0;

						row.getCell(CONCEPTCODES_RxNORM_EXISTS_IN_C2S_CELL_NUM,
								Row.CREATE_NULL_AS_BLANK).setCellValue("Yes");

						List<Object[]> valueSetNames = conceptCodeRepository
								.findValueSetsAndCategoriesForConceptCodes(getCellValueAsString(codeCell));

						String displayableValuesetValue = new String();

						for (Object[] valueSet : valueSetNames) {

							if (valueSetNames.size() > index++) {
								displayableValuesetValue = displayableValuesetValue
										.concat((String) valueSet[0])
										.concat(" (")
										.concat((String) valueSet[1])
										.concat(")\n");

								multipleValueSets++;
							}

							// System.out.println(valueSetNames);
							// String displayableValuesetValue =
							// valueSetName.concat(" (").concat(valueSetCategory).concat(")");
						}
						System.out.println("DVS: " + displayableValuesetValue);
						CellStyle cs = workbook.createCellStyle();
						cs.setWrapText(true);

						Cell cell = row.getCell(
								CONCEPTCODES_RxNORM_VALUESET_CELL_NUM,
								Row.CREATE_NULL_AS_BLANK);

						cell.setCellValue(displayableValuesetValue);
						cell.setCellStyle(cs);

					} else {
						row.getCell(CONCEPTCODES_RxNORM_EXISTS_IN_C2S_CELL_NUM,
								Row.CREATE_NULL_AS_BLANK).setCellValue("No");

					}

				}
			}
		}

		fileInputStream.close();

		FileOutputStream outFile = new FileOutputStream(cc_file);
		workbook.write(outFile);
		outFile.close();

		System.out.println("Found code: " + foundCode);
		System.out.println("Number Duplicates: " + numFound);
		System.out.println("Duplicate codes: " + anyDuplicateCodes);
		System.out.println("Rows ignored: " + rowIgnored);
		System.out.println("Multiple vlaue sets: " + multipleValueSets);
	}

	public static void batchUploadICD9ConceptCodesFromFile(String cc_file)
			throws ValueSetNotFoundException, CodeSystemNotFoundException,
			FileNotFoundException, IOException, DuplicateConceptCodeException {

		FileInputStream fileInputStream = new FileInputStream(cc_file);

		XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
		XSSFSheet sheet = workbook.getSheetAt(1);
		Iterator<Row> rowIterator = sheet.iterator();
		Row row = null;

		// reading and ignoring header row
		if (rowIterator.hasNext())
			rowIterator.next();

		while (rowIterator.hasNext()) {
			row = rowIterator.next();

			if (row != null) {

				Cell codeCell = row.getCell(
						CONCEPTCODES_ICD9_UPLOAD_CODE_CELL_NUM,
						Row.RETURN_BLANK_AS_NULL);
				Cell existsInC2SCell = row.getCell(
						CONCEPTCODES_ICD9_UPLOAD_EXISTS_IN_C2S_CELL_NUM,
						Row.RETURN_BLANK_AS_NULL);
				Cell valueSetNameC2SCell = row.getCell(
						CONCEPTCODES_ICD9_UPLOAD_VALUESET_CELL_NUM,
						Row.RETURN_BLANK_AS_NULL);
				Cell descriptionCell = row.getCell(
						CONCEPTCODES_ICD9_UPLOAD_DESCRIPTION_CELL_NUM,
						Row.RETURN_BLANK_AS_NULL);
				Cell altDescriptionCell = row.getCell(
						CONCEPTCODES_ICD9_UPLOAD_ALT_DESCRIPTION_CELL_NUM,
						Row.RETURN_BLANK_AS_NULL);

				if (!getCellValueAsString(valueSetNameC2SCell)
						.equalsIgnoreCase("n/a")) {
					// && !getCellValueAsString(valueSetNameC2SCell).isEmpty())
					// {
					System.out.println("Code: "
							+ getCellValueAsString(codeCell));
					System.out.println("Valueset: "
							+ getCellValueAsString(valueSetNameC2SCell));
					System.out.println("Exist in C2S: "
							+ getCellValueAsString(existsInC2SCell));
					System.out.println("desc: "
							+ getCellValueAsString(descriptionCell));
					System.out.println("alt desc: "
							+ getCellValueAsString(altDescriptionCell));

					ConceptCodeDto conceptCodeDto = new ConceptCodeDto();
					conceptCodeDto.setCode(getCellValueAsString(codeCell));
					conceptCodeDto.setUserName("consent2share.sysadmin");

					// conceptCode.setCodeSystemVersion((new
					// CodeSystemVersion()).set);

					if (getCellValueAsString(altDescriptionCell) != null
							&& !getCellValueAsString(altDescriptionCell)
									.isEmpty()) {
						conceptCodeDto
								.setDescription(getCellValueAsString(altDescriptionCell));
						conceptCodeDto
								.setName(getCellValueAsString(altDescriptionCell));
					} else {
						conceptCodeDto
								.setDescription(getCellValueAsString(descriptionCell));
						conceptCodeDto
								.setName(getCellValueAsString(descriptionCell));
					}

					// List<Object []> valueSets = (List<Object []>)
					// conceptCodeRepository.findValueSetsAndCategoriesForConceptCodes(getCellValueAsString(codeCell));
					List<Long> valueSetIds = valueSetRepository
							.findIdsByName(getCellValueAsString(valueSetNameC2SCell));

					conceptCodeDto.setValueSetIds(valueSetIds);
					conceptCodeDto
							.setCodeSystemVersionId(ICD9_CODE_SYSTEM_VERSION_ID);
					conceptCodeDto.setCodeSystemName(ICD9_CODE_SYSTEM_NAME);

					create(conceptCodeDto);
				} else {

					System.out.println("Ignore: "
							+ getCellValueAsString(codeCell));
				}
			}
		}

		fileInputStream.close();
	}

	public static void batchUploadRxNormConceptCodesFromFile(String cc_file)
			throws ValueSetNotFoundException, CodeSystemNotFoundException,
			FileNotFoundException, IOException, DuplicateConceptCodeException {

		FileInputStream fileInputStream = new FileInputStream(cc_file);

		XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		Row row = null;
		int numNA = 0, numEmpty = 0, numCreated = 0;

		// reading and ignoring header row
		if (rowIterator.hasNext())
			rowIterator.next();

		while (rowIterator.hasNext()) {
			row = rowIterator.next();

			if (row != null) {

				Cell codeCell = row.getCell(
						CONCEPTCODES_RxNORM_UPLOAD_CODE_CELL_NUM,
						Row.RETURN_BLANK_AS_NULL);

				Cell valueSetNameC2SCell = row.getCell(
						CONCEPTCODES_RxNORM_UPLOAD_VALUESET_CELL_NUM,
						Row.RETURN_BLANK_AS_NULL);
				Cell descriptionCell = row.getCell(
						CONCEPTCODES_RxNORM_UPLOAD_DESCRIPTION_CELL_NUM,
						Row.RETURN_BLANK_AS_NULL);

				String valueSetName = getCellValueAsString(valueSetNameC2SCell);

				if (valueSetName != null
						&& !valueSetName.equalsIgnoreCase("n/a")
						&& codeCell != null) {

					System.out.println("Code: "
							+ getCellValueAsString(codeCell));
					System.out.println("Valueset: "
							+ valueSetName.substring(0,
									valueSetName.indexOf('(')).trim());
					System.out.println("desc: "
							+ getCellValueAsString(descriptionCell));

					ConceptCodeDto conceptCodeDto = new ConceptCodeDto();
					conceptCodeDto.setCode(getCellValueAsString(codeCell));
					conceptCodeDto.setUserName("consent2share.sysadmin");

					conceptCodeDto
							.setDescription(getCellValueAsString(descriptionCell));
					conceptCodeDto
							.setName(getCellValueAsString(descriptionCell));

					List<Long> valueSetIds = valueSetRepository
							.findIdsByName(valueSetName.substring(0,
									valueSetName.indexOf('(')).trim());

					conceptCodeDto.setValueSetIds(valueSetIds);
					conceptCodeDto
							.setCodeSystemVersionId(RxNORM_CODE_SYSTEM_VERSION_ID);
					conceptCodeDto.setCodeSystemName(RxNORM_CODE_SYSTEM_NAME);

					create(conceptCodeDto);
					numCreated++;
				} else {
					if (valueSetName == null || codeCell == null) {
						numEmpty++;
						System.out.println("Ignore NULL: "
								+ getCellValueAsString(codeCell));
					} else if (valueSetName.equalsIgnoreCase("n/a")) {
						numNA++;
						System.out.println("Ignore NA: "
								+ getCellValueAsString(codeCell));
					}

				}
			}
		}

		System.out.println("Number NA: " + numNA);
		System.out.println("Number Empty: " + numEmpty);

		fileInputStream.close();
	}

	public static List<ConceptCodeDto> readConceptCodesFromFile(
			MultipartFile file, Long codeSystemVersionId,
			List<Long> valueSetIds, String userName) throws IOException {

		// validateInputs(codeSystemId, codeSystemVersionId, valueSetIds);

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
				if (headerRow.getCell(CONCEPTCODES_ICD9_CODE_CELL_NUM,
						Row.RETURN_BLANK_AS_NULL) == null
						|| headerRow.getCell(CONCEPTCODES_ICD9_NAME_CELL_NUM,
								Row.RETURN_BLANK_AS_NULL) == null
						|| headerRow.getCell(
								CONCEPTCODES_ICD9_DESCRIPTION_CELL_NUM,
								Row.RETURN_BLANK_AS_NULL) == null) {
					throw new InvalidCSVException(
							"Header row values in file should be in the following format: Code, Name, Description");

				}
			}
		}

		// iterate rows with value set fields
		while (rowIterator.hasNext()) {
			row = rowIterator.next();
			conceptCodeDto = new ConceptCodeDto();

			if (row != null) {

				Cell codeCell = row.getCell(CONCEPTCODES_ICD9_CODE_CELL_NUM,
						Row.RETURN_BLANK_AS_NULL);
				Cell nameCell = row.getCell(CONCEPTCODES_ICD9_NAME_CELL_NUM,
						Row.RETURN_BLANK_AS_NULL);
				Cell descriptionCell = row.getCell(
						CONCEPTCODES_ICD9_DESCRIPTION_CELL_NUM,
						Row.RETURN_BLANK_AS_NULL);

				// ignore empty row and throw error on missing fields
				if (codeCell == null || nameCell == null) {

					if (codeCell != null || nameCell != null) {
						throw new InvalidCSVException(
								"Cannot add value set. Required field(s) empty for row: "
										+ (row.getRowNum() + 1));
					}
				} else {
					// conceptCodeDto.setCodeSystemName(codeSystemId);
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

	@Transactional
	public static ConceptCodeDto create(ConceptCodeDto created)
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
			// Long valueSetId = created.getValueSetId();
			ValueSet selectedVs = valueSetRepository.findOne(valueSetId);
			if (selectedVs == null) {
				throw new ValueSetNotFoundException();
			}

			// STEP:3 :- Code and its association with value set save

			// Check if conceptcode already exists for the code system version
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
					// isVSPresent = true;
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

			// conceptCodeDto =
			// valueSetMgmtHelper.createConceptCodeDtoFromEntity(conceptCode);
		}
		// conceptCodeDto.setConceptCodesInserted(conceptCodesInserted);

		if (!isNewVS) {
			// throw new DuplicateConceptCodeException();
		}
		return conceptCodeDto;

	}

	@Transactional
	public static void deleteICD9CodesNotRequiredInC2S(File cc_file)
			throws IOException {
		Set listOfICD9CodesInFile = getListOfConceptCodesInICD9File(cc_file);

		List<String> listOfICD9ConceptCodesInC2S = conceptCodeRepository
				.findByCodeSystem("ICD-9-CM");
		System.out.println(listOfICD9ConceptCodesInC2S.size());
		int numToKeep = 0, numToDelete = 0;

		ConceptCode codeToDelete2 = conceptCodeRepository.findByCode("305.70");

		for (int i = 0; i < listOfICD9ConceptCodesInC2S.size(); i++) {
			if (listOfICD9CodesInFile.contains(listOfICD9ConceptCodesInC2S
					.get(i))) {
				numToKeep++;
				System.out.println(listOfICD9ConceptCodesInC2S.get(i)
						+ " exists in File");
			} else {
				numToDelete++;
				System.out.println(listOfICD9ConceptCodesInC2S.get(i)
						+ " DOES NOT exists in File -> REMOVE from C2S");
				// conceptCodeRepository.deleteByCode(listOfICD9ConceptCodesInC2S.get(i));

				ConceptCode codeToDelete = conceptCodeRepository
						.findByCode(listOfICD9ConceptCodesInC2S.get(i));

				conceptCodeRepository.delete(codeToDelete.getId());

			}
			System.out.println("Num to keep: " + numToKeep);
			System.out.println("Num to delete: " + numToDelete);
		}

		System.out.println("DONE");
	}

	@Transactional
	public static void deleteAllICD9InC2S() throws IOException {
		List<ConceptCode> conceptCodes = conceptCodeRepository.findAll();
		List<Long> idsToDelete = new ArrayList<Long>();

		for (ConceptCode conceptCode : conceptCodes)
			if (conceptCode.getCodeSystemVersion().getCodeSystem().getName()
					.equals(ICD9_CODE_SYSTEM_NAME))
				idsToDelete.add(conceptCode.getId());

		for (Long id : idsToDelete)
			conceptCodeRepository.delete(id);

	}

	@Transactional
	public static void deleteAllRxNormInC2S() throws IOException {
		List<ConceptCode> conceptCodes = conceptCodeRepository.findAll();
		List<Long> idsToDelete = new ArrayList<Long>();

		for (ConceptCode conceptCode : conceptCodes)
			if (conceptCode.getCodeSystemVersion().getCodeSystem().getName()
					.equals(RxNORM_CODE_SYSTEM_NAME))
				idsToDelete.add(conceptCode.getId());

		for (Long id : idsToDelete)
			conceptCodeRepository.delete(id);

	}

	public static Set<String> getListOfConceptCodesInICD9File(File cc_file)
			throws FileNotFoundException, IOException {
		List<ConceptCodeDto> listOfConceptCodesDtos = new ArrayList<ConceptCodeDto>();
		ConceptCodeDto conceptCodeDto;

		FileInputStream fileInputStream = new FileInputStream(cc_file);

		XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
		XSSFSheet sheet = workbook.getSheetAt(1);
		Iterator<Row> rowIterator = sheet.iterator();

		Set<String> listOfCodesInFile = new HashSet<String>();
		Row row = null;

		// reading header row
		if (rowIterator.hasNext()) {
			Row headerRow = rowIterator.next();

			if (headerRow != null) {
				if (headerRow.getCell(CONCEPTCODES_ICD9_CODE_CELL_NUM,
						Row.RETURN_BLANK_AS_NULL) == null
						|| headerRow.getCell(CONCEPTCODES_ICD9_NAME_CELL_NUM,
								Row.RETURN_BLANK_AS_NULL) == null
						|| headerRow.getCell(
								CONCEPTCODES_ICD9_DESCRIPTION_CELL_NUM,
								Row.RETURN_BLANK_AS_NULL) == null) {
					throw new InvalidCSVException(
							"Header row values in file should be in the following format: Code, Name, Description");

				}
			}
		}

		// iterate rows with value set fields
		while (rowIterator.hasNext()) {
			row = rowIterator.next();
			conceptCodeDto = new ConceptCodeDto();

			if (row != null) {

				Cell codeCell = row.getCell(CONCEPTCODES_ICD9_CODE_CELL_NUM,
						Row.RETURN_BLANK_AS_NULL);

				if (codeCell == null)
					throw new InvalidCSVException(
							"Cannot add value set. Required field(s) empty for row: "
									+ (row.getRowNum() + 1));
				else
					listOfCodesInFile.add(getCellValueAsString(codeCell));
			}
		}

		System.out.println("Size of set: " + listOfCodesInFile.size());

		return listOfCodesInFile;
	}

	public static String getCellValueAsString(Cell cell) {
		if (cell == null)
			return null;
		else if (cell.getCellType() == Cell.CELL_TYPE_STRING)
			return cell.toString();
		else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
			return new DataFormatter().formatCellValue(cell);
		else
			throw new InvalidCSVException(
					"Value stored in cell is invalid! Valid types are Numbers or Strings.");
	}
}
