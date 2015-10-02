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
public class LanguageProficiencyCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    LanguageProficiencyCodeDataOnDemand dod;

	@Autowired
    LanguageProficiencyCodeRepository languageProficiencyCodeRepository;

	@Test
    public void testCountAllLanguageProficiencyCodes() {
        Assert.assertNotNull("Data on demand for 'LanguageProficiencyCode' failed to initialize correctly", dod.getRandomLanguageProficiencyCode());
        long count = languageProficiencyCodeRepository.count();
        Assert.assertTrue("Counter for 'LanguageProficiencyCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindLanguageProficiencyCode() {
        LanguageProficiencyCode obj = dod.getRandomLanguageProficiencyCode();
        Assert.assertNotNull("Data on demand for 'LanguageProficiencyCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'LanguageProficiencyCode' failed to provide an identifier", id);
        obj = languageProficiencyCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'LanguageProficiencyCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'LanguageProficiencyCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllLanguageProficiencyCodes() {
        Assert.assertNotNull("Data on demand for 'LanguageProficiencyCode' failed to initialize correctly", dod.getRandomLanguageProficiencyCode());
        long count = languageProficiencyCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'LanguageProficiencyCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<LanguageProficiencyCode> result = languageProficiencyCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'LanguageProficiencyCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'LanguageProficiencyCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindLanguageProficiencyCodeEntries() {
        Assert.assertNotNull("Data on demand for 'LanguageProficiencyCode' failed to initialize correctly", dod.getRandomLanguageProficiencyCode());
        long count = languageProficiencyCodeRepository.count();
        if (count > 20) count = 20;
		int pageNumber = 0;
        int pageSize = 10;
        List<LanguageProficiencyCode> result = languageProficiencyCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'LanguageProficiencyCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'LanguageProficiencyCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        LanguageProficiencyCode obj = dod.getRandomLanguageProficiencyCode();
        Assert.assertNotNull("Data on demand for 'LanguageProficiencyCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'LanguageProficiencyCode' failed to provide an identifier", id);
        obj = languageProficiencyCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'LanguageProficiencyCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyLanguageProficiencyCode(obj);
        Integer currentVersion = obj.getVersion();
        languageProficiencyCodeRepository.flush();
        Assert.assertTrue("Version for 'LanguageProficiencyCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateLanguageProficiencyCodeUpdate() {
        LanguageProficiencyCode obj = dod.getRandomLanguageProficiencyCode();
        Assert.assertNotNull("Data on demand for 'LanguageProficiencyCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'LanguageProficiencyCode' failed to provide an identifier", id);
        obj = languageProficiencyCodeRepository.findOne(id);
        boolean modified =  dod.modifyLanguageProficiencyCode(obj);
        Integer currentVersion = obj.getVersion();
        LanguageProficiencyCode merged = (LanguageProficiencyCode)languageProficiencyCodeRepository.save(obj);
        languageProficiencyCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'LanguageProficiencyCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveLanguageProficiencyCode() {
        Assert.assertNotNull("Data on demand for 'LanguageProficiencyCode' failed to initialize correctly", dod.getRandomLanguageProficiencyCode());
        LanguageProficiencyCode obj = dod.getNewTransientLanguageProficiencyCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'LanguageProficiencyCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'LanguageProficiencyCode' identifier to be null", obj.getId());
        languageProficiencyCodeRepository.save(obj);
        languageProficiencyCodeRepository.flush();
        Assert.assertNotNull("Expected 'LanguageProficiencyCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteLanguageProficiencyCode() {
        LanguageProficiencyCode obj = dod.getRandomLanguageProficiencyCode();
        Assert.assertNotNull("Data on demand for 'LanguageProficiencyCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'LanguageProficiencyCode' failed to provide an identifier", id);
        obj = languageProficiencyCodeRepository.findOne(id);
        languageProficiencyCodeRepository.delete(obj);
        languageProficiencyCodeRepository.flush();
        Assert.assertNull("Failed to remove 'LanguageProficiencyCode' with identifier '" + id + "'", languageProficiencyCodeRepository.findOne(id));
    }
}
