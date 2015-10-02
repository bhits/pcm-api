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
public class ProblemStatusCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    ProblemStatusCodeDataOnDemand dod;

	@Autowired
    ProblemStatusCodeRepository problemStatusCodeRepository;

	@Test
    public void testCountAllProblemStatusCodes() {
        Assert.assertNotNull("Data on demand for 'ProblemStatusCode' failed to initialize correctly", dod.getRandomProblemStatusCode());
        long count = problemStatusCodeRepository.count();
        Assert.assertTrue("Counter for 'ProblemStatusCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindProblemStatusCode() {
        ProblemStatusCode obj = dod.getRandomProblemStatusCode();
        Assert.assertNotNull("Data on demand for 'ProblemStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ProblemStatusCode' failed to provide an identifier", id);
        obj = problemStatusCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ProblemStatusCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'ProblemStatusCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllProblemStatusCodes() {
        Assert.assertNotNull("Data on demand for 'ProblemStatusCode' failed to initialize correctly", dod.getRandomProblemStatusCode());
        long count = problemStatusCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'ProblemStatusCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<ProblemStatusCode> result = problemStatusCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'ProblemStatusCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'ProblemStatusCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindProblemStatusCodeEntries() {
        Assert.assertNotNull("Data on demand for 'ProblemStatusCode' failed to initialize correctly", dod.getRandomProblemStatusCode());
        long count = problemStatusCodeRepository.count();
        if (count > 20) count = 20;
        int pageNumber = 0;
        int pageSize = (int) count;
        List<ProblemStatusCode> result = problemStatusCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'ProblemStatusCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'ProblemStatusCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        ProblemStatusCode obj = dod.getRandomProblemStatusCode();
        Assert.assertNotNull("Data on demand for 'ProblemStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ProblemStatusCode' failed to provide an identifier", id);
        obj = problemStatusCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ProblemStatusCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyProblemStatusCode(obj);
        Integer currentVersion = obj.getVersion();
        problemStatusCodeRepository.flush();
        Assert.assertTrue("Version for 'ProblemStatusCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateProblemStatusCodeUpdate() {
        ProblemStatusCode obj = dod.getRandomProblemStatusCode();
        Assert.assertNotNull("Data on demand for 'ProblemStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ProblemStatusCode' failed to provide an identifier", id);
        obj = problemStatusCodeRepository.findOne(id);
        boolean modified =  dod.modifyProblemStatusCode(obj);
        Integer currentVersion = obj.getVersion();
        ProblemStatusCode merged = (ProblemStatusCode)problemStatusCodeRepository.save(obj);
        problemStatusCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'ProblemStatusCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveProblemStatusCode() {
        Assert.assertNotNull("Data on demand for 'ProblemStatusCode' failed to initialize correctly", dod.getRandomProblemStatusCode());
        ProblemStatusCode obj = dod.getNewTransientProblemStatusCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'ProblemStatusCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'ProblemStatusCode' identifier to be null", obj.getId());
        problemStatusCodeRepository.save(obj);
        problemStatusCodeRepository.flush();
        Assert.assertNotNull("Expected 'ProblemStatusCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteProblemStatusCode() {
        ProblemStatusCode obj = dod.getRandomProblemStatusCode();
        Assert.assertNotNull("Data on demand for 'ProblemStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ProblemStatusCode' failed to provide an identifier", id);
        obj = problemStatusCodeRepository.findOne(id);
        problemStatusCodeRepository.delete(obj);
        problemStatusCodeRepository.flush();
        Assert.assertNull("Failed to remove 'ProblemStatusCode' with identifier '" + id + "'", problemStatusCodeRepository.findOne(id));
    }
}
