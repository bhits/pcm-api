package gov.samhsa.pcm.service.valueset;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.pcm.domain.valueset.CodeSystemRepository;
import gov.samhsa.pcm.domain.valueset.CodeSystemVersion;
import gov.samhsa.pcm.domain.valueset.CodeSystemVersionRepository;
import gov.samhsa.pcm.domain.valueset.ConceptCode;
import gov.samhsa.pcm.domain.valueset.ConceptCodeRepository;
import gov.samhsa.pcm.domain.valueset.ConceptCodeValueSet;
import gov.samhsa.pcm.domain.valueset.ConceptCodeValueSetRepository;
import gov.samhsa.pcm.domain.valueset.ValueSet;
import gov.samhsa.pcm.domain.valueset.ValueSetRepository;
import gov.samhsa.pcm.service.dto.ConceptCodeDto;
import gov.samhsa.pcm.service.dto.ConceptCodeVSCSDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gov.samhsa.pcm.service.valueset.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RunWith(MockitoJUnitRunner.class)
public class ConceptCodeServiceImplTest {

	private final int conceptCodePageSize = 20;

	@Mock
	ConceptCodeRepository conceptCodeRepository;

	@Mock
	ValueSetRepository valueSetRepository;

	@Mock
	CodeSystemRepository codeSystemRepository;

	@Mock
	CodeSystemVersionRepository codeSystemVersionRepository;

	@Mock
	ConceptCodeValueSetRepository conceptCodeValueSetRepository;

	@Mock
	ValueSetMgmtHelper valueSetMgmtHelper;

	@InjectMocks
	ConceptCodeServiceImpl conceptCodeServiceImpl = new ConceptCodeServiceImpl(
			conceptCodePageSize, conceptCodeRepository, valueSetRepository,
			codeSystemRepository, codeSystemVersionRepository,
			conceptCodeValueSetRepository, valueSetMgmtHelper);

	@Test
	public void testDeleteConceptCode() throws ConceptCodeNotFoundException {

		ConceptCode deleted = mock(ConceptCode.class);
		when(conceptCodeRepository.findOne(anyLong())).thenReturn(deleted);

		ConceptCodeDto conceptCodeDto = mock(ConceptCodeDto.class);
		when(valueSetMgmtHelper.createConceptCodeDtoFromEntity(deleted))
				.thenReturn(conceptCodeDto);

		Assert.assertEquals(conceptCodeServiceImpl.delete((long) 1),
				conceptCodeDto);
	}

	@Test(expected = ConceptCodeNotFoundException.class)
	public void testDeleteConceptCode_throw_ConceptCodeNotFoundException()
			throws ConceptCodeNotFoundException {

		when(conceptCodeRepository.findOne(anyLong())).thenReturn(null);
		conceptCodeServiceImpl.delete((long) 1);

	}

	@Test(expected = ValueSetNotFoundException.class)
	public void testfindId() throws ValueSetNotFoundException,
			ConceptCodeNotFoundException {

		ConceptCode conceptCode = mock(ConceptCode.class);

		when(conceptCodeRepository.findOne(anyLong())).thenReturn(conceptCode);

		ConceptCodeDto conceptCodeDto = mock(ConceptCodeDto.class);
		when(valueSetMgmtHelper.createConceptCodeDtoFromEntity(conceptCode))
				.thenReturn(conceptCodeDto);

		conceptCodeServiceImpl.findById((long) 1);

	}

	@Test(expected = ValueSetNotFoundException.class)
	public void testUpdateConceptCode_throw_ValueSetNotFoundException()
			throws ConceptCodeNotFoundException, ValueSetNotFoundException {

		ConceptCodeDto updated = mock(ConceptCodeDto.class);

		when(updated.getId()).thenReturn((long) 1);
		when(updated.getCode()).thenReturn("code");
		when(updated.getName()).thenReturn("name");
		when(updated.getUserName()).thenReturn("username");

		ConceptCode conceptCode = mock(ConceptCode.class);

		when(conceptCodeRepository.findOne(anyLong())).thenReturn(conceptCode);

		ConceptCodeDto conceptCodeDto = mock(ConceptCodeDto.class);
		when(valueSetMgmtHelper.createConceptCodeDtoFromEntity(conceptCode))
				.thenReturn(conceptCodeDto);

		conceptCodeServiceImpl.update(updated);
	}

