package gov.samhsa.mhc.vss.service;

import gov.samhsa.mhc.pcm.domain.valueset.*;
import gov.samhsa.mhc.vss.service.dto.ValueSetDto;
import gov.samhsa.mhc.vss.service.dto.ValueSetVSCDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class ValueSetServiceImplTest {

    private final int valueSetPageSize = 20;

    @Mock
    ValueSetRepository valueSetRepository;

    @Mock
    ValueSetCategoryRepository valueSetCategoryRepository;

    @Mock
    ConceptCodeValueSetRepository conceptCodeValueSetRepository;

    @Mock
    ValueSetMgmtHelper valueSetMgmtHelper;

    @InjectMocks
    ValueSetServiceImpl vst = new ValueSetServiceImpl(valueSetPageSize,
            valueSetRepository, valueSetCategoryRepository,
            conceptCodeValueSetRepository, valueSetMgmtHelper);

    @Test(expected = ValueSetCategoryNotFoundException.class)
    public void testCreateValueSet_throw_ValueSetCategoryNotFoundException()
            throws ValueSetCategoryNotFoundException {
        ValueSetDto created = mock(ValueSetDto.class);
        when(created.getDescription()).thenReturn("description");
        when(created.getName()).thenReturn("name");
        when(created.getCode()).thenReturn("code");
        when(created.getValueSetCategoryId()).thenReturn((long) 1);

        ValueSetCategory selected = new ValueSetCategory();
        selected.setCode("code");
        selected.setName("name");
        selected.setId((long) 1);
        when(valueSetCategoryRepository.findOne((long) 1)).thenReturn(null);

        when(
                valueSetMgmtHelper
                        .createValuesetDtoFromEntity(any(ValueSet.class)))
                .thenReturn(created);

        ValueSetDto result = vst.create(created);
        assertNotNull(result);

    }

    @Test
    public void testDeleteVelueSet() throws ValueSetNotFoundException {

        ValueSet deleted = mock(ValueSet.class);
        when(valueSetRepository.findOne(anyLong())).thenReturn(deleted);

        ValueSetDto valueSetDto = mock(ValueSetDto.class);
        when(valueSetMgmtHelper.createValuesetDtoFromEntity(deleted))
                .thenReturn(valueSetDto);

        Assert.assertEquals(vst.delete((long) 1), valueSetDto);
    }

    @Test(expected = ValueSetNotFoundException.class)
    public void testDeleteVelueSet_throw_ValueSetNotFoundException()
            throws ValueSetNotFoundException {

        when(valueSetRepository.findOne(anyLong())).thenReturn(null);
        vst.delete((long) 1);

    }

    @Test
    public void testfindAll() {

        List<ValueSet> valueSetsMock = mock(List.class);
        when(valueSetRepository.findAll()).thenReturn(valueSetsMock);

        List<ValueSetDto> valueSetDtosMock = new ArrayList<ValueSetDto>();
        ValueSetDto valueSetDtoMock = mock(ValueSetDto.class);
        valueSetDtosMock.add(valueSetDtoMock);
        when(valueSetMgmtHelper.convertValueSetEntitiesToDtos(valueSetsMock))
                .thenReturn(valueSetDtosMock);

        when(valueSetDtoMock.getId()).thenReturn((long) 1);
        when(conceptCodeValueSetRepository.findAllByPkValueSetId(anyLong()))
                .thenReturn(null);

        assertEquals(vst.findAll(), valueSetDtosMock);
    }

    @Test
    public void testFindAllByName() {
        Page<ValueSet> valueSetsMock = mock(Page.class);
        when(
                valueSetRepository.findAllByNameLike(anyString(), anyString(),
                        any(Pageable.class))).thenReturn(valueSetsMock);

        List<ValueSetDto> valueSetDtosMock = new ArrayList<ValueSetDto>();
        ValueSetDto valueSetDtoMock = mock(ValueSetDto.class);
        valueSetDtosMock.add(valueSetDtoMock);
        when(
                valueSetMgmtHelper.convertValueSetEntitiesToDtos(valueSetsMock
                        .getContent())).thenReturn(valueSetDtosMock);

        when(valueSetDtoMock.getId()).thenReturn((long) 1);
        when(conceptCodeValueSetRepository.findAllByPkValueSetId(anyLong()))
                .thenReturn(null);

        assertEquals(vst.findAllByName("a", null, 0).get("valueSets"),
                valueSetDtosMock);

    }

    @Test
    public void testFindAllByCode() {
        Page<ValueSet> valueSetsMock = mock(Page.class);

        when(
                valueSetRepository.findAllByCodeLike(anyString(), anyString(),
                        any(Pageable.class))).thenReturn(valueSetsMock);

        List<ValueSetDto> valueSetDtosMock = new ArrayList<ValueSetDto>();
        ValueSetDto valueSetDtoMock = mock(ValueSetDto.class);
        valueSetDtosMock.add(valueSetDtoMock);
        when(
                valueSetMgmtHelper.convertValueSetEntitiesToDtos(valueSetsMock
                        .getContent())).thenReturn(valueSetDtosMock);

        when(valueSetDtoMock.getId()).thenReturn((long) 1);
        when(conceptCodeValueSetRepository.findAllByPkValueSetId(anyLong()))
                .thenReturn(null);

        assertEquals(vst.findAllByCode("a", "ETH", 0).get("valueSets"),
                valueSetDtosMock);

    }

    @Test(expected = ValueSetCategoryNotFoundException.class)
    public void testfindId() throws ValueSetCategoryNotFoundException {

        ValueSet valueSet = mock(ValueSet.class);

        when(valueSetRepository.findOne(anyLong())).thenReturn(valueSet);

        ValueSetDto valueSetDto = mock(ValueSetDto.class);
        when(valueSetMgmtHelper.createValuesetDtoFromEntity(valueSet))
                .thenReturn(valueSetDto);

        assertEquals(vst.findById((long) 1), valueSetDto);
    }

    @Test
    public void testUpdateValueSet() throws ValueSetNotFoundException,
            ValueSetCategoryNotFoundException {

        ValueSetDto updated = mock(ValueSetDto.class);

        when(updated.getId()).thenReturn((long) 1);
        when(updated.getCode()).thenReturn("code");
        when(updated.getName()).thenReturn("name");
        when(updated.getUserName()).thenReturn("username");

        ValueSet valueSet = mock(ValueSet.class);

        when(valueSetRepository.findOne(anyLong())).thenReturn(valueSet);
        // when(valueSet.getValueSetCategory().getId()).thenReturn((long)1);
        ValueSetDto valueSetDto = mock(ValueSetDto.class);
        when(valueSetMgmtHelper.createValuesetDtoFromEntity(valueSet))
                .thenReturn(valueSetDto);

        // assertEquals(vst.update(updated),valueSetDto);
    }

    @Test(expected = ValueSetNotFoundException.class)
    public void testUpdateValueSet_throw_ValueSetNotFoundException()
            throws ValueSetNotFoundException, ValueSetCategoryNotFoundException {
        ValueSetDto updated = mock(ValueSetDto.class);
        when(valueSetRepository.findOne(anyLong())).thenReturn(null);
        vst.update(updated);

    }

    @Test(expected = ValueSetCategoryNotFoundException.class)
    public void testValueSetVSCDto_throw_ValueSetCategoryNotFoundException()
            throws ValueSetCategoryNotFoundException {

        when(valueSetCategoryRepository.findAll()).thenReturn(null);
        vst.create();

    }

    @Test
    public void testValueSetVSCDto() throws ValueSetCategoryNotFoundException {
        ValueSetVSCDto valueSetVSCDto = new ValueSetVSCDto();

        ValueSetCategory valueSetCategory = mock(ValueSetCategory.class);
        List<ValueSetCategory> valueSetCategories = Arrays
                .asList(valueSetCategory);

        Map<Long, String> valueSetCategoriesMap = mock(Map.class);

        when(valueSetCategoryRepository.findAll()).thenReturn(
                valueSetCategories);
        when(
                valueSetMgmtHelper
                        .convertValueSetCategoryEntitiesToMap(valueSetCategories))
                .thenReturn(valueSetCategoriesMap);
        valueSetVSCDto.setValueSetCategoryMap(valueSetCategoriesMap);

        vst.create();

    }

}
