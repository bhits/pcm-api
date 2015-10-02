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
public class TargetSiteCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    TargetSiteCodeDataOnDemand dod;

	@Autowired
    TargetSiteCodeRepository targetSiteCodeRepository;

	@Test
    public void testCountAllTargetSiteCodes() {
        Assert.assertNotNull("Data on demand for 'TargetSiteCode' failed to initialize correctly", dod.getRandomTargetSiteCode());
        long count = targetSiteCodeRepository.count();
        Assert.assertTrue("Counter for 'TargetSiteCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindTargetSiteCode() {
        TargetSiteCode obj = dod.getRandomTargetSiteCode();
        Assert.assertNotNull("Data on demand for 'TargetSiteCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'TargetSiteCode' failed to provide an identifier", id);
        obj = targetSiteCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'TargetSiteCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'TargetSiteCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllTargetSiteCodes() {
        Assert.assertNotNull("Data on demand for 'TargetSiteCode' failed to initialize correctly", dod.getRandomTargetSiteCode());
        long count = targetSiteCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'TargetSiteCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<TargetSiteCode> result = targetSiteCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'TargetSiteCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'TargetSiteCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindTargetSiteCodeEntries() {
        Assert.assertNotNull("Data on demand for 'TargetSiteCode' failed to initialize correctly", dod.getRandomTargetSiteCode());
        long count = targetSiteCodeRepository.count();
        if (count > 20) count = 20;
        int pageNumber = 0;
        int pageSize = (int) count;
        List<TargetSiteCode> result = targetSiteCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'TargetSiteCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'TargetSiteCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        TargetSiteCode obj = dod.getRandomTargetSiteCode();
        Assert.assertNotNull("Data on demand for 'TargetSiteCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'TargetSiteCode' failed to provide an identifier", id);
        obj = targetSiteCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'TargetSiteCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyTargetSiteCode(obj);
        Integer currentVersion = obj.getVersion();
        targetSiteCodeRepository.flush();
        Assert.assertTrue("Version for 'TargetSiteCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateTargetSiteCodeUpdate() {
        TargetSiteCode obj = dod.getRandomTargetSiteCode();
        Assert.assertNotNull("Data on demand for 'TargetSiteCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'TargetSiteCode' failed to provide an identifier", id);
        obj = targetSiteCodeRepository.findOne(id);
        boolean modified =  dod.modifyTargetSiteCode(obj);
        Integer currentVersion = obj.getVersion();
        TargetSiteCode merged = (TargetSiteCode)targetSiteCodeRepository.save(obj);
        targetSiteCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'TargetSiteCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveTargetSiteCode() {
        Assert.assertNotNull("Data on demand for 'TargetSiteCode' failed to initialize correctly", dod.getRandomTargetSiteCode());
        TargetSiteCode obj = dod.getNewTransientTargetSiteCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'TargetSiteCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'TargetSiteCode' identifier to be null", obj.getId());
        targetSiteCodeRepository.save(obj);
        targetSiteCodeRepository.flush();
        Assert.assertNotNull("Expected 'TargetSiteCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteTargetSiteCode() {
        TargetSiteCode obj = dod.getRandomTargetSiteCode();
        Assert.assertNotNull("Data on demand for 'TargetSiteCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'TargetSiteCode' failed to provide an identifier", id);
        obj = targetSiteCodeRepository.findOne(id);
        targetSiteCodeRepository.delete(obj);
        targetSiteCodeRepository.flush();
        Assert.assertNull("Failed to remove 'TargetSiteCode' with identifier '" + id + "'", targetSiteCodeRepository.findOne(id));
    }
}