	@Test(expected = ConceptCodeNotFoundException.class)
	public void testUpdateConceptCode_throw_ConceptCodeNotFoundException()
			throws ConceptCodeNotFoundException, ValueSetNotFoundException {
		ConceptCodeDto updated = mock(ConceptCodeDto.class);
		when(conceptCodeRepository.findOne(anyLong())).thenReturn(null);
		conceptCodeServiceImpl.update(updated);

	}

	@Test
	public void testUpdateConceptCode2() throws ConceptCodeNotFoundException,
			ValueSetNotFoundException {

		ConceptCodeDto updated = mock(ConceptCodeDto.class);

		when(updated.getId()).thenReturn((long) 1);
		when(updated.getCode()).thenReturn("code");
		when(updated.getName()).thenReturn("name");
		when(updated.getUserName()).thenReturn("username");

		ConceptCode conceptCode = mock(ConceptCode.class);
		when(conceptCode.getId()).thenReturn((long) 1);

		when(conceptCodeRepository.findOne(anyLong())).thenReturn(conceptCode);

		ConceptCodeDto conceptCodeDto = mock(ConceptCodeDto.class);
		when(valueSetMgmtHelper.createConceptCodeDtoFromEntity(conceptCode))
				.thenReturn(conceptCodeDto);

		List<Long> selVsIds = Arrays.asList((long) 1, (long) 2, (long) 3);
		when(updated.getValueSetIds()).thenReturn(selVsIds);

		List<ConceptCodeValueSet> cValueSets = new ArrayList();
		ConceptCodeValueSet aCodeValueSet = mock(ConceptCodeValueSet.class);
		cValueSets.add(aCodeValueSet);

		when(conceptCodeValueSetRepository.findAllByPkConceptCodeId(anyLong()))
				.thenReturn(cValueSets);
		when(aCodeValueSet.getValueSet()).thenReturn(mock(ValueSet.class));

		when(aCodeValueSet.getValueSet().getId()).thenReturn((long) 1);

		when(valueSetMgmtHelper.createConceptCodeDtoFromEntity(conceptCode))
				.thenReturn(updated);

		// assertEquals(vst.update(updated),updated);
	}

	@Test(expected = ValueSetNotFoundException.class)
	public void testUpdateConceptCode_throw_valueSetNotFoundException2()
			throws ConceptCodeNotFoundException, ValueSetNotFoundException {

		ConceptCodeDto updated = mock(ConceptCodeDto.class);

		when(updated.getId()).thenReturn((long) 1);
		when(updated.getCode()).thenReturn("code");
		when(updated.getName()).thenReturn("name");
		when(updated.getUserName()).thenReturn("username");

		ConceptCode conceptCode = mock(ConceptCode.class);
		when(conceptCode.getId()).thenReturn((long) 1);

		when(conceptCodeRepository.findOne(anyLong())).thenReturn(conceptCode);

		ConceptCodeDto conceptCodeDto = mock(ConceptCodeDto.class);
		when(valueSetMgmtHelper.createConceptCodeDtoFromEntity(conceptCode))
				.thenReturn(conceptCodeDto);

		List<Long> selVsIds = Arrays.asList((long) 1, (long) 2, (long) 3);
		when(updated.getValueSetIds()).thenReturn(selVsIds);

		when(conceptCodeValueSetRepository.findAllByPkConceptCodeId(anyLong()))
				.thenReturn(null);

		conceptCodeServiceImpl.update(updated);
	}

	@Test
	public void testfindAll() {

		List<ConceptCode> conceptCodes = mock(List.class);
		when(conceptCodeRepository.findAll()).thenReturn(conceptCodes);

		List<ConceptCodeDto> conceptCodeDtos = new ArrayList();
		ConceptCodeDto conceptCodeDto = mock(ConceptCodeDto.class);
		conceptCodeDtos.add(conceptCodeDto);
		when(valueSetMgmtHelper.convertConceptCodeEntitiesToDtos(conceptCodes))
				.thenReturn(conceptCodeDtos);

		assertEquals(conceptCodeServiceImpl.findAll(), conceptCodeDtos);
	}

