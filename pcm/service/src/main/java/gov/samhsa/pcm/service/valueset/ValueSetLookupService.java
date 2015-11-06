package gov.samhsa.pcm.service.valueset;

import gov.samhsa.pcm.service.dto.ValueSetLookUpDto;
import gov.samhsa.pcm.service.dto.ValueSetQueryDto;
import gov.samhsa.pcm.service.dto.ValueSetQueryListDto;

public interface ValueSetLookupService {
	
	 public ValueSetLookUpDto lookupValueSetCategories(String code, String codeSystemOid) throws CodeSystemVersionNotFoundException, ConceptCodeNotFoundException, ValueSetNotFoundException;
	
	 public ValueSetQueryDto RestfulValueSetCategories(String code, String codeSystemOid) throws CodeSystemVersionNotFoundException, ConceptCodeNotFoundException, ValueSetNotFoundException;

	 public ValueSetQueryListDto RestfulValueSetCategories(ValueSetQueryListDto valueSetQueryListDtos) throws CodeSystemVersionNotFoundException, ConceptCodeNotFoundException, ValueSetNotFoundException;

}
