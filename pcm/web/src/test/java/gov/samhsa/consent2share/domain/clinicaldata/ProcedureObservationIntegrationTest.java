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
public class ProcedureObservationIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    ProcedureObservationDataOnDemand dod;
	
	@Autowired
    ProcedureObservationRepository procedureObservationRepository;

	@Test
    public void testCountAllProcedureObservations() {
        Assert.assertNotNull("Data on demand for 'ProcedureObservation' failed to initialize correctly", dod.getRandomProcedureObservation());
        long count = procedureObservationRepository.count();
        Assert.assertTrue("Counter for 'ProcedureObservation' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindProcedureObservation() {
        ProcedureObservation obj = dod.getRandomProcedureObservation();
        Assert.assertNotNull("Data on demand for 'ProcedureObservation' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ProcedureObservation' failed to provide an identifier", id);
        obj = procedureObservationRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ProcedureObservation' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'ProcedureObservation' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllProcedureObservations() {
        Assert.assertNotNull("Data on demand for 'ProcedureObservation' failed to initialize correctly", dod.getRandomProcedureObservation());
        long count = procedureObservationRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'ProcedureObservation', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<ProcedureObservation> result = procedureObservationRepository.findAll();
        Assert.assertNotNull("Find all method for 'ProcedureObservation' illegally returned null", result);
        Assert.assertTrue("Find all method for 'ProcedureObservation' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindProcedureObservationEntries() {
        Assert.assertNotNull("Data on demand for 'ProcedureObservation' failed to initialize correctly", dod.getRandomProcedureObservation());
        long count = procedureObservationRepository.count();
        if (count > 20) count = 20;
        int pageNumber = 0;
        int pageSize = (int) count;
        List<ProcedureObservation> result = procedureObservationRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'ProcedureObservation' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'ProcedureObservation' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        ProcedureObservation obj = dod.getRandomProcedureObservation();
        Assert.assertNotNull("Data on demand for 'ProcedureObservation' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ProcedureObservation' failed to provide an identifier", id);
        obj = procedureObservationRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ProcedureObservation' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyProcedureObservation(obj);
        Integer currentVersion = obj.getVersion();
        procedureObservationRepository.flush();
        Assert.assertTrue("Version for 'ProcedureObservation' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateProcedureObservationUpdate() {
        ProcedureObservation obj = dod.getRandomProcedureObservation();
        Assert.assertNotNull("Data on demand for 'ProcedureObservation' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ProcedureObservation' failed to provide an identifier", id);
        obj = procedureObservationRepository.findOne(id);
        boolean modified =  dod.modifyProcedureObservation(obj);
        Integer currentVersion = obj.getVersion();
        ProcedureObservation merged = procedureObservationRepository.save(obj);
        procedureObservationRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'ProcedureObservation' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveProcedureObservation() {
        Assert.assertNotNull("Data on demand for 'ProcedureObservation' failed to initialize correctly", dod.getRandomProcedureObservation());
        ProcedureObservation obj = dod.getNewTransientProcedureObservation(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'ProcedureObservation' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'ProcedureObservation' identifier to be null", obj.getId());
        procedureObservationRepository.save(obj);
        procedureObservationRepository.flush();
        Assert.assertNotNull("Expected 'ProcedureObservation' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteProcedureObservation() {
        ProcedureObservation obj = dod.getRandomProcedureObservation();
        Assert.assertNotNull("Data on demand for 'ProcedureObservation' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ProcedureObservation' failed to provide an identifier", id);
        obj = procedureObservationRepository.findOne(id);
        procedureObservationRepository.delete(obj);
        procedureObservationRepository.flush();
        Assert.assertNull("Failed to remove 'ProcedureObservation' with identifier '" + id + "'", procedureObservationRepository.findOne(id));
    }
}
