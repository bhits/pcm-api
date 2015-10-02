package gov.samhsa.consent2share.domain.clinicaldata;

import gov.samhsa.consent2share.service.clinicaldata.ClinicalDocumentService;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@Transactional
@ActiveProfiles("test")
@Configurable
public class ClinicalDocumentIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    ClinicalDocumentDataOnDemand dod;

	@Autowired
    ClinicalDocumentService clinicalDocumentService;

	@Autowired
    ClinicalDocumentRepository clinicalDocumentRepository;

	@Test
    public void testCountAllClinicalDocuments() {
        Assert.assertNotNull("Data on demand for 'ClinicalDocument' failed to initialize correctly", dod.getRandomClinicalDocument());
        long count = clinicalDocumentService.countAllClinicalDocuments();
        Assert.assertTrue("Counter for 'ClinicalDocument' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindClinicalDocument() {
        ClinicalDocument obj = dod.getRandomClinicalDocument();
        Assert.assertNotNull("Data on demand for 'ClinicalDocument' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ClinicalDocument' failed to provide an identifier", id);
        obj = clinicalDocumentService.findClinicalDocument(id);
        Assert.assertNotNull("Find method for 'ClinicalDocument' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'ClinicalDocument' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllClinicalDocuments() {
        Assert.assertNotNull("Data on demand for 'ClinicalDocument' failed to initialize correctly", dod.getRandomClinicalDocument());
        long count = clinicalDocumentService.countAllClinicalDocuments();
        Assert.assertTrue("Too expensive to perform a find all test for 'ClinicalDocument', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<ClinicalDocument> result = clinicalDocumentService.findAllClinicalDocuments();
        Assert.assertNotNull("Find all method for 'ClinicalDocument' illegally returned null", result);
        Assert.assertTrue("Find all method for 'ClinicalDocument' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindClinicalDocumentEntries() {
        Assert.assertNotNull("Data on demand for 'ClinicalDocument' failed to initialize correctly", dod.getRandomClinicalDocument());
        long count = clinicalDocumentService.countAllClinicalDocuments();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<ClinicalDocument> result = clinicalDocumentService.findClinicalDocumentEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'ClinicalDocument' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'ClinicalDocument' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        ClinicalDocument obj = dod.getRandomClinicalDocument();
        Assert.assertNotNull("Data on demand for 'ClinicalDocument' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ClinicalDocument' failed to provide an identifier", id);
        obj = clinicalDocumentService.findClinicalDocument(id);
        Assert.assertNotNull("Find method for 'ClinicalDocument' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyClinicalDocument(obj);
        Integer currentVersion = obj.getVersion();
        clinicalDocumentRepository.flush();
        Assert.assertTrue("Version for 'ClinicalDocument' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateClinicalDocumentUpdate() {
        ClinicalDocument obj = dod.getRandomClinicalDocument();
        Assert.assertNotNull("Data on demand for 'ClinicalDocument' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ClinicalDocument' failed to provide an identifier", id);
        obj = clinicalDocumentService.findClinicalDocument(id);
        boolean modified =  dod.modifyClinicalDocument(obj);
        Integer currentVersion = obj.getVersion();
        ClinicalDocument merged = clinicalDocumentService.updateClinicalDocument(obj);
        clinicalDocumentRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'ClinicalDocument' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveClinicalDocument() {
        Assert.assertNotNull("Data on demand for 'ClinicalDocument' failed to initialize correctly", dod.getRandomClinicalDocument());
        ClinicalDocument obj = dod.getNewTransientClinicalDocument(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'ClinicalDocument' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'ClinicalDocument' identifier to be null", obj.getId());
        clinicalDocumentRepository.save(obj);
//        clinicalDocumentRepository.flush();
        Assert.assertNotNull("Expected 'ClinicalDocument' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteClinicalDocument() {
        ClinicalDocument obj = dod.getRandomClinicalDocument();
        Assert.assertNotNull("Data on demand for 'ClinicalDocument' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ClinicalDocument' failed to provide an identifier", id);
        obj = clinicalDocumentService.findClinicalDocument(id);
        clinicalDocumentRepository.delete(obj);
        clinicalDocumentRepository.flush();
        Assert.assertNull("Failed to remove 'ClinicalDocument' with identifier '" + id + "'", clinicalDocumentService.findClinicalDocument(id));
    }
}
