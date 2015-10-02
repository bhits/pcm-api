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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@ActiveProfiles("test")
@Transactional
@Configurable
public class ProblemIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    ProblemDataOnDemand dod;

	@Autowired
    ProblemRepository problemRepository;

	@Test
    public void testCountAllProblems() {
        Assert.assertNotNull("Data on demand for 'Problem' failed to initialize correctly", dod.getRandomProblem());
        long count = problemRepository.count();
        Assert.assertTrue("Counter for 'Problem' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindProblem() {
        Problem obj = dod.getRandomProblem();
        Assert.assertNotNull("Data on demand for 'Problem' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Problem' failed to provide an identifier", id);
        obj = problemRepository.findOne(id);
        Assert.assertNotNull("Find method for 'Problem' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Problem' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllProblems() {
        Assert.assertNotNull("Data on demand for 'Problem' failed to initialize correctly", dod.getRandomProblem());
        long count = problemRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'Problem', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Problem> result = problemRepository.findAll();
        Assert.assertNotNull("Find all method for 'Problem' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Problem' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindProblemEntries() {
        Assert.assertNotNull("Data on demand for 'Problem' failed to initialize correctly", dod.getRandomProblem());
        long count = problemRepository.count();
        if (count > 20) count = 20;
        int pageNumber = 0;
        int pageSize = (int) count;
        List<Problem> result = problemRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'Problem' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Problem' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        Problem obj = dod.getRandomProblem();
        Assert.assertNotNull("Data on demand for 'Problem' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Problem' failed to provide an identifier", id);
        obj = problemRepository.findOne(id);
        Assert.assertNotNull("Find method for 'Problem' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyProblem(obj);
        Integer currentVersion = obj.getVersion();
        problemRepository.flush();
        Assert.assertTrue("Version for 'Problem' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateProblemUpdate() {
        Problem obj = dod.getRandomProblem();
        Assert.assertNotNull("Data on demand for 'Problem' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Problem' failed to provide an identifier", id);
        obj = problemRepository.findOne(id);
        boolean modified =  dod.modifyProblem(obj);
        Integer currentVersion = obj.getVersion();
        Problem merged = problemRepository.save(obj);
        problemRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Problem' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveProblem() {
        Assert.assertNotNull("Data on demand for 'Problem' failed to initialize correctly", dod.getRandomProblem());
        Problem obj = dod.getNewTransientProblem(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Problem' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Problem' identifier to be null", obj.getId());
        problemRepository.save(obj);
        problemRepository.flush();
        Assert.assertNotNull("Expected 'Problem' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteProblem() {
        Problem obj = dod.getRandomProblem();
        Assert.assertNotNull("Data on demand for 'Problem' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Problem' failed to provide an identifier", id);
        obj = problemRepository.findOne(id);
        problemRepository.delete(obj);
        problemRepository.flush();
        Assert.assertNull("Failed to remove 'Problem' with identifier '" + id + "'", problemRepository.findOne(id));
    }
}
