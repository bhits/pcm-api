package gov.samhsa.mhc.vss.service;

import gov.samhsa.mhc.pcm.domain.consent.ConsentRepository;
import gov.samhsa.mhc.pcm.domain.valueset.ValueSetCategory;
import gov.samhsa.mhc.pcm.domain.valueset.ValueSetCategoryRepository;
import gov.samhsa.mhc.vss.service.dto.AddConsentFieldsDto;
import gov.samhsa.mhc.vss.service.dto.ValueSetCategoryDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ValueSetCategoryServiceImplTest {

    @Mock
    ValueSetCategoryRepository valueSetCategoryRepository;

    @Mock
    ConsentRepository consentRepositoryMock;

    @Mock
    ValueSetMgmtHelper valueSetMgmtHelper;

    @InjectMocks
    ValueSetCategoryServiceImpl vst;

    @Test
    public void testCreateValueSetValueSetCategory() {
        ValueSetCategoryDto created = mock(ValueSetCategoryDto.class);
        when(created.getDescription()).thenReturn("description");
        when(created.getName()).thenReturn("name");
        when(created.getCode()).thenReturn("code");
        when(created.getUserName()).thenReturn("username");

        ValueSetCategory valueSetCategory = mock(ValueSetCategory.class);
        when(valueSetCategoryRepository.save(valueSetCategory)).thenReturn(
                valueSetCategory);
        // when(consentRepositoryMock.findAllByConsentDoNotShareSensitivityPolicyCodesValueSetCategory(valueSetCategory)).thenReturn(null);

        when(
                valueSetMgmtHelper.createValuesetCategoryDtoFromEntity(
                        any(ValueSetCategory.class),
                        any(ConsentRepository.class))).thenReturn(created);

        ValueSetCategoryDto result = vst.create(created);

        Assert.assertEquals(result, created);
    }

    @Test
    public void testDeleteValueSetCategory()
            throws ValueSetCategoryNotFoundException {

        ValueSetCategory deleted = mock(ValueSetCategory.class);
        when(valueSetCategoryRepository.findOne(anyLong())).thenReturn(deleted);
        when(
                consentRepositoryMock
                        .findAllByDoNotShareSensitivityPolicyCodesValueSetCategory(deleted))
                .thenReturn(null);

        ValueSetCategoryDto valueSetCategoryDto = mock(ValueSetCategoryDto.class);
        when(
                valueSetMgmtHelper.createValuesetCategoryDtoFromEntity(deleted,
                        consentRepositoryMock)).thenReturn(valueSetCategoryDto);

        Assert.assertEquals(vst.delete((long) 1), valueSetCategoryDto);
    }

    @Test(expected = ValueSetCategoryNotFoundException.class)
    public void testDeleteVelueSetCategory_throw_ValueSetCategoryNotFoundException()
            throws ValueSetCategoryNotFoundException {

        when(valueSetCategoryRepository.findOne(anyLong())).thenReturn(null);
        vst.delete((long) 1);

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testfindAll() {

        List<ValueSetCategory> valueSets = mock(List.class);
        when(valueSetCategoryRepository.findAll()).thenReturn(valueSets);

        List<ValueSetCategoryDto> valueSetDtos = new ArrayList<ValueSetCategoryDto>();
        ValueSetCategoryDto valueSetDto = mock(ValueSetCategoryDto.class);

        valueSetDtos.add(valueSetDto);
        when(
                valueSetMgmtHelper.convertValueSetCategoryEntitiesToDtos(
                        valueSets, consentRepositoryMock)).thenReturn(
                valueSetDtos);
        assertEquals(vst.findAll(), valueSetDtos);
    }

    @Test
    public void testfindId() {

        ValueSetCategory valueSetCategory = mock(ValueSetCategory.class);

        when(valueSetCategoryRepository.findOne(anyLong())).thenReturn(
                valueSetCategory);
        when(
                consentRepositoryMock
                        .findAllByDoNotShareSensitivityPolicyCodesValueSetCategory(valueSetCategory))
                .thenReturn(null);

        ValueSetCategoryDto valueSetCategoryDto = mock(ValueSetCategoryDto.class);
        when(
                valueSetMgmtHelper.createValuesetCategoryDtoFromEntity(
                        valueSetCategory, consentRepositoryMock)).thenReturn(
                valueSetCategoryDto);

        assertEquals(vst.findById((long) 1), valueSetCategoryDto);
    }

    @Test
    public void testUpdateValueSetCategory()
            throws ValueSetCategoryNotFoundException {

        ValueSetCategoryDto updated = mock(ValueSetCategoryDto.class);

        when(updated.getId()).thenReturn((long) 1);
        when(updated.getCode()).thenReturn("code");
        when(updated.getName()).thenReturn("name");
        when(updated.getUserName()).thenReturn("username");

        ValueSetCategory valueSetCategory = mock(ValueSetCategory.class);

        when(valueSetCategoryRepository.findOne(anyLong())).thenReturn(
                valueSetCategory);
        when(
                consentRepositoryMock
                        .findAllByDoNotShareSensitivityPolicyCodesValueSetCategory(valueSetCategory))
                .thenReturn(null);

        ValueSetCategoryDto valueSetCategoryDto = mock(ValueSetCategoryDto.class);
        when(
                valueSetMgmtHelper.createValuesetCategoryDtoFromEntity(
                        valueSetCategory, consentRepositoryMock)).thenReturn(
                valueSetCategoryDto);

        assertEquals(vst.update(updated), valueSetCategoryDto);
    }

    @Test(expected = ValueSetCategoryNotFoundException.class)
    public void testUpdateValueSetCategory_throw_ValueSetCategoryNotFoundException()
            throws ValueSetCategoryNotFoundException {
        ValueSetCategoryDto updated = mock(ValueSetCategoryDto.class);
        when(valueSetCategoryRepository.findOne(anyLong())).thenReturn(null);
        vst.update(updated);

    }

    @Test
    public void testFindAllValueSetCategoriesAddConsentFieldsDto() {
        // Arrange
        String vscMockCode = "vscMockCode";
        String vscMockName = "vscMockName";
        String vscMockDescription = "vscMockDescription";
        boolean vscMockIsFederal= true;
        Long vscMockDisplayOrder = 1l;
        String vscMock2Code = "vscMock2Code";
        String vscMock2Name = "vscMock2Name";
        String vscMock2Description = "vscMock2Description";
        boolean vscMock2IsFederal= true;
        Long vscMock2DisplayOrder = 2l;
        ValueSetCategory vscMock = mock(ValueSetCategory.class);
        ValueSetCategory vscMock2 = mock(ValueSetCategory.class);
        when(vscMock.getCode()).thenReturn(vscMockCode);
        when(vscMock.getName()).thenReturn(vscMockName);
        when(vscMock.getDescription()).thenReturn(vscMockDescription);
        when(vscMock.isFederal()).thenReturn(vscMockIsFederal);
        when(vscMock.getDisplayOrder()).thenReturn(vscMockDisplayOrder);
        when(vscMock2.getCode()).thenReturn(vscMock2Code);
        when(vscMock2.getName()).thenReturn(vscMock2Name);
        when(vscMock2.getDescription()).thenReturn(vscMock2Description);
        when(vscMock2.isFederal()).thenReturn(vscMock2IsFederal);
        when(vscMock2.getDisplayOrder()).thenReturn(vscMock2DisplayOrder);
        List<ValueSetCategory> valueSetCategoryListMock = new ArrayList<ValueSetCategory>();
        valueSetCategoryListMock.add(vscMock);
        valueSetCategoryListMock.add(vscMock2);
        when(valueSetCategoryRepository.findAll()).thenReturn(
                valueSetCategoryListMock);

        // Act
        List<AddConsentFieldsDto> sensitivityPolicyDto = vst
                .findAllValueSetCategoriesAddConsentFieldsDto();

        // Assert
        assertEquals(2, sensitivityPolicyDto.size());
        assertEquals(vscMockCode, sensitivityPolicyDto.get(0).getCode());
        assertEquals(vscMockName, sensitivityPolicyDto.get(0).getDisplayName());
        assertEquals(vscMockDescription, sensitivityPolicyDto.get(0)
                .getDescription());
        assertEquals(vscMockIsFederal,sensitivityPolicyDto.get(0).isFederal());
        assertEquals(vscMockDisplayOrder,sensitivityPolicyDto.get(0).getDisplayOrder());
        assertEquals(vscMock2Code, sensitivityPolicyDto.get(1).getCode());
        assertEquals(vscMock2Name, sensitivityPolicyDto.get(1).getDisplayName());
        assertEquals(vscMock2Description, sensitivityPolicyDto.get(1)
                .getDescription());
        assertEquals(vscMock2IsFederal,sensitivityPolicyDto.get(1).isFederal());
        assertEquals(vscMock2DisplayOrder,sensitivityPolicyDto.get(1).getDisplayOrder());
    }
}
