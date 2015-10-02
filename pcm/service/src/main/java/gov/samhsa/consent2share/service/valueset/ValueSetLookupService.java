package gov.samhsa.consent2share.service.valueset;

import gov.samhsa.consent2share.service.dto.ValueSetLookUpDto;
import gov.samhsa.consent2share.service.dto.ValueSetQueryDto;
import gov.samhsa.consent2share.service.dto.ValueSetQueryListDto;

public interface ValueSetLookupService {
	
	 public ValueSetLookUpDto lookupValueSetCategories(String code, String codeSystemOid) throws CodeSystemVersionNotFoundException, ConceptCodeNotFoundException, ValueSetNotFoundException;
	
	 public ValueSetQueryDto RestfulValueSetCategories(String code, String codeSystemOid) throws CodeSystemVersionNotFoundException, ConceptCodeNotFoundException, ValueSetNotFoundException;

	 public ValueSetQueryListDto RestfulValueSetCategories(ValueSetQueryListDto valueSetQueryListDtos) throws CodeSystemVersionNotFoundException, ConceptCodeNotFoundException, ValueSetNotFoundException;

}
