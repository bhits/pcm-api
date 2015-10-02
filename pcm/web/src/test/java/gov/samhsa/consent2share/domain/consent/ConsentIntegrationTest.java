package gov.samhsa.consent2share.domain.consent;

import gov.samhsa.consent2share.service.consent.ConsentService;
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

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@ActiveProfiles("test")
@Transactional
public class ConsentIntegrationTest {
	@Autowired
    ConsentDataOnDemand dod;

	@Autowired
    ConsentService consentService;

	@Autowired
    ConsentRepository consentRepository;

	@Test
    public void testCountAllConsents() {
        Assert.assertNotNull("Data on demand for 'Consent' failed to initialize correctly", dod.getRandomConsent());
        long count = consentService.countAllConsents();
        Assert.assertTrue("Counter for 'Consent' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindConsent() {
        Consent obj = dod.getRandomConsent();
        Assert.assertNotNull("Data on demand for 'Consent' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Consent' failed to provide an identifier", id);
        obj = consentService.findConsent(id);
        Assert.assertNotNull("Find method for 'Consent' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Consent' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllConsents() {
        Assert.assertNotNull("Data on demand for 'Consent' failed to initialize correctly", dod.getRandomConsent());
        long count = consentService.countAllConsents();
        Assert.assertTrue("Too expensive to perform a find all test for 'Consent', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Consent> result = consentService.findAllConsents();
        Assert.assertNotNull("Find all method for 'Consent' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Consent' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindConsentEntries() {
        Assert.assertNotNull("Data on demand for 'Consent' failed to initialize correctly", dod.getRandomConsent());
        long count = consentService.countAllConsents();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Consent> result = consentService.findConsentEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Consent' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Consent' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        Consent obj = dod.getRandomConsent();
        Assert.assertNotNull("Data on demand for 'Consent' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Consent' failed to provide an identifier", id);
        obj = consentService.findConsent(id);
        Assert.assertNotNull("Find method for 'Consent' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyConsent(obj);
        Integer currentVersion = obj.getVersion();
        consentRepository.flush();
        Assert.assertTrue("Version for 'Consent' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateConsentUpdate() {
        Consent obj = dod.getRandomConsent();
        Assert.assertNotNull("Data on demand for 'Consent' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Consent' failed to provide an identifier", id);
        obj = consentService.findConsent(id);
        boolean modified =  dod.modifyConsent(obj);
        Integer currentVersion = obj.getVersion();
        Consent merged = consentService.updateConsent(obj);
        consentRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Consent' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveConsent() {
        Assert.assertNotNull("Data on demand for 'Consent' failed to initialize correctly", dod.getRandomConsent());
        Consent obj = dod.getNewTransientConsent(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Consent' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Consent' identifier to be null", obj.getId());
        consentService.saveConsent(obj);
        consentRepository.flush();
        Assert.assertNotNull("Expected 'Consent' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteConsent() {
        Consent obj = dod.getRandomConsent();
        Assert.assertNotNull("Data on demand for 'Consent' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Consent' failed to provide an identifier", id);
        obj = consentService.findConsent(id);
        consentService.deleteConsent(obj);
        consentRepository.flush();
        Assert.assertNull("Failed to remove 'Consent' with identifier '" + id + "'", consentService.findConsent(id));
    }
}
