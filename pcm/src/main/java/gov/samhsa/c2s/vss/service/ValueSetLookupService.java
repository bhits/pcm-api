package gov.samhsa.c2s.vss.service;

import gov.samhsa.c2s.vss.service.dto.ValueSetQueryDto;
import gov.samhsa.c2s.vss.service.dto.ValueSetQueryListDto;
import gov.samhsa.c2s.vss.service.dto.ValueSetLookUpDto;

public interface ValueSetLookupService {

    public ValueSetLookUpDto lookupValueSetCategories(String code, String codeSystemOid) throws CodeSystemVersionNotFoundException, ConceptCodeNotFoundException, ValueSetNotFoundException;

    public ValueSetQueryDto restfulValueSetCategories(String code, String codeSystemOid) throws CodeSystemVersionNotFoundException, ConceptCodeNotFoundException, ValueSetNotFoundException;

    public ValueSetQueryListDto restfulValueSetCategories(ValueSetQueryListDto valueSetQueryListDtos) throws CodeSystemVersionNotFoundException, ConceptCodeNotFoundException, ValueSetNotFoundException;

}
