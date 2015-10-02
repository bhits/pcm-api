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
public class ProcedureStatusCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    ProcedureStatusCodeDataOnDemand dod;

	@Autowired
    ProcedureStatusCodeRepository procedureStatusCodeRepository;

	@Test
    public void testCountAllProcedureStatusCodes() {
        Assert.assertNotNull("Data on demand for 'ProcedureStatusCode' failed to initialize correctly", dod.getRandomProcedureStatusCode());
        long count = procedureStatusCodeRepository.count();
        Assert.assertTrue("Counter for 'ProcedureStatusCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindProcedureStatusCode() {
        ProcedureStatusCode obj = dod.getRandomProcedureStatusCode();
        Assert.assertNotNull("Data on demand for 'ProcedureStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ProcedureStatusCode' failed to provide an identifier", id);
        obj = procedureStatusCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ProcedureStatusCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'ProcedureStatusCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllProcedureStatusCodes() {
        Assert.assertNotNull("Data on demand for 'ProcedureStatusCode' failed to initialize correctly", dod.getRandomProcedureStatusCode());
        long count = procedureStatusCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'ProcedureStatusCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<ProcedureStatusCode> result = procedureStatusCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'ProcedureStatusCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'ProcedureStatusCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindProcedureStatusCodeEntries() {
        Assert.assertNotNull("Data on demand for 'ProcedureStatusCode' failed to initialize correctly", dod.getRandomProcedureStatusCode());
        long count = procedureStatusCodeRepository.count();
        if (count > 20) count = 20;
        int pageNumber = 0;
        int pageSize = (int) count;
        List<ProcedureStatusCode> result = procedureStatusCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'ProcedureStatusCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'ProcedureStatusCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        ProcedureStatusCode obj = dod.getRandomProcedureStatusCode();
        Assert.assertNotNull("Data on demand for 'ProcedureStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ProcedureStatusCode' failed to provide an identifier", id);
        obj = procedureStatusCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ProcedureStatusCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyProcedureStatusCode(obj);
        Integer currentVersion = obj.getVersion();
        procedureStatusCodeRepository.flush();
        Assert.assertTrue("Version for 'ProcedureStatusCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateProcedureStatusCodeUpdate() {
        ProcedureStatusCode obj = dod.getRandomProcedureStatusCode();
        Assert.assertNotNull("Data on demand for 'ProcedureStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ProcedureStatusCode' failed to provide an identifier", id);
        obj = procedureStatusCodeRepository.findOne(id);
        boolean modified =  dod.modifyProcedureStatusCode(obj);
        Integer currentVersion = obj.getVersion();
        ProcedureStatusCode merged = (ProcedureStatusCode)procedureStatusCodeRepository.save(obj);
        procedureStatusCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'ProcedureStatusCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveProcedureStatusCode() {
        Assert.assertNotNull("Data on demand for 'ProcedureStatusCode' failed to initialize correctly", dod.getRandomProcedureStatusCode());
        ProcedureStatusCode obj = dod.getNewTransientProcedureStatusCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'ProcedureStatusCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'ProcedureStatusCode' identifier to be null", obj.getId());
        procedureStatusCodeRepository.save(obj);
        procedureStatusCodeRepository.flush();
        Assert.assertNotNull("Expected 'ProcedureStatusCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteProcedureStatusCode() {
        ProcedureStatusCode obj = dod.getRandomProcedureStatusCode();
        Assert.assertNotNull("Data on demand for 'ProcedureStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ProcedureStatusCode' failed to provide an identifier", id);
        obj = procedureStatusCodeRepository.findOne(id);
        procedureStatusCodeRepository.delete(obj);
        procedureStatusCodeRepository.flush();
        Assert.assertNull("Failed to remove 'ProcedureStatusCode' with identifier '" + id + "'", procedureStatusCodeRepository.findOne(id));
    }
}
