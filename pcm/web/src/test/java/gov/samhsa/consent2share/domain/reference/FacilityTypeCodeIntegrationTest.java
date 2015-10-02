package gov.samhsa.consent2share.domain.reference;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@ActiveProfiles("test")
@Transactional
public class FacilityTypeCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    FacilityTypeCodeDataOnDemand dod;

	@Autowired
    FacilityTypeCodeRepository facilityTypeCodeRepository;

	@Test
    public void testCountAllFacilityTypeCodes() {
        Assert.assertNotNull("Data on demand for 'FacilityTypeCode' failed to initialize correctly", dod.getRandomFacilityTypeCode());
        long count = facilityTypeCodeRepository.count();
        Assert.assertTrue("Counter for 'FacilityTypeCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindFacilityTypeCode() {
        FacilityTypeCode obj = dod.getRandomFacilityTypeCode();
        Assert.assertNotNull("Data on demand for 'FacilityTypeCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'FacilityTypeCode' failed to provide an identifier", id);
        obj = facilityTypeCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'FacilityTypeCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'FacilityTypeCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllFacilityTypeCodes() {
        Assert.assertNotNull("Data on demand for 'FacilityTypeCode' failed to initialize correctly", dod.getRandomFacilityTypeCode());
        long count = facilityTypeCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'FacilityTypeCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<FacilityTypeCode> result = facilityTypeCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'FacilityTypeCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'FacilityTypeCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindFacilityTypeCodeEntries() {
        Assert.assertNotNull("Data on demand for 'FacilityTypeCode' failed to initialize correctly", dod.getRandomFacilityTypeCode());
        long count = facilityTypeCodeRepository.count();
        if (count > 20) count = 20;
		int pageNumber = 0;
        int pageSize = 10;
        List<FacilityTypeCode> result = facilityTypeCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'FacilityTypeCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'FacilityTypeCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        FacilityTypeCode obj = dod.getRandomFacilityTypeCode();
        Assert.assertNotNull("Data on demand for 'FacilityTypeCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'FacilityTypeCode' failed to provide an identifier", id);
        obj = facilityTypeCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'FacilityTypeCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyFacilityTypeCode(obj);
        Integer currentVersion = obj.getVersion();
        facilityTypeCodeRepository.flush();
        Assert.assertTrue("Version for 'FacilityTypeCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateFacilityTypeCodeUpdate() {
        FacilityTypeCode obj = dod.getRandomFacilityTypeCode();
        Assert.assertNotNull("Data on demand for 'FacilityTypeCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'FacilityTypeCode' failed to provide an identifier", id);
        obj = facilityTypeCodeRepository.findOne(id);
        boolean modified =  dod.modifyFacilityTypeCode(obj);
        Integer currentVersion = obj.getVersion();
        FacilityTypeCode merged = (FacilityTypeCode)facilityTypeCodeRepository.save(obj);
        facilityTypeCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'FacilityTypeCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveFacilityTypeCode() {
        Assert.assertNotNull("Data on demand for 'FacilityTypeCode' failed to initialize correctly", dod.getRandomFacilityTypeCode());
        FacilityTypeCode obj = dod.getNewTransientFacilityTypeCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'FacilityTypeCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'FacilityTypeCode' identifier to be null", obj.getId());
        facilityTypeCodeRepository.save(obj);
        facilityTypeCodeRepository.flush();
        Assert.assertNotNull("Expected 'FacilityTypeCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteFacilityTypeCode() {
        FacilityTypeCode obj = dod.getRandomFacilityTypeCode();
        Assert.assertNotNull("Data on demand for 'FacilityTypeCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'FacilityTypeCode' failed to provide an identifier", id);
        obj = facilityTypeCodeRepository.findOne(id);
        facilityTypeCodeRepository.delete(obj);
        facilityTypeCodeRepository.flush();
        Assert.assertNull("Failed to remove 'FacilityTypeCode' with identifier '" + id + "'", facilityTypeCodeRepository.findOne(id));
    }
}
