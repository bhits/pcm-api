package gov.samhsa.consent2share.domain.provider;

import gov.samhsa.consent2share.service.provider.IndividualProviderService;
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
public class IndividualProviderIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    IndividualProviderDataOnDemand dod;

	@Autowired
    IndividualProviderService individualProviderService;

	@Autowired
    IndividualProviderRepository individualProviderRepository;

	@Test
    public void testCountAllIndividualProviders() {
        Assert.assertNotNull("Data on demand for 'IndividualProvider' failed to initialize correctly", dod.getRandomIndividualProvider());
        long count = individualProviderService.countAllIndividualProviders();
        Assert.assertTrue("Counter for 'IndividualProvider' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindIndividualProvider() {
        IndividualProvider obj = dod.getRandomIndividualProvider();
        Assert.assertNotNull("Data on demand for 'IndividualProvider' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'IndividualProvider' failed to provide an identifier", id);
        obj = individualProviderService.findIndividualProvider(id);
        Assert.assertNotNull("Find method for 'IndividualProvider' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'IndividualProvider' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllIndividualProviders() {
        Assert.assertNotNull("Data on demand for 'IndividualProvider' failed to initialize correctly", dod.getRandomIndividualProvider());
        long count = individualProviderService.countAllIndividualProviders();
        Assert.assertTrue("Too expensive to perform a find all test for 'IndividualProvider', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<IndividualProvider> result = individualProviderService.findAllIndividualProviders();
        Assert.assertNotNull("Find all method for 'IndividualProvider' illegally returned null", result);
        Assert.assertTrue("Find all method for 'IndividualProvider' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindIndividualProviderEntries() {
        Assert.assertNotNull("Data on demand for 'IndividualProvider' failed to initialize correctly", dod.getRandomIndividualProvider());
        long count = individualProviderService.countAllIndividualProviders();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<IndividualProvider> result = individualProviderService.findIndividualProviderEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'IndividualProvider' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'IndividualProvider' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        IndividualProvider obj = dod.getRandomIndividualProvider();
        Assert.assertNotNull("Data on demand for 'IndividualProvider' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'IndividualProvider' failed to provide an identifier", id);
        obj = individualProviderService.findIndividualProvider(id);
        Assert.assertNotNull("Find method for 'IndividualProvider' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyIndividualProvider(obj);
        Integer currentVersion = obj.getVersion();
        individualProviderRepository.flush();
        Assert.assertTrue("Version for 'IndividualProvider' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateIndividualProviderUpdate() {
        IndividualProvider obj = dod.getRandomIndividualProvider();
        Assert.assertNotNull("Data on demand for 'IndividualProvider' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'IndividualProvider' failed to provide an identifier", id);
        obj = individualProviderService.findIndividualProvider(id);
        boolean modified =  dod.modifyIndividualProvider(obj);
        Integer currentVersion = obj.getVersion();
        IndividualProvider merged = (IndividualProvider)individualProviderService.updateIndividualProvider(obj);
        individualProviderRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'IndividualProvider' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveIndividualProvider() {
        Assert.assertNotNull("Data on demand for 'IndividualProvider' failed to initialize correctly", dod.getRandomIndividualProvider());
        IndividualProvider obj = dod.getNewTransientIndividualProvider(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'IndividualProvider' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'IndividualProvider' identifier to be null", obj.getId());
        individualProviderService.saveIndividualProvider(obj);
        individualProviderRepository.flush();
        Assert.assertNotNull("Expected 'IndividualProvider' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteIndividualProvider() {
        IndividualProvider obj = dod.getRandomIndividualProvider();
        Assert.assertNotNull("Data on demand for 'IndividualProvider' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'IndividualProvider' failed to provide an identifier", id);
        obj = individualProviderService.findIndividualProvider(id);
        individualProviderService.deleteIndividualProvider(obj);
        individualProviderRepository.flush();
        Assert.assertNull("Failed to remove 'IndividualProvider' with identifier '" + id + "'", individualProviderService.findIndividualProvider(id));
    }
}
