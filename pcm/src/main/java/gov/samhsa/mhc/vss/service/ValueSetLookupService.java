package gov.samhsa.mhc.vss.service;

import gov.samhsa.mhc.vss.service.dto.ValueSetLookUpDto;
import gov.samhsa.mhc.vss.service.dto.ValueSetQueryDto;
import gov.samhsa.mhc.vss.service.dto.ValueSetQueryListDto;

public interface ValueSetLookupService {

    public ValueSetLookUpDto lookupValueSetCategories(String code, String codeSystemOid) throws CodeSystemVersionNotFoundException, ConceptCodeNotFoundException, ValueSetNotFoundException;

    public ValueSetQueryDto restfulValueSetCategories(String code, String codeSystemOid) throws CodeSystemVersionNotFoundException, ConceptCodeNotFoundException, ValueSetNotFoundException;

    public ValueSetQueryListDto restfulValueSetCategories(ValueSetQueryListDto valueSetQueryListDtos) throws CodeSystemVersionNotFoundException, ConceptCodeNotFoundException, ValueSetNotFoundException;

}