	@Test
	public void testFindAllByName() {
		Page<ConceptCode> conceptCodes = mock(Page.class);
		List<ConceptCodeDto> conceptCodeDtos = mock(List.class);

		when(
				conceptCodeRepository.findAllByName(anyString(), anyString(),
						anyString(), anyString(), any(Pageable.class)))
				.thenReturn(conceptCodes);
		when(
				valueSetMgmtHelper
						.convertConceptCodeEntitiesToDtos(conceptCodes
								.getContent())).thenReturn(conceptCodeDtos);
		assertEquals(
				conceptCodeServiceImpl.findAllByName("a", null, null, null, 0)
						.get("conceptCodes"), conceptCodeDtos);

	}

	@Test
	public void testFindAllByCode() {

		Page<ConceptCode> conceptCodes = mock(Page.class);
		List<ConceptCodeDto> conceptCodeDtos = mock(List.class);

		when(
				conceptCodeRepository.findAllByCodeLike(anyString(),
						anyString(), anyString(), anyString(),
						any(Pageable.class))).thenReturn(conceptCodes);
		when(
				valueSetMgmtHelper
						.convertConceptCodeEntitiesToDtos(conceptCodes
								.getContent())).thenReturn(conceptCodeDtos);
		assertEquals(
				conceptCodeServiceImpl.findAllByCode("a", null, null, null, 0)
						.get("conceptCodes"), conceptCodeDtos);
	}

	@Test(expected = ValueSetNotFoundException.class)
	public void testCreateCodeSystem_throw_ValueSetCategoryNotFoundException()
			throws ValueSetNotFoundException,
			CodeSystemVersionNotFoundException, CodeSystemNotFoundException {
		ConceptCodeVSCSDto conceptCodeVSCSDto = mock(ConceptCodeVSCSDto.class);
		List<ValueSet> valueSets = mock(List.class);
		when(valueSetRepository.findAll()).thenReturn(valueSets);
		ConceptCodeVSCSDto result = conceptCodeServiceImpl.create();
	}

	@Test(expected = CodeSystemNotFoundException.class)
	public void testCreateCodeSystem_throw_CodeSystemNotFoundException()
			throws ValueSetNotFoundException,
			CodeSystemVersionNotFoundException, CodeSystemNotFoundException {
		ConceptCodeVSCSDto conceptCodeVSCSDto = mock(ConceptCodeVSCSDto.class);
		List<ValueSet> valueSets = new ArrayList<ValueSet>();
		ValueSet valueSet = mock(ValueSet.class);
		valueSets.add(valueSet);
		when(valueSetRepository.findAll()).thenReturn(valueSets);
		when(codeSystemVersionRepository.findOne(anyLong())).thenReturn(null);
		ConceptCodeVSCSDto result = conceptCodeServiceImpl.create();
	}

	@Test(expected = CodeSystemNotFoundException.class)
	public void testCreateCodeSystemFromDto_throw_CodeSystemNotFoundException()
			throws ValueSetNotFoundException, CodeSystemNotFoundException,
			DuplicateConceptCodeException {
		ConceptCodeDto created = mock(ConceptCodeDto.class);

		when(created.getDescription()).thenReturn("description");
		when(created.getCodeSystemVersionId()).thenReturn((long) 1);

		when(codeSystemVersionRepository.findOne(anyLong())).thenReturn(null);
		conceptCodeServiceImpl.create(created);
	}

	@Test(expected = ValueSetNotFoundException.class)
	public void testCreateCodeSystemFromDto_throw_ValueSetNotFoundException()
			throws ValueSetNotFoundException, CodeSystemNotFoundException,
			DuplicateConceptCodeException {
		ConceptCodeDto created = mock(ConceptCodeDto.class);

		when(created.getDescription()).thenReturn("description");
		when(created.getCodeSystemVersionId()).thenReturn((long) 1);

		CodeSystemVersion selectedCsv = mock(CodeSystemVersion.class);
		when(codeSystemVersionRepository.findOne(anyLong())).thenReturn(
				selectedCsv);

		List<Long> selVsIds = Arrays.asList((long) 1, (long) 2, (long) 3);

		when(created.getValueSetIds()).thenReturn(null);

		conceptCodeServiceImpl.create(created);
	}

