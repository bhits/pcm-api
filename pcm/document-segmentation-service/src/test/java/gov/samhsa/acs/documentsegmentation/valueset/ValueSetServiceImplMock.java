package gov.samhsa.acs.documentsegmentation.valueset;

import gov.samhsa.acs.common.tool.FileReader;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.documentsegmentation.valueset.dto.CodeAndCodeSystemSetDto;
import gov.samhsa.acs.documentsegmentation.valueset.dto.ValueSetQueryDto;
import gov.samhsa.acs.documentsegmentation.valueset.dto.ValueSetQueryListDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ValueSetServiceImplMock implements ValueSetService {
	private FileReader fileReader;
	private List<ConceptCode> conceptCodeList;
	private static final String VALUE_SET_MOCK_DATA_PATH = "MockValueSetData.csv";

	public ValueSetServiceImplMock() {
	}

	public ValueSetServiceImplMock(FileReader fileReader) {
		this.fileReader = fileReader;
		try {
			init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.documentsegmentation.valueset.ValueSetService#
	 * lookupValueSetCategories(java.lang.String, java.lang.String)
	 */
	@Override
	public Set<String> lookupValueSetCategories(String code, String codeSystem) {
		Set<String> categories = new HashSet<String>();
		for (ConceptCode conceptCode : conceptCodeList) {
			if (isEqual(conceptCode, code, codeSystem)) {
				String category = conceptCode.getValueSetCategory();
				if (!categories.contains(category)) {
					categories.add(conceptCode.getValueSetCategory());
				}
			}
		}
		return categories;
	}
	
	@Override
	public List<Map<String, Object>> lookupValuesetCategoriesOfMultipleCodeAndCodeSystemSet(
			List<CodeAndCodeSystemSetDto> codeAndCodeSystemSetDtoList) {
		List<Map<String, Object>> valueSetQueryDtoList=new ArrayList<Map<String, Object>>();
		for(CodeAndCodeSystemSetDto codeAndCodeSystemSetDto:codeAndCodeSystemSetDtoList) {
			Map<String, Object> valueSetMap=new HashMap<String, Object>();
			valueSetMap.put("conceptCode", codeAndCodeSystemSetDto.getConceptCode());
			valueSetMap.put("codeSystemOid", codeAndCodeSystemSetDto.getCodeSystemOid());
			valueSetMap.put("vsCategoryCodes", null);
			valueSetQueryDtoList.add(valueSetMap);
		}
		return valueSetQueryDtoList;
	}
	
	@Override
	public String toString(){
		return conceptCodeList.toString();
	}

	private void init() throws IOException {
		conceptCodeList = new LinkedList<ConceptCode>();
		// FileInputStream fis = new FileInputStream(VALUE_SET_MOCK_DATA_PATH);
		// File file = new File(fis);
		String file = fileReader.readFile(VALUE_SET_MOCK_DATA_PATH);
		// System.out.println(file);
		Scanner scanFile = new Scanner(file);
		while (scanFile.hasNextLine()) {
			String line = scanFile.nextLine();
			Scanner scanLine = new Scanner(line);
			scanLine.useDelimiter(",");
			ConceptCode code = new ConceptCode();
			while (scanLine.hasNext()) {
				String next = scanLine.next();
				if("304.31".equals(next)){
					// System.out.println(line);
				}
				code.setVariable(next);
			}
			conceptCodeList.add(code);
		}
		// System.out.println("Initialized Value Set Servise with size:"+conceptCodeList.size());
	}

	private boolean isEqual(ConceptCode c1, String code, String codeSystem) {
		return c1.getCode().equals(code)
				&& c1.getCodeSystem().equals(codeSystem);
	}

	public static void main(String[] args) {
		ValueSetServiceImplMock v = new ValueSetServiceImplMock(new FileReaderImpl());
		System.out.println(v.lookupValueSetCategories("111880001",
				"2.16.840.1.113883.6.96"));
		System.out.println(v);
	}

	@Override
	public ValueSetQueryListDto RestfulValueSetCategories(
			ValueSetQueryListDto valueSetQueryListDtos) {
		// TODO Auto-generated method stub
		return null;
	}

}
