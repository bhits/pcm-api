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
public class StateCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    StateCodeDataOnDemand dod;

	@Autowired
    StateCodeRepository stateCodeRepository;

	@Test
    public void testCountAllStateCodes() {
        Assert.assertNotNull("Data on demand for 'StateCode' failed to initialize correctly", dod.getRandomStateCode());
        long count = stateCodeRepository.count();
        Assert.assertTrue("Counter for 'StateCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindStateCode() {
        StateCode obj = dod.getRandomStateCode();
        Assert.assertNotNull("Data on demand for 'StateCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'StateCode' failed to provide an identifier", id);
        obj = stateCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'StateCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'StateCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllStateCodes() {
        Assert.assertNotNull("Data on demand for 'StateCode' failed to initialize correctly", dod.getRandomStateCode());
        long count = stateCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'StateCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<StateCode> result = stateCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'StateCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'StateCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindStateCodeEntries() {
        Assert.assertNotNull("Data on demand for 'StateCode' failed to initialize correctly", dod.getRandomStateCode());
        long count = stateCodeRepository.count();
        if (count > 20) count = 20;
		int pageNumber = 0;
        int pageSize = 10;
        List<StateCode> result = stateCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'StateCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'StateCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        StateCode obj = dod.getRandomStateCode();
        Assert.assertNotNull("Data on demand for 'StateCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'StateCode' failed to provide an identifier", id);
        obj = stateCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'StateCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyStateCode(obj);
        Integer currentVersion = obj.getVersion();
        stateCodeRepository.flush();
        Assert.assertTrue("Version for 'StateCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateStateCodeUpdate() {
        StateCode obj = dod.getRandomStateCode();
        Assert.assertNotNull("Data on demand for 'StateCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'StateCode' failed to provide an identifier", id);
        obj = stateCodeRepository.findOne(id);
        boolean modified =  dod.modifyStateCode(obj);
        Integer currentVersion = obj.getVersion();
        StateCode merged = (StateCode)stateCodeRepository.save(obj);
        stateCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'StateCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveStateCode() {
        Assert.assertNotNull("Data on demand for 'StateCode' failed to initialize correctly", dod.getRandomStateCode());
        StateCode obj = dod.getNewTransientStateCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'StateCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'StateCode' identifier to be null", obj.getId());
        stateCodeRepository.save(obj);
        stateCodeRepository.flush();
        Assert.assertNotNull("Expected 'StateCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteStateCode() {
        StateCode obj = dod.getRandomStateCode();
        Assert.assertNotNull("Data on demand for 'StateCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'StateCode' failed to provide an identifier", id);
        obj = stateCodeRepository.findOne(id);
        stateCodeRepository.delete(obj);
        stateCodeRepository.flush();
        Assert.assertNull("Failed to remove 'StateCode' with identifier '" + id + "'", stateCodeRepository.findOne(id));
    }
}
