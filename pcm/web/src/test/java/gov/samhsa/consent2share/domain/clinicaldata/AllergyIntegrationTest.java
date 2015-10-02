package gov.samhsa.consent2share.domain.clinicaldata;

import gov.samhsa.consent2share.service.clinicaldata.AllergyService;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@ActiveProfiles("test")
@Transactional
public class AllergyIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    AllergyDataOnDemand dod;

	@Autowired
    AllergyService allergyService;

	@Autowired
    AllergyRepository allergyRepository;

	@Test
    public void testCountAllAllergys() {
        Assert.assertNotNull("Data on demand for 'Allergy' failed to initialize correctly", dod.getRandomAllergy());
        long count = allergyService.countAllAllergys();
        Assert.assertTrue("Counter for 'Allergy' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindAllergy() {
        Allergy obj = dod.getRandomAllergy();
        Assert.assertNotNull("Data on demand for 'Allergy' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Allergy' failed to provide an identifier", id);
        obj = allergyService.findAllergy(id);
        Assert.assertNotNull("Find method for 'Allergy' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Allergy' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllAllergys() {
        Assert.assertNotNull("Data on demand for 'Allergy' failed to initialize correctly", dod.getRandomAllergy());
        long count = allergyService.countAllAllergys();
        Assert.assertTrue("Too expensive to perform a find all test for 'Allergy', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Allergy> result = allergyService.findAllAllergys();
        Assert.assertNotNull("Find all method for 'Allergy' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Allergy' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindAllergyEntries() {
        Assert.assertNotNull("Data on demand for 'Allergy' failed to initialize correctly", dod.getRandomAllergy());
        long count = allergyService.countAllAllergys();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Allergy> result = allergyService.findAllergyEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Allergy' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Allergy' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        Allergy obj = dod.getRandomAllergy();
        Assert.assertNotNull("Data on demand for 'Allergy' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Allergy' failed to provide an identifier", id);
        obj = allergyService.findAllergy(id);
        Assert.assertNotNull("Find method for 'Allergy' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyAllergy(obj);
        Integer currentVersion = obj.getVersion();
        allergyRepository.flush();
        Assert.assertTrue("Version for 'Allergy' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateAllergyUpdate() {
        Allergy obj = dod.getRandomAllergy();
        Assert.assertNotNull("Data on demand for 'Allergy' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Allergy' failed to provide an identifier", id);
        obj = allergyService.findAllergy(id);
        boolean modified =  dod.modifyAllergy(obj);
        Integer currentVersion = obj.getVersion();
        Allergy merged = allergyService.updateAllergy(obj);
        allergyRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Allergy' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveAllergy() {
        Assert.assertNotNull("Data on demand for 'Allergy' failed to initialize correctly", dod.getRandomAllergy());
        Allergy obj = dod.getNewTransientAllergy(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Allergy' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Allergy' identifier to be null", obj.getId());
        allergyService.saveAllergy(obj);
        allergyRepository.flush();
        Assert.assertNotNull("Expected 'Allergy' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteAllergy() {
        Allergy obj = dod.getRandomAllergy();
        Assert.assertNotNull("Data on demand for 'Allergy' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Allergy' failed to provide an identifier", id);
        obj = allergyService.findAllergy(id);
        allergyService.deleteAllergy(obj);
        allergyRepository.flush();
        Assert.assertNull("Failed to remove 'Allergy' with identifier '" + id + "'", allergyService.findAllergy(id));
    }
}
