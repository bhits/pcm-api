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
public class AllergyReactionCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    AllergyReactionCodeDataOnDemand dod;

	@Autowired
    AllergyReactionCodeRepository allergyReactionCodeRepository;

	@Test
    public void testCountAllAllergyReactionCodes() {
        Assert.assertNotNull("Data on demand for 'AllergyReactionCode' failed to initialize correctly", dod.getRandomAllergyReactionCode());
        long count = allergyReactionCodeRepository.count();
        Assert.assertTrue("Counter for 'AllergyReactionCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindAllergyReactionCode() {
        AllergyReactionCode obj = dod.getRandomAllergyReactionCode();
        Assert.assertNotNull("Data on demand for 'AllergyReactionCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'AllergyReactionCode' failed to provide an identifier", id);
        obj = allergyReactionCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'AllergyReactionCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'AllergyReactionCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllAllergyReactionCodes() {
        Assert.assertNotNull("Data on demand for 'AllergyReactionCode' failed to initialize correctly", dod.getRandomAllergyReactionCode());
        long count = allergyReactionCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'AllergyReactionCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<AllergyReactionCode> result = allergyReactionCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'AllergyReactionCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'AllergyReactionCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindAllergyReactionCodeEntries() {
        Assert.assertNotNull("Data on demand for 'AllergyReactionCode' failed to initialize correctly", dod.getRandomAllergyReactionCode());
        long count = allergyReactionCodeRepository.count();
        if (count > 20) count = 20;
        int pageNumber = 0;
        int pageSize = (int) count;
        List<AllergyReactionCode> result = allergyReactionCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'AllergyReactionCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'AllergyReactionCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        AllergyReactionCode obj = dod.getRandomAllergyReactionCode();
        Assert.assertNotNull("Data on demand for 'AllergyReactionCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'AllergyReactionCode' failed to provide an identifier", id);
        obj = allergyReactionCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'AllergyReactionCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyAllergyReactionCode(obj);
        Integer currentVersion = obj.getVersion();
        allergyReactionCodeRepository.flush();
        Assert.assertTrue("Version for 'AllergyReactionCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateAllergyReactionCodeUpdate() {
        AllergyReactionCode obj = dod.getRandomAllergyReactionCode();
        Assert.assertNotNull("Data on demand for 'AllergyReactionCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'AllergyReactionCode' failed to provide an identifier", id);
        obj = allergyReactionCodeRepository.findOne(id);
        boolean modified =  dod.modifyAllergyReactionCode(obj);
        Integer currentVersion = obj.getVersion();
        AllergyReactionCode merged = (AllergyReactionCode)allergyReactionCodeRepository.save(obj);
        allergyReactionCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'AllergyReactionCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveAllergyReactionCode() {
        Assert.assertNotNull("Data on demand for 'AllergyReactionCode' failed to initialize correctly", dod.getRandomAllergyReactionCode());
        AllergyReactionCode obj = dod.getNewTransientAllergyReactionCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'AllergyReactionCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'AllergyReactionCode' identifier to be null", obj.getId());
        allergyReactionCodeRepository.save(obj);
        allergyReactionCodeRepository.flush();
        Assert.assertNotNull("Expected 'AllergyReactionCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteAllergyReactionCode() {
        AllergyReactionCode obj = dod.getRandomAllergyReactionCode();
        Assert.assertNotNull("Data on demand for 'AllergyReactionCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'AllergyReactionCode' failed to provide an identifier", id);
        obj = allergyReactionCodeRepository.findOne(id);
        allergyReactionCodeRepository.delete(obj);
        allergyReactionCodeRepository.flush();
        Assert.assertNull("Failed to remove 'AllergyReactionCode' with identifier '" + id + "'", allergyReactionCodeRepository.findOne(id));
    }
}
