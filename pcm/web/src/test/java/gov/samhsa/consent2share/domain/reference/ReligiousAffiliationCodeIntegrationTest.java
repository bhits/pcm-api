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
public class ReligiousAffiliationCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    ReligiousAffiliationCodeDataOnDemand dod;

	@Autowired
    ReligiousAffiliationCodeRepository religiousAffiliationCodeRepository;

	@Test
    public void testCountAllReligiousAffiliationCodes() {
        Assert.assertNotNull("Data on demand for 'ReligiousAffiliationCode' failed to initialize correctly", dod.getRandomReligiousAffiliationCode());
        long count = religiousAffiliationCodeRepository.count();
        Assert.assertTrue("Counter for 'ReligiousAffiliationCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindReligiousAffiliationCode() {
        ReligiousAffiliationCode obj = dod.getRandomReligiousAffiliationCode();
        Assert.assertNotNull("Data on demand for 'ReligiousAffiliationCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ReligiousAffiliationCode' failed to provide an identifier", id);
        obj = religiousAffiliationCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ReligiousAffiliationCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'ReligiousAffiliationCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllReligiousAffiliationCodes() {
        Assert.assertNotNull("Data on demand for 'ReligiousAffiliationCode' failed to initialize correctly", dod.getRandomReligiousAffiliationCode());
        long count = religiousAffiliationCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'ReligiousAffiliationCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<ReligiousAffiliationCode> result = religiousAffiliationCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'ReligiousAffiliationCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'ReligiousAffiliationCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindReligiousAffiliationCodeEntries() {
        Assert.assertNotNull("Data on demand for 'ReligiousAffiliationCode' failed to initialize correctly", dod.getRandomReligiousAffiliationCode());
        long count = religiousAffiliationCodeRepository.count();
        if (count > 20) count = 20;
		int pageNumber = 0;
        int pageSize = 10;
        List<ReligiousAffiliationCode> result = religiousAffiliationCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'ReligiousAffiliationCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'ReligiousAffiliationCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        ReligiousAffiliationCode obj = dod.getRandomReligiousAffiliationCode();
        Assert.assertNotNull("Data on demand for 'ReligiousAffiliationCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ReligiousAffiliationCode' failed to provide an identifier", id);
        obj = religiousAffiliationCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ReligiousAffiliationCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyReligiousAffiliationCode(obj);
        Integer currentVersion = obj.getVersion();
        religiousAffiliationCodeRepository.flush();
        Assert.assertTrue("Version for 'ReligiousAffiliationCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateReligiousAffiliationCodeUpdate() {
        ReligiousAffiliationCode obj = dod.getRandomReligiousAffiliationCode();
        Assert.assertNotNull("Data on demand for 'ReligiousAffiliationCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ReligiousAffiliationCode' failed to provide an identifier", id);
        obj = religiousAffiliationCodeRepository.findOne(id);
        boolean modified =  dod.modifyReligiousAffiliationCode(obj);
        Integer currentVersion = obj.getVersion();
        ReligiousAffiliationCode merged = (ReligiousAffiliationCode)religiousAffiliationCodeRepository.save(obj);
        religiousAffiliationCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'ReligiousAffiliationCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveReligiousAffiliationCode() {
        Assert.assertNotNull("Data on demand for 'ReligiousAffiliationCode' failed to initialize correctly", dod.getRandomReligiousAffiliationCode());
        ReligiousAffiliationCode obj = dod.getNewTransientReligiousAffiliationCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'ReligiousAffiliationCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'ReligiousAffiliationCode' identifier to be null", obj.getId());
        religiousAffiliationCodeRepository.save(obj);
        religiousAffiliationCodeRepository.flush();
        Assert.assertNotNull("Expected 'ReligiousAffiliationCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteReligiousAffiliationCode() {
        ReligiousAffiliationCode obj = dod.getRandomReligiousAffiliationCode();
        Assert.assertNotNull("Data on demand for 'ReligiousAffiliationCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ReligiousAffiliationCode' failed to provide an identifier", id);
        obj = religiousAffiliationCodeRepository.findOne(id);
        religiousAffiliationCodeRepository.delete(obj);
        religiousAffiliationCodeRepository.flush();
        Assert.assertNull("Failed to remove 'ReligiousAffiliationCode' with identifier '" + id + "'", religiousAffiliationCodeRepository.findOne(id));
    }
}