	@Test(expected = ValueSetNotFoundException.class)
	public void testCreateCodeSystemFromDto_throw_ValueSetNotFoundException_2()
			throws ValueSetNotFoundException, CodeSystemNotFoundException,
			DuplicateConceptCodeException {
		ConceptCodeDto created = mock(ConceptCodeDto.class);

		when(created.getDescription()).thenReturn("description");
		when(created.getCodeSystemVersionId()).thenReturn((long) 1);

		CodeSystemVersion selectedCsv = mock(CodeSystemVersion.class);
		when(codeSystemVersionRepository.findOne(anyLong())).thenReturn(
				selectedCsv);

		List<Long> selVsIds = Arrays.asList((long) 1, (long) 2, (long) 3);
		when(created.getValueSetIds()).thenReturn(selVsIds);

		when(valueSetRepository.findOne(anyLong())).thenReturn(null);

		conceptCodeServiceImpl.create(created);
	}

	@Test
	public void testCreateCodeSystemFromDto_throw_ValueSetNotFoundException_3()
			throws ValueSetNotFoundException, CodeSystemNotFoundException,
			DuplicateConceptCodeException {
		ConceptCodeDto created = mock(ConceptCodeDto.class);

		when(created.getDescription()).thenReturn("description");
		when(created.getCodeSystemVersionId()).thenReturn((long) 1);

		CodeSystemVersion selectedCsv = mock(CodeSystemVersion.class);
		when(codeSystemVersionRepository.findOne(anyLong())).thenReturn(
				selectedCsv);

		List<Long> selVsIds = Arrays.asList((long) 1, (long) 2, (long) 3);
		when(created.getValueSetIds()).thenReturn(selVsIds);

		ValueSet selectedVs = mock(ValueSet.class);

		when(valueSetRepository.findOne(anyLong())).thenReturn(selectedVs);

		when(created.getCode()).thenReturn("code");
		when(selectedCsv.getId()).thenReturn((long) 1);

		ConceptCode conceptCode = mock(ConceptCode.class);
		when(
				conceptCodeRepository.findByCodeAndCodeSystemVersionId(
						anyString(), anyLong())).thenReturn(conceptCode);

		ConceptCodeDto conceptCodeDto = mock(ConceptCodeDto.class);
		when(
				valueSetMgmtHelper
						.createConceptCodeDtoFromEntity(any(ConceptCode.class)))
				.thenReturn(conceptCodeDto);

		assertEquals(conceptCodeServiceImpl.create(created), conceptCodeDto);
	}

	@Test
	public void testCreateCodeSystemFromDto_3()
			throws ValueSetNotFoundException, CodeSystemNotFoundException,
			DuplicateConceptCodeException {
		ConceptCodeDto created = mock(ConceptCodeDto.class);

		when(created.getDescription()).thenReturn("description");
		when(created.getCodeSystemVersionId()).thenReturn((long) 1);

		CodeSystemVersion selectedCsv = mock(CodeSystemVersion.class);
		when(codeSystemVersionRepository.findOne(anyLong())).thenReturn(
				selectedCsv);

		List<Long> selVsIds = Arrays.asList((long) 1, (long) 2, (long) 3);
		when(created.getValueSetIds()).thenReturn(selVsIds);

		ValueSet selectedVs = mock(ValueSet.class);

		when(valueSetRepository.findOne(anyLong())).thenReturn(selectedVs);

		when(created.getCode()).thenReturn("code");
		when(selectedCsv.getId()).thenReturn((long) 1);

		ConceptCode conceptCode = mock(ConceptCode.class);
		when(
				conceptCodeRepository.findByCodeAndCodeSystemVersionId(
						anyString(), anyLong())).thenReturn(null);

		ConceptCodeDto conceptCodeDto = mock(ConceptCodeDto.class);
		when(
				valueSetMgmtHelper
						.createConceptCodeDtoFromEntity(any(ConceptCode.class)))
				.thenReturn(conceptCodeDto);

		assertEquals(conceptCodeServiceImpl.create(created), conceptCodeDto);
	}

}
