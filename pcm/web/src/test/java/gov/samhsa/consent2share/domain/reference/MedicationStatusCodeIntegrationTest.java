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
public class MedicationStatusCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    MedicationStatusCodeDataOnDemand dod;

	@Autowired
    MedicationStatusCodeRepository medicationStatusCodeRepository;

	@Test
    public void testCountAllMedicationStatusCodes() {
        Assert.assertNotNull("Data on demand for 'MedicationStatusCode' failed to initialize correctly", dod.getRandomMedicationStatusCode());
        long count = medicationStatusCodeRepository.count();
        Assert.assertTrue("Counter for 'MedicationStatusCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindMedicationStatusCode() {
        MedicationStatusCode obj = dod.getRandomMedicationStatusCode();
        Assert.assertNotNull("Data on demand for 'MedicationStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'MedicationStatusCode' failed to provide an identifier", id);
        obj = medicationStatusCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'MedicationStatusCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'MedicationStatusCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllMedicationStatusCodes() {
        Assert.assertNotNull("Data on demand for 'MedicationStatusCode' failed to initialize correctly", dod.getRandomMedicationStatusCode());
        long count = medicationStatusCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'MedicationStatusCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<MedicationStatusCode> result = medicationStatusCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'MedicationStatusCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'MedicationStatusCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindMedicationStatusCodeEntries() {
        Assert.assertNotNull("Data on demand for 'MedicationStatusCode' failed to initialize correctly", dod.getRandomMedicationStatusCode());
        long count = medicationStatusCodeRepository.count();
        if (count > 20) count = 20;
        int pageNumber = 0;
        int pageSize = (int) count;
        List<MedicationStatusCode> result = medicationStatusCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'MedicationStatusCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'MedicationStatusCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        MedicationStatusCode obj = dod.getRandomMedicationStatusCode();
        Assert.assertNotNull("Data on demand for 'MedicationStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'MedicationStatusCode' failed to provide an identifier", id);
        obj = medicationStatusCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'MedicationStatusCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyMedicationStatusCode(obj);
        Integer currentVersion = obj.getVersion();
        medicationStatusCodeRepository.flush();
        Assert.assertTrue("Version for 'MedicationStatusCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateMedicationStatusCodeUpdate() {
        MedicationStatusCode obj = dod.getRandomMedicationStatusCode();
        Assert.assertNotNull("Data on demand for 'MedicationStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'MedicationStatusCode' failed to provide an identifier", id);
        obj = medicationStatusCodeRepository.findOne(id);
        boolean modified =  dod.modifyMedicationStatusCode(obj);
        Integer currentVersion = obj.getVersion();
        MedicationStatusCode merged = (MedicationStatusCode)medicationStatusCodeRepository.save(obj);
        medicationStatusCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'MedicationStatusCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveMedicationStatusCode() {
        Assert.assertNotNull("Data on demand for 'MedicationStatusCode' failed to initialize correctly", dod.getRandomMedicationStatusCode());
        MedicationStatusCode obj = dod.getNewTransientMedicationStatusCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'MedicationStatusCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'MedicationStatusCode' identifier to be null", obj.getId());
        medicationStatusCodeRepository.save(obj);
        medicationStatusCodeRepository.flush();
        Assert.assertNotNull("Expected 'MedicationStatusCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteMedicationStatusCode() {
        MedicationStatusCode obj = dod.getRandomMedicationStatusCode();
        Assert.assertNotNull("Data on demand for 'MedicationStatusCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'MedicationStatusCode' failed to provide an identifier", id);
        obj = medicationStatusCodeRepository.findOne(id);
        medicationStatusCodeRepository.delete(obj);
        medicationStatusCodeRepository.flush();
        Assert.assertNull("Failed to remove 'MedicationStatusCode' with identifier '" + id + "'", medicationStatusCodeRepository.findOne(id));
    }
}
