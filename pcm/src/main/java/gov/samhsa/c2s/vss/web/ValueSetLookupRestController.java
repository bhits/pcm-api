package gov.samhsa.c2s.vss.web;

import gov.samhsa.c2s.vss.service.ConceptCodeNotFoundException;
import gov.samhsa.c2s.vss.service.CodeSystemVersionNotFoundException;
import gov.samhsa.c2s.vss.service.ValueSetLookupService;
import gov.samhsa.c2s.vss.service.ValueSetNotFoundException;
import gov.samhsa.c2s.vss.service.dto.ConceptCodeAndCodeSystemOidDto;
import gov.samhsa.c2s.vss.service.dto.ValueSetQueryDto;
import gov.samhsa.c2s.vss.service.dto.ValueSetQueryListDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class ValueSetLookupRestController {
    protected static final String REDIRECT_MAPPING_LIST = "/lookupService";
    protected static final String MULTIPLE_VALUESET_LOOKUP = "/lookupService/multipleValueset";
    protected static final String REST_MAPPING_LIST = "/lookupService/rest";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ValueSetLookupService lookupService;

    /**
     * Processes create conceptCode requests.
     *
     * @param model
     * @return The name of the create conceptCode form view.
     */
    @RequestMapping(value = REDIRECT_MAPPING_LIST, method = RequestMethod.GET)
    public ValueSetQueryDto lookupValuesetCategories(
            @RequestParam(value = "code") String code,
            @RequestParam(value = "codeSystemOid") String codeSystemOid,
            Model model) {

        logger.debug("Rendering Concept Code list page");
        ValueSetQueryDto valueSetQueryDto = getValueSetQueryDto(code, codeSystemOid);

        return valueSetQueryDto;
    }

    @RequestMapping(value = MULTIPLE_VALUESET_LOOKUP, method = RequestMethod.GET)
    public List<ValueSetQueryDto> lookupValuesetCategoriesOfMultipleCodesAndCodeSystemSet(
            @RequestParam(value = "code:codeSystemOid") List<String> codeAndCodeSystemPairList,
            Model model) {
        List<ValueSetQueryDto> valueSetQueryDtoList = new ArrayList<ValueSetQueryDto>();
        for (String codeAndCodeSystemPair : codeAndCodeSystemPairList) {
            List<String> spilitedCodeAndCodeSystem = new ArrayList<String>(
                    Arrays.asList(codeAndCodeSystemPair.split(":")));
            if (spilitedCodeAndCodeSystem.size() != 2)
                continue;
            String code = spilitedCodeAndCodeSystem.get(0);
            String codeSystemOid = spilitedCodeAndCodeSystem.get(1);
            logger.debug("Rendering Concept Code list page");
            ValueSetQueryDto valueSetQueryDto = getValueSetQueryDto(code, codeSystemOid);
            valueSetQueryDtoList.add(valueSetQueryDto);
        }

        return valueSetQueryDtoList;
    }

    @RequestMapping(value = "/lookupService/valueSetCategories", method = RequestMethod.POST)
    public List<ValueSetQueryDto> lookupValueSetCategories(@Valid @RequestBody List<ConceptCodeAndCodeSystemOidDto> conceptCodeAndCodeSystemOidDtos) {
        return conceptCodeAndCodeSystemOidDtos.stream()
                .map(dto -> getValueSetQueryDto(dto.getConceptCode(), dto.getCodeSystemOid()))
                .collect(toList());
    }

    /**
     * Processes create conceptCode requests.
     *
     * @param valueSetQueryListDtos value set query list dtos
     * @return The name of the create conceptCode form view.
     */
    @RequestMapping(value = REST_MAPPING_LIST, method = RequestMethod.POST)
    public ValueSetQueryListDto lookupValuesetCategoriesList(
            @RequestBody ValueSetQueryListDto valueSetQueryListDtos) {

        logger.debug("Rendering Concept Code list page");
        // Set<ValueSetQueryDto> valueSetQueryDtos =
        // valueSetQueryListDtos.getValueSetQueryDtos();
        try {
            valueSetQueryListDtos = lookupService
                    .restfulValueSetCategories(valueSetQueryListDtos);
        } catch (CodeSystemVersionNotFoundException e) {
            logger.debug(e.getMessage());
        } catch (ConceptCodeNotFoundException e) {
            logger.debug(e.getMessage());
        } catch (ValueSetNotFoundException e) {
            logger.debug(e.getMessage());
        }

        return valueSetQueryListDtos;
    }

    private ValueSetQueryDto getValueSetQueryDto(String code, String codeSystemOid) {
        ValueSetQueryDto valueSetQueryDto = new ValueSetQueryDto();
        try {
            valueSetQueryDto.setCodeSystemOid(codeSystemOid);
            valueSetQueryDto.setConceptCode(code);
            valueSetQueryDto = lookupService.restfulValueSetCategories(
                    code.trim(), codeSystemOid.trim());
        } catch (CodeSystemVersionNotFoundException e) {
            logger.debug(e.getMessage());
        } catch (ConceptCodeNotFoundException e) {
            logger.debug(e.getMessage());
        } catch (ValueSetNotFoundException e) {
            logger.debug(e.getMessage());
        }
        return valueSetQueryDto;
    }
}
