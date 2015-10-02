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
public class BodySiteCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    BodySiteCodeDataOnDemand dod;

	@Autowired
    BodySiteCodeRepository bodySiteCodeRepository;

	@Test
    public void testCountAllBodySiteCodes() {
        Assert.assertNotNull("Data on demand for 'BodySiteCode' failed to initialize correctly", dod.getRandomBodySiteCode());
        long count = bodySiteCodeRepository.count();
        Assert.assertTrue("Counter for 'BodySiteCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindBodySiteCode() {
        BodySiteCode obj = dod.getRandomBodySiteCode();
        Assert.assertNotNull("Data on demand for 'BodySiteCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'BodySiteCode' failed to provide an identifier", id);
        obj = bodySiteCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'BodySiteCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'BodySiteCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllBodySiteCodes() {
        Assert.assertNotNull("Data on demand for 'BodySiteCode' failed to initialize correctly", dod.getRandomBodySiteCode());
        long count = bodySiteCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'BodySiteCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<BodySiteCode> result = bodySiteCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'BodySiteCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'BodySiteCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindBodySiteCodeEntries() {
        Assert.assertNotNull("Data on demand for 'BodySiteCode' failed to initialize correctly", dod.getRandomBodySiteCode());
        long count = bodySiteCodeRepository.count();
        if (count > 20) count = 20;
        int pageNumber = 0;
        int pageSize = (int) count;
        List<BodySiteCode> result = bodySiteCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'BodySiteCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'BodySiteCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        BodySiteCode obj = dod.getRandomBodySiteCode();
        Assert.assertNotNull("Data on demand for 'BodySiteCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'BodySiteCode' failed to provide an identifier", id);
        obj = bodySiteCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'BodySiteCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyBodySiteCode(obj);
        Integer currentVersion = obj.getVersion();
        bodySiteCodeRepository.flush();
        Assert.assertTrue("Version for 'BodySiteCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateBodySiteCodeUpdate() {
        BodySiteCode obj = dod.getRandomBodySiteCode();
        Assert.assertNotNull("Data on demand for 'BodySiteCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'BodySiteCode' failed to provide an identifier", id);
        obj = bodySiteCodeRepository.findOne(id);
        boolean modified =  dod.modifyBodySiteCode(obj);
        Integer currentVersion = obj.getVersion();
        BodySiteCode merged = (BodySiteCode)bodySiteCodeRepository.save(obj);
        bodySiteCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'BodySiteCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveBodySiteCode() {
        Assert.assertNotNull("Data on demand for 'BodySiteCode' failed to initialize correctly", dod.getRandomBodySiteCode());
        BodySiteCode obj = dod.getNewTransientBodySiteCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'BodySiteCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'BodySiteCode' identifier to be null", obj.getId());
        bodySiteCodeRepository.save(obj);
        bodySiteCodeRepository.flush();
        Assert.assertNotNull("Expected 'BodySiteCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteBodySiteCode() {
        BodySiteCode obj = dod.getRandomBodySiteCode();
        Assert.assertNotNull("Data on demand for 'BodySiteCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'BodySiteCode' failed to provide an identifier", id);
        obj = bodySiteCodeRepository.findOne(id);
        bodySiteCodeRepository.delete(obj);
        bodySiteCodeRepository.flush();
        Assert.assertNull("Failed to remove 'BodySiteCode' with identifier '" + id + "'", bodySiteCodeRepository.findOne(id));
    }
}
