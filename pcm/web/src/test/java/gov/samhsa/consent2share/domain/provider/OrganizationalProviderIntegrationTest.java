package gov.samhsa.consent2share.domain.provider;

import gov.samhsa.consent2share.service.provider.OrganizationalProviderService;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@ActiveProfiles("test")
@Transactional
@Configurable
public class OrganizationalProviderIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    OrganizationalProviderDataOnDemand dod;

	@Autowired
    OrganizationalProviderService organizationalProviderService;

	@Autowired
    OrganizationalProviderRepository organizationalProviderRepository;

	@Test
    public void testCountAllOrganizationalProviders() {
        Assert.assertNotNull("Data on demand for 'OrganizationalProvider' failed to initialize correctly", dod.getRandomOrganizationalProvider());
        long count = organizationalProviderService.countAllOrganizationalProviders();
        Assert.assertTrue("Counter for 'OrganizationalProvider' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindOrganizationalProvider() {
        OrganizationalProvider obj = dod.getRandomOrganizationalProvider();
        Assert.assertNotNull("Data on demand for 'OrganizationalProvider' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'OrganizationalProvider' failed to provide an identifier", id);
        obj = organizationalProviderService.findOrganizationalProvider(id);
        Assert.assertNotNull("Find method for 'OrganizationalProvider' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'OrganizationalProvider' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllOrganizationalProviders() {
        Assert.assertNotNull("Data on demand for 'OrganizationalProvider' failed to initialize correctly", dod.getRandomOrganizationalProvider());
        long count = organizationalProviderService.countAllOrganizationalProviders();
        Assert.assertTrue("Too expensive to perform a find all test for 'OrganizationalProvider', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<OrganizationalProvider> result = organizationalProviderService.findAllOrganizationalProviders();
        Assert.assertNotNull("Find all method for 'OrganizationalProvider' illegally returned null", result);
        Assert.assertTrue("Find all method for 'OrganizationalProvider' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindOrganizationalProviderEntries() {
        Assert.assertNotNull("Data on demand for 'OrganizationalProvider' failed to initialize correctly", dod.getRandomOrganizationalProvider());
        long count = organizationalProviderService.countAllOrganizationalProviders();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<OrganizationalProvider> result = organizationalProviderService.findOrganizationalProviderEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'OrganizationalProvider' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'OrganizationalProvider' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        OrganizationalProvider obj = dod.getRandomOrganizationalProvider();
        Assert.assertNotNull("Data on demand for 'OrganizationalProvider' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'OrganizationalProvider' failed to provide an identifier", id);
        obj = organizationalProviderService.findOrganizationalProvider(id);
        Assert.assertNotNull("Find method for 'OrganizationalProvider' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyOrganizationalProvider(obj);
        Integer currentVersion = obj.getVersion();
        organizationalProviderRepository.flush();
        Assert.assertTrue("Version for 'OrganizationalProvider' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateOrganizationalProviderUpdate() {
        OrganizationalProvider obj = dod.getRandomOrganizationalProvider();
        Assert.assertNotNull("Data on demand for 'OrganizationalProvider' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'OrganizationalProvider' failed to provide an identifier", id);
        obj = organizationalProviderService.findOrganizationalProvider(id);
        boolean modified =  dod.modifyOrganizationalProvider(obj);
        Integer currentVersion = obj.getVersion();
        OrganizationalProvider merged = (OrganizationalProvider)organizationalProviderService.updateOrganizationalProvider(obj);
        organizationalProviderRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'OrganizationalProvider' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveOrganizationalProvider() {
        Assert.assertNotNull("Data on demand for 'OrganizationalProvider' failed to initialize correctly", dod.getRandomOrganizationalProvider());
        OrganizationalProvider obj = dod.getNewTransientOrganizationalProvider(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'OrganizationalProvider' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'OrganizationalProvider' identifier to be null", obj.getId());
        organizationalProviderService.saveOrganizationalProvider(obj);
        organizationalProviderRepository.flush();
        Assert.assertNotNull("Expected 'OrganizationalProvider' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteOrganizationalProvider() {
        OrganizationalProvider obj = dod.getRandomOrganizationalProvider();
        Assert.assertNotNull("Data on demand for 'OrganizationalProvider' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'OrganizationalProvider' failed to provide an identifier", id);
        obj = organizationalProviderService.findOrganizationalProvider(id);
        organizationalProviderService.deleteOrganizationalProvider(obj);
        organizationalProviderRepository.flush();
        Assert.assertNull("Failed to remove 'OrganizationalProvider' with identifier '" + id + "'", organizationalProviderService.findOrganizationalProvider(id));
    }
}
