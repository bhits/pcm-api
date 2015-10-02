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
public class SensitivityPolicyCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    SensitivityPolicyCodeDataOnDemand dod;

	@Autowired
    SensitivityPolicyCodeRepository sensitivityPolicyCodeRepository;

	@Test
    public void testCountAllSensitivityPolicyCodes() {
        Assert.assertNotNull("Data on demand for 'SensitivityPolicyCode' failed to initialize correctly", dod.getRandomSensitivityPolicyCode());
        long count = sensitivityPolicyCodeRepository.count();
        Assert.assertTrue("Counter for 'SensitivityPolicyCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindSensitivityPolicyCode() {
        SensitivityPolicyCode obj = dod.getRandomSensitivityPolicyCode();
        Assert.assertNotNull("Data on demand for 'SensitivityPolicyCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SensitivityPolicyCode' failed to provide an identifier", id);
        obj = sensitivityPolicyCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'SensitivityPolicyCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'SensitivityPolicyCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllSensitivityPolicyCodes() {
        Assert.assertNotNull("Data on demand for 'SensitivityPolicyCode' failed to initialize correctly", dod.getRandomSensitivityPolicyCode());
        long count = sensitivityPolicyCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'SensitivityPolicyCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<SensitivityPolicyCode> result = sensitivityPolicyCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'SensitivityPolicyCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'SensitivityPolicyCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindSensitivityPolicyCodeEntries() {
        Assert.assertNotNull("Data on demand for 'SensitivityPolicyCode' failed to initialize correctly", dod.getRandomSensitivityPolicyCode());
        long count = sensitivityPolicyCodeRepository.count();
        if (count > 20) count = 20;
		int pageNumber = 0;
        int pageSize = 10;
        List<SensitivityPolicyCode> result = sensitivityPolicyCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'SensitivityPolicyCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'SensitivityPolicyCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        SensitivityPolicyCode obj = dod.getRandomSensitivityPolicyCode();
        Assert.assertNotNull("Data on demand for 'SensitivityPolicyCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SensitivityPolicyCode' failed to provide an identifier", id);
        obj = sensitivityPolicyCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'SensitivityPolicyCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifySensitivityPolicyCode(obj);
        Integer currentVersion = obj.getVersion();
        sensitivityPolicyCodeRepository.flush();
        Assert.assertTrue("Version for 'SensitivityPolicyCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateSensitivityPolicyCodeUpdate() {
        SensitivityPolicyCode obj = dod.getRandomSensitivityPolicyCode();
        Assert.assertNotNull("Data on demand for 'SensitivityPolicyCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SensitivityPolicyCode' failed to provide an identifier", id);
        obj = sensitivityPolicyCodeRepository.findOne(id);
        boolean modified =  dod.modifySensitivityPolicyCode(obj);
        Integer currentVersion = obj.getVersion();
        SensitivityPolicyCode merged = (SensitivityPolicyCode)sensitivityPolicyCodeRepository.save(obj);
        sensitivityPolicyCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'SensitivityPolicyCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveSensitivityPolicyCode() {
        Assert.assertNotNull("Data on demand for 'SensitivityPolicyCode' failed to initialize correctly", dod.getRandomSensitivityPolicyCode());
        SensitivityPolicyCode obj = dod.getNewTransientSensitivityPolicyCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'SensitivityPolicyCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'SensitivityPolicyCode' identifier to be null", obj.getId());
        sensitivityPolicyCodeRepository.save(obj);
        sensitivityPolicyCodeRepository.flush();
        Assert.assertNotNull("Expected 'SensitivityPolicyCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteSensitivityPolicyCode() {
        SensitivityPolicyCode obj = dod.getRandomSensitivityPolicyCode();
        Assert.assertNotNull("Data on demand for 'SensitivityPolicyCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SensitivityPolicyCode' failed to provide an identifier", id);
        obj = sensitivityPolicyCodeRepository.findOne(id);
        sensitivityPolicyCodeRepository.delete(obj);
        sensitivityPolicyCodeRepository.flush();
        Assert.assertNull("Failed to remove 'SensitivityPolicyCode' with identifier '" + id + "'", sensitivityPolicyCodeRepository.findOne(id));
    }
}
