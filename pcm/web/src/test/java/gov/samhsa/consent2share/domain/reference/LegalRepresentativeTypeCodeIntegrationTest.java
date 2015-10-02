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
public class LegalRepresentativeTypeCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    LegalRepresentativeTypeCodeDataOnDemand dod;

	@Autowired
    LegalRepresentativeTypeCodeRepository legalRepresentativeTypeCodeRepository;

	@Test
    public void testCountAllLegalRepresentativeTypeCodes() {
        Assert.assertNotNull("Data on demand for 'LegalRepresentativeTypeCode' failed to initialize correctly", dod.getRandomLegalRepresentativeTypeCode());
        long count = legalRepresentativeTypeCodeRepository.count();
        Assert.assertTrue("Counter for 'LegalRepresentativeTypeCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindLegalRepresentativeTypeCode() {
        LegalRepresentativeTypeCode obj = dod.getRandomLegalRepresentativeTypeCode();
        Assert.assertNotNull("Data on demand for 'LegalRepresentativeTypeCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'LegalRepresentativeTypeCode' failed to provide an identifier", id);
        obj = legalRepresentativeTypeCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'LegalRepresentativeTypeCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'LegalRepresentativeTypeCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllLegalRepresentativeTypeCodes() {
        Assert.assertNotNull("Data on demand for 'LegalRepresentativeTypeCode' failed to initialize correctly", dod.getRandomLegalRepresentativeTypeCode());
        long count = legalRepresentativeTypeCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'LegalRepresentativeTypeCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<LegalRepresentativeTypeCode> result = legalRepresentativeTypeCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'LegalRepresentativeTypeCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'LegalRepresentativeTypeCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindLegalRepresentativeTypeCodeEntries() {
        Assert.assertNotNull("Data on demand for 'LegalRepresentativeTypeCode' failed to initialize correctly", dod.getRandomLegalRepresentativeTypeCode());
        long count = legalRepresentativeTypeCodeRepository.count();
        if (count > 20) count = 20;
		int pageNumber = 0;
        int pageSize = 10;
        List<LegalRepresentativeTypeCode> result = legalRepresentativeTypeCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'LegalRepresentativeTypeCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'LegalRepresentativeTypeCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        LegalRepresentativeTypeCode obj = dod.getRandomLegalRepresentativeTypeCode();
        Assert.assertNotNull("Data on demand for 'LegalRepresentativeTypeCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'LegalRepresentativeTypeCode' failed to provide an identifier", id);
        obj = legalRepresentativeTypeCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'LegalRepresentativeTypeCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyLegalRepresentativeTypeCode(obj);
        Integer currentVersion = obj.getVersion();
        legalRepresentativeTypeCodeRepository.flush();
        Assert.assertTrue("Version for 'LegalRepresentativeTypeCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateLegalRepresentativeTypeCodeUpdate() {
        LegalRepresentativeTypeCode obj = dod.getRandomLegalRepresentativeTypeCode();
        Assert.assertNotNull("Data on demand for 'LegalRepresentativeTypeCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'LegalRepresentativeTypeCode' failed to provide an identifier", id);
        obj = legalRepresentativeTypeCodeRepository.findOne(id);
        boolean modified =  dod.modifyLegalRepresentativeTypeCode(obj);
        Integer currentVersion = obj.getVersion();
        LegalRepresentativeTypeCode merged = (LegalRepresentativeTypeCode)legalRepresentativeTypeCodeRepository.save(obj);
        legalRepresentativeTypeCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'LegalRepresentativeTypeCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveLegalRepresentativeTypeCode() {
        Assert.assertNotNull("Data on demand for 'LegalRepresentativeTypeCode' failed to initialize correctly", dod.getRandomLegalRepresentativeTypeCode());
        LegalRepresentativeTypeCode obj = dod.getNewTransientLegalRepresentativeTypeCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'LegalRepresentativeTypeCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'LegalRepresentativeTypeCode' identifier to be null", obj.getId());
        legalRepresentativeTypeCodeRepository.save(obj);
        legalRepresentativeTypeCodeRepository.flush();
        Assert.assertNotNull("Expected 'LegalRepresentativeTypeCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteLegalRepresentativeTypeCode() {
        LegalRepresentativeTypeCode obj = dod.getRandomLegalRepresentativeTypeCode();
        Assert.assertNotNull("Data on demand for 'LegalRepresentativeTypeCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'LegalRepresentativeTypeCode' failed to provide an identifier", id);
        obj = legalRepresentativeTypeCodeRepository.findOne(id);
        legalRepresentativeTypeCodeRepository.delete(obj);
        legalRepresentativeTypeCodeRepository.flush();
        Assert.assertNull("Failed to remove 'LegalRepresentativeTypeCode' with identifier '" + id + "'", legalRepresentativeTypeCodeRepository.findOne(id));
    }
}
