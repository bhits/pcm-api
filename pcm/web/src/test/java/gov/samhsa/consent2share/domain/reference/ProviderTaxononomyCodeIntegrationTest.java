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
public class ProviderTaxononomyCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    ProviderTaxononomyCodeDataOnDemand dod;

	@Autowired
    ProviderTaxononomyCodeRepository providerTaxononomyCodeRepository;

	@Test
    public void testCountAllProviderTaxononomyCodes() {
        Assert.assertNotNull("Data on demand for 'ProviderTaxononomyCode' failed to initialize correctly", dod.getRandomProviderTaxononomyCode());
        long count = providerTaxononomyCodeRepository.count();
        Assert.assertTrue("Counter for 'ProviderTaxononomyCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindProviderTaxononomyCode() {
        ProviderTaxononomyCode obj = dod.getRandomProviderTaxononomyCode();
        Assert.assertNotNull("Data on demand for 'ProviderTaxononomyCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ProviderTaxononomyCode' failed to provide an identifier", id);
        obj = providerTaxononomyCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ProviderTaxononomyCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'ProviderTaxononomyCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllProviderTaxononomyCodes() {
        Assert.assertNotNull("Data on demand for 'ProviderTaxononomyCode' failed to initialize correctly", dod.getRandomProviderTaxononomyCode());
        long count = providerTaxononomyCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'ProviderTaxononomyCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<ProviderTaxononomyCode> result = providerTaxononomyCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'ProviderTaxononomyCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'ProviderTaxononomyCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindProviderTaxononomyCodeEntries() {
        Assert.assertNotNull("Data on demand for 'ProviderTaxononomyCode' failed to initialize correctly", dod.getRandomProviderTaxononomyCode());
        long count = providerTaxononomyCodeRepository.count();
        if (count > 20) count = 20;
		int pageNumber = 0;
        int pageSize = 10;
        List<ProviderTaxononomyCode> result = providerTaxononomyCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'ProviderTaxononomyCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'ProviderTaxononomyCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        ProviderTaxononomyCode obj = dod.getRandomProviderTaxononomyCode();
        Assert.assertNotNull("Data on demand for 'ProviderTaxononomyCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ProviderTaxononomyCode' failed to provide an identifier", id);
        obj = providerTaxononomyCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ProviderTaxononomyCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyProviderTaxononomyCode(obj);
        Integer currentVersion = obj.getVersion();
        providerTaxononomyCodeRepository.flush();
        Assert.assertTrue("Version for 'ProviderTaxononomyCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateProviderTaxononomyCodeUpdate() {
        ProviderTaxononomyCode obj = dod.getRandomProviderTaxononomyCode();
        Assert.assertNotNull("Data on demand for 'ProviderTaxononomyCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ProviderTaxononomyCode' failed to provide an identifier", id);
        obj = providerTaxononomyCodeRepository.findOne(id);
        boolean modified =  dod.modifyProviderTaxononomyCode(obj);
        Integer currentVersion = obj.getVersion();
        ProviderTaxononomyCode merged = (ProviderTaxononomyCode)providerTaxononomyCodeRepository.save(obj);
        providerTaxononomyCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'ProviderTaxononomyCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveProviderTaxononomyCode() {
        Assert.assertNotNull("Data on demand for 'ProviderTaxononomyCode' failed to initialize correctly", dod.getRandomProviderTaxononomyCode());
        ProviderTaxononomyCode obj = dod.getNewTransientProviderTaxononomyCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'ProviderTaxononomyCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'ProviderTaxononomyCode' identifier to be null", obj.getId());
        providerTaxononomyCodeRepository.save(obj);
        providerTaxononomyCodeRepository.flush();
        Assert.assertNotNull("Expected 'ProviderTaxononomyCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteProviderTaxononomyCode() {
        ProviderTaxononomyCode obj = dod.getRandomProviderTaxononomyCode();
        Assert.assertNotNull("Data on demand for 'ProviderTaxononomyCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ProviderTaxononomyCode' failed to provide an identifier", id);
        obj = providerTaxononomyCodeRepository.findOne(id);
        providerTaxononomyCodeRepository.delete(obj);
        providerTaxononomyCodeRepository.flush();
        Assert.assertNull("Failed to remove 'ProviderTaxononomyCode' with identifier '" + id + "'", providerTaxononomyCodeRepository.findOne(id));
    }
}
