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
public class MaritalStatusCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    MaritalStatusCodeDataOnDemand dod;

	@Autowired
    MaritalStatusCodeRepository maritalStatusCodeRepository;

	@Test
    public void testCountAllMaritalStatusCodes() {
        Assert.assertNotNull("Data on demand for 'MaritalStatusCode' failed to initialize correctly", dod.getRandomMaritalStatusCode());
        long count = maritalStatusCodeRepository.count();
        Assert.assertTrue("Counter for 'MaritalStatusCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindMaritalStatusCode() {
        MaritalStatusCode obj = dod.getRandomMaritalStatusCode();
        Assert.assertNotNull("Data on demand for 'MaritalStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'MaritalStatusCode' failed to provide an identifier", id);
        obj = maritalStatusCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'MaritalStatusCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'MaritalStatusCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllMaritalStatusCodes() {
        Assert.assertNotNull("Data on demand for 'MaritalStatusCode' failed to initialize correctly", dod.getRandomMaritalStatusCode());
        long count = maritalStatusCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'MaritalStatusCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<MaritalStatusCode> result = maritalStatusCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'MaritalStatusCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'MaritalStatusCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindMaritalStatusCodeEntries() {
        Assert.assertNotNull("Data on demand for 'MaritalStatusCode' failed to initialize correctly", dod.getRandomMaritalStatusCode());
        long count = maritalStatusCodeRepository.count();
        if (count > 20) count = 20;
		int pageNumber = 0;
        int pageSize = 10;
        List<MaritalStatusCode> result = maritalStatusCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'MaritalStatusCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'MaritalStatusCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        MaritalStatusCode obj = dod.getRandomMaritalStatusCode();
        Assert.assertNotNull("Data on demand for 'MaritalStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'MaritalStatusCode' failed to provide an identifier", id);
        obj = maritalStatusCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'MaritalStatusCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyMaritalStatusCode(obj);
        Integer currentVersion = obj.getVersion();
        maritalStatusCodeRepository.flush();
        Assert.assertTrue("Version for 'MaritalStatusCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateMaritalStatusCodeUpdate() {
        MaritalStatusCode obj = dod.getRandomMaritalStatusCode();
        Assert.assertNotNull("Data on demand for 'MaritalStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'MaritalStatusCode' failed to provide an identifier", id);
        obj = maritalStatusCodeRepository.findOne(id);
        boolean modified =  dod.modifyMaritalStatusCode(obj);
        Integer currentVersion = obj.getVersion();
        MaritalStatusCode merged = (MaritalStatusCode)maritalStatusCodeRepository.save(obj);
        maritalStatusCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'MaritalStatusCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveMaritalStatusCode() {
        Assert.assertNotNull("Data on demand for 'MaritalStatusCode' failed to initialize correctly", dod.getRandomMaritalStatusCode());
        MaritalStatusCode obj = dod.getNewTransientMaritalStatusCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'MaritalStatusCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'MaritalStatusCode' identifier to be null", obj.getId());
        maritalStatusCodeRepository.save(obj);
        maritalStatusCodeRepository.flush();
        Assert.assertNotNull("Expected 'MaritalStatusCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteMaritalStatusCode() {
        MaritalStatusCode obj = dod.getRandomMaritalStatusCode();
        Assert.assertNotNull("Data on demand for 'MaritalStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'MaritalStatusCode' failed to provide an identifier", id);
        obj = maritalStatusCodeRepository.findOne(id);
        maritalStatusCodeRepository.delete(obj);
        maritalStatusCodeRepository.flush();
        Assert.assertNull("Failed to remove 'MaritalStatusCode' with identifier '" + id + "'", maritalStatusCodeRepository.findOne(id));
    }
}
