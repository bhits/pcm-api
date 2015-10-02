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
public class SocialHistoryStatusCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    SocialHistoryStatusCodeDataOnDemand dod;

	@Autowired
    SocialHistoryStatusCodeRepository socialHistoryStatusCodeRepository;

	@Test
    public void testCountAllSocialHistoryStatusCodes() {
        Assert.assertNotNull("Data on demand for 'SocialHistoryStatusCode' failed to initialize correctly", dod.getRandomSocialHistoryStatusCode());
        long count = socialHistoryStatusCodeRepository.count();
        Assert.assertTrue("Counter for 'SocialHistoryStatusCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindSocialHistoryStatusCode() {
        SocialHistoryStatusCode obj = dod.getRandomSocialHistoryStatusCode();
        Assert.assertNotNull("Data on demand for 'SocialHistoryStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SocialHistoryStatusCode' failed to provide an identifier", id);
        obj = socialHistoryStatusCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'SocialHistoryStatusCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'SocialHistoryStatusCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllSocialHistoryStatusCodes() {
        Assert.assertNotNull("Data on demand for 'SocialHistoryStatusCode' failed to initialize correctly", dod.getRandomSocialHistoryStatusCode());
        long count = socialHistoryStatusCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'SocialHistoryStatusCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<SocialHistoryStatusCode> result = socialHistoryStatusCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'SocialHistoryStatusCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'SocialHistoryStatusCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindSocialHistoryStatusCodeEntries() {
        Assert.assertNotNull("Data on demand for 'SocialHistoryStatusCode' failed to initialize correctly", dod.getRandomSocialHistoryStatusCode());
        long count = socialHistoryStatusCodeRepository.count();
        if (count > 20) count = 20;
        int pageNumber = 0;
        int pageSize = (int) count;
        List<SocialHistoryStatusCode> result = socialHistoryStatusCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'SocialHistoryStatusCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'SocialHistoryStatusCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        SocialHistoryStatusCode obj = dod.getRandomSocialHistoryStatusCode();
        Assert.assertNotNull("Data on demand for 'SocialHistoryStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SocialHistoryStatusCode' failed to provide an identifier", id);
        obj = socialHistoryStatusCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'SocialHistoryStatusCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifySocialHistoryStatusCode(obj);
        Integer currentVersion = obj.getVersion();
        socialHistoryStatusCodeRepository.flush();
        Assert.assertTrue("Version for 'SocialHistoryStatusCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateSocialHistoryStatusCodeUpdate() {
        SocialHistoryStatusCode obj = dod.getRandomSocialHistoryStatusCode();
        Assert.assertNotNull("Data on demand for 'SocialHistoryStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SocialHistoryStatusCode' failed to provide an identifier", id);
        obj = socialHistoryStatusCodeRepository.findOne(id);
        boolean modified =  dod.modifySocialHistoryStatusCode(obj);
        Integer currentVersion = obj.getVersion();
        SocialHistoryStatusCode merged = (SocialHistoryStatusCode)socialHistoryStatusCodeRepository.save(obj);
        socialHistoryStatusCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'SocialHistoryStatusCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveSocialHistoryStatusCode() {
        Assert.assertNotNull("Data on demand for 'SocialHistoryStatusCode' failed to initialize correctly", dod.getRandomSocialHistoryStatusCode());
        SocialHistoryStatusCode obj = dod.getNewTransientSocialHistoryStatusCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'SocialHistoryStatusCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'SocialHistoryStatusCode' identifier to be null", obj.getId());
        socialHistoryStatusCodeRepository.save(obj);
        socialHistoryStatusCodeRepository.flush();
        Assert.assertNotNull("Expected 'SocialHistoryStatusCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteSocialHistoryStatusCode() {
        SocialHistoryStatusCode obj = dod.getRandomSocialHistoryStatusCode();
        Assert.assertNotNull("Data on demand for 'SocialHistoryStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SocialHistoryStatusCode' failed to provide an identifier", id);
        obj = socialHistoryStatusCodeRepository.findOne(id);
        socialHistoryStatusCodeRepository.delete(obj);
        socialHistoryStatusCodeRepository.flush();
        Assert.assertNull("Failed to remove 'SocialHistoryStatusCode' with identifier '" + id + "'", socialHistoryStatusCodeRepository.findOne(id));
    }
}
