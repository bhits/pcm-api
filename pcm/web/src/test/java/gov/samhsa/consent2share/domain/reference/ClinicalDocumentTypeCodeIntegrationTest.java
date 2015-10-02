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
public class ClinicalDocumentTypeCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    ClinicalDocumentTypeCodeDataOnDemand dod;

	@Autowired
    ClinicalDocumentTypeCodeRepository clinicalDocumentTypeCodeRepository;

	@Test
    public void testCountAllClinicalDocumentTypeCodes() {
        Assert.assertNotNull("Data on demand for 'ClinicalDocumentTypeCode' failed to initialize correctly", dod.getRandomClinicalDocumentTypeCode());
        long count = clinicalDocumentTypeCodeRepository.count();
        Assert.assertTrue("Counter for 'ClinicalDocumentTypeCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindClinicalDocumentTypeCode() {
        ClinicalDocumentTypeCode obj = dod.getRandomClinicalDocumentTypeCode();
        Assert.assertNotNull("Data on demand for 'ClinicalDocumentTypeCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ClinicalDocumentTypeCode' failed to provide an identifier", id);
        obj = clinicalDocumentTypeCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ClinicalDocumentTypeCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'ClinicalDocumentTypeCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllClinicalDocumentTypeCodes() {
        Assert.assertNotNull("Data on demand for 'ClinicalDocumentTypeCode' failed to initialize correctly", dod.getRandomClinicalDocumentTypeCode());
        long count = clinicalDocumentTypeCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'ClinicalDocumentTypeCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<ClinicalDocumentTypeCode> result = clinicalDocumentTypeCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'ClinicalDocumentTypeCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'ClinicalDocumentTypeCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindClinicalDocumentTypeCodeEntries() {
        Assert.assertNotNull("Data on demand for 'ClinicalDocumentTypeCode' failed to initialize correctly", dod.getRandomClinicalDocumentTypeCode());
        long count = clinicalDocumentTypeCodeRepository.count();
        if (count > 20) count = 20;
		int pageNumber = 0;
        int pageSize = 10;
        List<ClinicalDocumentTypeCode> result = clinicalDocumentTypeCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'ClinicalDocumentTypeCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'ClinicalDocumentTypeCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        ClinicalDocumentTypeCode obj = dod.getRandomClinicalDocumentTypeCode();
        Assert.assertNotNull("Data on demand for 'ClinicalDocumentTypeCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ClinicalDocumentTypeCode' failed to provide an identifier", id);
        obj = clinicalDocumentTypeCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ClinicalDocumentTypeCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyClinicalDocumentTypeCode(obj);
        Integer currentVersion = obj.getVersion();
        clinicalDocumentTypeCodeRepository.flush();
        Assert.assertTrue("Version for 'ClinicalDocumentTypeCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateClinicalDocumentTypeCodeUpdate() {
        ClinicalDocumentTypeCode obj = dod.getRandomClinicalDocumentTypeCode();
        Assert.assertNotNull("Data on demand for 'ClinicalDocumentTypeCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ClinicalDocumentTypeCode' failed to provide an identifier", id);
        obj = clinicalDocumentTypeCodeRepository.findOne(id);
        boolean modified =  dod.modifyClinicalDocumentTypeCode(obj);
        Integer currentVersion = obj.getVersion();
        ClinicalDocumentTypeCode merged = (ClinicalDocumentTypeCode)clinicalDocumentTypeCodeRepository.save(obj);
        clinicalDocumentTypeCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'ClinicalDocumentTypeCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveClinicalDocumentTypeCode() {
        Assert.assertNotNull("Data on demand for 'ClinicalDocumentTypeCode' failed to initialize correctly", dod.getRandomClinicalDocumentTypeCode());
        ClinicalDocumentTypeCode obj = dod.getNewTransientClinicalDocumentTypeCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'ClinicalDocumentTypeCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'ClinicalDocumentTypeCode' identifier to be null", obj.getId());
        clinicalDocumentTypeCodeRepository.save(obj);
        clinicalDocumentTypeCodeRepository.flush();
        Assert.assertNotNull("Expected 'ClinicalDocumentTypeCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteClinicalDocumentTypeCode() {
        ClinicalDocumentTypeCode obj = dod.getRandomClinicalDocumentTypeCode();
        Assert.assertNotNull("Data on demand for 'ClinicalDocumentTypeCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ClinicalDocumentTypeCode' failed to provide an identifier", id);
        obj = clinicalDocumentTypeCodeRepository.findOne(id);
        clinicalDocumentTypeCodeRepository.delete(obj);
        clinicalDocumentTypeCodeRepository.flush();
        Assert.assertNull("Failed to remove 'ClinicalDocumentTypeCode' with identifier '" + id + "'", clinicalDocumentTypeCodeRepository.findOne(id));
    }
}
