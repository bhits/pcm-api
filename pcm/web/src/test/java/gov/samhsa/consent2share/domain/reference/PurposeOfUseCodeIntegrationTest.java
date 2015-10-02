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
public class PurposeOfUseCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    PurposeOfUseCodeDataOnDemand dod;

	@Autowired
    PurposeOfUseCodeRepository purposeOfUseCodeRepository;

	@Test
    public void testCountAllPurposeOfUseCodes() {
        Assert.assertNotNull("Data on demand for 'PurposeOfUseCode' failed to initialize correctly", dod.getRandomPurposeOfUseCode());
        long count = purposeOfUseCodeRepository.count();
        Assert.assertTrue("Counter for 'PurposeOfUseCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindPurposeOfUseCode() {
        PurposeOfUseCode obj = dod.getRandomPurposeOfUseCode();
        Assert.assertNotNull("Data on demand for 'PurposeOfUseCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'PurposeOfUseCode' failed to provide an identifier", id);
        obj = purposeOfUseCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'PurposeOfUseCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'PurposeOfUseCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllPurposeOfUseCodes() {
        Assert.assertNotNull("Data on demand for 'PurposeOfUseCode' failed to initialize correctly", dod.getRandomPurposeOfUseCode());
        long count = purposeOfUseCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'PurposeOfUseCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<PurposeOfUseCode> result = purposeOfUseCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'PurposeOfUseCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'PurposeOfUseCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindPurposeOfUseCodeEntries() {
        Assert.assertNotNull("Data on demand for 'PurposeOfUseCode' failed to initialize correctly", dod.getRandomPurposeOfUseCode());
        long count = purposeOfUseCodeRepository.count();
        if (count > 20) count = 20;
		int pageNumber = 0;
        int pageSize = 10;
        List<PurposeOfUseCode> result = purposeOfUseCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'PurposeOfUseCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'PurposeOfUseCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        PurposeOfUseCode obj = dod.getRandomPurposeOfUseCode();
        Assert.assertNotNull("Data on demand for 'PurposeOfUseCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'PurposeOfUseCode' failed to provide an identifier", id);
        obj = purposeOfUseCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'PurposeOfUseCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyPurposeOfUseCode(obj);
        Integer currentVersion = obj.getVersion();
        purposeOfUseCodeRepository.flush();
        Assert.assertTrue("Version for 'PurposeOfUseCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdatePurposeOfUseCodeUpdate() {
        PurposeOfUseCode obj = dod.getRandomPurposeOfUseCode();
        Assert.assertNotNull("Data on demand for 'PurposeOfUseCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'PurposeOfUseCode' failed to provide an identifier", id);
        obj = purposeOfUseCodeRepository.findOne(id);
        boolean modified =  dod.modifyPurposeOfUseCode(obj);
        Integer currentVersion = obj.getVersion();
        PurposeOfUseCode merged = (PurposeOfUseCode)purposeOfUseCodeRepository.save(obj);
        purposeOfUseCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'PurposeOfUseCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSavePurposeOfUseCode() {
        Assert.assertNotNull("Data on demand for 'PurposeOfUseCode' failed to initialize correctly", dod.getRandomPurposeOfUseCode());
        PurposeOfUseCode obj = dod.getNewTransientPurposeOfUseCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'PurposeOfUseCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'PurposeOfUseCode' identifier to be null", obj.getId());
        purposeOfUseCodeRepository.save(obj);
        purposeOfUseCodeRepository.flush();
        Assert.assertNotNull("Expected 'PurposeOfUseCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeletePurposeOfUseCode() {
        PurposeOfUseCode obj = dod.getRandomPurposeOfUseCode();
        Assert.assertNotNull("Data on demand for 'PurposeOfUseCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'PurposeOfUseCode' failed to provide an identifier", id);
        obj = purposeOfUseCodeRepository.findOne(id);
        purposeOfUseCodeRepository.delete(obj);
        purposeOfUseCodeRepository.flush();
        Assert.assertNull("Failed to remove 'PurposeOfUseCode' with identifier '" + id + "'", purposeOfUseCodeRepository.findOne(id));
    }
}
