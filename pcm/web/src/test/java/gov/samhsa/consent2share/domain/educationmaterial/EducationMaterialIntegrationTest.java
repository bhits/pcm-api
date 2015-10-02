package gov.samhsa.consent2share.domain.educationmaterial;

import gov.samhsa.consent2share.service.educationmaterial.EducationMaterialService;
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
public class EducationMaterialIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    EducationMaterialDataOnDemand dod;

	@Autowired
    EducationMaterialService educationMaterialService;

	@Autowired
    EducationMaterialRepository educationMaterialRepository;

	@Test
    public void testCountAllEducationMaterials() {
        Assert.assertNotNull("Data on demand for 'EducationMaterial' failed to initialize correctly", dod.getRandomEducationMaterial());
        long count = educationMaterialService.countAllEducationMaterials();
        Assert.assertTrue("Counter for 'EducationMaterial' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindEducationMaterial() {
        EducationMaterial obj = dod.getRandomEducationMaterial();
        Assert.assertNotNull("Data on demand for 'EducationMaterial' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'EducationMaterial' failed to provide an identifier", id);
        obj = educationMaterialService.findEducationMaterial(id);
        Assert.assertNotNull("Find method for 'EducationMaterial' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'EducationMaterial' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllEducationMaterials() {
        Assert.assertNotNull("Data on demand for 'EducationMaterial' failed to initialize correctly", dod.getRandomEducationMaterial());
        long count = educationMaterialService.countAllEducationMaterials();
        Assert.assertTrue("Too expensive to perform a find all test for 'EducationMaterial', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<EducationMaterial> result = educationMaterialService.findAllEducationMaterials();
        Assert.assertNotNull("Find all method for 'EducationMaterial' illegally returned null", result);
        Assert.assertTrue("Find all method for 'EducationMaterial' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindEducationMaterialEntries() {
        Assert.assertNotNull("Data on demand for 'EducationMaterial' failed to initialize correctly", dod.getRandomEducationMaterial());
        long count = educationMaterialService.countAllEducationMaterials();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<EducationMaterial> result = educationMaterialService.findEducationMaterialEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'EducationMaterial' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'EducationMaterial' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        EducationMaterial obj = dod.getRandomEducationMaterial();
        Assert.assertNotNull("Data on demand for 'EducationMaterial' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'EducationMaterial' failed to provide an identifier", id);
        obj = educationMaterialService.findEducationMaterial(id);
        Assert.assertNotNull("Find method for 'EducationMaterial' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyEducationMaterial(obj);
        Integer currentVersion = obj.getVersion();
        educationMaterialRepository.flush();
        Assert.assertTrue("Version for 'EducationMaterial' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateEducationMaterialUpdate() {
        EducationMaterial obj = dod.getRandomEducationMaterial();
        Assert.assertNotNull("Data on demand for 'EducationMaterial' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'EducationMaterial' failed to provide an identifier", id);
        obj = educationMaterialService.findEducationMaterial(id);
        boolean modified =  dod.modifyEducationMaterial(obj);
        Integer currentVersion = obj.getVersion();
        EducationMaterial merged = educationMaterialService.updateEducationMaterial(obj);
        educationMaterialRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'EducationMaterial' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveEducationMaterial() {
        Assert.assertNotNull("Data on demand for 'EducationMaterial' failed to initialize correctly", dod.getRandomEducationMaterial());
        EducationMaterial obj = dod.getNewTransientEducationMaterial(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'EducationMaterial' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'EducationMaterial' identifier to be null", obj.getId());
        educationMaterialService.saveEducationMaterial(obj);
        educationMaterialRepository.flush();
        Assert.assertNotNull("Expected 'EducationMaterial' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteEducationMaterial() {
        EducationMaterial obj = dod.getRandomEducationMaterial();
        Assert.assertNotNull("Data on demand for 'EducationMaterial' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'EducationMaterial' failed to provide an identifier", id);
        obj = educationMaterialService.findEducationMaterial(id);
        educationMaterialService.deleteEducationMaterial(obj);
        educationMaterialRepository.flush();
        Assert.assertNull("Failed to remove 'EducationMaterial' with identifier '" + id + "'", educationMaterialService.findEducationMaterial(id));
    }
}
