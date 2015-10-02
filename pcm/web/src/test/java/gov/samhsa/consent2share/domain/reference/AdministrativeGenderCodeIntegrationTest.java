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
public class AdministrativeGenderCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    AdministrativeGenderCodeDataOnDemand dod;

	@Autowired
    AdministrativeGenderCodeRepository administrativeGenderCodeRepository;

	@Test
    public void testCountAllAdministrativeGenderCodes() {
        Assert.assertNotNull("Data on demand for 'AdministrativeGenderCode' failed to initialize correctly", dod.getRandomAdministrativeGenderCode());
        long count = administrativeGenderCodeRepository.count();
        Assert.assertTrue("Counter for 'AdministrativeGenderCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindAdministrativeGenderCode() {
        AdministrativeGenderCode obj = dod.getRandomAdministrativeGenderCode();
        Assert.assertNotNull("Data on demand for 'AdministrativeGenderCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'AdministrativeGenderCode' failed to provide an identifier", id);
        obj = administrativeGenderCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'AdministrativeGenderCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'AdministrativeGenderCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllAdministrativeGenderCodes() {
        Assert.assertNotNull("Data on demand for 'AdministrativeGenderCode' failed to initialize correctly", dod.getRandomAdministrativeGenderCode());
        long count = administrativeGenderCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'AdministrativeGenderCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<AdministrativeGenderCode> result = administrativeGenderCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'AdministrativeGenderCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'AdministrativeGenderCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindAdministrativeGenderCodeEntries() {
        Assert.assertNotNull("Data on demand for 'AdministrativeGenderCode' failed to initialize correctly", dod.getRandomAdministrativeGenderCode());
        long count = administrativeGenderCodeRepository.count();
        if (count > 20) count = 20;
		int pageNumber = 0;
        int pageSize = 10;
        List<AdministrativeGenderCode> result = administrativeGenderCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'AdministrativeGenderCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'AdministrativeGenderCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        AdministrativeGenderCode obj = dod.getRandomAdministrativeGenderCode();
        Assert.assertNotNull("Data on demand for 'AdministrativeGenderCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'AdministrativeGenderCode' failed to provide an identifier", id);
        obj = administrativeGenderCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'AdministrativeGenderCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyAdministrativeGenderCode(obj);
        Integer currentVersion = obj.getVersion();
        administrativeGenderCodeRepository.flush();
        Assert.assertTrue("Version for 'AdministrativeGenderCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateAdministrativeGenderCodeUpdate() {
        AdministrativeGenderCode obj = dod.getRandomAdministrativeGenderCode();
        Assert.assertNotNull("Data on demand for 'AdministrativeGenderCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'AdministrativeGenderCode' failed to provide an identifier", id);
        obj = administrativeGenderCodeRepository.findOne(id);
        boolean modified =  dod.modifyAdministrativeGenderCode(obj);
        Integer currentVersion = obj.getVersion();
        AdministrativeGenderCode merged = (AdministrativeGenderCode)administrativeGenderCodeRepository.save(obj);
        administrativeGenderCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'AdministrativeGenderCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveAdministrativeGenderCode() {
        Assert.assertNotNull("Data on demand for 'AdministrativeGenderCode' failed to initialize correctly", dod.getRandomAdministrativeGenderCode());
        AdministrativeGenderCode obj = dod.getNewTransientAdministrativeGenderCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'AdministrativeGenderCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'AdministrativeGenderCode' identifier to be null", obj.getId());
        administrativeGenderCodeRepository.save(obj);
        administrativeGenderCodeRepository.flush();
        Assert.assertNotNull("Expected 'AdministrativeGenderCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteAdministrativeGenderCode() {
        AdministrativeGenderCode obj = dod.getRandomAdministrativeGenderCode();
        Assert.assertNotNull("Data on demand for 'AdministrativeGenderCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'AdministrativeGenderCode' failed to provide an identifier", id);
        obj = administrativeGenderCodeRepository.findOne(id);
        administrativeGenderCodeRepository.delete(obj);
        administrativeGenderCodeRepository.flush();
        Assert.assertNull("Failed to remove 'AdministrativeGenderCode' with identifier '" + id + "'", administrativeGenderCodeRepository.findOne(id));
    }
}
