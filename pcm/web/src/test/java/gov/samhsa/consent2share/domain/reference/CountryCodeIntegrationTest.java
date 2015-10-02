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
public class CountryCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    CountryCodeDataOnDemand dod;

	@Autowired
    CountryCodeRepository countryCodeRepository;

	@Test
    public void testCountAllCountryCodes() {
        Assert.assertNotNull("Data on demand for 'CountryCode' failed to initialize correctly", dod.getRandomCountryCode());
        long count = countryCodeRepository.count();
        Assert.assertTrue("Counter for 'CountryCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindCountryCode() {
        CountryCode obj = dod.getRandomCountryCode();
        Assert.assertNotNull("Data on demand for 'CountryCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CountryCode' failed to provide an identifier", id);
        obj = countryCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'CountryCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'CountryCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllCountryCodes() {
        Assert.assertNotNull("Data on demand for 'CountryCode' failed to initialize correctly", dod.getRandomCountryCode());
        long count = countryCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'CountryCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<CountryCode> result = countryCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'CountryCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'CountryCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindCountryCodeEntries() {
        Assert.assertNotNull("Data on demand for 'CountryCode' failed to initialize correctly", dod.getRandomCountryCode());
        long count = countryCodeRepository.count();
        if (count > 20) count = 20;
		int pageNumber = 0;
        int pageSize = 10;
        List<CountryCode> result = countryCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'CountryCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'CountryCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        CountryCode obj = dod.getRandomCountryCode();
        Assert.assertNotNull("Data on demand for 'CountryCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CountryCode' failed to provide an identifier", id);
        obj = countryCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'CountryCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyCountryCode(obj);
        Integer currentVersion = obj.getVersion();
        countryCodeRepository.flush();
        Assert.assertTrue("Version for 'CountryCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateCountryCodeUpdate() {
        CountryCode obj = dod.getRandomCountryCode();
        Assert.assertNotNull("Data on demand for 'CountryCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CountryCode' failed to provide an identifier", id);
        obj = countryCodeRepository.findOne(id);
        boolean modified =  dod.modifyCountryCode(obj);
        Integer currentVersion = obj.getVersion();
        CountryCode merged = (CountryCode)countryCodeRepository.save(obj);
        countryCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'CountryCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveCountryCode() {
        Assert.assertNotNull("Data on demand for 'CountryCode' failed to initialize correctly", dod.getRandomCountryCode());
        CountryCode obj = dod.getNewTransientCountryCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'CountryCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'CountryCode' identifier to be null", obj.getId());
        countryCodeRepository.save(obj);
        countryCodeRepository.flush();
        Assert.assertNotNull("Expected 'CountryCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteCountryCode() {
        CountryCode obj = dod.getRandomCountryCode();
        Assert.assertNotNull("Data on demand for 'CountryCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CountryCode' failed to provide an identifier", id);
        obj = countryCodeRepository.findOne(id);
        countryCodeRepository.delete(obj);
        countryCodeRepository.flush();
        Assert.assertNull("Failed to remove 'CountryCode' with identifier '" + id + "'", countryCodeRepository.findOne(id));
    }
}
