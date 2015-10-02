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
public class MedicationIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    MedicationDataOnDemand dod;

	@Autowired
    MedicationRepository medicationRepository;

	@Test
    public void testCountAllMedications() {
        Assert.assertNotNull("Data on demand for 'Medication' failed to initialize correctly", dod.getRandomMedication());
        long count = medicationRepository.count();
        Assert.assertTrue("Counter for 'Medication' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindMedication() {
        Medication obj = dod.getRandomMedication();
        Assert.assertNotNull("Data on demand for 'Medication' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Medication' failed to provide an identifier", id);
        obj = medicationRepository.findOne(id);
        Assert.assertNotNull("Find method for 'Medication' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Medication' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllMedications() {
        Assert.assertNotNull("Data on demand for 'Medication' failed to initialize correctly", dod.getRandomMedication());
        long count = medicationRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'Medication', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Medication> result = medicationRepository.findAll();
        Assert.assertNotNull("Find all method for 'Medication' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Medication' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindMedicationEntries() {
        Assert.assertNotNull("Data on demand for 'Medication' failed to initialize correctly", dod.getRandomMedication());
        long count = medicationRepository.count();
        if (count > 20) count = 20;
        int pageNumber = 0;
        int pageSize = (int) count;
        List<Medication> result = medicationRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'Medication' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Medication' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        Medication obj = dod.getRandomMedication();
        Assert.assertNotNull("Data on demand for 'Medication' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Medication' failed to provide an identifier", id);
        obj = medicationRepository.findOne(id);
        Assert.assertNotNull("Find method for 'Medication' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyMedication(obj);
        Integer currentVersion = obj.getVersion();
        medicationRepository.flush();
        Assert.assertTrue("Version for 'Medication' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateMedicationUpdate() {
        Medication obj = dod.getRandomMedication();
        Assert.assertNotNull("Data on demand for 'Medication' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Medication' failed to provide an identifier", id);
        obj = medicationRepository.findOne(id);
        boolean modified =  dod.modifyMedication(obj);
        Integer currentVersion = obj.getVersion();
        Medication merged = medicationRepository.save(obj);
        medicationRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Medication' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveMedication() {
        Assert.assertNotNull("Data on demand for 'Medication' failed to initialize correctly", dod.getRandomMedication());
        Medication obj = dod.getNewTransientMedication(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Medication' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Medication' identifier to be null", obj.getId());
        medicationRepository.save(obj);
        medicationRepository.flush();
        Assert.assertNotNull("Expected 'Medication' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteMedication() {
        Medication obj = dod.getRandomMedication();
        Assert.assertNotNull("Data on demand for 'Medication' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Medication' failed to provide an identifier", id);
        obj = medicationRepository.findOne(id);
        medicationRepository.delete(obj);
        medicationRepository.flush();
        Assert.assertNull("Failed to remove 'Medication' with identifier '" + id + "'", medicationRepository.findOne(id));
    }
}
