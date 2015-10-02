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
public class AllergySeverityCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    AllergySeverityCodeDataOnDemand dod;

	@Autowired
    AllergySeverityCodeRepository allergySeverityCodeRepository;

	@Test
    public void testCountAllAllergySeverityCodes() {
        Assert.assertNotNull("Data on demand for 'AllergySeverityCode' failed to initialize correctly", dod.getRandomAllergySeverityCode());
        long count = allergySeverityCodeRepository.count();
        Assert.assertTrue("Counter for 'AllergySeverityCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindAllergySeverityCode() {
        AllergySeverityCode obj = dod.getRandomAllergySeverityCode();
        Assert.assertNotNull("Data on demand for 'AllergySeverityCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'AllergySeverityCode' failed to provide an identifier", id);
        obj = allergySeverityCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'AllergySeverityCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'AllergySeverityCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllAllergySeverityCodes() {
        Assert.assertNotNull("Data on demand for 'AllergySeverityCode' failed to initialize correctly", dod.getRandomAllergySeverityCode());
        long count = allergySeverityCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'AllergySeverityCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<AllergySeverityCode> result = allergySeverityCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'AllergySeverityCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'AllergySeverityCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindAllergySeverityCodeEntries() {
        Assert.assertNotNull("Data on demand for 'AllergySeverityCode' failed to initialize correctly", dod.getRandomAllergySeverityCode());
        long count = allergySeverityCodeRepository.count();
        if (count > 20) count = 20;
        int pageNumber = 0;
        int pageSize = (int) count;
        List<AllergySeverityCode> result = allergySeverityCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'AllergySeverityCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'AllergySeverityCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        AllergySeverityCode obj = dod.getRandomAllergySeverityCode();
        Assert.assertNotNull("Data on demand for 'AllergySeverityCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'AllergySeverityCode' failed to provide an identifier", id);
        obj = allergySeverityCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'AllergySeverityCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyAllergySeverityCode(obj);
        Integer currentVersion = obj.getVersion();
        allergySeverityCodeRepository.flush();
        Assert.assertTrue("Version for 'AllergySeverityCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateAllergySeverityCodeUpdate() {
        AllergySeverityCode obj = dod.getRandomAllergySeverityCode();
        Assert.assertNotNull("Data on demand for 'AllergySeverityCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'AllergySeverityCode' failed to provide an identifier", id);
        obj = allergySeverityCodeRepository.findOne(id);
        boolean modified =  dod.modifyAllergySeverityCode(obj);
        Integer currentVersion = obj.getVersion();
        AllergySeverityCode merged = (AllergySeverityCode)allergySeverityCodeRepository.save(obj);
        allergySeverityCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'AllergySeverityCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveAllergySeverityCode() {
        Assert.assertNotNull("Data on demand for 'AllergySeverityCode' failed to initialize correctly", dod.getRandomAllergySeverityCode());
        AllergySeverityCode obj = dod.getNewTransientAllergySeverityCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'AllergySeverityCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'AllergySeverityCode' identifier to be null", obj.getId());
        allergySeverityCodeRepository.save(obj);
        allergySeverityCodeRepository.flush();
        Assert.assertNotNull("Expected 'AllergySeverityCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteAllergySeverityCode() {
        AllergySeverityCode obj = dod.getRandomAllergySeverityCode();
        Assert.assertNotNull("Data on demand for 'AllergySeverityCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'AllergySeverityCode' failed to provide an identifier", id);
        obj = allergySeverityCodeRepository.findOne(id);
        allergySeverityCodeRepository.delete(obj);
        allergySeverityCodeRepository.flush();
        Assert.assertNull("Failed to remove 'AllergySeverityCode' with identifier '" + id + "'", allergySeverityCodeRepository.findOne(id));
    }
}
