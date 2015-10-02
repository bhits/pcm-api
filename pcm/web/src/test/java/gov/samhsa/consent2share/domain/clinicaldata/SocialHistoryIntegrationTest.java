package gov.samhsa.consent2share.domain.clinicaldata;

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
public class SocialHistoryIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    SocialHistoryDataOnDemand dod;

	@Autowired
    SocialHistoryRepository socialHistoryRepository;

	@Test
    public void testCountAllSocialHistorys() {
        Assert.assertNotNull("Data on demand for 'SocialHistory' failed to initialize correctly", dod.getRandomSocialHistory());
        long count = socialHistoryRepository.count();
        Assert.assertTrue("Counter for 'SocialHistory' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindSocialHistory() {
        SocialHistory obj = dod.getRandomSocialHistory();
        Assert.assertNotNull("Data on demand for 'SocialHistory' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SocialHistory' failed to provide an identifier", id);
        obj = socialHistoryRepository.findOne(id);
        Assert.assertNotNull("Find method for 'SocialHistory' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'SocialHistory' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllSocialHistorys() {
        Assert.assertNotNull("Data on demand for 'SocialHistory' failed to initialize correctly", dod.getRandomSocialHistory());
        long count = socialHistoryRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'SocialHistory', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<SocialHistory> result = socialHistoryRepository.findAll();
        Assert.assertNotNull("Find all method for 'SocialHistory' illegally returned null", result);
        Assert.assertTrue("Find all method for 'SocialHistory' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindSocialHistoryEntries() {
        Assert.assertNotNull("Data on demand for 'SocialHistory' failed to initialize correctly", dod.getRandomSocialHistory());
        long count = socialHistoryRepository.count();
        if (count > 20) count = 20;
        int pageNumber = 0;
        int pageSize = (int) count;
        List<SocialHistory> result = socialHistoryRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'SocialHistory' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'SocialHistory' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        SocialHistory obj = dod.getRandomSocialHistory();
        Assert.assertNotNull("Data on demand for 'SocialHistory' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SocialHistory' failed to provide an identifier", id);
        obj = socialHistoryRepository.findOne(id);
        Assert.assertNotNull("Find method for 'SocialHistory' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifySocialHistory(obj);
        Integer currentVersion = obj.getVersion();
        socialHistoryRepository.flush();
        Assert.assertTrue("Version for 'SocialHistory' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateSocialHistoryUpdate() {
        SocialHistory obj = dod.getRandomSocialHistory();
        Assert.assertNotNull("Data on demand for 'SocialHistory' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SocialHistory' failed to provide an identifier", id);
        obj = socialHistoryRepository.findOne(id);
        boolean modified =  dod.modifySocialHistory(obj);
        Integer currentVersion = obj.getVersion();
        SocialHistory merged = socialHistoryRepository.save(obj);
        socialHistoryRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'SocialHistory' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveSocialHistory() {
        Assert.assertNotNull("Data on demand for 'SocialHistory' failed to initialize correctly", dod.getRandomSocialHistory());
        SocialHistory obj = dod.getNewTransientSocialHistory(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'SocialHistory' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'SocialHistory' identifier to be null", obj.getId());
        socialHistoryRepository.save(obj);
        socialHistoryRepository.flush();
        Assert.assertNotNull("Expected 'SocialHistory' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteSocialHistory() {
        SocialHistory obj = dod.getRandomSocialHistory();
        Assert.assertNotNull("Data on demand for 'SocialHistory' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SocialHistory' failed to provide an identifier", id);
        obj = socialHistoryRepository.findOne(id);
        socialHistoryRepository.delete(obj);
        socialHistoryRepository.flush();
        Assert.assertNull("Failed to remove 'SocialHistory' with identifier '" + id + "'", socialHistoryRepository.findOne(id));
    }
}
