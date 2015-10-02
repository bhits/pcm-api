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
public class PrivacyLawPolicyCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    PrivacyLawPolicyCodeDataOnDemand dod;

	@Autowired
    PrivacyLawPolicyCodeRepository privacyLawPolicyCodeRepository;

	@Test
    public void testCountAllPrivacyLawPolicyCodes() {
        Assert.assertNotNull("Data on demand for 'PrivacyLawPolicyCode' failed to initialize correctly", dod.getRandomPrivacyLawPolicyCode());
        long count = privacyLawPolicyCodeRepository.count();
        Assert.assertTrue("Counter for 'PrivacyLawPolicyCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindPrivacyLawPolicyCode() {
        PrivacyLawPolicyCode obj = dod.getRandomPrivacyLawPolicyCode();
        Assert.assertNotNull("Data on demand for 'PrivacyLawPolicyCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'PrivacyLawPolicyCode' failed to provide an identifier", id);
        obj = privacyLawPolicyCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'PrivacyLawPolicyCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'PrivacyLawPolicyCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllPrivacyLawPolicyCodes() {
        Assert.assertNotNull("Data on demand for 'PrivacyLawPolicyCode' failed to initialize correctly", dod.getRandomPrivacyLawPolicyCode());
        long count = privacyLawPolicyCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'PrivacyLawPolicyCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<PrivacyLawPolicyCode> result = privacyLawPolicyCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'PrivacyLawPolicyCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'PrivacyLawPolicyCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindPrivacyLawPolicyCodeEntries() {
        Assert.assertNotNull("Data on demand for 'PrivacyLawPolicyCode' failed to initialize correctly", dod.getRandomPrivacyLawPolicyCode());
        long count = privacyLawPolicyCodeRepository.count();
        if (count > 20) count = 20;
		int pageNumber = 0;
        int pageSize = 10;
        List<PrivacyLawPolicyCode> result = privacyLawPolicyCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'PrivacyLawPolicyCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'PrivacyLawPolicyCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        PrivacyLawPolicyCode obj = dod.getRandomPrivacyLawPolicyCode();
        Assert.assertNotNull("Data on demand for 'PrivacyLawPolicyCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'PrivacyLawPolicyCode' failed to provide an identifier", id);
        obj = privacyLawPolicyCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'PrivacyLawPolicyCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyPrivacyLawPolicyCode(obj);
        Integer currentVersion = obj.getVersion();
        privacyLawPolicyCodeRepository.flush();
        Assert.assertTrue("Version for 'PrivacyLawPolicyCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdatePrivacyLawPolicyCodeUpdate() {
        PrivacyLawPolicyCode obj = dod.getRandomPrivacyLawPolicyCode();
        Assert.assertNotNull("Data on demand for 'PrivacyLawPolicyCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'PrivacyLawPolicyCode' failed to provide an identifier", id);
        obj = privacyLawPolicyCodeRepository.findOne(id);
        boolean modified =  dod.modifyPrivacyLawPolicyCode(obj);
        Integer currentVersion = obj.getVersion();
        PrivacyLawPolicyCode merged = (PrivacyLawPolicyCode)privacyLawPolicyCodeRepository.save(obj);
        privacyLawPolicyCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'PrivacyLawPolicyCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSavePrivacyLawPolicyCode() {
        Assert.assertNotNull("Data on demand for 'PrivacyLawPolicyCode' failed to initialize correctly", dod.getRandomPrivacyLawPolicyCode());
        PrivacyLawPolicyCode obj = dod.getNewTransientPrivacyLawPolicyCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'PrivacyLawPolicyCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'PrivacyLawPolicyCode' identifier to be null", obj.getId());
        privacyLawPolicyCodeRepository.save(obj);
        privacyLawPolicyCodeRepository.flush();
        Assert.assertNotNull("Expected 'PrivacyLawPolicyCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeletePrivacyLawPolicyCode() {
        PrivacyLawPolicyCode obj = dod.getRandomPrivacyLawPolicyCode();
        Assert.assertNotNull("Data on demand for 'PrivacyLawPolicyCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'PrivacyLawPolicyCode' failed to provide an identifier", id);
        obj = privacyLawPolicyCodeRepository.findOne(id);
        privacyLawPolicyCodeRepository.delete(obj);
        privacyLawPolicyCodeRepository.flush();
        Assert.assertNull("Failed to remove 'PrivacyLawPolicyCode' with identifier '" + id + "'", privacyLawPolicyCodeRepository.findOne(id));
    }
}
