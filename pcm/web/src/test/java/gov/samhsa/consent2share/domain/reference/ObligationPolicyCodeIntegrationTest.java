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
public class ObligationPolicyCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    ObligationPolicyCodeDataOnDemand dod;

	@Autowired
    ObligationPolicyCodeRepository obligationPolicyCodeRepository;

	@Test
    public void testCountAllObligationPolicyCodes() {
        Assert.assertNotNull("Data on demand for 'ObligationPolicyCode' failed to initialize correctly", dod.getRandomObligationPolicyCode());
        long count = obligationPolicyCodeRepository.count();
        Assert.assertTrue("Counter for 'ObligationPolicyCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindObligationPolicyCode() {
        ObligationPolicyCode obj = dod.getRandomObligationPolicyCode();
        Assert.assertNotNull("Data on demand for 'ObligationPolicyCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ObligationPolicyCode' failed to provide an identifier", id);
        obj = obligationPolicyCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ObligationPolicyCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'ObligationPolicyCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllObligationPolicyCodes() {
        Assert.assertNotNull("Data on demand for 'ObligationPolicyCode' failed to initialize correctly", dod.getRandomObligationPolicyCode());
        long count = obligationPolicyCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'ObligationPolicyCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<ObligationPolicyCode> result = obligationPolicyCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'ObligationPolicyCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'ObligationPolicyCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindObligationPolicyCodeEntries() {
        Assert.assertNotNull("Data on demand for 'ObligationPolicyCode' failed to initialize correctly", dod.getRandomObligationPolicyCode());
        long count = obligationPolicyCodeRepository.count();
        if (count > 20) count = 20;
		int pageNumber = 0;
        int pageSize = 10;
        List<ObligationPolicyCode> result = obligationPolicyCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'ObligationPolicyCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'ObligationPolicyCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        ObligationPolicyCode obj = dod.getRandomObligationPolicyCode();
        Assert.assertNotNull("Data on demand for 'ObligationPolicyCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ObligationPolicyCode' failed to provide an identifier", id);
        obj = obligationPolicyCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ObligationPolicyCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyObligationPolicyCode(obj);
        Integer currentVersion = obj.getVersion();
        obligationPolicyCodeRepository.flush();
        Assert.assertTrue("Version for 'ObligationPolicyCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateObligationPolicyCodeUpdate() {
        ObligationPolicyCode obj = dod.getRandomObligationPolicyCode();
        Assert.assertNotNull("Data on demand for 'ObligationPolicyCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ObligationPolicyCode' failed to provide an identifier", id);
        obj = obligationPolicyCodeRepository.findOne(id);
        boolean modified =  dod.modifyObligationPolicyCode(obj);
        Integer currentVersion = obj.getVersion();
        ObligationPolicyCode merged = (ObligationPolicyCode)obligationPolicyCodeRepository.save(obj);
        obligationPolicyCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'ObligationPolicyCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveObligationPolicyCode() {
        Assert.assertNotNull("Data on demand for 'ObligationPolicyCode' failed to initialize correctly", dod.getRandomObligationPolicyCode());
        ObligationPolicyCode obj = dod.getNewTransientObligationPolicyCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'ObligationPolicyCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'ObligationPolicyCode' identifier to be null", obj.getId());
        obligationPolicyCodeRepository.save(obj);
        obligationPolicyCodeRepository.flush();
        Assert.assertNotNull("Expected 'ObligationPolicyCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteObligationPolicyCode() {
        ObligationPolicyCode obj = dod.getRandomObligationPolicyCode();
        Assert.assertNotNull("Data on demand for 'ObligationPolicyCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ObligationPolicyCode' failed to provide an identifier", id);
        obj = obligationPolicyCodeRepository.findOne(id);
        obligationPolicyCodeRepository.delete(obj);
        obligationPolicyCodeRepository.flush();
        Assert.assertNull("Failed to remove 'ObligationPolicyCode' with identifier '" + id + "'", obligationPolicyCodeRepository.findOne(id));
    }
}
