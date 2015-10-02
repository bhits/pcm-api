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
public class ResultStatusCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    ResultStatusCodeDataOnDemand dod;

	@Autowired
    ResultStatusCodeRepository resultStatusCodeRepository;

	@Test
    public void testCountAllResultStatusCodes() {
        Assert.assertNotNull("Data on demand for 'ResultStatusCode' failed to initialize correctly", dod.getRandomResultStatusCode());
        long count = resultStatusCodeRepository.count();
        Assert.assertTrue("Counter for 'ResultStatusCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindResultStatusCode() {
        ResultStatusCode obj = dod.getRandomResultStatusCode();
        Assert.assertNotNull("Data on demand for 'ResultStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ResultStatusCode' failed to provide an identifier", id);
        obj = resultStatusCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ResultStatusCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'ResultStatusCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllResultStatusCodes() {
        Assert.assertNotNull("Data on demand for 'ResultStatusCode' failed to initialize correctly", dod.getRandomResultStatusCode());
        long count = resultStatusCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'ResultStatusCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<ResultStatusCode> result = resultStatusCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'ResultStatusCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'ResultStatusCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindResultStatusCodeEntries() {
        Assert.assertNotNull("Data on demand for 'ResultStatusCode' failed to initialize correctly", dod.getRandomResultStatusCode());
        long count = resultStatusCodeRepository.count();
        if (count > 20) count = 20;
        int pageNumber = 0;
        int pageSize = (int) count;
        List<ResultStatusCode> result = resultStatusCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'ResultStatusCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'ResultStatusCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        ResultStatusCode obj = dod.getRandomResultStatusCode();
        Assert.assertNotNull("Data on demand for 'ResultStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ResultStatusCode' failed to provide an identifier", id);
        obj = resultStatusCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ResultStatusCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyResultStatusCode(obj);
        Integer currentVersion = obj.getVersion();
        resultStatusCodeRepository.flush();
        Assert.assertTrue("Version for 'ResultStatusCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateResultStatusCodeUpdate() {
        ResultStatusCode obj = dod.getRandomResultStatusCode();
        Assert.assertNotNull("Data on demand for 'ResultStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ResultStatusCode' failed to provide an identifier", id);
        obj = resultStatusCodeRepository.findOne(id);
        boolean modified =  dod.modifyResultStatusCode(obj);
        Integer currentVersion = obj.getVersion();
        ResultStatusCode merged = (ResultStatusCode)resultStatusCodeRepository.save(obj);
        resultStatusCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'ResultStatusCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveResultStatusCode() {
        Assert.assertNotNull("Data on demand for 'ResultStatusCode' failed to initialize correctly", dod.getRandomResultStatusCode());
        ResultStatusCode obj = dod.getNewTransientResultStatusCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'ResultStatusCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'ResultStatusCode' identifier to be null", obj.getId());
        resultStatusCodeRepository.save(obj);
        resultStatusCodeRepository.flush();
        Assert.assertNotNull("Expected 'ResultStatusCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteResultStatusCode() {
        ResultStatusCode obj = dod.getRandomResultStatusCode();
        Assert.assertNotNull("Data on demand for 'ResultStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ResultStatusCode' failed to provide an identifier", id);
        obj = resultStatusCodeRepository.findOne(id);
        resultStatusCodeRepository.delete(obj);
        resultStatusCodeRepository.flush();
        Assert.assertNull("Failed to remove 'ResultStatusCode' with identifier '" + id + "'", resultStatusCodeRepository.findOne(id));
    }
}
