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
public class AllergyStatusCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    AllergyStatusCodeDataOnDemand dod;

	@Autowired
    AllergyStatusCodeRepository allergyStatusCodeRepository;

	@Test
    public void testCountAllAllergyStatusCodes() {
        Assert.assertNotNull("Data on demand for 'AllergyStatusCode' failed to initialize correctly", dod.getRandomAllergyStatusCode());
        long count = allergyStatusCodeRepository.count();
        Assert.assertTrue("Counter for 'AllergyStatusCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindAllergyStatusCode() {
        AllergyStatusCode obj = dod.getRandomAllergyStatusCode();
        Assert.assertNotNull("Data on demand for 'AllergyStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'AllergyStatusCode' failed to provide an identifier", id);
        obj = allergyStatusCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'AllergyStatusCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'AllergyStatusCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllAllergyStatusCodes() {
        Assert.assertNotNull("Data on demand for 'AllergyStatusCode' failed to initialize correctly", dod.getRandomAllergyStatusCode());
        long count = allergyStatusCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'AllergyStatusCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<AllergyStatusCode> result = allergyStatusCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'AllergyStatusCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'AllergyStatusCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindAllergyStatusCodeEntries() {
        Assert.assertNotNull("Data on demand for 'AllergyStatusCode' failed to initialize correctly", dod.getRandomAllergyStatusCode());
        long count = allergyStatusCodeRepository.count();
        if (count > 20) count = 20;
        int pageNumber = 0;
        int pageSize = (int) count;
        List<AllergyStatusCode> result = allergyStatusCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();

        Assert.assertNotNull("Find entries method for 'AllergyStatusCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'AllergyStatusCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        AllergyStatusCode obj = dod.getRandomAllergyStatusCode();
        Assert.assertNotNull("Data on demand for 'AllergyStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'AllergyStatusCode' failed to provide an identifier", id);
        obj = allergyStatusCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'AllergyStatusCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyAllergyStatusCode(obj);
        Integer currentVersion = obj.getVersion();
        allergyStatusCodeRepository.flush();
        Assert.assertTrue("Version for 'AllergyStatusCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateAllergyStatusCodeUpdate() {
        AllergyStatusCode obj = dod.getRandomAllergyStatusCode();
        Assert.assertNotNull("Data on demand for 'AllergyStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'AllergyStatusCode' failed to provide an identifier", id);
        obj = allergyStatusCodeRepository.findOne(id);
        boolean modified =  dod.modifyAllergyStatusCode(obj);
        Integer currentVersion = obj.getVersion();
        AllergyStatusCode merged = (AllergyStatusCode)allergyStatusCodeRepository.save(obj);
        allergyStatusCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'AllergyStatusCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveAllergyStatusCode() {
        Assert.assertNotNull("Data on demand for 'AllergyStatusCode' failed to initialize correctly", dod.getRandomAllergyStatusCode());
        AllergyStatusCode obj = dod.getNewTransientAllergyStatusCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'AllergyStatusCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'AllergyStatusCode' identifier to be null", obj.getId());
        allergyStatusCodeRepository.save(obj);
        allergyStatusCodeRepository.flush();
        Assert.assertNotNull("Expected 'AllergyStatusCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteAllergyStatusCode() {
        AllergyStatusCode obj = dod.getRandomAllergyStatusCode();
        Assert.assertNotNull("Data on demand for 'AllergyStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'AllergyStatusCode' failed to provide an identifier", id);
        obj = allergyStatusCodeRepository.findOne(id);
        allergyStatusCodeRepository.delete(obj);
        allergyStatusCodeRepository.flush();
        Assert.assertNull("Failed to remove 'AllergyStatusCode' with identifier '" + id + "'", allergyStatusCodeRepository.findOne(id));
    }
}
