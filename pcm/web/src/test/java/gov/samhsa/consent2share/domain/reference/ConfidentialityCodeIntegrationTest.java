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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@ActiveProfiles("test")
@Transactional
@Configurable
public class ConfidentialityCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    ConfidentialityCodeDataOnDemand dod;

	@Autowired
    ConfidentialityCodeRepository confidentialityCodeRepository;

	@Test
    public void testCountAllConfidentialityCodes() {
        Assert.assertNotNull("Data on demand for 'ConfidentialityCode' failed to initialize correctly", dod.getRandomConfidentialityCode());
        long count = confidentialityCodeRepository.count();
        Assert.assertTrue("Counter for 'ConfidentialityCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindConfidentialityCode() {
        ConfidentialityCode obj = dod.getRandomConfidentialityCode();
        Assert.assertNotNull("Data on demand for 'ConfidentialityCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ConfidentialityCode' failed to provide an identifier", id);
        obj = confidentialityCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ConfidentialityCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'ConfidentialityCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllConfidentialityCodes() {
        Assert.assertNotNull("Data on demand for 'ConfidentialityCode' failed to initialize correctly", dod.getRandomConfidentialityCode());
        long count = confidentialityCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'ConfidentialityCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<ConfidentialityCode> result = confidentialityCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'ConfidentialityCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'ConfidentialityCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindConfidentialityCodeEntries() {
        Assert.assertNotNull("Data on demand for 'ConfidentialityCode' failed to initialize correctly", dod.getRandomConfidentialityCode());
        long count = confidentialityCodeRepository.count();
        if (count > 20) count = 20;
		int pageNumber = 0;
        int pageSize = 10;
        List<ConfidentialityCode> result = confidentialityCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'ConfidentialityCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'ConfidentialityCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        ConfidentialityCode obj = dod.getRandomConfidentialityCode();
        Assert.assertNotNull("Data on demand for 'ConfidentialityCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ConfidentialityCode' failed to provide an identifier", id);
        obj = confidentialityCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ConfidentialityCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyConfidentialityCode(obj);
        Integer currentVersion = obj.getVersion();
        confidentialityCodeRepository.flush();
        Assert.assertTrue("Version for 'ConfidentialityCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateConfidentialityCodeUpdate() {
        ConfidentialityCode obj = dod.getRandomConfidentialityCode();
        Assert.assertNotNull("Data on demand for 'ConfidentialityCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ConfidentialityCode' failed to provide an identifier", id);
        obj = confidentialityCodeRepository.findOne(id);
        boolean modified =  dod.modifyConfidentialityCode(obj);
        Integer currentVersion = obj.getVersion();
        ConfidentialityCode merged = (ConfidentialityCode)confidentialityCodeRepository.save(obj);
        confidentialityCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'ConfidentialityCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveConfidentialityCode() {
        Assert.assertNotNull("Data on demand for 'ConfidentialityCode' failed to initialize correctly", dod.getRandomConfidentialityCode());
        ConfidentialityCode obj = dod.getNewTransientConfidentialityCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'ConfidentialityCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'ConfidentialityCode' identifier to be null", obj.getId());
        confidentialityCodeRepository.save(obj);
        confidentialityCodeRepository.flush();
        Assert.assertNotNull("Expected 'ConfidentialityCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteConfidentialityCode() {
        ConfidentialityCode obj = dod.getRandomConfidentialityCode();
        Assert.assertNotNull("Data on demand for 'ConfidentialityCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ConfidentialityCode' failed to provide an identifier", id);
        obj = confidentialityCodeRepository.findOne(id);
        confidentialityCodeRepository.delete(obj);
        confidentialityCodeRepository.flush();
        Assert.assertNull("Failed to remove 'ConfidentialityCode' with identifier '" + id + "'", confidentialityCodeRepository.findOne(id));
    }
}
