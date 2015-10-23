package gov.samhsa.pcm.service.valueset;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.pcm.domain.valueset.CodeSystem;
import gov.samhsa.pcm.domain.valueset.CodeSystemRepository;
import gov.samhsa.pcm.domain.valueset.CodeSystemVersion;
import gov.samhsa.pcm.domain.valueset.CodeSystemVersionRepository;
import gov.samhsa.pcm.domain.valueset.ValueSetCategory;
import gov.samhsa.pcm.service.dto.CodeSystemVersionCSDto;
import gov.samhsa.pcm.service.dto.CodeSystemVersionDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gov.samhsa.pcm.service.valueset.CodeSystemNotFoundException;
import gov.samhsa.pcm.service.valueset.CodeSystemVersionNotFoundException;
import gov.samhsa.pcm.service.valueset.CodeSystemVersionServiceImpl;
import gov.samhsa.pcm.service.valueset.ValueSetMgmtHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CodeSystemVersionServiceImplTest {

	@Mock
	CodeSystemRepository codeSystemRepository;

	@Mock
	CodeSystemVersionRepository codeSystemVersionRepository;

	@Mock
	ValueSetMgmtHelper valueSetMgmtHelper;;

	@InjectMocks
	CodeSystemVersionServiceImpl vst;

	@Test(expected = CodeSystemNotFoundException.class)
	public void testCreateCodeSystemVersion_throw_CodeSystemNotFoundException()
			throws CodeSystemNotFoundException {
		CodeSystemVersionDto createdMock = mock(CodeSystemVersionDto.class);
		when(createdMock.getDescription()).thenReturn("description");
		when(createdMock.getName()).thenReturn("name");
		when(createdMock.getCode()).thenReturn("code");
		when(createdMock.getUserName()).thenReturn("username");

		ValueSetCategory selected = new ValueSetCategory();
		selected.setCode("code");
		selected.setName("name");
		selected.setId((long) 1);
		when(codeSystemRepository.findOne((long) 1)).thenReturn(null);

		vst.create(createdMock);
	}

	@Test
	public void testDeleteCodeSystemVersion()
			throws CodeSystemVersionNotFoundException {

		CodeSystemVersion deleted = mock(CodeSystemVersion.class);
		when(codeSystemVersionRepository.findOne(anyLong()))
				.thenReturn(deleted);

		CodeSystemVersionDto codeSystemVersionDto = mock(CodeSystemVersionDto.class);
		when(valueSetMgmtHelper.createCodeSystemVersionDtoFromEntity(deleted))
				.thenReturn(codeSystemVersionDto);

		Assert.assertEquals(vst.delete((long) 1), codeSystemVersionDto);
	}

	@Test(expected = CodeSystemVersionNotFoundException.class)
	public void testDeleteCodeSystemVersion_throw_CodeSystemVersionNotFoundException()
			throws CodeSystemVersionNotFoundException {

		when(codeSystemVersionRepository.findOne(anyLong())).thenReturn(null);
		vst.delete((long) 1);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testfindAll() {

		List<CodeSystemVersion> codeSystemVersionsMock = mock(List.class);
		when(codeSystemVersionRepository.findAll()).thenReturn(
				codeSystemVersionsMock);

		List<CodeSystemVersionDto> codeSystemVersionDtos = new ArrayList();
		CodeSystemVersionDto codeSystemVersionDto = mock(CodeSystemVersionDto.class);
		codeSystemVersionDtos.add(codeSystemVersionDto);
		when(
				valueSetMgmtHelper
						.convertCodeSystemVersionEntitiesToDtos(codeSystemVersionsMock))
				.thenReturn(codeSystemVersionDtos);

		assertEquals(vst.findAll(), codeSystemVersionDtos);
	}

	@Test
	public void testfindId() throws CodeSystemNotFoundException {

		CodeSystemVersion codeSystemVersionMock = mock(CodeSystemVersion.class);

		List<CodeSystem> codeSystemsMock = new ArrayList<CodeSystem>();
		CodeSystem codeSystemMock = mock(CodeSystem.class);
		codeSystemsMock.add(codeSystemMock);

		CodeSystemVersionDto codeSystemVersionDtoMock = mock(CodeSystemVersionDto.class);

		when(codeSystemVersionRepository.findOne(anyLong())).thenReturn(
				codeSystemVersionMock);
		when(codeSystemRepository.findAll()).thenReturn(codeSystemsMock);

		when(
				valueSetMgmtHelper
						.createCodeSystemVersionDtoFromEntity(codeSystemVersionMock))
				.thenReturn(codeSystemVersionDtoMock);

		assertEquals(vst.findById((long) 1), codeSystemVersionDtoMock);
	}

	@Test
	public void testUpdateCodeSystemVersion()
			throws CodeSystemVersionNotFoundException,
			CodeSystemNotFoundException {

		CodeSystemVersionDto updatedMock = mock(CodeSystemVersionDto.class);

		when(updatedMock.getId()).thenReturn((long) 1);
		when(updatedMock.getCode()).thenReturn("code");
		when(updatedMock.getName()).thenReturn("name");
		when(updatedMock.getUserName()).thenReturn("username");
		CodeSystemVersionDto codeSystemVersionDtoMock = mock(CodeSystemVersionDto.class);

		CodeSystemVersion codeSystemVersionMock = mock(CodeSystemVersion.class);

		CodeSystem codeSystemMock = mock(CodeSystem.class);

		when(codeSystemVersionRepository.findOne(anyLong())).thenReturn(
				codeSystemVersionMock);

		when(codeSystemVersionMock.getCodeSystem()).thenReturn(codeSystemMock);

		when(codeSystemMock.getId()).thenReturn((long) 1);

		when(codeSystemRepository.findOne(anyLong()))
				.thenReturn(codeSystemMock);

		when(
				valueSetMgmtHelper
						.createCodeSystemVersionDtoFromEntity(codeSystemVersionMock))
				.thenReturn(codeSystemVersionDtoMock);

		assertEquals(vst.update(updatedMock), codeSystemVersionDtoMock);
	}

	@Test(expected = CodeSystemVersionNotFoundException.class)
	public void testUpdateCodeSystemVersion_throw_CodeSystemVersionNotFoundException()
			throws CodeSystemVersionNotFoundException,
			CodeSystemNotFoundException {
		CodeSystemVersionDto updated = mock(CodeSystemVersionDto.class);

		when(codeSystemVersionRepository.findOne(anyLong())).thenReturn(null);
		vst.update(updated);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCodeSystemVersionCSDto() throws CodeSystemNotFoundException {
		CodeSystemVersionCSDto codeSystemVersionVSCDto = new CodeSystemVersionCSDto();

		List<CodeSystem> codeSystemsMock = new ArrayList<CodeSystem>();
		CodeSystem codeSystem = mock(CodeSystem.class);
		codeSystemsMock.add(codeSystem);

		when(codeSystemRepository.findAll()).thenReturn(codeSystemsMock);

		Map<Long, String> codeSystemCategoriesMap = mock(Map.class);

		when(valueSetMgmtHelper.convertCodeSystemEntitiesToMap(codeSystemsMock))
				.thenReturn(codeSystemCategoriesMap);
		codeSystemVersionVSCDto.setCodeSystemDtoMap(codeSystemCategoriesMap);

		vst.create();

	}

}
