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
public class RefrainPolicyCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    RefrainPolicyCodeDataOnDemand dod;

	@Autowired
    RefrainPolicyCodeRepository refrainPolicyCodeRepository;

	@Test
    public void testCountAllRefrainPolicyCodes() {
        Assert.assertNotNull("Data on demand for 'RefrainPolicyCode' failed to initialize correctly", dod.getRandomRefrainPolicyCode());
        long count = refrainPolicyCodeRepository.count();
        Assert.assertTrue("Counter for 'RefrainPolicyCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindRefrainPolicyCode() {
        RefrainPolicyCode obj = dod.getRandomRefrainPolicyCode();
        Assert.assertNotNull("Data on demand for 'RefrainPolicyCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'RefrainPolicyCode' failed to provide an identifier", id);
        obj = refrainPolicyCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'RefrainPolicyCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'RefrainPolicyCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllRefrainPolicyCodes() {
        Assert.assertNotNull("Data on demand for 'RefrainPolicyCode' failed to initialize correctly", dod.getRandomRefrainPolicyCode());
        long count = refrainPolicyCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'RefrainPolicyCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<RefrainPolicyCode> result = refrainPolicyCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'RefrainPolicyCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'RefrainPolicyCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindRefrainPolicyCodeEntries() {
        Assert.assertNotNull("Data on demand for 'RefrainPolicyCode' failed to initialize correctly", dod.getRandomRefrainPolicyCode());
        long count = refrainPolicyCodeRepository.count();
        if (count > 20) count = 20;
		int pageNumber = 0;
        int pageSize = 10;
        List<RefrainPolicyCode> result = refrainPolicyCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'RefrainPolicyCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'RefrainPolicyCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        RefrainPolicyCode obj = dod.getRandomRefrainPolicyCode();
        Assert.assertNotNull("Data on demand for 'RefrainPolicyCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'RefrainPolicyCode' failed to provide an identifier", id);
        obj = refrainPolicyCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'RefrainPolicyCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyRefrainPolicyCode(obj);
        Integer currentVersion = obj.getVersion();
        refrainPolicyCodeRepository.flush();
        Assert.assertTrue("Version for 'RefrainPolicyCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateRefrainPolicyCodeUpdate() {
        RefrainPolicyCode obj = dod.getRandomRefrainPolicyCode();
        Assert.assertNotNull("Data on demand for 'RefrainPolicyCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'RefrainPolicyCode' failed to provide an identifier", id);
        obj = refrainPolicyCodeRepository.findOne(id);
        boolean modified =  dod.modifyRefrainPolicyCode(obj);
        Integer currentVersion = obj.getVersion();
        RefrainPolicyCode merged = (RefrainPolicyCode)refrainPolicyCodeRepository.save(obj);
        refrainPolicyCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'RefrainPolicyCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveRefrainPolicyCode() {
        Assert.assertNotNull("Data on demand for 'RefrainPolicyCode' failed to initialize correctly", dod.getRandomRefrainPolicyCode());
        RefrainPolicyCode obj = dod.getNewTransientRefrainPolicyCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'RefrainPolicyCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'RefrainPolicyCode' identifier to be null", obj.getId());
        refrainPolicyCodeRepository.save(obj);
        refrainPolicyCodeRepository.flush();
        Assert.assertNotNull("Expected 'RefrainPolicyCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteRefrainPolicyCode() {
        RefrainPolicyCode obj = dod.getRandomRefrainPolicyCode();
        Assert.assertNotNull("Data on demand for 'RefrainPolicyCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'RefrainPolicyCode' failed to provide an identifier", id);
        obj = refrainPolicyCodeRepository.findOne(id);
        refrainPolicyCodeRepository.delete(obj);
        refrainPolicyCodeRepository.flush();
        Assert.assertNull("Failed to remove 'RefrainPolicyCode' with identifier '" + id + "'", refrainPolicyCodeRepository.findOne(id));
    }
}
