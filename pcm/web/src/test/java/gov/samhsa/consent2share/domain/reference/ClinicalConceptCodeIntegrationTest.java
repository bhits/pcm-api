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
public class ClinicalConceptCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    ClinicalConceptCodeDataOnDemand dod;

	@Autowired
    ClinicalConceptCodeRepository clinicalConceptCodeRepository;

	@Test
    public void testCountAllClinicalConceptCodes() {
        Assert.assertNotNull("Data on demand for 'ClinicalConceptCode' failed to initialize correctly", dod.getRandomClinicalConceptCode());
        long count = clinicalConceptCodeRepository.count();
        Assert.assertTrue("Counter for 'ClinicalConceptCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindClinicalConceptCode() {
        ClinicalConceptCode obj = dod.getRandomClinicalConceptCode();
        Assert.assertNotNull("Data on demand for 'ClinicalConceptCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ClinicalConceptCode' failed to provide an identifier", id);
        obj = clinicalConceptCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ClinicalConceptCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'ClinicalConceptCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllClinicalConceptCodes() {
        Assert.assertNotNull("Data on demand for 'ClinicalConceptCode' failed to initialize correctly", dod.getRandomClinicalConceptCode());
        long count = clinicalConceptCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'ClinicalConceptCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<ClinicalConceptCode> result = clinicalConceptCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'ClinicalConceptCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'ClinicalConceptCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindClinicalConceptCodeEntries() {
        Assert.assertNotNull("Data on demand for 'ClinicalConceptCode' failed to initialize correctly", dod.getRandomClinicalConceptCode());
        long count = clinicalConceptCodeRepository.count();
        if (count > 20) count = 20;
		int pageNumber = 0;
        int pageSize = 10;
        List<ClinicalConceptCode> result = clinicalConceptCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'ClinicalConceptCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'ClinicalConceptCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        ClinicalConceptCode obj = dod.getRandomClinicalConceptCode();
        Assert.assertNotNull("Data on demand for 'ClinicalConceptCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ClinicalConceptCode' failed to provide an identifier", id);
        obj = clinicalConceptCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ClinicalConceptCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyClinicalConceptCode(obj);
        Integer currentVersion = obj.getVersion();
        clinicalConceptCodeRepository.flush();
        Assert.assertTrue("Version for 'ClinicalConceptCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateClinicalConceptCodeUpdate() {
        ClinicalConceptCode obj = dod.getRandomClinicalConceptCode();
        Assert.assertNotNull("Data on demand for 'ClinicalConceptCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ClinicalConceptCode' failed to provide an identifier", id);
        obj = clinicalConceptCodeRepository.findOne(id);
        boolean modified =  dod.modifyClinicalConceptCode(obj);
        Integer currentVersion = obj.getVersion();
        ClinicalConceptCode merged = (ClinicalConceptCode)clinicalConceptCodeRepository.save(obj);
        clinicalConceptCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'ClinicalConceptCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveClinicalConceptCode() {
        Assert.assertNotNull("Data on demand for 'ClinicalConceptCode' failed to initialize correctly", dod.getRandomClinicalConceptCode());
        ClinicalConceptCode obj = dod.getNewTransientClinicalConceptCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'ClinicalConceptCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'ClinicalConceptCode' identifier to be null", obj.getId());
        clinicalConceptCodeRepository.save(obj);
        clinicalConceptCodeRepository.flush();
        Assert.assertNotNull("Expected 'ClinicalConceptCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteClinicalConceptCode() {
        ClinicalConceptCode obj = dod.getRandomClinicalConceptCode();
        Assert.assertNotNull("Data on demand for 'ClinicalConceptCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ClinicalConceptCode' failed to provide an identifier", id);
        obj = clinicalConceptCodeRepository.findOne(id);
        clinicalConceptCodeRepository.delete(obj);
        clinicalConceptCodeRepository.flush();
        Assert.assertNull("Failed to remove 'ClinicalConceptCode' with identifier '" + id + "'", clinicalConceptCodeRepository.findOne(id));
    }
}
