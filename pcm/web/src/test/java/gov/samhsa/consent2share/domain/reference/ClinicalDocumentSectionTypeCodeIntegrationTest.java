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
public class ClinicalDocumentSectionTypeCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    ClinicalDocumentSectionTypeCodeDataOnDemand dod;

	@Autowired
    ClinicalDocumentSectionTypeCodeRepository clinicalDocumentSectionTypeCodeRepository;

	@Test
    public void testCountAllClinicalDocumentSectionTypeCodes() {
        Assert.assertNotNull("Data on demand for 'ClinicalDocumentSectionTypeCode' failed to initialize correctly", dod.getRandomClinicalDocumentSectionTypeCode());
        long count = clinicalDocumentSectionTypeCodeRepository.count();
        Assert.assertTrue("Counter for 'ClinicalDocumentSectionTypeCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindClinicalDocumentSectionTypeCode() {
        ClinicalDocumentSectionTypeCode obj = dod.getRandomClinicalDocumentSectionTypeCode();
        Assert.assertNotNull("Data on demand for 'ClinicalDocumentSectionTypeCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ClinicalDocumentSectionTypeCode' failed to provide an identifier", id);
        obj = clinicalDocumentSectionTypeCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ClinicalDocumentSectionTypeCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'ClinicalDocumentSectionTypeCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllClinicalDocumentSectionTypeCodes() {
        Assert.assertNotNull("Data on demand for 'ClinicalDocumentSectionTypeCode' failed to initialize correctly", dod.getRandomClinicalDocumentSectionTypeCode());
        long count = clinicalDocumentSectionTypeCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'ClinicalDocumentSectionTypeCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<ClinicalDocumentSectionTypeCode> result = clinicalDocumentSectionTypeCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'ClinicalDocumentSectionTypeCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'ClinicalDocumentSectionTypeCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindClinicalDocumentSectionTypeCodeEntries() {
        Assert.assertNotNull("Data on demand for 'ClinicalDocumentSectionTypeCode' failed to initialize correctly", dod.getRandomClinicalDocumentSectionTypeCode());
        long count = clinicalDocumentSectionTypeCodeRepository.count();
        if (count > 20) count = 20;
		int pageNumber = 0;
        int pageSize = 10;
        List<ClinicalDocumentSectionTypeCode> result = clinicalDocumentSectionTypeCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'ClinicalDocumentSectionTypeCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'ClinicalDocumentSectionTypeCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        ClinicalDocumentSectionTypeCode obj = dod.getRandomClinicalDocumentSectionTypeCode();
        Assert.assertNotNull("Data on demand for 'ClinicalDocumentSectionTypeCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ClinicalDocumentSectionTypeCode' failed to provide an identifier", id);
        obj = clinicalDocumentSectionTypeCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ClinicalDocumentSectionTypeCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyClinicalDocumentSectionTypeCode(obj);
        Integer currentVersion = obj.getVersion();
        clinicalDocumentSectionTypeCodeRepository.flush();
        Assert.assertTrue("Version for 'ClinicalDocumentSectionTypeCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateClinicalDocumentSectionTypeCodeUpdate() {
        ClinicalDocumentSectionTypeCode obj = dod.getRandomClinicalDocumentSectionTypeCode();
        Assert.assertNotNull("Data on demand for 'ClinicalDocumentSectionTypeCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ClinicalDocumentSectionTypeCode' failed to provide an identifier", id);
        obj = clinicalDocumentSectionTypeCodeRepository.findOne(id);
        boolean modified =  dod.modifyClinicalDocumentSectionTypeCode(obj);
        Integer currentVersion = obj.getVersion();
        ClinicalDocumentSectionTypeCode merged = (ClinicalDocumentSectionTypeCode)clinicalDocumentSectionTypeCodeRepository.save(obj);
        clinicalDocumentSectionTypeCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'ClinicalDocumentSectionTypeCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveClinicalDocumentSectionTypeCode() {
        Assert.assertNotNull("Data on demand for 'ClinicalDocumentSectionTypeCode' failed to initialize correctly", dod.getRandomClinicalDocumentSectionTypeCode());
        ClinicalDocumentSectionTypeCode obj = dod.getNewTransientClinicalDocumentSectionTypeCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'ClinicalDocumentSectionTypeCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'ClinicalDocumentSectionTypeCode' identifier to be null", obj.getId());
        clinicalDocumentSectionTypeCodeRepository.save(obj);
        clinicalDocumentSectionTypeCodeRepository.flush();
        Assert.assertNotNull("Expected 'ClinicalDocumentSectionTypeCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteClinicalDocumentSectionTypeCode() {
        ClinicalDocumentSectionTypeCode obj = dod.getRandomClinicalDocumentSectionTypeCode();
        Assert.assertNotNull("Data on demand for 'ClinicalDocumentSectionTypeCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ClinicalDocumentSectionTypeCode' failed to provide an identifier", id);
        obj = clinicalDocumentSectionTypeCodeRepository.findOne(id);
        clinicalDocumentSectionTypeCodeRepository.delete(obj);
        clinicalDocumentSectionTypeCodeRepository.flush();
        Assert.assertNull("Failed to remove 'ClinicalDocumentSectionTypeCode' with identifier '" + id + "'", clinicalDocumentSectionTypeCodeRepository.findOne(id));
    }
}
