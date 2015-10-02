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
public class TelecomUseCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    TelecomUseCodeDataOnDemand dod;

	@Autowired
    TelecomUseCodeRepository telecomUseCodeRepository;

	@Test
    public void testCountAllTelecomUseCodes() {
        Assert.assertNotNull("Data on demand for 'TelecomUseCode' failed to initialize correctly", dod.getRandomTelecomUseCode());
        long count = telecomUseCodeRepository.count();
        Assert.assertTrue("Counter for 'TelecomUseCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindTelecomUseCode() {
        TelecomUseCode obj = dod.getRandomTelecomUseCode();
        Assert.assertNotNull("Data on demand for 'TelecomUseCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'TelecomUseCode' failed to provide an identifier", id);
        obj = telecomUseCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'TelecomUseCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'TelecomUseCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllTelecomUseCodes() {
        Assert.assertNotNull("Data on demand for 'TelecomUseCode' failed to initialize correctly", dod.getRandomTelecomUseCode());
        long count = telecomUseCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'TelecomUseCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<TelecomUseCode> result = telecomUseCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'TelecomUseCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'TelecomUseCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindTelecomUseCodeEntries() {
        Assert.assertNotNull("Data on demand for 'TelecomUseCode' failed to initialize correctly", dod.getRandomTelecomUseCode());
        long count = telecomUseCodeRepository.count();
        if (count > 20) count = 20;
        int pageNumber = 0;
        int pageSize = (int) count;
        List<TelecomUseCode> result = telecomUseCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'TelecomUseCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'TelecomUseCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        TelecomUseCode obj = dod.getRandomTelecomUseCode();
        Assert.assertNotNull("Data on demand for 'TelecomUseCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'TelecomUseCode' failed to provide an identifier", id);
        obj = telecomUseCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'TelecomUseCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyTelecomUseCode(obj);
        Integer currentVersion = obj.getVersion();
        telecomUseCodeRepository.flush();
        Assert.assertTrue("Version for 'TelecomUseCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateTelecomUseCodeUpdate() {
        TelecomUseCode obj = dod.getRandomTelecomUseCode();
        Assert.assertNotNull("Data on demand for 'TelecomUseCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'TelecomUseCode' failed to provide an identifier", id);
        obj = telecomUseCodeRepository.findOne(id);
        boolean modified =  dod.modifyTelecomUseCode(obj);
        Integer currentVersion = obj.getVersion();
        TelecomUseCode merged = (TelecomUseCode)telecomUseCodeRepository.save(obj);
        telecomUseCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'TelecomUseCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveTelecomUseCode() {
        Assert.assertNotNull("Data on demand for 'TelecomUseCode' failed to initialize correctly", dod.getRandomTelecomUseCode());
        TelecomUseCode obj = dod.getNewTransientTelecomUseCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'TelecomUseCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'TelecomUseCode' identifier to be null", obj.getId());
        telecomUseCodeRepository.save(obj);
        telecomUseCodeRepository.flush();
        Assert.assertNotNull("Expected 'TelecomUseCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteTelecomUseCode() {
        TelecomUseCode obj = dod.getRandomTelecomUseCode();
        Assert.assertNotNull("Data on demand for 'TelecomUseCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'TelecomUseCode' failed to provide an identifier", id);
        obj = telecomUseCodeRepository.findOne(id);
        telecomUseCodeRepository.delete(obj);
        telecomUseCodeRepository.flush();
        Assert.assertNull("Failed to remove 'TelecomUseCode' with identifier '" + id + "'", telecomUseCodeRepository.findOne(id));
    }
}
