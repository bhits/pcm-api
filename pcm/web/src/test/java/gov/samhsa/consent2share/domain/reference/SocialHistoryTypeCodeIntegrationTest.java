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
public class SocialHistoryTypeCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    SocialHistoryTypeCodeDataOnDemand dod;

	@Autowired
    SocialHistoryTypeCodeRepository socialHistoryTypeCodeRepository;

	@Test
    public void testCountAllSocialHistoryTypeCodes() {
        Assert.assertNotNull("Data on demand for 'SocialHistoryTypeCode' failed to initialize correctly", dod.getRandomSocialHistoryTypeCode());
        long count = socialHistoryTypeCodeRepository.count();
        Assert.assertTrue("Counter for 'SocialHistoryTypeCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindSocialHistoryTypeCode() {
        SocialHistoryTypeCode obj = dod.getRandomSocialHistoryTypeCode();
        Assert.assertNotNull("Data on demand for 'SocialHistoryTypeCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SocialHistoryTypeCode' failed to provide an identifier", id);
        obj = socialHistoryTypeCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'SocialHistoryTypeCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'SocialHistoryTypeCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllSocialHistoryTypeCodes() {
        Assert.assertNotNull("Data on demand for 'SocialHistoryTypeCode' failed to initialize correctly", dod.getRandomSocialHistoryTypeCode());
        long count = socialHistoryTypeCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'SocialHistoryTypeCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<SocialHistoryTypeCode> result = socialHistoryTypeCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'SocialHistoryTypeCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'SocialHistoryTypeCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindSocialHistoryTypeCodeEntries() {
        Assert.assertNotNull("Data on demand for 'SocialHistoryTypeCode' failed to initialize correctly", dod.getRandomSocialHistoryTypeCode());
        long count = socialHistoryTypeCodeRepository.count();
        if (count > 20) count = 20;
        int pageNumber = 0;
        int pageSize = (int) count;
        List<SocialHistoryTypeCode> result = socialHistoryTypeCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'SocialHistoryTypeCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'SocialHistoryTypeCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        SocialHistoryTypeCode obj = dod.getRandomSocialHistoryTypeCode();
        Assert.assertNotNull("Data on demand for 'SocialHistoryTypeCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SocialHistoryTypeCode' failed to provide an identifier", id);
        obj = socialHistoryTypeCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'SocialHistoryTypeCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifySocialHistoryTypeCode(obj);
        Integer currentVersion = obj.getVersion();
        socialHistoryTypeCodeRepository.flush();
        Assert.assertTrue("Version for 'SocialHistoryTypeCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateSocialHistoryTypeCodeUpdate() {
        SocialHistoryTypeCode obj = dod.getRandomSocialHistoryTypeCode();
        Assert.assertNotNull("Data on demand for 'SocialHistoryTypeCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SocialHistoryTypeCode' failed to provide an identifier", id);
        obj = socialHistoryTypeCodeRepository.findOne(id);
        boolean modified =  dod.modifySocialHistoryTypeCode(obj);
        Integer currentVersion = obj.getVersion();
        SocialHistoryTypeCode merged = (SocialHistoryTypeCode)socialHistoryTypeCodeRepository.save(obj);
        socialHistoryTypeCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'SocialHistoryTypeCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveSocialHistoryTypeCode() {
        Assert.assertNotNull("Data on demand for 'SocialHistoryTypeCode' failed to initialize correctly", dod.getRandomSocialHistoryTypeCode());
        SocialHistoryTypeCode obj = dod.getNewTransientSocialHistoryTypeCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'SocialHistoryTypeCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'SocialHistoryTypeCode' identifier to be null", obj.getId());
        socialHistoryTypeCodeRepository.save(obj);
        socialHistoryTypeCodeRepository.flush();
        Assert.assertNotNull("Expected 'SocialHistoryTypeCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteSocialHistoryTypeCode() {
        SocialHistoryTypeCode obj = dod.getRandomSocialHistoryTypeCode();
        Assert.assertNotNull("Data on demand for 'SocialHistoryTypeCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SocialHistoryTypeCode' failed to provide an identifier", id);
        obj = socialHistoryTypeCodeRepository.findOne(id);
        socialHistoryTypeCodeRepository.delete(obj);
        socialHistoryTypeCodeRepository.flush();
        Assert.assertNull("Failed to remove 'SocialHistoryTypeCode' with identifier '" + id + "'", socialHistoryTypeCodeRepository.findOne(id));
    }
}
