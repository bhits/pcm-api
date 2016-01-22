package gov.samhsa.bhits.vss.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import gov.samhsa.bhits.pcm.domain.valueset.CodeSystem;
import gov.samhsa.bhits.pcm.domain.valueset.CodeSystemRepository;
import gov.samhsa.bhits.vss.service.dto.CodeSystemDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CodeSystemServiceImplTest {

    @Mock
    CodeSystemRepository codeSystemRepository;

    @Mock
    ValueSetMgmtHelper valueSetMgmtHelper;
    ;

    @InjectMocks
    CodeSystemServiceImpl vst;

    @Test
    public void testCreateCodeSystem() {
        CodeSystemDto created = mock(CodeSystemDto.class);
        when(created.getDisplayName()).thenReturn("displayName");

        CodeSystem codeSystem = mock(CodeSystem.class);
        when(codeSystemRepository.save(any(CodeSystem.class))).thenReturn(
                codeSystem);
        when(valueSetMgmtHelper.createCodeSystemDtoFromEntity(codeSystem))
                .thenReturn(created);

        Assert.assertEquals(vst.create(created), created);

    }

    @Test
    public void testDeleteCodeSystem() throws CodeSystemNotFoundException {

        CodeSystem deleted = mock(CodeSystem.class);
        when(codeSystemRepository.findOne(anyLong())).thenReturn(deleted);

        CodeSystemDto codeSystemDto = mock(CodeSystemDto.class);
        when(valueSetMgmtHelper.createCodeSystemDtoFromEntity(deleted))
                .thenReturn(codeSystemDto);

        Assert.assertEquals(vst.delete((long) 1), codeSystemDto);
    }

    @Test(expected = CodeSystemNotFoundException.class)
    public void testDeleteCodeSystem_throw_CodeSystemNotFoundException()
            throws CodeSystemNotFoundException {

        when(codeSystemRepository.findOne(anyLong())).thenReturn(null);
        vst.delete((long) 1);

    }

    @Test
    public void testfindAll() {

        List<CodeSystem> codeSystems = mock(List.class);
        when(codeSystemRepository.findAll()).thenReturn(codeSystems);

        List<CodeSystemDto> codeSystemDtos = new ArrayList();
        CodeSystemDto codeSystemDto = mock(CodeSystemDto.class);
        codeSystemDtos.add(codeSystemDto);
        when(valueSetMgmtHelper.convertCodeSystemEntitiesToDtos(codeSystems))
                .thenReturn(codeSystemDtos);

        assertEquals(vst.findAll(), codeSystemDtos);
    }

    @Test
    public void testfindId() {

        CodeSystem codeSystem = mock(CodeSystem.class);

        when(codeSystemRepository.findOne(anyLong())).thenReturn(codeSystem);

        CodeSystemDto codeSystemDto = mock(CodeSystemDto.class);
        when(valueSetMgmtHelper.createCodeSystemDtoFromEntity(codeSystem))
                .thenReturn(codeSystemDto);

        assertEquals(vst.findById((long) 1), codeSystemDto);
    }

    @Test
    public void testUpdateCodeSystem() throws CodeSystemNotFoundException {

        CodeSystemDto updated = mock(CodeSystemDto.class);

        when(updated.getId()).thenReturn((long) 1);
        when(updated.getCode()).thenReturn("code");
        when(updated.getName()).thenReturn("name");
        when(updated.getUserName()).thenReturn("username");

        CodeSystem codeSystem = mock(CodeSystem.class);

        when(codeSystemRepository.findOne(anyLong())).thenReturn(codeSystem);

        CodeSystemDto codeSystemDto = mock(CodeSystemDto.class);
        when(valueSetMgmtHelper.createCodeSystemDtoFromEntity(codeSystem))
                .thenReturn(codeSystemDto);

        assertEquals(vst.update(updated), codeSystemDto);
    }

    @Test(expected = CodeSystemNotFoundException.class)
    public void testUpdateCodeSystem_throw_CodeSystemNotFoundException()
            throws CodeSystemNotFoundException {
        CodeSystemDto updated = mock(CodeSystemDto.class);
        when(codeSystemRepository.findOne(anyLong())).thenReturn(null);
        vst.update(updated);

    }

}
