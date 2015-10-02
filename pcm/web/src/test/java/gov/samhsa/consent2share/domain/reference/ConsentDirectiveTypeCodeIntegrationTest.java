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
public class ConsentDirectiveTypeCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    ConsentDirectiveTypeCodeDataOnDemand dod;

	@Autowired
    ConsentDirectiveTypeCodeRepository consentDirectiveTypeCodeRepository;

	@Test
    public void testCountAllConsentDirectiveTypeCodes() {
        Assert.assertNotNull("Data on demand for 'ConsentDirectiveTypeCode' failed to initialize correctly", dod.getRandomConsentDirectiveTypeCode());
        long count = consentDirectiveTypeCodeRepository.count();
        Assert.assertTrue("Counter for 'ConsentDirectiveTypeCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindConsentDirectiveTypeCode() {
        ConsentDirectiveTypeCode obj = dod.getRandomConsentDirectiveTypeCode();
        Assert.assertNotNull("Data on demand for 'ConsentDirectiveTypeCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ConsentDirectiveTypeCode' failed to provide an identifier", id);
        obj = consentDirectiveTypeCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ConsentDirectiveTypeCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'ConsentDirectiveTypeCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllConsentDirectiveTypeCodes() {
        Assert.assertNotNull("Data on demand for 'ConsentDirectiveTypeCode' failed to initialize correctly", dod.getRandomConsentDirectiveTypeCode());
        long count = consentDirectiveTypeCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'ConsentDirectiveTypeCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<ConsentDirectiveTypeCode> result = consentDirectiveTypeCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'ConsentDirectiveTypeCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'ConsentDirectiveTypeCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindConsentDirectiveTypeCodeEntries() {
        Assert.assertNotNull("Data on demand for 'ConsentDirectiveTypeCode' failed to initialize correctly", dod.getRandomConsentDirectiveTypeCode());
        long count = consentDirectiveTypeCodeRepository.count();
        if (count > 20) count = 20;
		int pageNumber = 0;
        int pageSize = 10;
        List<ConsentDirectiveTypeCode> result = consentDirectiveTypeCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'ConsentDirectiveTypeCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'ConsentDirectiveTypeCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        ConsentDirectiveTypeCode obj = dod.getRandomConsentDirectiveTypeCode();
        Assert.assertNotNull("Data on demand for 'ConsentDirectiveTypeCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ConsentDirectiveTypeCode' failed to provide an identifier", id);
        obj = consentDirectiveTypeCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ConsentDirectiveTypeCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyConsentDirectiveTypeCode(obj);
        Integer currentVersion = obj.getVersion();
        consentDirectiveTypeCodeRepository.flush();
        Assert.assertTrue("Version for 'ConsentDirectiveTypeCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateConsentDirectiveTypeCodeUpdate() {
        ConsentDirectiveTypeCode obj = dod.getRandomConsentDirectiveTypeCode();
        Assert.assertNotNull("Data on demand for 'ConsentDirectiveTypeCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ConsentDirectiveTypeCode' failed to provide an identifier", id);
        obj = consentDirectiveTypeCodeRepository.findOne(id);
        boolean modified =  dod.modifyConsentDirectiveTypeCode(obj);
        Integer currentVersion = obj.getVersion();
        ConsentDirectiveTypeCode merged = (ConsentDirectiveTypeCode)consentDirectiveTypeCodeRepository.save(obj);
        consentDirectiveTypeCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'ConsentDirectiveTypeCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveConsentDirectiveTypeCode() {
        Assert.assertNotNull("Data on demand for 'ConsentDirectiveTypeCode' failed to initialize correctly", dod.getRandomConsentDirectiveTypeCode());
        ConsentDirectiveTypeCode obj = dod.getNewTransientConsentDirectiveTypeCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'ConsentDirectiveTypeCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'ConsentDirectiveTypeCode' identifier to be null", obj.getId());
        consentDirectiveTypeCodeRepository.save(obj);
        consentDirectiveTypeCodeRepository.flush();
        Assert.assertNotNull("Expected 'ConsentDirectiveTypeCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteConsentDirectiveTypeCode() {
        ConsentDirectiveTypeCode obj = dod.getRandomConsentDirectiveTypeCode();
        Assert.assertNotNull("Data on demand for 'ConsentDirectiveTypeCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ConsentDirectiveTypeCode' failed to provide an identifier", id);
        obj = consentDirectiveTypeCodeRepository.findOne(id);
        consentDirectiveTypeCodeRepository.delete(obj);
        consentDirectiveTypeCodeRepository.flush();
        Assert.assertNull("Failed to remove 'ConsentDirectiveTypeCode' with identifier '" + id + "'", consentDirectiveTypeCodeRepository.findOne(id));
    }
}
